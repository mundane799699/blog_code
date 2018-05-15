package me.mundane.customrxjava;

import me.mundane.customrxjava.rxjava.Observable.Transformer;
import me.mundane.customrxjava.rxjava.Subscriber;

/**
 * Created by mundane on 2018/4/16 下午8:57
 */
// 从Subscriber<? super T>类型转换成了Subscriber<? super R>类型
public interface Operator<R, T> extends Transformer<Subscriber<? super R>, Subscriber<? super T>> {
}
