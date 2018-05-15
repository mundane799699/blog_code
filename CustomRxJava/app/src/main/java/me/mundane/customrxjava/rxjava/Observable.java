package me.mundane.customrxjava.rxjava;

import me.mundane.customrxjava.OnSubscribeLift;
import me.mundane.customrxjava.Operator;
import me.mundane.customrxjava.OperatorMerge;
import me.mundane.customrxjava.rxjava.scheduler.Scheduler;
import me.mundane.customrxjava.rxjava.scheduler.Scheduler.Workder;

/**
 * Created by mundane on 2018/4/14 下午8:26
 * 被观察者
 */

public class Observable<T> {
    public interface OnSubscribe<T> {
        void call(Subscriber<? super T> subscriber);
    }

    OnSubscribe<T> onSubscribe;

    private Observable(OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new Observable<>(onSubscribe);
    }

    public void subscribe(Subscriber<? super T> subscriber) {
        subscriber.onStart();
        onSubscribe.call(subscriber);
    }

    public <R> Observable<R> map(Transformer<? super T, ? extends R> transformer) {
        return create(new OnSubscribeMap(this, transformer));
    }

    public static <T> Observable<T> merge(Observable<? extends Observable<? extends T>> source) {
        return source.lift(new OperatorMerge<T>());
    }

    // R: String, T: Integer
    // 当lift变换之后的observable被订阅的时候, OnSubscribeLift里面的call方法会被调用, call调用的参数是R类型也就是String类型的subscriber
    // OnSubscribeLift里面的call方法会先调用operator的call方法, 返回一个new出来的Integer类型的subscriber
    // 注意这个new出来的的Integer类型的subscriber的onNext()方法调用了最后订阅的那个String类型的subscriber的onNext()方法
    // 然后调用原始的onSubscribe比如Integer类型的onSubscribe的call方法, 调用参数是那个new出来的Integer类型的subscriber
    // 在原始的onSubscribe的call会调用Integer类型的subscriber的onNext()方法
    // 它的onNext()方法又会调用String类型的subscriber的onNext()方法
    // 也就是最终订阅的subscriber的onNext()方法
    public final <R> Observable<R> lift(final Operator<? extends R, ? super T> operator) {
        return create(new OnSubscribeLift<T, R>(onSubscribe, operator));
    }

    public Observable<T> subscribeOn(final Scheduler scheduler) {
        // subscribe的时候首先会调用subscriber的onStart()方法
        // 然后会调用这里的call方法(见下面)
        return create(new OperatorSubscribeOn(this, scheduler));
    }

    // todo 有待重构
    public Observable<T> observeOn(final Scheduler scheduler) {
        // subscribe的时候会调用onSubscribe的call方法, 也就是这里的call方法
        // 这里的call方法会调用原来的Observable里面的onSubscribe的call方法
        // 然后这里的call方法传入的不是最终subscribe的subscriber
        // 而是一个新的subscriber, 这个subscriber的各个方法都被重写了,
        // 在新的线程里调用最终subscribe传入的subscriber的各个方法
        // 这样就将事件的消费转移到了新的线程

        // 比如发射事件调用subscriber.onNext(i)的时候(其实subscriber这时候已经是这里new出来的这个)会先将i事件传入,
        // 调用的其实是下面1处新的subscriber重写后的onNext()方法
        return create(new OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                final Workder workder = scheduler.createWorkder();
                Observable.this.onSubscribe.call(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        workder.schedule(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onCompleted();
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        workder.schedule(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onError(e);
                            }
                        });
                    }

                    // 1
                    @Override
                    public void onNext(final T t) {
                        workder.schedule(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onNext(t);
                            }
                        });
                    }
                });
            }
        });
    }

    // 重构之前的subscribeOn
    private Observable<T> subscribeOn1(final Scheduler scheduler) {
        // subscribe的时候首先会调用subscriber的onStart()方法
        // 然后会调用这里的call方法(见下面)
        return create(new OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                // 这里的call方法
                // 切换到新的线程之后开始调用原来的observable的call方法
                scheduler.createWorkder().schedule(new Runnable() {
                    @Override
                    public void run() {
                        Observable.this.onSubscribe.call(subscriber);
                        // 不能用Observable.this.subscribe(subscriber);
                        // 因为这样写的话subscriber的onstart就会被调用两次
                        // 因为有两次subscribe的过程
                    }
                });
            }
        });
    }

    // 重构之前的map
    // T ---> R
    private <R> Observable<R> map1(final Transformer<? super T, ? extends R> transformer) {
        // 在内部传入了一个桥接的OnSubscribe, 在这个OnSubscribe的call方法里进行特殊处理
        return create(new OnSubscribe<R>() {
            @Override
            public void call(final Subscriber<? super R> subscriber) {
                // 变换之后又会传入一个R类型的subscriber
                // 被订阅的时候它里面的call方法会被调用, 也就是这里的call方法
                // 注意这里的call方法不是直接去调用subscriber的onNext()方法了
                // 这里又会生成一个新的subscriber然后订阅
                // 然后这个Observalbe<T>对象内部的onSubscribe的call方法又会被调用
                // 注意这里是Observable<T>.create(onSubscribe)中的onSubscribe的call方法会被调用
                // 也就是一开始创建Observable<T>的onSubscribe中call方法会被调用
                // 调用的参数subscriber已经变成了这里最新传入进去的subscriber
                // 然后Observalbe<R>的subscriber的onNext()方法才会被调用
                Observable.this.subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(T t) {
                        subscriber.onNext(transformer.call(t));
                    }
                });
            }
        });
    }

    public interface Transformer<T, R> {
        // 从T类型转换成了R类型
        R call(T t);
    }
}
