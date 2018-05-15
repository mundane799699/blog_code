package me.mundane.customrxjava.rxjava.scheduler;

import java.util.concurrent.Executors;

/**
 * Created by mundane on 2018/4/15 下午6:50
 */

public final class Schedulers {
    private static final Scheduler ioScheduler = new CachedThreadScheduler(Executors.newSingleThreadExecutor());

    public static Scheduler io() {
        return ioScheduler;
    }
}
