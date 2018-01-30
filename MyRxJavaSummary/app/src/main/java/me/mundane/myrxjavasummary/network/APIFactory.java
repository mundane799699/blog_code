package me.mundane.myrxjavasummary.network;


import me.mundane.myrxjavasummary.network.api.GankAPI;

/**
 * Created by mundane on 2017/12/4 下午4:20
 */

public class APIFactory {
	public static GankAPI createGankAPI() {
		return RetrofitManager.getInstance().getGankAPI();
	}
}
