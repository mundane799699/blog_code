package me.mundane.myrxjavasummary;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by mundane on 2018/3/21 下午2:56
 */

public class ProxyObservable<T> {
    private Observable<T> mDelegateObservable;

    public interface OnSubscribeListener{
        void onSubscribe(Subscription subscription);
    }

    private OnSubscribeListener mOnSubscribeListener;

    public void setOnSubscribeListener(OnSubscribeListener onSubscribeListener) {
        this.mOnSubscribeListener = onSubscribeListener;
    }

    public ProxyObservable(Observable<T> observable) {
        mDelegateObservable = observable;
    }

    public final Subscription subscribe(Subscriber<? super T> subscriber) {
        Subscription subscription = mDelegateObservable.subscribe(subscriber);
        if (mOnSubscribeListener != null) {
            mOnSubscribeListener.onSubscribe(subscription);
        }
        return subscription;
    }
}
