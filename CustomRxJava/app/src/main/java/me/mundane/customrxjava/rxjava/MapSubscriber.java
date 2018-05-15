package me.mundane.customrxjava.rxjava;

import me.mundane.customrxjava.rxjava.Observable.Transformer;

/**
 * Created by mundane on 2018/4/15 下午6:19
 */

public class MapSubscriber<T, R> extends Subscriber<T> {
    // 这里的泛型换成<R, T>还是<T, R>好像都没有关系

    Subscriber<? super R> actual;

    Observable.Transformer<? super T, ? extends R> transformer;

    public MapSubscriber(Subscriber<? super R> actual,
            Transformer<? super T, ? extends R> transformer) {
        this.actual = actual;
        this.transformer = transformer;
    }

    @Override
    public void onCompleted() {
        actual.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        actual.onError(e);
    }

    @Override
    public void onNext(T t) {
        actual.onNext(transformer.call(t));
    }
}
