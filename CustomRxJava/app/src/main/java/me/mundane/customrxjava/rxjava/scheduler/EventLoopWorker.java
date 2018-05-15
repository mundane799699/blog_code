package me.mundane.customrxjava.rxjava.scheduler;

import java.util.concurrent.Executor;
import me.mundane.customrxjava.rxjava.scheduler.Scheduler.Workder;

/**
 * Created by mundane on 2018/4/15 下午10:22
 */

public class EventLoopWorker extends Workder {

    Executor executor;

    public EventLoopWorker(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void schedule(Runnable runnable) {
        executor.execute(runnable);
    }
}
