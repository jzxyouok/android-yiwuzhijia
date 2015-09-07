package com.android.manager.component;

import android.view.View;
import android.widget.TextView;

public class RankListHolder{
	public TextView pos;
	public TextView name;
	public TextView money;
	
	public void initHolder(View view,int pos,int name,int money){
		this.pos = (TextView)view.findViewById(pos);
		this.name = (TextView)view.findViewById(name);
		this.money = (TextView)view.findViewById(money);
	}
	
	public void setWalletInfo(String pos,String name,String money){
		this.pos.setText(pos);
		this.name.setText(name);
		this.money.setText(money);
	}
}