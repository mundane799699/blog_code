package me.mundane.componentbuilder.di.components;

import dagger.Component;
import me.mundane.componentbuilder.di.modules.FatherModule;

/**
 * Created by mundane on 2018/5/19 下午8:40
 */

@Component(modules = FatherModule.class)
public interface FatherComponent {
    SonComponent.Builder getSonComponentBuilder();
}
