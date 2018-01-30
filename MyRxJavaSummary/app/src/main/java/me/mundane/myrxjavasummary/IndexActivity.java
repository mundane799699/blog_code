package me.mundane.myrxjavasummary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        findViewById(R.id.btn_main_activity).setOnClickListener(this);
        findViewById(R.id.btn_edittext_sample).setOnClickListener(this);
        findViewById(R.id.btn_retrofit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_activity:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_edittext_sample:
                startActivity(new Intent(this, EditTextSampleActivity.class));
                break;
            case R.id.btn_retrofit:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
        }

    }
}
