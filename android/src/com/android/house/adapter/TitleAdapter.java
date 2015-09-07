package com.android.house.adapter;

import java.util.ArrayList;
import java.util.List;

import com.BeeFramework.view.WebImageView;
import com.funmi.house.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class TitleAdapter  extends PagerAdapter{
	
	public List<String> urls;
	
	private List<ImageView> images=new ArrayList<ImageView>();
	
	private LayoutInflater layoutInflater;
	
	private DisplayImageOptions option;
	
	private ImageLoader imageLoader;
	
	public TitleAdapter(Context context)
	{
		layoutInflater=LayoutInflater.from(context);
		option=new DisplayImageOptions.Builder()
        .showImageForEmptyUri(R.drawable.user_info_background) // resource or drawable  
        .showImageOnFail(R.drawable.user_info_background) // resource or drawable  
        .resetViewBeforeLoading(false)  // default  
        .delayBeforeLoading(300)  
        .cacheOnDisc(true)
        .cacheInMemory(true)
        .bitmapConfig(Bitmap.Config.RGB_565)
        .build();  
		imageLoader=ImageLoader.getInstance();
	}
	
	public void bindData( List<String> list	)
	{
		urls=list;
		for(int i=0;i<list.size();i++)
		{
			ImageView image=(ImageView)this.layoutInflater.inflate(R.layout.house_detail_title_cell,null );
			imageLoader.displayImage(list.get(i), image, option);
			images.add(image);
		}
	}
	
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		if(urls.size()==1)
			return 1;
		else
			return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO 自动生成的方法存根
		return arg0==arg1;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO 自动生成的方法存根
		container.removeView(images.get(position%this.images.size()));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		ImageView image=images.get(position%this.images.size());
		container.addView(image);
		return image;
	}
}
