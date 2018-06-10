package me.mundane.dagger2learning.di.modules;

import android.util.Log;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by mundane on 2018/5/15 下午6:02
 */

@Module
public class NetModule {
    private static final String TAG = "NetModule";
    @Provides
    public OkHttpClient provideOkhttpClient(){
        Log.d(TAG, "provideOkhttpClient");
        return new OkHttpClient().newBuilder().build();
    }
}
