package me.mundane.leakdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class StaticViewLeakActivity extends AppCompatActivity {

    private static View sView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_view_leak);
        sView = findViewById(R.id.tv);

    }
}