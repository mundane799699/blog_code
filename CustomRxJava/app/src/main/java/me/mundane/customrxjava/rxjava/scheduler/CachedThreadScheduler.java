package me.mundane.customrxjava.rxjava.scheduler;

import java.util.concurrent.Executor;

/**
 * Created by mundane on 2018/4/15 下午10:27
 */

public class CachedThreadScheduler extends Scheduler {

    Executor executor;

    public CachedThreadScheduler(Executor executor) {
        this.executor = executor;
    }
    @Override
    public Workder createWorkder() {
        return new EventLoopWorker(executor);
    }
}
