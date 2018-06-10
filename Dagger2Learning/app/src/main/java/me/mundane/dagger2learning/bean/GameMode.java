package me.mundane.dagger2learning.bean;

import javax.inject.Inject;

/**
 * Created by mundane on 2018/5/18 上午10:28
 */

public class GameMode {
    
    private String mGameName;
    @Inject
    public GameMode(String gameName){
        mGameName = gameName;
    }
    
    public String getGameName() {
        return mGameName;
    }
    
    public void setGameName(String gameName) {
        mGameName = gameName;
    }
}