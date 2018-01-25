package me.mundane.parallaxrecyclerview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import me.mundane.parallaxrecyclerview.R;
import me.mundane.parallaxrecyclerview.view.TestImageView;

public class TestActivity extends AppCompatActivity {

    private SeekBar mSeekBar;
    private TestImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        bindView();
        bindListener();
    }

    private void bindListener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // progress越是增大, ImageView中的内容越是往下移动
                mIv.setCurrentTranslate(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.btn_tran_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        findViewById(R.id.btn_tran_cut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        findViewById(R.id.btn_tran_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void bindView() {
        mSeekBar = findViewById(R.id.seekbar);
        mIv = findViewById(R.id.iv);
    }
}
