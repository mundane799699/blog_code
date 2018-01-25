package me.mundane.parallaxrecyclerview.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.mundane.parallaxrecyclerview.R;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        bindView();
        bindListener();
    }

    private void bindListener() {
        findViewById(R.id.btn_test).setOnClickListener(this);
        findViewById(R.id.btn_list).setOnClickListener(this);
        findViewById(R.id.btn_recycler).setOnClickListener(this);
    }

    private void bindView() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                goToActivity(TestActivity.class);
                break;
            case R.id.btn_list:
                break;
            case R.id.btn_recycler:
                goToActivity(RecyclerActivity.class);
                break;
        }
    }

    private void goToActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }
}
