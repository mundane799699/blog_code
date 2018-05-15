package me.mundane.customrxjava.rxjava;

/**
 * Created by mundane on 2018/4/14 下午8:21
 * 观察者
 */

public interface Observer<T> {
    void onCompleted();

    void onError(Throwable e);

    void onNext(T t);

}
