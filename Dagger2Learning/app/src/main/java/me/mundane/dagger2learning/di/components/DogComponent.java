package me.mundane.dagger2learning.di.components;

import dagger.Component;
import me.mundane.dagger2learning.activity.LazyActivity;
import me.mundane.dagger2learning.di.modules.DogModule;

/**
 * Created by mundane on 2018/5/17 上午11:27
 */

@Component(modules = DogModule.class)
public interface DogComponent {
    void inject(LazyActivity activity);
}
