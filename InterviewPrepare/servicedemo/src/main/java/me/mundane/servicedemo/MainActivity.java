package me.mundane.servicedemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import me.mundane.servicedemo.service.MusicService;
import me.mundane.servicedemo.service.MusicService.OnProgressListener;
import me.mundane.servicedemo.service.MusicService.SimpleBinder;
import me.mundane.servicedemo.service.MyIntentService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private final String TAG = "MainActivity";
    private SimpleBinder mSimpleBinder;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.tv_start_service).setOnClickListener(this);
		findViewById(R.id.tv_stop_service).setOnClickListener(this);
		findViewById(R.id.tv_bind_service).setOnClickListener(this);
	}

	public void pause(View view) {
		if (mSimpleBinder != null) {
            mSimpleBinder.stop();
		}
	}

	public void play(View view) {
		if (mSimpleBinder != null) {
            mSimpleBinder.play();
		}
	}

    public void intentService(View view) {
        MyIntentService.startActionFoo(this, "hehe", "hehe");
    }


	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, MusicService.class);
		switch (v.getId()) {
			case R.id.tv_start_service:
				intent.putExtra("action", "playMusic");
				startService(intent);
				break;
			case R.id.tv_stop_service:
				stopService(intent);
				break;
			case R.id.tv_bind_service:
				MyserviceConnection connection = new MyserviceConnection();
				intent.putExtra("action", "playMusic");
				bindService(intent, connection, Service.BIND_AUTO_CREATE);
				break;
		}
	}

	private class MyserviceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
            mSimpleBinder = (SimpleBinder) service;
            MusicService musicService = mSimpleBinder.getService();
            musicService.setProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(int progress) {

                }
            });
            Log.d(TAG, "binder.getService() = " + musicService.toString());
		}


		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	}
}
