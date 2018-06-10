package me.mundane.dagger2learning.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import javax.inject.Inject;
import me.mundane.dagger2learning.R;
import me.mundane.dagger2learning.bean.User;
import me.mundane.dagger2learning.di.components.DaggerMainComponent;
import me.mundane.dagger2learning.di.modules.TextViewModule;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = "MainActivity";
    
    @Inject
    User mUser;
    
    @Inject
    TextView mTv;
    
    private RelativeLayout mRl;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRl = findViewById(R.id.rl);
        DaggerMainComponent.builder().textViewModule(new TextViewModule(getApplicationContext())).build().inject(this);
        Log.d(TAG, "mUser = " + mUser);
        Log.d(TAG, "mTv = " + mTv);
        mTv.setText("mUser = " +  mUser);
        mRl.addView(mTv);
    }
}
