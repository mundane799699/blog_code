package me.mundane.viewanalysis;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class MeasureActivity extends AppCompatActivity {

    private View mTv;
    private static final String TAG = "MeasureActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        mTv = findViewById(R.id.tv);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTv.getLayoutParams();
        mTv.measure(0, 0);
        Log.d(TAG, "getMeasuredHeight = " + mTv.getMeasuredHeight());
        new AlertDialog.Builder(this).create().show();
    }
}
