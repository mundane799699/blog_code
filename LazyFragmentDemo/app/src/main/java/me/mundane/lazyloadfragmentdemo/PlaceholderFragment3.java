package me.mundane.lazyloadfragmentdemo;

/**
 * Created by mundane on 2018/3/7 下午8:09
 */

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment3 extends BaseLazyFragment {

    private TextView mTextView;
    private ProgressBar mPb;
    private Handler mHandler = new Handler();
    private String mData;

    public PlaceholderFragment3() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment3 newInstance() {
        PlaceholderFragment3 fragment = new PlaceholderFragment3();
        return fragment;
    }

    @Override
    public void loadDataStart() {
        Log.d(TAG, "loadDataStart");
        // 模拟请求数据
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mData = "这是加载下来的数据";
                // 一旦获取到数据, 就应该立刻标记数据加载完成
                mLoadDataFinished = true;
                if (mViewInflateFinished) {
                    mTextView.setVisibility(View.VISIBLE);
                    mTextView.setText(mData);
                    mTextView.setText("这是改变后的数据");
                    mPb.setVisibility(View.GONE);
                }
            }
        }, 500);
    }

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.d(TAG, "initRootView");
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    protected void findViewById(View view) {
        mTextView = view.findViewById(R.id.section_label);
        mPb = view.findViewById(R.id.pb);
        if (mLoadDataFinished) { // 一般情况下这时候数据请求都还没完成, 所以不会进这个if
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(mData);
            mPb.setVisibility(View.GONE);
        }
    }

}
