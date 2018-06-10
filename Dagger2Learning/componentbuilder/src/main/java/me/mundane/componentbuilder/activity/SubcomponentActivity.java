package me.mundane.componentbuilder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import javax.inject.Inject;
import me.mundane.componentbuilder.R;
import me.mundane.componentbuilder.bean.Father;
import me.mundane.componentbuilder.bean.Son;
import me.mundane.componentbuilder.di.components.DaggerFatherComponent;

public class SubcomponentActivity extends AppCompatActivity {
    
    private static final String TAG = "SubcomponentActivity";
    
    @Inject
    Father mFather;
    
    @Inject
    Son mSon;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcomponent);
        DaggerFatherComponent.create()
                .getSonComponentBuilder()
                .setSonName("mundane")
                .build()
                .inject(this);
        //DaggerFatherComponent.create().getSonComponent().inject(this);
        Log.d(TAG, "father = " + mFather);
        Log.d(TAG, "son = " + mSon);
        mSon.say();
        
    }
}
