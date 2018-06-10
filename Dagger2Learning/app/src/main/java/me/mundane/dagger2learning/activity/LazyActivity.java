package me.mundane.dagger2learning.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import dagger.Lazy;
import javax.inject.Inject;
import javax.inject.Provider;
import me.mundane.dagger2learning.R;
import me.mundane.dagger2learning.bean.Dog;
import me.mundane.dagger2learning.di.components.DaggerDogComponent;

public class LazyActivity extends AppCompatActivity {
    private static final String TAG = "LazyActivity";
    
    
    @Inject
    Lazy<Dog> mDogLazy;//注入Lazy元素
    
    @Inject
    Provider<Dog> mDogProvider;//注入Provider元素
    
    @Inject
    Dog mDog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy);
        Log.d(TAG, "注入前");
        DaggerDogComponent.create().inject(this);
        Log.d(TAG, "注入完成");
        Log.d(TAG, "mDog = " + mDog);
        Log.d(TAG, "------------------  我是分割线 ------------------");
        // 在这时才创建Dog实例，以后每次调用get会得到同一个Dog对象
        Log.d(TAG, "dogLazy1 = " + mDogLazy.get());
        Log.d(TAG, "dogLazy2 = " + mDogLazy.get());
        Log.d(TAG, "dogLazy3 = " + mDogLazy.get());
        Log.d(TAG, "------------------  我是分割线 ------------------");
        
        // 在这时才创建对象，以后每次调用get会再强制调用对应Module层的Provides方法一次
        // 根据Provides方法具体实现不同， 可能返回跟之前是同一个对象，也可能不同。
        Log.d(TAG, "dogProvider1 = " + mDogProvider.get());
        Log.d(TAG, "dogProvider2 = " + mDogProvider.get());
        Log.d(TAG, "dogProvider3 = " + mDogProvider.get());
        
    }
}
