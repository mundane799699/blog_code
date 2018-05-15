package me.mundane.dagger2learning.di.modules;

import android.content.Context;
import android.widget.TextView;
import dagger.Module;
import dagger.Provides;

/**
 * Created by mundane on 2018/5/11 下午4:47
 */

@Module
public class TextViewModule {
    
    private Context mContext;
    
    public TextViewModule(Context context) {
        mContext = context;
    }
    
    @Provides
    TextView provideTextView() {
        return new TextView(mContext);
    }
    
    //@Provides
    //Context provideContext() {
    //    return mContext;
    //}
}
