package me.mundane.dagger2learning.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.util.Map;
import javax.inject.Inject;
import me.mundane.dagger2learning.R;
import me.mundane.dagger2learning.bean.Player;
import me.mundane.dagger2learning.di.components.DaggerIntoMapComponent;

public class IntoMapActivity extends AppCompatActivity {
    
    private static final String TAG = "IntoMapActivity";
    
    @Inject
    Map<String, Player> mPlayers;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into_map);
        DaggerIntoMapComponent.create().inject(this);
        Log.d(TAG, "mPlayers = " + mPlayers);
        for (String s : mPlayers.keySet()) {
            Player player = mPlayers.get(s);
            Log.d(TAG, "key = " + s + ", value  = " + player);
            player.play();
        }
        
    }
}
