package me.mundane.componentbuilder.di.components;

import android.app.Application;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import dagger.BindsInstance;
import dagger.Component;
import javax.inject.Singleton;
import me.mundane.componentbuilder.activity.MainActivity;
import me.mundane.componentbuilder.di.modules.AppModule;

/**
 * Created by mundane on 2018/5/19 下午2:49
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    
    void inject(MainActivity mainActivity);
    
    SharedPreferences getSharedPrefs();
    
    @Component.Builder
    interface Builder {
        
        AppComponent build();
        
        @BindsInstance
        Builder application(Application application);
    
        @BindsInstance
        Builder gson(Gson gson);
    }
}