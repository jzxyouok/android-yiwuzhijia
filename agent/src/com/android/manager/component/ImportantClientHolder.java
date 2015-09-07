package com.android.manager.component;

import com.BeeFramework.view.WebImageView;
import com.android.manager.activity.AddClientActivity;
import com.android.manager.activity.MyClientActivity;
import com.android.manager.costants.AppConstants;
import com.android.manager.protocol.Customer;
import com.android.manager.util.JSONUtil;
import com.android.manager.util.UserInfoCacheUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ImportantClientHolder {
	private Context mContext;
	
	private TextView add;
	private TextView name;
	private TextView house;
	private TextView phone;
	
	private WebImageView img;
	
	
	private Customer currentCustomer;
	
	public ImportantClientHolder(Context context) {
		mContext = context;
	}
	/**
	 * 设置资源文件
	 * @param view 子View
	 * @param add 增加按钮
	 * @param name 客户名字
	 * @param house 楼盘名称
	 * @param phone 客户电话
	 * @param img 客户头像
	 */
	public void initHolder(View view,int add,int name,int house
			,int phone,int img){
		
		this.add = (TextView)view.findViewById(add);
		int stype=UserInfoCacheUtil.getCacheInfo(mContext).getStype();
		if (stype==2) {
			this.add.setVisibility(View.GONE);
		}else {
			this.add.setVisibility(View.VISIBLE);
		}
		this.add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, MyClientActivity.class);
				Bundle bundle =new Bundle();
				bundle.putSerializable("customer", currentCustomer);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
		});
		this.name = (TextView)view.findViewById(name);
		this.house = (TextView)view.findViewById(house);
		this.phone = (TextView)view.findViewById(phone);
		
		this.img = (WebImageView)view.findViewById(img);
	}
	
	
	public void setCustomer(Customer customer)
	{
		this.currentCustomer=customer;
	}

	/**
	 * 设置资源文件
	 * @param view 子View
	 * @param add 增加按钮
	 * @param name 客户名字
	 * @param house 楼盘名称
	 * @param phone 客户电话
	 * @param img 客户头像
	 */
	public void setHolderInfo(String name,int house,String phone,String  imageUrl){
		this.name.setText(name);
		
		this.phone.setText(phone);
		if(imageUrl!=null&&!("".equals(imageUrl)))
		{
			img.setImageWithURL(mContext, AppConstants.WEBHOME+JSONUtil.getImagePath(imageUrl));
		}
		this.house.setVisibility(View.GONE);
	}
}
