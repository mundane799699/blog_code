package me.mundane.dagger2learning.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import javax.inject.Inject;
import me.mundane.dagger2learning.R;
import me.mundane.dagger2learning.di.components.DaggerBindsComponent;
import me.mundane.dagger2learning.mvp.Presenter;

public class BindsActivity extends AppCompatActivity {
    private static final String TAG = "BindsActivity";
    
    @Inject
    Presenter mPresenterImpl;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binds);
        DaggerBindsComponent.create().inject(this);
        Log.d(TAG, "mPresenterImpl = " + mPresenterImpl);
        mPresenterImpl.print();
    }
}
