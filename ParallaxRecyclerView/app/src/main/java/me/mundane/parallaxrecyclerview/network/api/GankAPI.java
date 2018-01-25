package me.mundane.parallaxrecyclerview.network.api;

import me.mundane.parallaxrecyclerview.model.GankMeiziResult;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by mundane on 2017/12/4 上午12:01
 */

public interface GankAPI {
	// http://gank.io/api/data/福利/10/1
	@GET("福利/{pagesize}/{page}")
	Observable<GankMeiziResult> getMeiziData(@Path("pagesize") int pageSize, @Path("page") int page);
}
