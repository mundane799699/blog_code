package me.mundane.daggerfragmentinjectdemo.di.module;

import dagger.Module;
import dagger.Provides;
import me.mundane.daggerfragmentinjectdemo.bean.ActivityUser;

/**
 * Created by mundane on 2018/6/9 下午9:30
 */

@Module
public class MainActivityModule {
    
    @Provides
    ActivityUser provideUser() {
        ActivityUser user = new ActivityUser("mundane", 0);
        return user;
    }
    
}
