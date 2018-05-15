package me.mundane.jswithandroid;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewActivity1 extends AppCompatActivity {

    private WebView mWebView;

    private static final String TAG = "WebViewActivity1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view1);
        mWebView = findViewById(R.id.webview);

        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        mWebView.loadUrl("file:///android_asset/web.html");
        mWebView.addJavascriptInterface(new AndroidtoJs(), "jsCallAndroid");
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(WebViewActivity1.this).setTitle("Alert")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setCancelable(true)
                        .create()
                        .show();
                return true;
            }
        });
    }

    public void loadUrlCallJs(View view) {
        String param = "this is param";
        mWebView.loadUrl("javascript:callJS2('" + param + "')");
    }


    @RequiresApi(api = VERSION_CODES.KITKAT)
    public void evaluateJavascript(View view) {
        mWebView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.d(TAG, "value = " + value);
            }
        });
    }

    // 混合使用
    public void mixMethod(View view) {

        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.d(TAG, "value = " + value);
                }
            });
        } else {
            mWebView.loadUrl("javascript:callJS()");
        }
    }

    // 定义一个与JS对象映射关系的Android类AndroidtoJs , 继承自Object类
    public class AndroidtoJs extends Object {
        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void jsMethod(String msg) {
            Log.d(TAG, "java调用js方法的返回值是" + msg);
        }
    }

}
