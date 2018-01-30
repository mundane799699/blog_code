package me.mundane.myrxjavasummary.network;

import java.util.concurrent.TimeUnit;

import me.mundane.myrxjavasummary.network.api.GankAPI;
import okhttp3.OkHttpClient;
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
	
	private final Retrofit mRetrofit;
	private final String BASE_URL = "http://gank.io/api/data/";

	private RetrofitManager() {
		mRetrofit = new Retrofit.Builder()
		        .client(genericClient())
		        .baseUrl(BASE_URL)
		        .addConverterFactory(GsonConverterFactory.create())
		        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
		        .build();
	}
	
	public GankAPI getGankAPI() {
		return mRetrofit.create(GankAPI.class);
	}
	
	private static RetrofitManager INSTANCE;
	
	public static RetrofitManager getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder{
		private static final RetrofitManager INSTANCE = new RetrofitManager();
	}
	
	
	private OkHttpClient genericClient() {
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
			@Override
			public void log(String message) {
				Platform.get().log(INFO, decodeUnicodeToString(message), null);
			}
		});
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);
		
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
		    .connectTimeout(10, TimeUnit.SECONDS)
		    .addInterceptor(logging)
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
