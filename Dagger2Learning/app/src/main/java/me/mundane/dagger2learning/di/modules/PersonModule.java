package me.mundane.dagger2learning.di.modules;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import me.mundane.dagger2learning.bean.Person;

/**
 * Created by mundane on 2018/5/15 上午10:17
 */

@Module
public class PersonModule {
    @Singleton
    @Provides
    public Person provideSingletonPerson() {
        return new Person();
    }
}
