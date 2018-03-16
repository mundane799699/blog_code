package me.mundane.servicedemo.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import me.mundane.servicedemo.MainActivity;
import me.mundane.servicedemo.R;

public class MusicService extends Service {
	public MusicService() {
	}

	private MediaPlayer player;

	private static final String TAG = "MusicService";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()");
	}


    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: ");
        super.onRebind(intent);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "onStart: ");
        super.onStart(intent, startId);
    }

    @Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand()");

		String action = intent.getStringExtra("action");
		if("playMusic".equals(action)) {
			//播放
			playMusic();
		}else if("stopMusic".equals(action)){
			//停止
			stopMusic();
		}

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

		Notification notification = new NotificationCompat.Builder(this)
			.setSmallIcon(R.mipmap.ic_launcher)
			.setWhen(System.currentTimeMillis())
			.setContentTitle("this is title")
			.setContentText("this is text")
			.setContentIntent(pendingIntent)
			.build();

		startForeground(110, notification);

		return super.onStartCommand(intent, flags, startId);
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
		stopForeground(true);
		stopMusic();
	}


	public void stopMusic() {
		if (player != null) {
			player.stop();
			player.reset();
			player.release();//释放加载的文件
			player = null;//不要忘了！
		}
	}

	public void playMusic() {
		if (player == null) {
			player = MediaPlayer.create(this, R.raw.jinglebells);
			player.setLooping(true);

		}
		if (player != null && !player.isPlaying()) {
			player.start();
            if (mOnProgressListener != null) {
                mOnProgressListener.onProgress(0);
            }

		}
	}


	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind");

		String action = intent.getStringExtra("action");
		if("playMusic".equals(action)) {
			//播放
			playMusic();
		}else if("stopMusic".equals(action)){
			//停止
			stopMusic();
		}

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

		Notification notification = new NotificationCompat.Builder(this)
			.setSmallIcon(R.mipmap.ic_launcher)
			.setWhen(System.currentTimeMillis())
			.setContentTitle("this is title")
			.setContentText("this is text")
			.setContentIntent(pendingIntent)
			.build();

		startForeground(110, notification);

		SimpleBinder simpleBinder = new SimpleBinder();
		return simpleBinder;
	}

    public interface OnProgressListener {
        void onProgress(int progress);
    }

    private OnProgressListener mOnProgressListener;

    public void setProgressListener(OnProgressListener onProgressListener) {
        mOnProgressListener = onProgressListener;
    }

	public class SimpleBinder extends Binder {
		public SimpleBinder() {

		}

        public void play() {
            playMusic();
        }

        public void stop() {
            stopMusic();
        }

		public MusicService getService() {
			return MusicService.this;
		}

	}


	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, "onUnbind");
		return super.onUnbind(intent);
	}
}
