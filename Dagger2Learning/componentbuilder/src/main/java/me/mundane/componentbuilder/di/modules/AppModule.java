package me.mundane.componentbuilder.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by mundane on 2018/5/19 下午2:49
 */

@Module
public class AppModule {
    
    private static final String DATA_STORE = "DATA_STORE";
    
    
    @Provides
    @Singleton
    public SharedPreferences providePreferences(Application application) {
        return application.getSharedPreferences(DATA_STORE, Context.MODE_PRIVATE);
    }
}