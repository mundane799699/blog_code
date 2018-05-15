package me.mundane.eventbusanalysis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import me.mundane.eventbusanalysis.event.MessageEvent;
import org.greenrobot.eventbus.EventBus;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void sendMessage(View view) {
        EventBus.getDefault().post(new MessageEvent("这是SecondActivity发送的消息"));
    }
}
