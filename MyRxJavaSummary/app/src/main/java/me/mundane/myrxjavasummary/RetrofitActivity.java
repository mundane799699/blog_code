package me.mundane.myrxjavasummary;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import me.mundane.myrxjavasummary.base.BaseActivity;
import me.mundane.myrxjavasummary.bean.GankMeiziResult;
import me.mundane.myrxjavasummary.network.APIFactory;
import me.mundane.myrxjavasummary.network.RxSchedulersHelper;
import me.mundane.myrxjavasummary.network.api.GankAPI;
import rx.Subscriber;
import rx.functions.Action0;

public class RetrofitActivity extends BaseActivity {
    private static final String TAG = "RetrofitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        GankAPI gankAPI = APIFactory.createGankAPI();
        // 这里的gankAPI.getMeiziData(10, 1)代替之前的call
        gankAPI.getMeiziData(10, 1)
                .compose(RxSchedulersHelper.<GankMeiziResult>io2main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        // 貌似doOnSubsribe的线程不受前面subscribeOn()指定线程的影响, 默认为主线程
                        Log.d(TAG, "currentThread = " + Thread.currentThread().getName());
                        showProgressDialog();
                    }
                })
                .subscribe(new Subscriber<GankMeiziResult>() {
                    @Override
                    public void onCompleted() {
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressDialog();
                    }

                    @Override
                    public void onNext(GankMeiziResult gankMeizhiResult) {
                        Toast.makeText(RetrofitActivity.this, "请求完成", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
