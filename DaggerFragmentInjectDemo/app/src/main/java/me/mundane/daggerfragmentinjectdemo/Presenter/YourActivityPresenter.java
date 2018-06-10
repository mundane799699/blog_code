package me.mundane.daggerfragmentinjectdemo.Presenter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import javax.inject.Inject;

/**
 * Created by mundane on 2018/6/10 上午11:15
 */

public class YourActivityPresenter {
    SharedPreferences mSharedPreferences;
    
    @Inject
    public YourActivityPresenter(@NonNull SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }
    
    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }
}
