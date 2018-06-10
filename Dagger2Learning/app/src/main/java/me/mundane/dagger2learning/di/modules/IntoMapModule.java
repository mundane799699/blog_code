package me.mundane.dagger2learning.di.modules;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import me.mundane.dagger2learning.bean.Player;

/**
 * Created by mundane on 2018/5/17 下午5:44
 */

@Module
public class IntoMapModule {
    
    @Provides
    @IntoMap
    @StringKey(value = "1")
    public Player providePlayer1(){
        return new Player("1");
    }
    
    @Provides
    @IntoMap
    @StringKey(value = "2")
    public Player providePlayer2(){
        return new Player("2");
    }
}
