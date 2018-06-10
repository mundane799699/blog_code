package me.mundane.daggerfragmentinjectdemo.Presenter;

import android.content.SharedPreferences;
import javax.inject.Inject;

/**
 * Created by mundane on 2018/6/10 下午1:06
 */

public class MainActivityPresenter {
    @Inject
    SharedPreferences mSharedPreferences;
    
    @Inject
    public MainActivityPresenter() {
    }
    
    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }
}
