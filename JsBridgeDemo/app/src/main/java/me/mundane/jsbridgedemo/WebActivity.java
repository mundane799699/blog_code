package me.mundane.jsbridgedemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

public class WebActivity extends Activity {

    private static final String TAG = "WebActivity";


    private BridgeWebView mBridgeWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);
        mBridgeWebView = (BridgeWebView) findViewById(R.id.web_view);
        mBridgeWebView.loadUrl("file:///android_asset/web.html");
        mBridgeWebView.registerHandler("takePhoto", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "收到js的消息 = " + data);
                String avatarUrl = getAvatarByUserId(data);
                String msg = "用户的applyId是 = " + data;
                Toast.makeText(WebActivity.this, msg, Toast.LENGTH_SHORT).show();
                function.onCallBack(avatarUrl);
            }
        });


    }
    
    // 假装拍照获取照片, 然后将拍的照片上传到服务器
    // 上传到服务器需要用到用户的userId
    // 然后将服务器返回的用户的头像链接再返回给js, 设置到h5上
    private String getAvatarByUserId(String userId) {
        String url = null;
        if (TextUtils.equals("1234", userId)) {
            url = "http://ww3.sinaimg.cn/mw690/96a29af5jw8fdfu43tnvlj20ro0rotab.jpg";
        }
        return url;
    }
    
    public void javaCallJs(View view) {
        mBridgeWebView.callHandler("functionInJs", "这是java发给js的数据", new CallBackFunction() {

            @Override
            public void onCallBack(String data) {
                Log.i(TAG, "来自js的应答" + data);
            }

        });
    }

}
