package me.mundane.myrxjavasummary.network;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mundane on 2018/1/30 上午10:33
 * 处理Rx线程
 */

public class RxSchedulersHelper {
    public static <T> Observable.Transformer<T, T> io2main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
