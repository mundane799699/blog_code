package me.mundane.customrxjava.rxjava;

import me.mundane.customrxjava.rxjava.scheduler.Scheduler;

/**
 * Created by mundane on 2018/4/15 下午9:03
 */

public final class OperatorSubscribeOn<T> implements Observable.OnSubscribe<T> {

    Observable<T> source;
    Scheduler scheduler;

    public OperatorSubscribeOn(Observable<T> source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    public void call(final Subscriber<? super T> subscriber) {

        scheduler.createWorkder().schedule(new Runnable() {
            @Override
            public void run() {
                source.onSubscribe.call(subscriber);
            }
        });
    }
}
