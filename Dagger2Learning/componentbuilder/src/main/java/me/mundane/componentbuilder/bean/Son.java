package me.mundane.componentbuilder.bean;

import android.util.Log;

/**
 * Created by mundane on 2018/5/19 下午8:39
 */

public class Son {
    
    private static final String TAG = "Son";

    private String name;
    
    public Son(String name) {
        this.name = name;
    }
    
    public void say() {
        Log.d(TAG, "my name is " + name);
    }
}
