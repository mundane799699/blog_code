package me.mundane.parallaxrecyclerview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by mundane on 2018/1/23 下午6:01
 */

public class BaseActivity extends AppCompatActivity {
    // mRetrofit.create(AddressAPI.class).getAddress("js")
    // 通常, mRetrofit.create(AddressAPI.class)这一段是包装起来的
    // 之前的做法是addSubscription(ApiService.deviceList(...))这样子
    // 现在的想法是在baseActivity里一开始就创建一个requestQueue,
    // requestQueue.register(observable).subscribe(subscriber)
    // 将所有的Subscription添加到一个CompositeSubscription里
    // activity在ondestroy的时候调用requestQueue.cancelAll()将CompositeSubscription.unsubscribe()
    private CompositeSubscription mCompositeSubscription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompositeSubscription = new CompositeSubscription();

    }

    @Override
    protected void onDestroy() {
        if (mCompositeSubscription != null && mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
        super.onDestroy();
    }
}
