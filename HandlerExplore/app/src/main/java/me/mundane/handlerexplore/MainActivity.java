package me.mundane.handlerexplore;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final int MSG_CODE = 0x33;
    private static final String TAG = "MainActivity";
    private FHandler mFHandler;

    private Handler mHandler = new Handler() {
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

    private void updateButtonText(String text) {
        mBtnSendMsg.setText(text);
    }

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

        mFHandler = new FHandler(this);
        // 但是这里匿名内部又会默认持有activity的引用
        mFHandler.setOnMsgArrivedListener(new FHandler.OnMsgArrivedListener() {
            @Override
            public void updateUI(String text) {
                mBtnSendMsg.setText(text);
            }
        });
        startSendMsgToFhandler();
    }

    private void startSendMsgToFhandler() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // a. 定义要发送的消息
                Message msg = Message.obtain();
                msg.what = 1;// 消息标识
                msg.obj = "AA";// 消息存放
                // b. 传入主线程的Handler & 向其MessageQueue发送消息
                mFHandler.sendMessage(msg);
            }
        }.start();

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

    // 分析1：自定义Handler子类
    // 设置为：静态内部类
    private static class FHandler extends Handler {

        // 定义 弱引用实例
        private WeakReference<MainActivity> reference;

        private WeakReference<OnMsgArrivedListener> mListenerReference;

        // 在构造方法中传入需持有的Activity实例
        public FHandler(MainActivity activity) {
            // 使用WeakReference弱引用持有Activity实例
            reference = new WeakReference<>(activity);
        }

        public interface OnMsgArrivedListener {
            void updateUI(String text);
        }

        public void setOnMsgArrivedListener(OnMsgArrivedListener listener) {
            mListenerReference = new WeakReference<>(listener);
        }

        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String text = (String) msg.obj;
                    MainActivity activity = reference.get();
                    Log.d(TAG, "activity =" + activity);
                    if (activity != null) {
                        activity.updateButtonText(text);
                    }
                    OnMsgArrivedListener onMsgArrivedListener = mListenerReference.get();
                    if (onMsgArrivedListener != null) {
                        onMsgArrivedListener.updateUI(text);
                    }
                    break;
                case 2:
                    Log.d(TAG, " 收到线程2的消息");
                    break;


            }
        }
    }


}
