package com.android.manager.component;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InvalidClientHolder{
	private TextView add;
	private TextView name;
	private TextView house;
	private TextView phone;
	private TextView reason;
	
	private ImageView img;
	
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
			,int phone,int img,int reason){
		this.add = (TextView)view.findViewById(add);
		this.name = (TextView)view.findViewById(name);
		this.house = (TextView)view.findViewById(house);
		this.phone = (TextView)view.findViewById(phone);
		this.reason = (TextView)view.findViewById(reason);
		
		this.img = (ImageView)view.findViewById(img);
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
	public void setHolderInfo(String name,String house,String phone,String reason,Bitmap img){
		this.name.setText(name);
		this.phone.setText(phone);
		this.house.setText(house);
		this.reason.setText(reason);
		
		this.img.setImageBitmap(img);
	}
}
