package me.mundane.parallaxrecyclerview.network;


import me.mundane.parallaxrecyclerview.network.api.GankAPI;
import me.mundane.parallaxrecyclerview.network.api.LofterAPI;

/**
 * Created by mundane on 2017/12/4 下午4:20
 */

public class APIFactory {
	public static GankAPI createGankAPI() {
		return RetrofitManager.getInstance().getGankAPI();
	}

	public static LofterAPI createLofterAPI() {
		return RetrofitManager.getInstance().getLofterAPI();
	}
}
