package me.mundane.dagger2learning.di.modules;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import dagger.multibindings.IntoSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import me.mundane.dagger2learning.bean.Player;

/**
 * Created by mundane on 2018/5/17 下午4:28
 */

@Module
public class IntoSetModule {
    
    @Provides
    @IntoSet
    public Player providePlayer2() {
        return new Player("2");
    }
    
    @Provides
    @IntoSet
    public Player providePlayer1() {
        return new Player("1");
    }
    
    @Provides
    @ElementsIntoSet
    public Set<Player> providePlayers(){
        return new HashSet<>(Arrays.asList(new Player("3"), new Player("4")));
    }
    
}
