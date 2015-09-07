package com.android.manager.component;

import android.view.View;
import android.widget.TextView;

public class ManageHouseHolder {
	private TextView name;
	private TextView money;
	private TextView status;
	private TextView endTime;
	private TextView sellTime;
	
	public void initHolder(View view,int name,int money,int status
			,int endTime,int sellTime){
		this.name = (TextView)view.findViewById(name);
		this.money = (TextView)view.findViewById(money);
		this.status = (TextView)view.findViewById(status);
		this.endTime = (TextView)view.findViewById(endTime);
		this.sellTime = (TextView)view.findViewById(sellTime);
	}
	
	/**
	 * 
	 * @param name 楼盘名称
	 * @param money 楼盘价钱
	 * @param status 合作状态
	 * @param endTime 结束时间
	 * @param sellTime 销售时间
	 * @param startTime 发放时间
	 */
	public void setHolderInfo(String name,String money,String status
			,String endTime,String sellTime){
		this.name.setText(name);
		this.money.setText(money);
		this.status.setText(status);
		this.endTime.setText(endTime);
		this.sellTime.setText(sellTime);
	}
}
