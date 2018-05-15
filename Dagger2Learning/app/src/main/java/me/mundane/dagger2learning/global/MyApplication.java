package me.mundane.dagger2learning.global;

import android.app.Application;
import me.mundane.dagger2learning.di.components.AppComponent;
import me.mundane.dagger2learning.di.components.DaggerAppComponent;

/**
 * Created by mundane on 2018/5/14 下午5:07
 */

public class MyApplication extends Application {
    
    private AppComponent mAppComponent;
    
    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.create();
    }
    
    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
