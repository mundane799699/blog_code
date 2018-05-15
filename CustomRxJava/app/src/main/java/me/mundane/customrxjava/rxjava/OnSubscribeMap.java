package me.mundane.customrxjava.rxjava;

import me.mundane.customrxjava.rxjava.Observable.Transformer;

/**
 * Created by mundane on 2018/4/15 下午6:15
 */

public final class OnSubscribeMap<T, R> implements Observable.OnSubscribe<R> {
    Observable<T> source;
    Observable.Transformer<? super T, ? extends R> transformer;

    public OnSubscribeMap(Observable<T> source, Transformer<? super T, ? extends R> transformer) {
        this.source = source;
        this.transformer = transformer;
    }

    @Override
    public void call(Subscriber<? super R> subscriber) {
        source.subscribe(new MapSubscriber<T, R>(subscriber, transformer));
    }
}
