package me.mundane.dagger2learning.di.modules;

import android.util.Log;
import dagger.Module;
import dagger.Provides;
import me.mundane.dagger2learning.bean.Dog;

/**
 * Created by mundane on 2018/5/17 上午11:25
 */

@Module
public class DogModule {
    private static final String TAG = "DogModule";
    @Provides
    public Dog provideDog() {
        Log.d(TAG, "provideDog");
        return new Dog();
    }
}
