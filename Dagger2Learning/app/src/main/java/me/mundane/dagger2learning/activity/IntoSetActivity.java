package me.mundane.dagger2learning.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.util.Set;
import javax.inject.Inject;
import me.mundane.dagger2learning.R;
import me.mundane.dagger2learning.bean.Player;
import me.mundane.dagger2learning.di.components.DaggerIntoSetComponent;

public class IntoSetActivity extends AppCompatActivity {
    private static final String TAG = "IntoSetActivity";
    
    
    @Inject
    Set<Player> mPlayers;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into_set);
        DaggerIntoSetComponent.create().inject(this);
        Log.d(TAG, "mPlayers = " + mPlayers);
        for (Player player : mPlayers) {
            player.play();
        }
    }
}
