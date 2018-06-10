package me.mundane.daggerfragmentinjectdemo.di.module.bindmodule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.mundane.daggerfragmentinjectdemo.activity.YourActivity;
import me.mundane.daggerfragmentinjectdemo.di.module.UserModule;
import me.mundane.daggerfragmentinjectdemo.di.scope.ActivityScope;

/**
 * Created by mundane on 2018/6/2 下午7:49
 */

@Module
public abstract class BindYourActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = { UserModule.class})
    abstract YourActivity contributeYourActivityInjector();
}
