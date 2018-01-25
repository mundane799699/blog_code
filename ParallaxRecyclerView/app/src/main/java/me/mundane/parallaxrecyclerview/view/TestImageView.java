package me.mundane.parallaxrecyclerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by mundane on 2018/1/24 下午2:04
 */

public class TestImageView extends AppCompatImageView {
    private float mTranslate;

    public TestImageView(Context context) {
        super(context);
    }

    public TestImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        Matrix matrix = canvas.getMatrix();
        matrix.postTranslate(0, mTranslate);
        canvas.concat(matrix);
        super.onDraw(canvas);
        canvas.restore();
    }

    public void setCurrentTranslate(float translate) {
        mTranslate = translate;
        invalidate();
    }
}
