package com.android.house.component;

import com.android.house.activity.HouseDetailActivity;
import com.funmi.house.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HouseMarker {
	private Context mContext;
	
	private View view;
	private TextView houseName;
	
	public HouseMarker(Context context) {
		mContext = context;
		
		view = LayoutInflater.from(mContext).inflate(R.layout.item_house_map_marker, null);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, HouseDetailActivity.class);
				mContext.startActivity(intent);
			}
		});
		
		houseName = (TextView)view.findViewById(R.id.map_house_name);
	}
	
	public View getHouseMarker(){
		return view;
	}
	
	/**
	 * 设置楼盘名
	 * @param name 楼盘名
	 */
	public void setHouseMarkerName(String name){
		houseName.setText(name);
	}
}
