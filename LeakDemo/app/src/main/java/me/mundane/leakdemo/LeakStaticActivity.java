package me.mundane.leakdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LeakStaticActivity extends AppCompatActivity {


    private static Context sContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_static);
        sContext = this;
    }

    @Override
    protected void onDestroy() {
        //sContext = null;
        super.onDestroy();
    }
}
