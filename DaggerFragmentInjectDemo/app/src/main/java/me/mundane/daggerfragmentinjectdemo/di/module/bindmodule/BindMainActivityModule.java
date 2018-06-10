package me.mundane.daggerfragmentinjectdemo.di.module.bindmodule;

import android.app.Activity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import me.mundane.daggerfragmentinjectdemo.activity.MainActivity;
import me.mundane.daggerfragmentinjectdemo.di.component.MainActivitySubcomponent;

/**
 * Created by mundane on 2018/6/2 下午7:44
 */

// 使用@Subcomponent注解是为了使用AppComponent中的AndroidSupportInjectionModule
@Module(subcomponents = MainActivitySubcomponent.class)
public abstract class BindMainActivityModule {
    
    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMainActivityInjectorFactory(
            MainActivitySubcomponent.Builder builder);
}
