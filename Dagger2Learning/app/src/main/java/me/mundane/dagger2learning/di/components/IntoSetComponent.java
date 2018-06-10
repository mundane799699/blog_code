package me.mundane.dagger2learning.di.components;

import dagger.Component;
import me.mundane.dagger2learning.activity.IntoSetActivity;
import me.mundane.dagger2learning.di.modules.IntoSetModule;

/**
 * Created by mundane on 2018/5/17 下午4:32
 */

@Component(modules = IntoSetModule.class)
public interface IntoSetComponent {
    void inject(IntoSetActivity activity);
}
