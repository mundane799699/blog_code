package me.mundane.customrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import me.mundane.customrxjava.rxjava.Observable;
import me.mundane.customrxjava.rxjava.Observable.OnSubscribe;
import me.mundane.customrxjava.rxjava.Observable.Transformer;
import me.mundane.customrxjava.rxjava.Subscriber;
import me.mundane.customrxjava.rxjava.scheduler.AndroidSchedulers;
import me.mundane.customrxjava.rxjava.scheduler.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void myrxjava(View view) {
        Observable.create(new OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) { // <------
                // 其实这里的参数subscriber就是下面订阅的(你传进去的那个subscriber)
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
            }
        }).subscribe(new Subscriber<Integer>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d(TAG, "onStart");
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext " + integer);
            }
        });
    }

    public void map(View view) {
        Observable.create(new OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
            }
        }).map(new Transformer<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return "这是变换后的" + integer;
            }
        }).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d(TAG, "onStart()");
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "s = " + s);
            }
        });
    }

    public void testLift(View view) {
        Observable.create(new OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
            }
        }).lift(new Operator<String, Integer>() {
            @Override
            public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                return new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        subscriber.onNext("这是变换后的" + integer);
                    }
                };
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "s = " + s);
            }
        });
    }

    public void testAndroidSchedulers(View view) {

        Observable.create(new OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.d(TAG, "callthread = " + Thread.currentThread().getName());
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d(TAG, "onstart thread = " + Thread.currentThread().getName());
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "integer = " + integer);
                Log.d(TAG, "onNext thread = " + Thread.currentThread().getName());
            }
        });
    }

    public void testObserveOn(View view) {
        Observable.create(new OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.d(TAG, "callthread = " + Thread.currentThread().getName());
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
            }
        }).observeOn(Schedulers.io()).subscribe(new Subscriber<Integer>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d(TAG, "onstart thread = " + Thread.currentThread().getName());
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "integer = " + integer);
                Log.d(TAG, "onNext thread = " + Thread.currentThread().getName());
            }
        });
    }

    public void testSubscribeOn(View view) {
        // 在订阅(subscribe)的时候会调用create的时候传进去的onSubscribe的call()方法
        //
        Observable.create(new OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.d(TAG, "callthread = " + Thread.currentThread().getName());
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Integer>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d(TAG, "onstart thread = " + Thread.currentThread().getName());
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "integer = " + integer);
                Log.d(TAG, "onNext thread = " + Thread.currentThread().getName());
            }
        });
    }




}
