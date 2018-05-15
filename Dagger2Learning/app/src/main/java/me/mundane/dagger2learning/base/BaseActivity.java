package me.mundane.dagger2learning.base;

import android.support.v7.app.AppCompatActivity;
import me.mundane.dagger2learning.di.components.AppComponent;
import me.mundane.dagger2learning.global.MyApplication;

/**
 * Created by mundane on 2018/5/14 下午5:08
 */

public class BaseActivity extends AppCompatActivity {
    
    public AppComponent getAppComponent() {
        MyApplication myApplication = (MyApplication) getApplication();
        return myApplication.getAppComponent();
    }
}
