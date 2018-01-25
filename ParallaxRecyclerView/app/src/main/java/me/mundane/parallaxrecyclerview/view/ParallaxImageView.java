package me.mundane.parallaxrecyclerview.view;

import android.content.Context;
import android.graphics.Matrix;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by mundane on 2017/12/22 下午4:02
 */

public class ParallaxImageView extends AppCompatImageView {
    private static final String TAG = "ParallaxImageView";
    private int itemHeight;
    private int itemYPos;
    private int rvHeight;
    private int rvYPos;
    private final float DEFAULT_FACTOR = 0.2f;
    private float mFactor = DEFAULT_FACTOR;

    public ParallaxImageView(Context context) {
        super(context);
        init();
    }

    public ParallaxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParallaxImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
    }

    public void setFactor(float factor) {
        this.mFactor = factor;
    }

//    @Override
//    public void setImageDrawable(@Nullable Drawable drawable) {
//        super.setImageDrawable(drawable);
//        doTranslate();
//    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        doTranslate();
    }

    public interface ParallaxImageListener {
        int[] requireValuesForTranslate();
    }

    private ParallaxImageListener mListener;

    public void setListener(ParallaxImageListener listener) {
        this.mListener = listener;
    }

    public synchronized void doTranslate() {
        if (getDrawable() == null || !getValues()) {
            return;
        }
        int drawableHeight = getDrawable().getIntrinsicHeight();
        float scale = recomputeImageMatrix();
        drawableHeight *= scale;
        final int viewHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        float maxTranslate = drawableHeight * 0.5f - viewHeight * 0.5f;
        float minTranslate = -maxTranslate;
        // distance是缩放后的图片中心线和ImageView中心线之间的距离
        float distance = computeDistance(scale);

        // translate是recyclerView中心线和itemView中心线之间的距离
        float translate = (rvYPos + rvHeight * 0.5f) - (itemYPos + itemHeight * 0.5f);
        translate *= mFactor;
        if (translate >= maxTranslate) {
            translate = maxTranslate;
        } else if (translate <= minTranslate) {
            translate = minTranslate;
        }
        transform(scale, distance, translate);
        invalidate();
    }

    private float computeDistance(float scale) {
        // 获取imageView的高度减去padding之后的部分
        final int viewHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        // 获取drawable的高度
        int drawableHeight = getDrawable().getIntrinsicHeight();

        // 按照比例变换后的drawableHeight
        drawableHeight *= scale;
        return viewHeight * 0.5f - drawableHeight * 0.5f;

    }

    private void transform(float scale, float distance, float translate) {
        Matrix imageMatrix = getImageMatrix();
        if (scale != 1) {
            imageMatrix.setScale(scale, scale);
        }
        float[] matrixValues = new float[9];
        imageMatrix.getValues(matrixValues);
        // 获取当前的y值, 比如一开始y值是0, 目标是让当前的y值变为distance
        // 那么就在y方向上偏移 distance - currentY
        float currentY = matrixValues[Matrix.MTRANS_Y];
        float dy = translate + distance - currentY;
        imageMatrix.postTranslate(0, dy);
        setImageMatrix(imageMatrix);
    }

    /**
     * 重新计算ImageView的变换矩阵
     *
     * @return
     */
    private float recomputeImageMatrix() {
        float scale;
        // 获取imageView的宽度减去padding之后的部分
        final int viewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        // 获取imageView的高度减去padding之后的部分
        final int viewHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        // 获取drawable的宽度
        final int drawableWidth = getDrawable().getIntrinsicWidth();
        // 获取drawable的高度
        final int drawableHeight = getDrawable().getIntrinsicHeight();

        // 如果drawable的宽高比大于view的宽高比
        // drawableWidth / drawableHeight > viewWidth / viewHeight
        if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
            // 如果drawable的宽高比大于view的宽高比
            // 那么就让drawable乘以一个scale, 使得drawable的高度能够等于view的高度, 使得drawable能够填充整个view
            // drawableHeight * (scale = viewHeight/ drawableHeight) = viewHeight
            scale = (float) viewHeight / (float) drawableHeight;
        } else { // 如果drawable的宽高比小于view的宽高比  <------  代码会走这里

            // 为了使drawable能够填充整个view, 需要使drawable的宽度能够等于view的宽度
            // drawableWidth * (scale = viewWidth / drawableWidth) = viewWidth
            scale = (float) viewWidth / (float) drawableWidth;
        }

        return scale;
    }


    private boolean getValues() {
        // itemView的高度, itemView在屏幕上的y坐标, recyclerView的高度, recyclerView在屏幕上的y坐标
        int[] values = mListener.requireValuesForTranslate();
        if (values == null) {
            return false;
        }
        // 行高是itemView的高度, 行的y坐标是itemView在屏幕上的y坐标
        this.itemHeight = values[0];
        this.itemYPos = values[1];
        this.rvHeight = values[2];
        this.rvYPos = values[3];
        return true;
    }
}
