package me.mundane.dagger2learning.bean;

import android.util.Log;
import javax.inject.Inject;
import okhttp3.OkHttpClient;

/**
 * Created by mundane on 2018/5/15 下午5:56
 */

public class ApiServer {
    private static final String TAG = "ApiServer";
    private OkHttpClient mOkHttpClient;
    
    @Inject
    public ApiServer(OkHttpClient okHttpClient) {
        Log.d(TAG, "ApiServer构造函数");
        mOkHttpClient = okHttpClient;
    }
    
    /**
     * 往服务端保存用户信息
     */
    public void register() {
        Log.d(TAG,"注册信息");
    }
    
}
