package me.mundane.jswithandroid;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.HashMap;
import java.util.Set;

public class WebActivity2 extends AppCompatActivity {

    private WebView mWebView;
    private static final String TAG = "WebActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);
        mWebView = findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.loadUrl("file:///android_asset/web.html");

        mWebView.addJavascriptInterface(new AndroidtoJs(), "jsCallAndroid");

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            // 拦截JS的确认框
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                    JsPromptResult result) {
                // 根据协议的参数，判断是否是所需要的url(原理同方式2)
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                // 假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

                Uri uri = Uri.parse(message);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if ( uri.getScheme().equals("js")) {
                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {
                        // 执行JS所需要调用的逻辑
                        System.out.println("js调用了Android的方法");
                        // 可以在协议上带有参数并传递到Android上
                        HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();

                        //参数result:代表消息框的返回值(输入值)
                        result.confirm("js调用了Android的方法成功啦");
                    }
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 步骤2：根据协议的参数，判断是否是所需要的url
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                // 假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

                Uri uri = Uri.parse(url);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if ( uri.getScheme().equals("js")) {
                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {
                        //  步骤3：
                        // 执行JS所需要调用的逻辑
                        System.out.println("js调用了Android的方法");
                        // 可以在协议上带有参数并传递到Android上
                        HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();

                    }

                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    // 定义一个与JS对象映射关系的Android类AndroidtoJs , 继承自Object类
    private class AndroidtoJs extends Object {
        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void jsMethod(String msg) {
            Log.d(TAG, "java调用js方法的返回值是" + msg);
        }

        @JavascriptInterface
        public void jsCall(String msg) {
            Log.d(TAG, "jsCall: " + msg);
        }
    }
}
