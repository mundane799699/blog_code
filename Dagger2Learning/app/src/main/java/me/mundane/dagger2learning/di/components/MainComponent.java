package me.mundane.dagger2learning.di.components;

import dagger.Component;
import me.mundane.dagger2learning.activity.MainActivity;
import me.mundane.dagger2learning.di.modules.TextViewModule;

/**
 * Created by mundane on 2018/5/11 下午2:24
 */

@Component(modules = TextViewModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
