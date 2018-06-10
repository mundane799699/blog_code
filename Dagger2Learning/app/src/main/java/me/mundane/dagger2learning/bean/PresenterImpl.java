package me.mundane.dagger2learning.bean;

import android.util.Log;
import me.mundane.dagger2learning.mvp.Presenter;

/**
 * Created by mundane on 2018/5/17 下午2:31
 */

public class PresenterImpl implements Presenter {
    private static final String TAG = "PresenterImpl";
    
    public PresenterImpl() {
    
    }
    
    @Override
    public void print() {
        Log.d(TAG, "This is PresenterImpl");
    }
}