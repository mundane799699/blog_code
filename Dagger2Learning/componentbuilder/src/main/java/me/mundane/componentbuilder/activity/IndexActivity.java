package me.mundane.componentbuilder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import me.mundane.componentbuilder.R;

public class IndexActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }
    
    public void mainactivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
    
    public void subcomponent(View view) {
        startActivity(new Intent(this, SubcomponentActivity.class));
    }
    
    
}
