package me.mundane.viewanalysis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class GetMeasuredHeightActivity extends AppCompatActivity {

    private View mLl;
    private static final String TAG = "GetMeasuredHeightActivi";
    private View mRlRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_measured_height);
        //mLl = findViewById(R.id.ll);
        mRlRed = findViewById(R.id.rl_red);
    }

    public void print(View view) {
        //Log.d(TAG, "mLl.getHeight() = " + mLl.getHeight());
        //Log.d(TAG, "mLl.getMeasuredHeight() = " + mLl.getMeasuredHeight());
        Log.d(TAG, "mRlRed.getHeight() = " + mRlRed.getHeight());
        Log.d(TAG, "mRlRed.getMeasuredHeight() = " + mRlRed.getMeasuredHeight());
    }
}
