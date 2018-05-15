package me.mundane.dagger2learning.activity;

import android.os.Bundle;
import android.util.Log;
import javax.inject.Inject;
import me.mundane.dagger2learning.R;
import me.mundane.dagger2learning.base.BaseActivity;
import me.mundane.dagger2learning.bean.Person;
import me.mundane.dagger2learning.di.components.DaggerSingletonComponent2;
import okhttp3.OkHttpClient;

public class SingletonActivity2 extends BaseActivity {
    private static final String TAG = "SingletonActivity2";
    
    @Inject
    OkHttpClient mOkHttpClient1;
    
    @Inject
    OkHttpClient mOkHttpClient2;
    
    @Inject
    Person mPerson1;
    
    @Inject
    Person mPerson2;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleton2);
        DaggerSingletonComponent2.builder().appComponent(getAppComponent()).build().inject(this);
        Log.d(TAG, "mOkHttpClient1 = " + mOkHttpClient1);
        Log.d(TAG, "mOkHttpClient2 = " + mOkHttpClient2);
        Log.d(TAG, "mPerson1 = " + mPerson1);
        Log.d(TAG, "mPerson2 = " + mPerson2);
    }
}
