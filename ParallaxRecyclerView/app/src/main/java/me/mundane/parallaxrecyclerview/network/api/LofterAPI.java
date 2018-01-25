package me.mundane.parallaxrecyclerview.network.api;

import me.mundane.parallaxrecyclerview.model.LofterDataResult;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by mundane on 2017/12/4 上午12:01
 */

public interface LofterAPI {
    // http://api.lofter.com/v1.1/batchdata.api?product=lofter-android-5.9.5
    @FormUrlEncoded
    @POST("v1.1/batchdata.api")
    Observable<LofterDataResult> getData(@Field("product") String product);
}
