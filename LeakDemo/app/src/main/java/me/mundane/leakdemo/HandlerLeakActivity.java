package me.mundane.leakdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.lang.ref.WeakReference;

public class HandlerLeakActivity extends AppCompatActivity implements OnClickListener {

    private TextView mTextView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            System.out.println("===== handle message ====");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_leak);
        mTextView = findViewById(R.id.tv);
        //StaticRunnable staticRunnable = new StaticRunnable(this);
        //sHandler.postDelayed(staticRunnable, 60000);
        mTextView.setOnClickListener(this);
    }

    StaticHandler sHandler = new StaticHandler(this);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                mHandler.sendEmptyMessageDelayed(0x12, 60 * 1000L);
                finish();
                break;
        }
    }

    static class StaticHandler extends Handler {
        private WeakReference<HandlerLeakActivity> reference;

        StaticHandler(HandlerLeakActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HandlerLeakActivity activity = reference.get();
            if (activity != null) {
                activity.getTextView().setText("测试");
            }
        }
    }

    static class StaticRunnable implements Runnable{

        private WeakReference<HandlerLeakActivity> mWeakReference;

        public StaticRunnable(HandlerLeakActivity activity) {
            mWeakReference = new WeakReference<HandlerLeakActivity>(activity);
        }

        @Override
        public void run() {
            HandlerLeakActivity handlerLeakActivity = mWeakReference.get();
            if (handlerLeakActivity != null) {
                handlerLeakActivity.update("runnable");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sHandler.removeCallbacksAndMessages(null);
    }

    private TextView getTextView() {
        return mTextView;
    }

    private void update(String text) {
        mTextView.setText(text);
    }
}
