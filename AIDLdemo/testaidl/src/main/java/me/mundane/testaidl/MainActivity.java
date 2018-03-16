package me.mundane.testaidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import me.mundane.testaidl.aidl.Book;
import me.mundane.testaidl.aidl.IBookManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIBookManager != null) {
            unbindService(mServiceConnection);
        }
    }

    private IBookManager mIBookManager;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIBookManager = IBookManager.Stub.asInterface(service);
            Toast.makeText(MainActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIBookManager = null;
        }
    };

    public void bindService(View view) {
        Intent intent = new Intent();
        intent.setAction("me.mundane.testaidl.aidl.BookManagerService");
        intent.setPackage(getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    public void addBook(View view) {
        if (mIBookManager != null) {
            try {
                mIBookManager.addBook(new Book(18, "漫画书"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
