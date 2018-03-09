package me.mundane.okhttpsourcecodeanalysis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_get).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                getRequest2();
                //getRequest();
                //try {
                //    simpleRequest();
                //} catch (IOException e) {
                //    e.printStackTrace();
                //}
                break;
        }
    }

    private void getRequest2() {
        // http://www.publicobject.com/helloworld.txt
        // https://www.toutiao.com/search/suggest/initial_page/
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //Log.i(TAG, "addInterceptor");
                //Request request = chain.request();
                //Response response = chain.proceed(request);
                //return response;
                return null;
            }
        }).addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.i(TAG, "addNetworkInterceptor");
                Request request = chain.request();
                Response response = chain.proceed(request);
                return response;
            }
        }).build();
        Request request =
                new Request.Builder().url("http://www.publicobject.com/helloworld.txt")
                        .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "response = " + response.body().string());
            }
        });
    }

    private void simpleRequest() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request =
                new Request.Builder().url("https://www.toutiao.com/search/suggest/initial_page/")
                        .build();
        Call call = client.newCall(request);
        call.execute();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "response = " + response.body().string());
            }
        });
    }

    private void getRequest() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long t1 = System.nanoTime();
                Log.i(TAG, String.format("Sending request %s on %s%n%s", request.url(),
                        chain.connection(), request.headers()));
                Response response = chain.proceed(request);
                long t2 = System.nanoTime();
                Log.i(TAG, String.format("Received response for %s in %.1fms%n%s",
                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                return response;
            }
        }).build();
        Request request =
                new Request.Builder().url("https://www.toutiao.com/search/suggest/initial_page/")
                        .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "response = " + response.body().string());
                // 如果已经是异步的回调的话就没必要用rxjava了, 只有在子线程的耗时操作才用
                // 但是这里的回调发生在子线程, 可以用rxjava在子线程把数据发送出来然后切换到主线程
            }
        });
    }
}
