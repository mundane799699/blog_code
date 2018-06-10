package me.mundane.daggerfragmentinjectdemo.di.module;

import dagger.Module;
import dagger.Provides;
import me.mundane.daggerfragmentinjectdemo.bean.FragmentUser;

/**
 * Created by mundane on 2018/6/9 下午9:04
 */

@Module
public class MainFragmentModule {
    @Provides
    FragmentUser provideUser() {
        FragmentUser user = new FragmentUser("mundane", 0);
        return user;
    }
}
