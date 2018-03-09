package me.mundane.interviewprepare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.mundane.interviewprepare.activity.LifecycleActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_activity_lifecycle)
    Button mBtnActivityLifecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBtnActivityLifecycle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LifecycleActivity.class);
                startActivity(intent);
            }
        });
    }
}
