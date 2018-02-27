package me.mundane.leakdemo.app;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by mundane on 2018/2/13 下午9:51
 */

public class LeakDemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}
