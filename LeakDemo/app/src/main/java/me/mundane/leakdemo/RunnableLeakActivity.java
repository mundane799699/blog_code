package me.mundane.leakdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RunnableLeakActivity extends AppCompatActivity {

    private Runnable runnable1 = new StaticRunnable();
    private Runnable runnable2 = new Runnable() {
        @Override
        public void run() {

        }
    };

    static class StaticRunnable implements Runnable {
        @Override
        public void run() {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runnable_leak);
    }

}
