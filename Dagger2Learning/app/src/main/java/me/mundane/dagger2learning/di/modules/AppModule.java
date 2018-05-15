package me.mundane.dagger2learning.di.modules;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;

/**
 * Created by mundane on 2018/5/14 上午11:48
 */

@Module
public class AppModule {
    
    @Singleton
    @Provides
    public OkHttpClient provideSingletonOkhttpClient() {
        return new OkHttpClient().newBuilder().build();
    }
}
