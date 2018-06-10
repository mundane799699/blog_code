package me.mundane.daggerfragmentinjectdemo.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by mundane on 2018/6/9 下午10:34
 */

@Module
public abstract class AppModule {
    private static final String TAG = "AppModule";
    
    private static final String DATA_STORE = "DATA_STORE";
    
    @Singleton
    @Provides
    static Context provideApplicationContext(Application application) {
        return application;
    }
    
    @Singleton
    @Provides
    static SharedPreferences providePreferences(Application application) {
        SharedPreferences preferences =
                application.getSharedPreferences(DATA_STORE, Context.MODE_PRIVATE);
        Log.d(TAG, "preferences = " + preferences);
        return preferences;
    }
}
