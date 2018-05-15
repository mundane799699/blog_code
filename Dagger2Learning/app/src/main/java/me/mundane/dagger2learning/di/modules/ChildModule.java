package me.mundane.dagger2learning.di.modules;

import dagger.Module;
import dagger.Provides;
import me.mundane.dagger2learning.bean.OkhttpClientManager;
import me.mundane.dagger2learning.di.scopes.ActivityScope;
import okhttp3.OkHttpClient;

/**
 * Created by mundane on 2018/5/15 上午9:27
 */

@Module
public class ChildModule {
    
    @ActivityScope
    @Provides
    public OkhttpClientManager provideOkhttpClientManager(OkHttpClient okHttpClient){
        return new OkhttpClientManager(okHttpClient);
    }
}
