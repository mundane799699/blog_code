package me.mundane.dagger2learning.di.components;

import dagger.Component;
import me.mundane.dagger2learning.activity.QualifierActivity;
import me.mundane.dagger2learning.di.modules.ApiServerModule;
import me.mundane.dagger2learning.di.modules.NetModule;

/**
 * Created by mundane on 2018/5/15 下午5:54
 */

@Component(modules = {ApiServerModule.class, NetModule.class})
public interface QualifierComponent {
    void inject(QualifierActivity activity);
}
