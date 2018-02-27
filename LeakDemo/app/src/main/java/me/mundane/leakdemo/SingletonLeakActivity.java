package me.mundane.leakdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import me.mundane.leakdemo.app.AppManager;

public class SingletonLeakActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleton_leak);
        AppManager.getInstance(this);
    }
}
