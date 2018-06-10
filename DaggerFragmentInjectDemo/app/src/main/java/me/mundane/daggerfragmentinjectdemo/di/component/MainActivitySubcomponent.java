package me.mundane.daggerfragmentinjectdemo.di.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import me.mundane.daggerfragmentinjectdemo.activity.MainActivity;
import me.mundane.daggerfragmentinjectdemo.di.module.MainActivityModule;
import me.mundane.daggerfragmentinjectdemo.di.module.bindmodule.BindMainFragmentModule;

/**
 * Created by mundane on 2018/6/2 下午7:42
 */

@Subcomponent(modules = {MainActivityModule.class, BindMainFragmentModule.class})
public interface MainActivitySubcomponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {}
}
