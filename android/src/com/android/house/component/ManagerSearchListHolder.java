package com.android.house.component;

import android.widget.TextView;

public class ManagerSearchListHolder{
	public TextView name;
	public TextView position;
	
	public void setManagerSearchInfo(String name, String position){
		this.name.setText(name);
		this.position.setText(position);
	}
}
