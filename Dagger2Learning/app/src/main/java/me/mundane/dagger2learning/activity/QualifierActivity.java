package me.mundane.dagger2learning.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import javax.inject.Inject;
import me.mundane.dagger2learning.R;
import me.mundane.dagger2learning.bean.ApiServer;
import me.mundane.dagger2learning.di.components.DaggerQualifierComponent;
import me.mundane.dagger2learning.di.modules.ApiServerModule;
import me.mundane.dagger2learning.di.qualifiers.Release;
import me.mundane.dagger2learning.di.qualifiers.Test;

public class QualifierActivity extends AppCompatActivity {
    private static final String TAG = "QualifierActivity";
    
    private boolean isTest = true;
    
    @Test
    @Inject
    ApiServer mApiServerTest;
    
    @Release
    @Inject
    ApiServer mApiServerRelease;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualifier);
        DaggerQualifierComponent.builder()
                .apiServerModule(getApiServerModule())
                .build()
                .inject(this);
        Log.d(TAG, "mApiServerTest = " + mApiServerTest);
        Log.d(TAG, "mApiServerRelease = " + mApiServerRelease);
        if (isTest) {
            mApiServerTest.register();
        } else {
            mApiServerRelease.register();
        }
    }
    
    @NonNull
    private ApiServerModule getApiServerModule() {
        return new ApiServerModule(getApplicationContext());
    }
}
