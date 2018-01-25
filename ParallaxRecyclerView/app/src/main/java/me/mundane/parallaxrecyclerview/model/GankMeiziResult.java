package me.mundane.parallaxrecyclerview.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mundane on 2017/12/4 下午3:19
 */

public class GankMeiziResult {
	
	public boolean error;
	@SerializedName("results")
	public List<GankBeauty> beauties;
	
	public static class GankBeauty {
		public String _id;
		public String createdAt;
		public String desc;
		public String publishedAt;
		public String source;
		public String type;
		public String url;
		public boolean used;
		public String who;
	}
}
