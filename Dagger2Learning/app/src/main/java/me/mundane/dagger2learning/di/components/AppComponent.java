package me.mundane.dagger2learning.di.components;

import dagger.Component;
import javax.inject.Singleton;
import me.mundane.dagger2learning.di.modules.AppModule;
import okhttp3.OkHttpClient;

/**
 * Created by mundane on 2018/5/14 上午11:47
 */

// 这个AppComponent是给其他Component提供依赖的,所以我们就可以不用inject方法
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    // 它的作用就是告诉依赖于 AppComponent 的 Component,
    // AppComponent 能为你们提供 OkHttpClient 对象,
    // 如果没有这个方法, AppComponent 就不能提供 OkHttpClient 对象
    OkHttpClient provideSingletonOkhttpClient();
}
