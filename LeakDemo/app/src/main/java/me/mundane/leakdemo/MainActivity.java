package me.mundane.leakdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindListener();

        
    }

    private void bindListener() {
        findViewById(R.id.btn_static_activity).setOnClickListener(this);
        findViewById(R.id.btn_asynctask_leak).setOnClickListener(this);
        findViewById(R.id.btn_singleton_leak).setOnClickListener(this);
        findViewById(R.id.btn_static_view).setOnClickListener(this);
        findViewById(R.id.btn_runnable_leak).setOnClickListener(this);
        findViewById(R.id.btn_handler_leak).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_static_activity:
                startActivity(new Intent(this, LeakStaticActivity.class));
                break;
            case R.id.btn_asynctask_leak:
                startActivity(new Intent(this, AsyncTaskActivity.class));
                break;
            case R.id.btn_singleton_leak:
                startActivity(new Intent(this, SingletonLeakActivity.class));
                break;
            case R.id.btn_static_view:
                startActivity(new Intent(this, StaticViewLeakActivity.class));
                break;
            case R.id.btn_runnable_leak:
                startActivity(new Intent(this, RunnableLeakActivity.class));
                break;
            case R.id.btn_handler_leak:
                startActivity(new Intent(this, HandlerLeakActivity.class));
                break;
        }
    }

}
