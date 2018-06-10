package me.mundane.componentbuilder.di.modules;

import dagger.Module;
import dagger.Provides;
import me.mundane.componentbuilder.bean.Father;

/**
 * Created by mundane on 2018/5/19 下午8:40
 */

@Module
public class FatherModule {
    
    @Provides
    Father provideFather() {
        return new Father();
    }
}
