package me.mundane.daggerfragmentinjectdemo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;
import me.mundane.daggerfragmentinjectdemo.Presenter.MainActivityPresenter;
import me.mundane.daggerfragmentinjectdemo.R;
import me.mundane.daggerfragmentinjectdemo.bean.ActivityUser;
import me.mundane.daggerfragmentinjectdemo.fragment.MainFragment;
import me.mundane.daggerfragmentinjectdemo.util.ActivityUtils;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private static final String TAG = "MainActivity";
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;
    
    @Inject
    ActivityUser mUser;
    
    @Inject
    MainActivityPresenter mPresenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        Log.d(TAG, "mUser = " + mUser);
        Log.d(TAG, "mPresenter = " + mPresenter);
        Log.d(TAG, "mPresenter.getSharedPreferences() = " + mPresenter.getSharedPreferences());
        MainFragment fragment = new MainFragment();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment,
                R.id.fl_container);
    }
    
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentInjector;
    }
}
