package me.mundane.builddesignpattern;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by mundane on 2018/3/10 下午6:03
 */

public class EasyDialogFragment extends DialogFragment implements OnClickListener {

    private static final String KEY_TITLE = "key_title";
    private String mTitle;
    private TextView mTvTitle;
    private View mBtnCancel;
    private View mBtnConfirm;

    interface OnClickListener {
        void onClick();
    }

    private OnClickListener mPositiveListener;

    private OnClickListener mNegativeListener;

    private void setPositiveListener(OnClickListener onClickListener) {
        mPositiveListener = onClickListener;
    }

    private void setNegativeListener(OnClickListener onClickListener) {
        mNegativeListener = onClickListener;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                if (mNegativeListener != null) {
                    mNegativeListener.onClick();
                }
                break;
            case R.id.btn_confirm:
                if (mPositiveListener != null) {
                    mPositiveListener.onClick();
                }
                break;
        }
        dismiss();
    }

    public static final class Builder {
        private String title;
        private OnClickListener mPositiveListener;
        private OnClickListener mNegativeListener;

        public Builder() {
            title = "";
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPositiveButton(OnClickListener onClickListener) {
            mPositiveListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(OnClickListener onClickListener) {
            mNegativeListener = onClickListener;
            return this;
        }


        public DialogFragment build() {
            EasyDialogFragment dialogFragment = new EasyDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_TITLE, title);
            dialogFragment.setArguments(bundle);
            dialogFragment.setPositiveListener(mPositiveListener);
            dialogFragment.setNegativeListener(mNegativeListener);
            return dialogFragment;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTitle = bundle.getString(KEY_TITLE);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 圆角背景
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View rootView = inflater.inflate(R.layout.layout_easy_dialogfragment, container, false);
        mTvTitle = rootView.findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);
        mBtnCancel = rootView.findViewById(R.id.btn_cancel);
        mBtnConfirm = rootView.findViewById(R.id.btn_confirm);
        mBtnCancel.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
        return rootView;
    }
}
