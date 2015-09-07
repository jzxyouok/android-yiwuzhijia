package com.android.house.component;

import com.android.house.activity.HouseDetailActivity;
import com.funmi.house.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FreeCarMarker {
	private Context mContext;
	
	private View view;
	private ImageView carImg;
	
	public FreeCarMarker(Context context) {
		mContext = context;
		
		view = LayoutInflater.from(mContext).inflate(R.layout.item_map_freecar_marker, null);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, HouseDetailActivity.class);
				mContext.startActivity(intent);
			}
		});
		
		carImg = (ImageView)view.findViewById(R.id.map_freecar_img);
	}
	
	public View getCarMarker(){
		return carImg;
	}
	
	/**
	 * 设置楼盘名
	 * @param name 楼盘名
	 */
	public void setHouseMarkerName(Bitmap img){
		carImg.setImageBitmap(img);
	}
}
