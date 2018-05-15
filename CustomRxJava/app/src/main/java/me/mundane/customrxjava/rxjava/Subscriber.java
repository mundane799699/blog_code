package me.mundane.customrxjava.rxjava;

/**
 * Created by mundane on 2018/4/14 下午8:23
 * 订阅者, 实现了观察者
 */

public abstract class Subscriber<T> implements Observer<T> {
    public void onStart() {
    }
}
