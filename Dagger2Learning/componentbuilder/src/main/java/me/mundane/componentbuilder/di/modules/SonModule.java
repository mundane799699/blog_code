package me.mundane.componentbuilder.di.modules;

import dagger.Module;
import dagger.Provides;
import me.mundane.componentbuilder.bean.Son;

/**
 * Created by mundane on 2018/5/19 下午8:41
 */

@Module
public class SonModule {
    
    @Provides
    Son provideSon(String name) {
        return new Son(name);
    }
}
