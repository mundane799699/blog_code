package me.mundane.viewanalysis.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mundane on 2018/3/17 下午1:26
 */

public class MyTestView extends View {
    public MyTestView(Context context) {
        super(context);
    }

    public MyTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
