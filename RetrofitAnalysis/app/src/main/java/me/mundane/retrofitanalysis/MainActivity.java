package me.mundane.retrofitanalysis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import me.mundane.retrofitanalysis.api.GankApi;
import me.mundane.retrofitanalysis.model.MeizhiModel;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Retrofit mRetrofit;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRetrofit = getRetrofit();
    }

    public void getMeiZhi(View view) {
        GankApi gankApi = mRetrofit.create(GankApi.class);
        Call<MeizhiModel> call = gankApi.getMeiZhi(10, 1);
        Log.d(TAG, "call = " + call);
        call.enqueue(new Callback<MeizhiModel>() {
            @Override
            public void onResponse(Call<MeizhiModel> call, Response<MeizhiModel> response) {
                Log.d(TAG, "currentThread = " + Thread.currentThread().getName());
                Log.d(TAG, "response = " + response.body().toString());
            }

            @Override
            public void onFailure(Call<MeizhiModel> call, Throwable t) {

            }
        });
    }

    public void getMeiZhi2(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                GankApi gankApi = mRetrofit.create(GankApi.class);
                final Call<MeizhiModel> call = gankApi.getMeiZhi(10, 1);
                try {
                    MeizhiModel meizhiModel = call.execute().body();
                    Log.d(TAG, "meizhiModel = " + meizhiModel.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Retrofit getRetrofit() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .serializeNulls()
                .create();

        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(Level.BODY);
        OkHttpClient client =
                new Builder().addInterceptor(logger).connectTimeout(12, TimeUnit.SECONDS).build();
        String baseUrl = "http://gank.io/";
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.build();
        return retrofit;
    }
}
