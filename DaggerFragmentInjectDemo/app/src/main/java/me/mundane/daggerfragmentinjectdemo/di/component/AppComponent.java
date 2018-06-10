package me.mundane.daggerfragmentinjectdemo.di.component;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;
import me.mundane.daggerfragmentinjectdemo.app.DemoApplication;
import me.mundane.daggerfragmentinjectdemo.di.module.AppModule;
import me.mundane.daggerfragmentinjectdemo.di.module.bindmodule.BindMainActivityModule;
import me.mundane.daggerfragmentinjectdemo.di.module.bindmodule.BindYourActivityModule;

/**
 * Created by mundane on 2018/6/2 下午7:47
 */

@Singleton
@Component(modules = {
        AppModule.class,
        BindYourActivityModule.class,
        BindMainActivityModule.class,
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class
})
public interface AppComponent {
    void inject(DemoApplication application);
    
    //SharedPreferences getSharedPrefs();
    
    @Component.Builder
    interface Builder {
        
        AppComponent build();
        
        @BindsInstance
        Builder application(Application application);
    }
}
