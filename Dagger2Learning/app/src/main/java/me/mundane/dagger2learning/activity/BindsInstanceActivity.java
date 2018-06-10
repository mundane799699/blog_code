package me.mundane.dagger2learning.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import me.mundane.dagger2learning.R;
import me.mundane.dagger2learning.bean.GameMode;
import me.mundane.dagger2learning.di.components.DaggerGameComponent;
import me.mundane.dagger2learning.di.components.GameComponent;

public class BindsInstanceActivity extends AppCompatActivity {
    
    private static final String TAG = "BindsInstanceActivity";
    
    //@Inject
    //GameMode mGameMode;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binds_instance);
        // DaggerGameComponent.builder().gameModeName("hard").build().inject(this);
        // 在构建 GameComponent 的时候手动注入了字符串 "hard"。相当于在 Module 中 @Provides 了这个字符串。
        // Subcomponent 同样提供了 Subcomponet.Builder 来完成同样的事情
        GameComponent component = DaggerGameComponent.builder().gameModeName("hard").build();
        GameMode gameMode = component.getGameMode();
        Log.d(TAG, "gameMode = " + gameMode);
        Log.d(TAG, "gameName = " + gameMode.getGameName());
    }
}
