package com.android.manager.component;

import android.view.View;
import android.widget.TextView;

public class SeeClientHolder {
	private TextView sum;
	private TextView name;
	private TextView area;
	private TextView time;
	private TextView from;
	private TextView seeClient;
	private TextView phone;
	
	/**
	 * @param view 子view
	 * @param sum 意向总价
	 * @param name 客户名称
	 * @param area 意向区域
	 * @param time 推送时间
	 * @param seeClient 接待
	 */
	public void initHolder(View view,int sum,int name,int area
			,int time,int from,int phone,int seeClient){
		this.sum = (TextView)view.findViewById(sum);
		this.name = (TextView)view.findViewById(name);
		this.area = (TextView)view.findViewById(area);
		this.time = (TextView)view.findViewById(time);
		this.from = (TextView)view.findViewById(from);
		this.seeClient = (TextView)view.findViewById(seeClient);
		this.phone=(TextView) view.findViewById(phone);
	}
	
	
	/**
	 * 
	 * @param sum 意向总价
	 * @param name 客户名称
	 * @param area 意向区域
	 * @param time 推送时间
	 * @param seeClient 接待
	 */
	public void setHolderInfo(String sum,String name,String area
			,String time,String from,String phone){
		this.sum.setText(sum);
		this.name.setText(name);
		this.area.setText(area);
		this.time.setText(time);
		this.from.setText(from);
		this.seeClient.setText("接待");
		this.phone.setText(phone);
	
	}
}
