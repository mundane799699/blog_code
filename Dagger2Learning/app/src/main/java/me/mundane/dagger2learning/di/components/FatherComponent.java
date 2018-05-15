package me.mundane.dagger2learning.di.components;

import dagger.Component;
import javax.inject.Singleton;
import me.mundane.dagger2learning.di.modules.OkhttpClientModule;

/**
 * Created by mundane on 2018/5/15 上午9:18
 */

@Singleton
@Component(modules = OkhttpClientModule.class)
public interface FatherComponent {
    // 实现了父子组件之间的关联
    ChildComponent getChildComponent();
}
