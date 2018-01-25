package me.mundane.parallaxrecyclerview.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import me.mundane.parallaxrecyclerview.R;
import me.mundane.parallaxrecyclerview.model.GankMeiziResult;
import me.mundane.parallaxrecyclerview.model.LofterDataResult;
import me.mundane.parallaxrecyclerview.network.APIFactory;
import me.mundane.parallaxrecyclerview.network.api.GankAPI;
import me.mundane.parallaxrecyclerview.network.api.LofterAPI;
import me.mundane.parallaxrecyclerview.rv.MeizhiAdapter;
import me.mundane.parallaxrecyclerview.rv.ParallaxViewHolder;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class RecyclerActivity extends BaseActivity {

    private RecyclerView mRv;
    private ProgressDialog mProgressDialog;
    private MeizhiAdapter mMeizhiAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        bindListener();
        initData();
    }

    private final String TAG = "RecyclerActivity";

    private void bindListener() {
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                // 获取第一个可见条目的position
                int firstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                // 获取所有可见条目的数量
                int visibleItemCount = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition() - firstVisibleItem + 1;
                for (int i = 0; i < visibleItemCount; i++) {
                    View childView = recyclerView.getChildAt(i);
                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(childView);
                    if (viewHolder instanceof ParallaxViewHolder) {
                        ParallaxViewHolder parallaxViewHolder = (ParallaxViewHolder) viewHolder;
                        parallaxViewHolder.animateImage();
                    }
                }

            }
        });

    }

    private void initData() {
        GankAPI gankAPI = APIFactory.createGankAPI();
        // 这里的gankAPI.getMeiziData(10, 1)代替之前的call
        mSubscription = gankAPI.getMeiziData(20, 1)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgressDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())  // 指定doOnSubscribe()所发生的线程, 其实这句代码可以不加的
                .observeOn(AndroidSchedulers.mainThread())
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
                        mMeizhiAdapter = new MeizhiAdapter(gankMeizhiResult.beauties);
                        mRv.setAdapter(mMeizhiAdapter);
                    }
                });
    }

    private void fetchLofterData() {
        LofterAPI lofterAPI = APIFactory.createLofterAPI();
        lofterAPI.getData("lofter-android-5.9.5")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgressDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LofterDataResult>() {
                    @Override
                    public void onCompleted() {
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressDialog();
                    }

                    @Override
                    public void onNext(LofterDataResult lofterDataResult) {
                        Log.d(TAG, "lofterDataResult = " + lofterDataResult);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        unsubscribe(mSubscription);
        super.onDestroy();
    }

    private <T extends Subscription> void unsubscribe(T t) {
        if (t != null && t.isUnsubscribed()) {
            t.unsubscribe();
        }
    }

    private void bindView() {
        mRv = findViewById(R.id.rv);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRv.setLayoutManager(mLinearLayoutManager);
        mRv.setHasFixedSize(true);
    }

    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        mProgressDialog.dismiss();
    }
}
