package com.android.manager.component;

import android.widget.TextView;

public class ManageMoneylListHolder{
	public TextView time;
	public TextView thing;
	public TextView money;
	public TextView status;
	
	public void setWalletInfo(String time,String thing,String money,String status){
		this.time.setText(time);
		this.thing.setText(thing);
		this.money.setText(money);
		this.status.setText(status);
		
	}
}