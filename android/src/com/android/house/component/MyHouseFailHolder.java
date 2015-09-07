package com.android.house.component;

import android.widget.TextView;

public class MyHouseFailHolder{
	public TextView date;
	public TextView time;
	public TextView action;
	public TextView manager;
	
	public void setFailListInfo(String date,String time,String action,String manager){
		this.date.setText("日期");
		this.time.setText("时间");
		this.action.setText("行为");
		this.manager.setText("经纪人");
	}
}