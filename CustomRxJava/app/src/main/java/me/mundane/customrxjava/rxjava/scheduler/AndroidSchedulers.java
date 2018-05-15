package me.mundane.customrxjava.rxjava.scheduler;

import android.os.Looper;

/**
 * Created by mundane on 2018/4/15 下午10:06
 */

public class AndroidSchedulers {

    private static final Scheduler mainThreadScheduler =
            new LooperScheduler(Looper.getMainLooper());

    public static Scheduler mainThread() {
        return mainThreadScheduler;
    }

}
