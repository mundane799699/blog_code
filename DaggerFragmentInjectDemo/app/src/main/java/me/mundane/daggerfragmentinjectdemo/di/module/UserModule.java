package me.mundane.daggerfragmentinjectdemo.di.module;

import android.widget.TextView;
import dagger.Module;
import dagger.Provides;
import me.mundane.daggerfragmentinjectdemo.activity.YourActivity;
import me.mundane.daggerfragmentinjectdemo.bean.User;
import me.mundane.daggerfragmentinjectdemo.di.scope.ActivityScope;

/**
 * Created by mundane on 2018/6/2 下午8:05
 */

@Module
public class UserModule {
    
    @Provides
    User provideUser() {
        User user = new User("mundane", 0);
        return user;
    }
    
    @ActivityScope
    @Provides
    TextView provideTextView(YourActivity context) {
        TextView textView = new TextView(context);
        return textView;
    }
    
    
    
}
