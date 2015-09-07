package com.android.house.component;

import android.widget.TextView;

public class HouseSearchListHolder{
	public TextView age;
	public TextView car;
	public TextView name;
	public TextView from;
	public TextView selling;
	
	public void setSearchListInfo(String age,String name,String car,String from,String selling){
		this.age.setText(age);
		this.car.setText(car);
		this.name.setText(name);
		this.from.setText(from);
		this.selling.setText(selling);
	}
}
