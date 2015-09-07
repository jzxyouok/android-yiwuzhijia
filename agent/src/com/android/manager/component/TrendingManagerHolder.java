package com.android.manager.component;

import android.view.View;
import android.widget.TextView;

public class TrendingManagerHolder{
	public TextView date;
	public TextView time;
	public TextView name;
	public TextView phone;
	public TextView houseName;
	public TextView housePrice;
	public TextView managerMoney;
	public TextView addRecord;
	
	public int currentPostion;
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
	public void initHolder(View view,int date,int time,int name
			,int phone,int houseName,int housePrice,int managerMoney, int add){
		this.date = (TextView)view.findViewById(date);
		this.time = (TextView)view.findViewById(time);
		this.name = (TextView)view.findViewById(name);
		this.phone = (TextView)view.findViewById(phone);
		this.houseName = (TextView)view.findViewById(houseName);
		this.housePrice = (TextView)view.findViewById(housePrice);
		this.managerMoney = (TextView)view.findViewById(managerMoney);
		this.addRecord=(TextView)view.findViewById(add);
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
			String phone,String houseName,String housePrice,String managerMoney){
		this.date.setText(date);
		this.time.setText(time);
		this.name.setText(name);
		this.phone.setText(phone);
		this.houseName.setText(houseName);
		this.housePrice.setText(housePrice);
		this.managerMoney.setText(managerMoney);
	}
}
