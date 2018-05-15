package me.mundane.dagger2learning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import javax.inject.Inject;
import me.mundane.dagger2learning.R;
import me.mundane.dagger2learning.base.BaseActivity;
import me.mundane.dagger2learning.bean.Person;
import me.mundane.dagger2learning.di.components.DaggerSingletonComponent;
import okhttp3.OkHttpClient;

public class SingletonActivity extends BaseActivity {
    private static final String TAG = "SingletonActivity";
    
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
        setContentView(R.layout.activity_singleton);
        DaggerSingletonComponent.builder().appComponent(getAppComponent()).build().inject(this);
        Log.d(TAG, "mOkHttpClient1 = " + mOkHttpClient1);
        Log.d(TAG, "mOkHttpClient2 = " + mOkHttpClient2);
        Log.d(TAG, "mPerson1 = " + mPerson1);
        Log.d(TAG, "mPerson2 = " + mPerson2);
    }
    
    public void go2SingletonActivity2(View view) {
        startActivity(new Intent(this, SingletonActivity2.class));
    }
}
