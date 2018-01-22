package me.mundane.handlerexplore;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int MSG_CODE = 0x33;
    private static final String TAG = "MainActivity";

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CODE:
                    Toast.makeText(MainActivity.this, "收到消息了", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "currentThread = " + Thread.currentThread().getName());
                    Log.d(TAG, "收到消息了");
                    break;
            }
        }
    };
    private Button mBtnSendMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        bindListener();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(MSG_CODE);

            }
        }).start();
    }

    private void bindListener() {
        mBtnSendMsg.setOnClickListener(this);
    }

    private void bindView() {
        mBtnSendMsg = findViewById(R.id.btn_send_msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_msg:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "currentThread = " + Thread.currentThread().getName());
                        for (int i = 0; i < 5; i++) {
                            Log.d(TAG, "模拟做点什么" + i);
                        }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(MSG_CODE);
                    }
                }).start();
                break;
        }
    }
}
