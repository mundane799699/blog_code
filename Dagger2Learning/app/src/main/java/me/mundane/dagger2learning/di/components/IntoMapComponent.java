package me.mundane.dagger2learning.di.components;

import dagger.Component;
import me.mundane.dagger2learning.activity.IntoMapActivity;
import me.mundane.dagger2learning.di.modules.IntoMapModule;

/**
 * Created by mundane on 2018/5/17 下午5:57
 */

@Component(modules = IntoMapModule.class)
public interface IntoMapComponent {
    void inject(IntoMapActivity activity);
}
