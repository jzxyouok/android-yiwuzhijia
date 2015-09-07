package com.android.house.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.BeeFramework.view.WebImageView;
import com.funmi.house.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class HouseDetailAdapter extends BaseAdapter {

	List<String> imageUrls;
	
	LayoutInflater layoutInflater;
	
	Context context;
	
	ImageLoader loader;
	
	DisplayImageOptions option;
	
	
	public HouseDetailAdapter(Context context)
	{
		this.context=context;
		layoutInflater=LayoutInflater.from(context);
		loader=ImageLoader.getInstance();
		option=new DisplayImageOptions.Builder()
	        .showImageForEmptyUri(R.drawable.user_info_background) // resource or drawable  
	        .showImageOnFail(R.drawable.user_info_background) // resource or drawable  
	        .resetViewBeforeLoading(false)  // default  
	        .delayBeforeLoading(300)  
	        .cacheOnDisc(true)
	        .cacheInMemory(true)
	        .bitmapConfig(Bitmap.Config.RGB_565)
	        .build();  
	}
	
	public void setData(List<String >list)
	{
		this.imageUrls=list;
	}
	
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return this.imageUrls.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		View view=arg1;
		final ViewHolder holder;
		if(arg1==null)
		{
			view=layoutInflater.inflate(R.layout.activity_housedetail_cell,null);
			holder=new ViewHolder();
			holder.image=(ImageView) view.findViewById(R.id.detail_image);
			holder.image.setTag(imageUrls.get(arg0));
			view.setTag(holder);
		}else
		{
			holder=(ViewHolder) view.getTag();
		}
		
		loader.displayImage(imageUrls.get(arg0),holder.image,option,
				new SimpleImageLoadingListener(){

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						if(view.getTag().equals(imageUri))
						{
							((ImageView)view).setImageBitmap(loadedImage);
						}
					}
		});
		
		return view;
	}

	private static class ViewHolder
	{
		String url;
		ImageView image;
	}
}
