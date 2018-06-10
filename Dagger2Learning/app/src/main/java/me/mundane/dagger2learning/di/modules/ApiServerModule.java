package me.mundane.dagger2learning.di.modules;

import android.content.Context;
import android.util.Log;
import dagger.Module;
import dagger.Provides;
import me.mundane.dagger2learning.bean.ApiServer;
import me.mundane.dagger2learning.di.qualifiers.Release;
import me.mundane.dagger2learning.di.qualifiers.Test;
import okhttp3.OkHttpClient;

/**
 * Created by mundane on 2018/5/16 上午9:23
 */

@Module
public class ApiServerModule {
    private static final String TAG = "ApiServerModule";
    
    private Context mContext;
    
    public ApiServerModule(Context context){
        mContext = context;
    }
    
    @Test
    @Provides
    public ApiServer provideTestApiServer(OkHttpClient okHttpClient){
        ApiServer apiServer = new ApiServer(okHttpClient);
        Log.d(TAG,"provideTestApiServer " + apiServer);
        return apiServer;
    }
    
    @Release
    @Provides
    public ApiServer provideReleaseApiServer(OkHttpClient okHttpClient){
        ApiServer apiServer = new ApiServer(okHttpClient);
        Log.d(TAG,"provideReleaseApiServer " + apiServer);
        return apiServer;
    }
}
