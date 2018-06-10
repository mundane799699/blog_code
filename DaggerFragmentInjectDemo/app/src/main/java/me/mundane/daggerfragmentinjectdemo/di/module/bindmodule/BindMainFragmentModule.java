package me.mundane.daggerfragmentinjectdemo.di.module.bindmodule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.mundane.daggerfragmentinjectdemo.di.module.MainFragmentModule;
import me.mundane.daggerfragmentinjectdemo.fragment.MainFragment;

/**
 * Created by mundane on 2018/6/9 下午9:09
 */

//@Module(subcomponents = MainFragmentSubcomponent.class)
@Module
public abstract class BindMainFragmentModule {
    //@Binds
    //@IntoMap
    //@FragmentKey(MainFragment.class)
    //abstract AndroidInjector.Factory<? extends Fragment> bindMainFragmentInjectorFactory(
    //        MainFragmentSubcomponent.Builder builder);
    
    @ContributesAndroidInjector(modules = MainFragmentModule.class)
    abstract MainFragment contributeMainFragmentInjector();
}
