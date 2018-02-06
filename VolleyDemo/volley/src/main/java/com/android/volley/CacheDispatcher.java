/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.volley;

import android.os.Process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Provides a thread for performing cache triage on a queue of requests.
 *
 * Requests added to the specified cache queue are resolved from cache.
 * Any deliverable response is posted back to the caller via a
 * {@link ResponseDelivery}.  Cache misses and responses that require
 * refresh are enqueued on the specified network queue for processing
 * by a {@link NetworkDispatcher}.
 */
public class CacheDispatcher extends Thread {

    private static final boolean DEBUG = VolleyLog.DEBUG;

    /** The queue of requests coming in for triage. */
    private final BlockingQueue<Request<?>> mCacheQueue;

    /** The queue of requests going out to the network. */
    private final BlockingQueue<Request<?>> mNetworkQueue;

    /** The cache to read from. */
    private final Cache mCache;

    /** For posting responses. */
    private final ResponseDelivery mDelivery;

    /** Used for telling us to die. */
    private volatile boolean mQuit = false;

    /** Manage list of waiting requests and de-duplicate requests with same cache key. */
    private final WaitingRequestManager mWaitingRequestManager;

    /**
     * Creates a new cache triage dispatcher thread.  You must call {@link #start()}
     * in order to begin processing.
     *
     * @param cacheQueue Queue of incoming requests for triage
     * @param networkQueue Queue to post requests that require network to
     * @param cache Cache interface to use for resolution
     * @param delivery Delivery interface to use for posting responses
     */
    public CacheDispatcher(
            BlockingQueue<Request<?>> cacheQueue, BlockingQueue<Request<?>> networkQueue,
            Cache cache, ResponseDelivery delivery) {
        mCacheQueue = cacheQueue;
        mNetworkQueue = networkQueue;
        mCache = cache;
        mDelivery = delivery;
        mWaitingRequestManager = new WaitingRequestManager(this);
    }

    /**
     * Forces this dispatcher to quit immediately.  If any requests are still in
     * the queue, they are not guaranteed to be processed.
     */
    public void quit() {
        mQuit = true;
        interrupt();
    }

    @Override
    public void run() {
        if (DEBUG) VolleyLog.v("start new dispatcher");
        // 设置线程的优先级为background
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        // Make a blocking call to initialize the cache.
        // 初始化缓存目录
        mCache.initialize();

        while (true) {
            try {
                processRequest();
            } catch (InterruptedException e) {
                // We may have been interrupted because it was time to quit.
                if (mQuit) {
                    return;
                }
            }
        }
    }

    // Extracted to its own method to ensure locals have a constrained liveness scope by the GC.
    // This is needed to avoid keeping previous request references alive for an indeterminate amount
    // of time. Update consumer-proguard-rules.pro when modifying this. See also
    // https://github.com/google/volley/issues/114

    // 将这段方法抽取出来来确保本地变量有一个被约束的范围
    private void processRequest() throws InterruptedException {
        // Get a request from the cache triage queue, blocking until
        // at least one is available.
        // 从缓存队列中取出request, 这段过程会阻塞, 直到获得了一个请求
        final Request<?> request = mCacheQueue.take();
        request.addMarker("cache-queue-take");

        // If the request has been canceled, don't bother dispatching it.
        // 如果一个request被取消了, 不用费心思去分发它了(直接干掉它)
        if (request.isCanceled()) {
            request.finish("cache-discard-canceled");
            return;
        }

        // Attempt to retrieve this item from cache.
        // 通过request的url尝试去获取缓存
        // attention 这个方法需要进去看一下
        Cache.Entry entry = mCache.get(request.getCacheKey());
        // 如果获取缓存失败(miss),
        if (entry == null) {
            request.addMarker("cache-miss");
            // Cache miss; send off to the network dispatcher.
            // 如果waittingRequests中还没有这个request, 将它加入网络队列中
            if (!mWaitingRequestManager.maybeAddToWaitingRequests(request)) {
                mNetworkQueue.put(request);
            }
            return;
        }

        // If it is completely expired, just send it to the network.
        // 如果缓存过期了
        if (entry.isExpired()) {
            // 将这个request标记为"缓存命中, 但是过期"
            request.addMarker("cache-hit-expired");
            // 给这个request设置缓存
            request.setCacheEntry(entry);
            // 如果缓存过期了, waittingRequests中还没有这个request, 还是需要请求网络
            if (!mWaitingRequestManager.maybeAddToWaitingRequests(request)) {
                mNetworkQueue.put(request);
            }
            return;
        }

        // We have a cache hit; parse its data for delivery back to the request.
        // 我们在这里命中了缓存(并且没有过期), 解析它的数据并回传给request
        // 添加标记"缓存命中"
        request.addMarker("cache-hit");
        // 解析response
        Response<?> response = request.parseNetworkResponse(
                new NetworkResponse(entry.data, entry.responseHeaders));
        // 添加标记"缓存命中并且已经被解析"
        request.addMarker("cache-hit-parsed");

        // 如果缓存不需要刷新, 直接发送响应即可
        if (!entry.refreshNeeded()) {
            // Completely unexpired cache hit. Just deliver the response.
            mDelivery.postResponse(request, response);
        } else {
            // Soft-expired cache hit. We can deliver the cached response,
            // but we need to also send the request to the network for
            // refreshing.

            // 缓存没有过期, 但是需要更新.
            // 我们分发这个缓存, 但我们也需要把这个request发送到网络来刷新它

            // 添加标记"缓存命中但是需要刷新"
            request.addMarker("cache-hit-refresh-needed");
            // 给request设置缓存
            request.setCacheEntry(entry);
            // Mark the response as intermediate.
            // 将这个response标记为过渡的
            response.intermediate = true;

            // 如果waittingRequests中没有这个request
            if (!mWaitingRequestManager.maybeAddToWaitingRequests(request)) {
                // Post the intermediate response back to the user and have
                // the delivery then forward the request along to the network.

                // 将这个过渡的response返回给用户, 并且放到网络请求的队列中
                mDelivery.postResponse(request, response, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mNetworkQueue.put(request);
                        } catch (InterruptedException e) {
                            // Restore the interrupted status
                            Thread.currentThread().interrupt();
                        }
                    }
                });
            } else {
                // request has been added to list of waiting requests
                // to receive the network response from the first request once it returns.

                // request已经被添加到waittingRequest中
                // 来接收返回的第一个网络响应
                mDelivery.postResponse(request, response);
            }
        }
    }

    private static class WaitingRequestManager implements Request.NetworkRequestCompleteListener {

        /**
         * Staging area for requests that already have a duplicate request in flight.
         *
         * <ul>
         *     <li>containsKey(cacheKey) indicates that there is a request in flight for the given cache
         *          key.</li>
         *     <li>get(cacheKey) returns waiting requests for the given cache key. The in flight request
         *          is <em>not</em> contained in that list. Is null if no requests are staged.</li>
         * </ul>
         */
        private final Map<String, List<Request<?>>> mWaitingRequests = new HashMap<>();

        private final CacheDispatcher mCacheDispatcher;

        WaitingRequestManager(CacheDispatcher cacheDispatcher) {
            mCacheDispatcher = cacheDispatcher;
        }

        /** Request received a valid response that can be used by other waiting requests. */
        @Override
        public void onResponseReceived(Request<?> request, Response<?> response) {
            if (response.cacheEntry == null || response.cacheEntry.isExpired()) {
                onNoUsableResponseReceived(request);
                return;
            }
            String cacheKey = request.getCacheKey();
            List<Request<?>> waitingRequests;
            synchronized (this) {
                waitingRequests = mWaitingRequests.remove(cacheKey);
            }
            if (waitingRequests != null) {
                if (VolleyLog.DEBUG) {
                    VolleyLog.v("Releasing %d waiting requests for cacheKey=%s.",
                            waitingRequests.size(), cacheKey);
                }
                // Process all queued up requests.
                for (Request<?> waiting : waitingRequests) {
                    mCacheDispatcher.mDelivery.postResponse(waiting, response);
                }
            }
        }

        /** No valid response received from network, release waiting requests. */
        @Override
        public synchronized void onNoUsableResponseReceived(Request<?> request) {
            String cacheKey = request.getCacheKey();
            List<Request<?>> waitingRequests = mWaitingRequests.remove(cacheKey);
            if (waitingRequests != null && !waitingRequests.isEmpty()) {
                if (VolleyLog.DEBUG) {
                    VolleyLog.v("%d waiting requests for cacheKey=%s; resend to network",
                            waitingRequests.size(), cacheKey);
                }
                Request<?> nextInLine = waitingRequests.remove(0);
                mWaitingRequests.put(cacheKey, waitingRequests);
                nextInLine.setNetworkRequestCompleteListener(this);
                try {
                    mCacheDispatcher.mNetworkQueue.put(nextInLine);
                } catch (InterruptedException iex) {
                    VolleyLog.e("Couldn't add request to queue. %s", iex.toString());
                    // Restore the interrupted status of the calling thread (i.e. NetworkDispatcher)
                    Thread.currentThread().interrupt();
                    // Quit the current CacheDispatcher thread.
                    mCacheDispatcher.quit();
                }
            }
        }

        /**
         * For cacheable requests, if a request for the same cache key is already in flight,
         * add it to a queue to wait for that in-flight request to finish.
         * @return whether the request was queued. If false, we should continue issuing the request
         * over the network. If true, we should put the request on hold to be processed when
         * the in-flight request finishes.
         */
        private synchronized boolean maybeAddToWaitingRequests(Request<?> request) {
            String cacheKey = request.getCacheKey();
            // Insert request into stage if there's already a request with the same cache key
            // in flight.
            if (mWaitingRequests.containsKey(cacheKey)) {
                // There is already a request in flight. Queue up.
                List<Request<?>> stagedRequests = mWaitingRequests.get(cacheKey);
                if (stagedRequests == null) {
                    stagedRequests = new ArrayList<Request<?>>();
                }
                request.addMarker("waiting-for-response");
                stagedRequests.add(request);
                mWaitingRequests.put(cacheKey, stagedRequests);
                if (VolleyLog.DEBUG) {
                    VolleyLog.d("Request for cacheKey=%s is in flight, putting on hold.", cacheKey);
                }
                return true;
            } else {
                // Insert 'null' queue for this cacheKey, indicating there is now a request in
                // flight.
                mWaitingRequests.put(cacheKey, null);
                request.setNetworkRequestCompleteListener(this);
                if (VolleyLog.DEBUG) {
                    VolleyLog.d("new request, sending to network %s", cacheKey);
                }
                return false;
            }
        }
    }
}
