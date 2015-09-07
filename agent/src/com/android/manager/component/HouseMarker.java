package com.android.manager.component;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.manager.R;
import com.android.manager.protocol.House;

public class HouseMarker {
	private HouseMarker() {
		
	}
	
	public static View getHouseMarker(Context context,House house,View view){
		view = LayoutInflater.from(context).inflate(R.layout.item_house_map_marker, null);
		TextView houseName = (TextView)view.findViewById(R.id.map_house_name);
		houseName.setText(house.getName());
		houseName.getBackground().setAlpha(200);
		return view;
	}
	
	
	public static View getDefaultHouseMarker(Context context,String text)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.item_house_map_marker, null);
		TextView houseName = (TextView)view.findViewById(R.id.map_house_name);
		houseName.setText(text);
		return view;
	}
	/**
	 * 设置楼盘名
	 * @param name 楼盘名
	 */
	//public void setHouseMarkerName(String name){
	//	houseName.setText(name);
	//}
}
