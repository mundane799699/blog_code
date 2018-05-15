package me.mundane.myrxjavasummary.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.mundane.myrxjavasummary.ProxyObservable;
import me.mundane.myrxjavasummary.ProxyObservable.OnSubscribeListener;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 用于管理RxBus的事件和Rxjava相关代码的生命周期处理
 * Created by baixiaokang on 16/4/28.
 */
public class RxManager implements OnSubscribeListener {

    private Map<String, Observable<?>> mObservables = new HashMap<>();// 管理观察者
    private List<ProxyObservable> mObservableList = new ArrayList<>();
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();// 管理订阅者者

    public ProxyObservable register(Observable observable) {
        ProxyObservable delegate = new ProxyObservable(observable);
        delegate.setOnSubscribeListener(this);
        return delegate;
    }

    public void clear() {
        mCompositeSubscription.unsubscribe();// 取消订阅
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        mCompositeSubscription.add(subscription);
    }
}
