package me.mundane.dagger2learning.di.components;

import dagger.Component;
import me.mundane.dagger2learning.activity.BindsActivity;
import me.mundane.dagger2learning.di.modules.PresenterImplModule;
import me.mundane.dagger2learning.di.modules.PresenterModule;

/**
 * Created by mundane on 2018/5/17 下午3:00
 */

@Component(modules = {PresenterModule.class, PresenterImplModule.class})
public interface BindsComponent {
    void inject(BindsActivity activity);
}
