package me.mundane.customrxjava;

import me.mundane.customrxjava.rxjava.Observable.OnSubscribe;
import me.mundane.customrxjava.rxjava.Subscriber;

/**
 * Created by mundane on 2018/4/16 下午8:52
 */

public class OnSubscribeLift<T, R> implements OnSubscribe<R> {

    final OnSubscribe<T> parent;
    final Operator<? extends R, ? super T> operator;

    public OnSubscribeLift(OnSubscribe<T> parent, Operator<? extends R, ? super T> operator) {
        this.parent = parent;
        this.operator = operator;
    }

    @Override
    public void call(Subscriber<? super R> r) {
        Subscriber<? super T> st = operator.call(r);
        parent.call(st);
    }
}
