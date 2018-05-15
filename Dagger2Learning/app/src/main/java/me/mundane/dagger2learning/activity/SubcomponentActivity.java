package me.mundane.dagger2learning.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import javax.inject.Inject;
import me.mundane.dagger2learning.R;
import me.mundane.dagger2learning.bean.OkhttpClientManager;
import me.mundane.dagger2learning.di.components.ChildComponent;
import me.mundane.dagger2learning.di.components.DaggerFatherComponent;
import me.mundane.dagger2learning.di.components.FatherComponent;

public class SubcomponentActivity extends AppCompatActivity {
    
    private static final String TAG = "SubcomponentActivity";
    
    @Inject
    OkhttpClientManager mOkhttpClientManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcomponent);
        
        FatherComponent fatherComponent = DaggerFatherComponent.builder().build();

        ChildComponent childComponent = fatherComponent.getChildComponent();

        childComponent.inject(this);
        
    
        Log.d(TAG, "mOkhttpClientManager = " + mOkhttpClientManager);
        
        
    }
}
