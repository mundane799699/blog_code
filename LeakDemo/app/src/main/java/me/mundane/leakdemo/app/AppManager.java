package me.mundane.leakdemo.app;

import android.content.Context;

/**
 * Created by mundane on 2018/2/14 下午9:05
 */

public class AppManager {
    private Context mContext;

    private AppManager(Context context) {
        mContext = context.getApplicationContext();
    }

    private  volatile static  AppManager INSTANCE;

    public static AppManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppManager.class) {
                if (INSTANCE== null) {
                    INSTANCE = new AppManager(context);
                }
            }
        }
        return INSTANCE;
    }
}
