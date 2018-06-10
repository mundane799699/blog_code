package me.mundane.daggerfragmentinjectdemo.app;

import android.app.Activity;
import android.app.Application;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import javax.inject.Inject;
import me.mundane.daggerfragmentinjectdemo.di.component.DaggerAppComponent;

/**
 * Created by mundane on 2018/6/2 下午7:58
 */

public class DemoApplication extends Application implements HasActivityInjector {
    
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;
    
    @Override
    public void onCreate() {
        super.onCreate();
        //DaggerAppComponent.builder().application(this).build().inject(this);
        //DaggerAppComponent.builder().build().inject(this);
        //DaggerAppComponent.create().inject(this);
        DaggerAppComponent.builder().application(this).build().inject(this);
        
        
    }
    
    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
    
    
}
