package me.mundane.dagger2learning.bean;

import okhttp3.OkHttpClient;

/**
 * Created by mundane on 2018/5/15 上午9:52
 */

public class OkhttpClientManager {
    private OkHttpClient okHttpClient;
    
    public OkhttpClientManager(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }
}
