package me.mundane.jswithandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }

    public void webview1(View view) {
        startActivity(new Intent(this, WebViewActivity1.class));
    }

    public void webview2(View view) {
        startActivity(new Intent(this, WebActivity2.class));
    }
}
