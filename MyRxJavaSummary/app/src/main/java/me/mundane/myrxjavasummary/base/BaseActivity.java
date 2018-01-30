package me.mundane.myrxjavasummary.base;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mundane on 2018/1/30 上午11:31
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
