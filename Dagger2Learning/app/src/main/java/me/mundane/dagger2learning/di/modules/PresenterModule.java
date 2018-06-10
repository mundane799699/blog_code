package me.mundane.dagger2learning.di.modules;

import dagger.Binds;
import dagger.Module;
import me.mundane.dagger2learning.bean.PresenterImpl;
import me.mundane.dagger2learning.mvp.Presenter;

/**
 * Created by mundane on 2018/5/17 下午2:34
 */

// @Module may not contain both non-static @Provides methods
// and abstract @Binds or @Multibinds declarations
// 注意也要用抽象类和抽象方法
@Module
public abstract class PresenterModule {
    // 这个方法必须返回接口或抽象类，比如Presenter，不能直接返回PresenterImpl
    // 方法的参数类型就是这个方法返回的注入对象的类型，类似@Provides修饰的方法的返回类型
    @Binds
    abstract Presenter bindPresenter(PresenterImpl presenter);
    
}
