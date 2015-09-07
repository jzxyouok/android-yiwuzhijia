package com.android.house.component;

import android.widget.TextView;

public class MyHouseSeenListHolder {
	public TextView name;
	public TextView time;
	public TextView position;
	
	/**
	 * 设置列表信息
	 * @param name 楼盘名
	 * @param time 看楼盘的时间
	 * @param pos 楼盘的位置
	 */
	public void setSeenListInfo(String name, String time, String pos){
//		this.name.setText("name");
//		this.time.setText("time");
//		this.position.setText("pos");
		this.name.setText(name);
		this.time.setText(time);
		this.position.setText(pos);
	}
}
