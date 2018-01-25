package me.mundane.parallaxrecyclerview.rv;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.mundane.parallaxrecyclerview.R;
import me.mundane.parallaxrecyclerview.model.GankMeiziResult;
import me.mundane.parallaxrecyclerview.view.ParallaxImageView;

/**
 * Created by mundane on 2017/12/23 下午5:10
 */
public class MeizhiAdapter extends RecyclerView.Adapter<MeizhiAdapter.ViewHolder> {
	
	private List<GankMeiziResult.GankBeauty> mDataList;
    public static final int KEY_POSITION = 4;

    public MeizhiAdapter(List<GankMeiziResult.GankBeauty> list) {
		mDataList = list;
	}
	
	private @LayoutRes int provideItemLayout() {
		return R.layout.item_layout;
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(provideItemLayout(), parent, false);
		return new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.bind(mDataList.get(position), position);
	}
	
	@Override
	public int getItemCount() {
		return mDataList == null || mDataList.isEmpty() ? 0 : mDataList.size();
	}
	
	static class ViewHolder extends ParallaxViewHolder {
		ParallaxImageView iv;
		TextView tv;
		Context context;

		@Override
		public int getParallaxImageId() {
			return R.id.iv;
		}

		public ViewHolder(View itemView) {
			super(itemView);
			context = itemView.getContext();
			// findViewById
			iv = itemView.findViewById(R.id.iv);
			tv = itemView.findViewById(R.id.tv);
		}
		
		public void bind(GankMeiziResult.GankBeauty data, int position) {
			Glide.with(context)
			     .load(data.url)
			     .dontAnimate()
			     .into(iv);
			iv.setTag(R.id.tag_position, position);
			tv.setText("标题 " + position);
		}
	}
}