package me.mundane.parallaxrecyclerview.rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.mundane.parallaxrecyclerview.view.ParallaxImageView;

/**
 * Created by mundane on 2018/1/24 上午11:28
 */

public abstract class ParallaxViewHolder extends RecyclerView.ViewHolder implements ParallaxImageView.ParallaxImageListener {
    private ParallaxImageView mParallaxImageView;

    public abstract int getParallaxImageId();
    public ParallaxViewHolder(View itemView) {
        super(itemView);
        mParallaxImageView = itemView.findViewById(getParallaxImageId());
        mParallaxImageView.setListener(this);

    }

    public void animateImage() {
        mParallaxImageView.doTranslate();
    }

    @Override
    public int[] requireValuesForTranslate() {
        if (itemView.getParent() == null) {
            return null;
        } else {
            int[] itemPosition = new int[2];
            // 获取itemView左上角在屏幕上的坐标
            itemView.getLocationOnScreen(itemPosition);
            int[] recyclerViewPosition = new int[2];
            // 获取recyclerView在屏幕上的坐标
            ((RecyclerView) itemView.getParent()).getLocationOnScreen(recyclerViewPosition);
            // 将参数传递过去
            // itemView的高度, itemView在屏幕上的y坐标, recyclerView的高度, recyclerView在屏幕上的y坐标
            return new int[]{itemView.getMeasuredHeight(), itemPosition[1], ((RecyclerView) itemView.getParent()).getHeight(), recyclerViewPosition[1]};
        }
    }
}
