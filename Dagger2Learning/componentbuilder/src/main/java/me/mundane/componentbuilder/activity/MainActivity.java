package me.mundane.componentbuilder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import javax.inject.Inject;
import me.mundane.componentbuilder.R;
import me.mundane.componentbuilder.app.MyApplication;

public class MainActivity extends AppCompatActivity {
    
    
    @Inject
    Gson mGson;
    
    private static final String TAG = "MainActivity";
    private String json = "{\"name\":\"Pikachu\", \"id\":25}";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getAppComponent().inject(this);
        Pokemon pokemon = mGson.fromJson(json, Pokemon.class);
        Log.i(TAG, "pokemon = " + pokemon);
        Log.i(TAG, "pokemon.name = " + pokemon.name);
    }
    
    
    static class Pokemon {
        public String name;
        
        public long id;
        
    }
}
