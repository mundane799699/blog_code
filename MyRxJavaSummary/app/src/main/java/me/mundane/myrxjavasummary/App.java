package me.mundane.myrxjavasummary;

import android.app.Application;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by mundane on 2018/2/28 下午5:07
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
