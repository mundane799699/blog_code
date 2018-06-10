package me.mundane.dagger2learning.bean;

import android.util.Log;

/**
 * Created by mundane on 2018/5/17 下午4:28
 */

public class Player {
    
    private static final String TAG = "Player";
    
    public String name;
    
    public Player(String name) {
        this.name = name;
    }
    
    public void play() {
        Log.d(TAG, name + " is playing ");
    }
}
