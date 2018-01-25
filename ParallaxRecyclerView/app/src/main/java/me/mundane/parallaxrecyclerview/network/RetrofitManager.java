package me.mundane.parallaxrecyclerview.network;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.mundane.parallaxrecyclerview.network.api.GankAPI;
import me.mundane.parallaxrecyclerview.network.api.LofterAPI;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.internal.platform.Platform.INFO;

/**
 * Created by mundane on 2017/12/4 下午3:38
 */

public class RetrofitManager {

    private final Retrofit mGankRetrofit;
    private final Retrofit mLofterRetrofit;
    private final String GANK_BASE_URL = "http://gank.io/api/data/";
    private final String LOFTER_BASE_URL = "http://api.lofter.com/";
//	private final GankAPI mGankAPI;

    private RetrofitManager() {
        mGankRetrofit = new Retrofit.Builder()
                .client(genericClient())
                .baseUrl(GANK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mLofterRetrofit = new Retrofit.Builder()
                .client(loftClient())
                .baseUrl(LOFTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private OkHttpClient loftClient() {
        HttpLoggingInterceptor logger = getLogger();
        Interceptor headInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("secruityinfo", "{\"datatype\":\"aimt_datas\",\"id_ver\":\"Android_1.0.1\",\"rdata\":\"6CGZ/ICP+pRluyq3UJOh7OazJbwWb6o0vrUGdD04PInsRJ3J4s0oQY48cBpEWFnXIv1URKCMxl304sjxzg/K2Hu6+dq0k+Ol+uDEhRlVJ3dXRcFlenj8Jeuzu5yPWGQlcpF9tJQDeRXGl3kWKWmqb0u/jHCMGuq89KFEDLb7TbxMW/1197nuhDjHtSYwglxTd+UtnZq+rNClnOyVxzSD7VHiMhMIIQohfu0K5zudmto/2ILesLCZ/+nZUsP1EoxtxGHDa9OrVKt+v7Oq3T0MSFTZl5HKsl2p+Lso+33qyZIL3sqgp2bvM7vkdh8UmxGR/Yks6okWhWA5av7cVXxoOxUlvltTwSV6bQceHmJsfRw/oBVPkR4I53CfpwTKaVsK8l1c85epA9H9Rzwhrl1dS3EUy7ZHJvRIVCgeElARYCuK/5eZtCWARR1LsmKbe9eR0o6Vd1s3UW6WVWlrNNRYCF5QO1osxJ8uCwZLRj3TkIWlxakCvyFXFylBFuHjOK5jCD4cDfCxEBw+3Q8/gJrt9USjL8owdrRrsE5Dx8yCVNo=\",\"rk\":\"I+GW0qE9EbdCIh7PwCDu2L+lpvvzCna3CwFD+HzQhYLc90Z3EEmjAqwUECA+NQKwaXfYuHOGAocSY3TNEYsn5EErxLzbdTdyA9TvnH0H0q/aCsL1Zyd5NfCFEDlsU0woM7LHMQfJ46Kl6erLmWRW6x4m7ba3VL2kpje9PV4RJWk=\"}")
                        .addHeader("market", "LOFTER")
                        .addHeader("deviceid", "00000000-5205-f7d1-cc49-f2540033c587")
                        .addHeader("dadeviceid", "d2f452c9fdac70c979e54cc4bcf0714a95a30cdc")
                        .addHeader("User-Agent", "LOFTER-Android 5.9.5 (MI 4LTE; Android 6.0.1; null) WIFI")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                        .addHeader("Host", "api.lofter.com")
                        .addHeader("cookie", "NTESLOFTSI=1516869834042BFA094E9C401064AD16F4F61C3198601.hzabj-lofter3-8010; Domain=.api.lofter.com; Path=/")
                        .build();
                Response response = chain.proceed(request);
                return response;
            }
        };
        OkHttpClient client = new OkHttpClient
                .Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(logger)
                .addInterceptor(headInterceptor)
                .build();

        return client;
    }

    @NonNull
    private HttpLoggingInterceptor getLogger() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Platform.get().log(INFO, decodeUnicodeToString(message), null);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    public GankAPI getGankAPI() {
        return mGankRetrofit.create(GankAPI.class);
    }

    public LofterAPI getLofterAPI() {
        return mLofterRetrofit.create(LofterAPI.class);
    }

    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }


    private OkHttpClient genericClient() {
        HttpLoggingInterceptor logger = getLogger();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(logger)
                .build();
        return okHttpClient;
    }

    private String decodeUnicodeToString(String uString) {
        StringBuilder sb = new StringBuilder();
        int i = -1, pos = 0;
        while ((i = uString.indexOf("\\u", pos)) != -1) {
            sb.append(uString.substring(pos, i));
            if (i + 5 < uString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(uString.substring(i + 2, i + 6), 16));
            }
        }
        sb.append(uString.substring(pos));
        return sb.toString();
    }
}
