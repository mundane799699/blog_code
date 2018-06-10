package me.mundane.dagger2learning.di.components;

import dagger.BindsInstance;
import dagger.Component;
import me.mundane.dagger2learning.bean.GameMode;

/**
 * Created by mundane on 2018/5/18 上午10:29
 */

@Component
public interface GameComponent {
    //void inject(BindsInstanceActivity activity);
    
    GameMode getGameMode();
    
    @Component.Builder
    interface Builder{
        
        @BindsInstance
        Builder gameModeName(String str);
        
        GameComponent build();
        
    }
}
