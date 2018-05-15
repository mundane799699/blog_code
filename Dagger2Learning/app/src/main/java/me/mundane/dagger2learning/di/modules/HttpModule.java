package me.mundane.dagger2learning.di.modules;

import dagger.Module;
import dagger.Provides;
import me.mundane.dagger2learning.bean.Person;
import me.mundane.dagger2learning.di.scopes.ActivityScope;

/**
 * Created by mundane on 2018/5/14 上午9:38
 */

@Module
public class HttpModule {
    
    //@Singleton
    //@Provides
    //public OkHttpClient provideSingletonOkhttpClient() {
    //    return new OkHttpClient.Builder().build();
    //}
    
    @ActivityScope
    @Provides
    public Person providePerson() {
        return new Person();
    }
}
