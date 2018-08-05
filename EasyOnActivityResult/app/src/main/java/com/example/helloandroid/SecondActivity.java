package com.example.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }

    public void returnResult(View view) {
        Intent intent = new Intent();
        intent.putExtra("name", "mundane");
        setResult(RESULT_OK, intent);
        finish();

    }
}
