package me.mundane.dagger2learning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import me.mundane.dagger2learning.R;

public class IndexActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }
    
    public void inject(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
    
    public void singleton(View view) {
        startActivity(new Intent(this, SingletonActivity.class));
    }
    
    public void subcomponent(View view) {
        startActivity(new Intent(this, SubcomponentActivity.class));
    }
}
