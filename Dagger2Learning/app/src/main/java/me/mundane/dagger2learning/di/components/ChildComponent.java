package me.mundane.dagger2learning.di.components;

import dagger.Subcomponent;
import me.mundane.dagger2learning.activity.SubcomponentActivity;
import me.mundane.dagger2learning.di.modules.ChildModule;
import me.mundane.dagger2learning.di.scopes.ActivityScope;

/**
 * Created by mundane on 2018/5/15 上午9:21
 */

@ActivityScope
@Subcomponent(modules = ChildModule.class)
public interface ChildComponent {
    void inject(SubcomponentActivity activity);
}
