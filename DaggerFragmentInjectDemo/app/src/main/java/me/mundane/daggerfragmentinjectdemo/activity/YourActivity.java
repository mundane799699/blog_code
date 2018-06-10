package me.mundane.daggerfragmentinjectdemo.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import dagger.android.AndroidInjection;
import javax.inject.Inject;
import me.mundane.daggerfragmentinjectdemo.Presenter.YourActivityPresenter;
import me.mundane.daggerfragmentinjectdemo.R;
import me.mundane.daggerfragmentinjectdemo.bean.User;

public class YourActivity extends AppCompatActivity {
    
    private static final String TAG = "YourActivity";
    
    @Inject
    User mUser;
    @Inject
    TextView mTv;
    @Inject
    SharedPreferences mSharedPreferences;
    @Inject
    YourActivityPresenter mPresenter;
    @Inject
    Context mApplicationContext;
    
    private FrameLayout mFl;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your);
        Log.d(TAG, "mUser = " + mUser);
        mFl = findViewById(R.id.fl);
    
        mTv.setText(mUser.name);
        mFl.addView(mTv);
        Log.d(TAG, "mSharedPreferences = " + mSharedPreferences);
        Log.d(TAG, "mPresenter = " + mPresenter);
        Log.d(TAG, "mPresenter.getSharedPreferences() = " + mPresenter.getSharedPreferences());
        Log.d(TAG, "mApplicationContext = " + mApplicationContext);
    }
}
