package com.example.helloandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void start(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        ActResultRequest request = new ActResultRequest(this);
        request.startForResult(intent, new ActResultRequest.Callback() {
            @Override
            public void onActivityResult(int resultCode, Intent data) {
                Log.d(TAG, "resultCode = " + resultCode);
                String name = data.getStringExtra("name");
                Log.d(TAG, "name = " + name);
                Toast.makeText(MainActivity.this, "name = " + name + ", resultCode = " + resultCode,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
