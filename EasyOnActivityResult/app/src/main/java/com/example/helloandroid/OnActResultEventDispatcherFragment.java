package com.example.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

public class OnActResultEventDispatcherFragment extends Fragment{
    public static final String TAG = "on_act_result_event_dispatcher";

    public  int mRequestCode = 0x11;

    private SparseArray<ActResultRequest.Callback> mCallbacks = new SparseArray<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void startForResult(Intent intent, ActResultRequest.Callback callback) {
        // mRequestCode与callback需要一一对应
        mCallbacks.put(mRequestCode, callback);
        startActivityForResult(intent, mRequestCode);
        mRequestCode++;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ActResultRequest.Callback callback = mCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);

        if (callback != null) {
            callback.onActivityResult(resultCode, data);
        }
    }

}
