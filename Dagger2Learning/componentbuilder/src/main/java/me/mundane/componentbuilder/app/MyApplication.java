package me.mundane.componentbuilder.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import me.mundane.componentbuilder.di.components.AppComponent;
import me.mundane.componentbuilder.di.components.DaggerAppComponent;

/**
 * Created by mundane on 2018/5/19 下午2:47
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static AppComponent mAppComponent;
    
    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .application(this)
                .gson(new Gson())
                .build();
        SharedPreferences sharedPrefs = mAppComponent.getSharedPrefs();
        Log.d(TAG, "sharedPrefs = " + sharedPrefs);
    }
    
    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}
