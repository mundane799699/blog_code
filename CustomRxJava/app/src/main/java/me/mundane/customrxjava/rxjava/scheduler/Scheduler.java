package me.mundane.customrxjava.rxjava.scheduler;

/**
 * Created by mundane on 2018/4/15 下午6:49
 */

public abstract class Scheduler {

    public abstract Workder createWorkder();

    public abstract static class Workder {

        public abstract void schedule(Runnable runnable);
    }
}
