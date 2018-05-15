package me.mundane.customrxjava.rxjava.scheduler;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by mundane on 2018/4/15 下午10:07
 */

public class LooperScheduler extends Scheduler{
    private Handler handler;

    LooperScheduler(Looper looper) {
        this.handler = new Handler(looper);
    }

    public LooperScheduler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public Workder createWorkder() {
        return new HandlerWorker(handler);
    }

    static class HandlerWorker extends Workder {

        private final Handler handler;

        public HandlerWorker(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void schedule(Runnable runnable) {
            handler.post(runnable);
        }
    }
}
