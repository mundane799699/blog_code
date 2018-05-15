package me.mundane.viewanalysis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by mundane on 2018/3/17 下午1:35
 */

public class MyTestViewgroup extends ViewGroup {
    public MyTestViewgroup(Context context) {
        super(context);
    }

    public MyTestViewgroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTestViewgroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
