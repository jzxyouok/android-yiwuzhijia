package com.android.house.component;

import android.widget.RatingBar;
import android.widget.TextView;

public class ManagerDetailListHolder{
	public TextView time;
	public TextView phone;
	public TextView house;
	public TextView customer;
	public TextView evaluate;
	
	public RatingBar evaluateRate;
	
	public void setDetail(String time, String phone, String house, String customer, String evaluate, int evaluateRate){
		this.time.setText(time);
		this.phone.setText("手机尾号："+phone);
		this.house.setText("购买:"+house);
		this.customer.setText(customer);
		this.evaluate.setText(evaluate);
		
		this.evaluateRate.setRating(evaluateRate);
	}
}