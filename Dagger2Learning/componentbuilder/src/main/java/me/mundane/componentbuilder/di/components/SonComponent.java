package me.mundane.componentbuilder.di.components;

import dagger.BindsInstance;
import dagger.Subcomponent;
import me.mundane.componentbuilder.activity.SubcomponentActivity;
import me.mundane.componentbuilder.di.modules.SonModule;

/**
 * Created by mundane on 2018/5/19 下午8:42
 */


@Subcomponent(modules = SonModule.class)
public interface SonComponent {
    void inject(SubcomponentActivity activity);
    
    @Subcomponent.Builder
    interface Builder {
    
        SonComponent build();
        
        @BindsInstance
        SonComponent.Builder setSonName(String name);
        
    }
}
