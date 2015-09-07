package com.android.house.adapter;

import java.util.ArrayList;
import java.util.List;

import com.BeeFramework.view.WebImageView;
import com.funmi.house.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class HouseDetailHeaderAdapter extends PagerAdapter {

	
	private Context mContext;
	List<String > urlsList;
	List<WebImageView> viewList=new ArrayList<WebImageView>();
	LayoutInflater layoutInflater;
	public HouseDetailHeaderAdapter(Context context)
	{
		mContext=context;
		layoutInflater=LayoutInflater.from(mContext);
	}
	
	public void bindData(List<String> urlList)
	{
		this.urlsList=urlList;
		for(int i=0;i<urlsList.size();i++)
		{
			WebImageView image=(WebImageView) layoutInflater.inflate(R.layout.activity_housedetial_header_cell, null);
			viewList.add(image);
		}
	}
	
	@Override
	public int getCount() {
		if(urlsList.size()==1)
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
		container.removeView(viewList.get(position%this.viewList.size()));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		WebImageView image=viewList.get(position%this.viewList.size());
		image.setImageWithURL(mContext, this.urlsList.get(position%this.viewList.size()));
		container.addView(image);
		return image;
	}

	
}
