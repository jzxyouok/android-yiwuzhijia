package com.android.manager.component;

import com.BeeFramework.view.WebImageView;
import com.android.manager.activity.ManagerMainActivity;
import com.android.manager.activity.MyClientActivity;
import com.android.manager.costants.AppConstants;
import com.android.manager.protocol.Customer;
import com.android.manager.util.JSONUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SuccessClientHolder{
	
	private Context mContext;
	private TextView date;
	private TextView time;
	private TextView name;
	private TextView phone;
	private TextView houseName;
	private TextView housePrice;
	private TextView managerMoney;
	
	private WebImageView img;

	private Customer currentCustomer;
	
	public SuccessClientHolder(Context context)
	{
		this.mContext=context;
	}
	
	/**
	 * 
	 * @param view 子View
	 * @param img 头像
	 * @param date 日期
	 * @param time 时间
	 * @param name 客户名
	 * @param child 评论
	 * @param phone 客户电话
	 * @param houseName 楼盘名
	 * @param housePrice 楼盘总价
	 * @param managerMoney 佣金
	 */
	public void initHolder(View view,int img,int date,int time,int name
			,int phone,int houseName,int housePrice,int managerMoney){
		
		this.date = (TextView)view.findViewById(date);
		this.time = (TextView)view.findViewById(time);
		this.name = (TextView)view.findViewById(name);
		this.phone = (TextView)view.findViewById(phone);
		this.houseName = (TextView)view.findViewById(houseName);
		this.housePrice = (TextView)view.findViewById(housePrice);
		this.managerMoney = (TextView)view.findViewById(managerMoney);
		
		this.img = (WebImageView)view.findViewById(img);
	}

	/**
	 * 
	 * @param view 子View
	 * @param img 头像
	 * @param date 日期
	 * @param time 时间
	 * @param name 客户名
	 * @param child 评论
	 * @param phone 客户电话
	 * @param houseName 楼盘名
	 * @param housePrice 楼盘总价
	 * @param managerMoney 佣金
	 */
	public void setHolderInfo(String date,String time,String name,
			String phone,int houseName,String housePrice,String managerMoney,String imageUrl){
		this.date.setText(date);
		this.time.setText(time);
		this.name.setText(name);
		this.phone.setText(phone);
		if (houseName==1) {
			this.houseName.setText("客户预约");
			
		}else {
			this.houseName.setText("推荐");
			
		}
		this.housePrice.setText(housePrice);
		this.managerMoney.setText(managerMoney);
		if(imageUrl!=null&&!("".equals(imageUrl)))
		{
			img.setImageWithURL(mContext, AppConstants.WEBHOME+JSONUtil.getImagePath(imageUrl));
		}
		
	}
	
	
	public void setHolderInfo(Customer customer)
	{
		this.date.setText("");
		this.time.setText("成交时间："+customer.getCreate_time().substring(0,10));
		this.name.setText("客户："+customer.getUser_name());
		this.phone.setText("电话："+customer.getUser_phone());
		this.houseName.setText(null);
		this.housePrice.setText(null);
		this.managerMoney.setText("佣金："+customer.getLatest_agent_payment()+"元");
		//this.managerMoney.setText();
		//this.img.setImageBitmap(img);
		String imageUrl=customer.getUser_head_pic();
		if(imageUrl!=null&&!("".equals(imageUrl)))
		{
			this.img.setImageWithURL(mContext, AppConstants.WEBHOME+JSONUtil.getImagePath(imageUrl));
		}
	}
	
	public void setCustomer(Customer customer)
	{
		this.currentCustomer=customer;
	}
}
