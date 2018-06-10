package me.mundane.dagger2learning.di.modules;

import dagger.Module;
import dagger.Provides;
import me.mundane.dagger2learning.bean.PresenterImpl;

/**
 * Created by mundane on 2018/5/17 下午3:13
 */

@Module
public class PresenterImplModule {
    @Provides
    PresenterImpl providePresenterImpl() {
        return new PresenterImpl();
    }
}
