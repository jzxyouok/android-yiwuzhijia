package com.android.house.component;

import com.android.house.activity.ManagerDetailActivity;
import com.funmi.house.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ManagerMarker{
	private Context mContext;
	
	private View view;
	private TextView mapManagerName;
	private ImageView mapManagerImg;
	private RatingBar mapManagerRating;

	public ManagerMarker(Context context) {
		mContext = context;
		
		view = LayoutInflater.from(mContext).inflate(R.layout.item_manager_map_marker, null);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ManagerDetailActivity.class);
				mContext.startActivity(intent);
			}
		});
		
		mapManagerName = (TextView)view.findViewById(R.id.map_manager_name);
		mapManagerImg = (ImageView)view.findViewById(R.id.map_manager_img);
		mapManagerRating = (RatingBar)view.findViewById(R.id.map_manager_ratingbar);
	}
	
	public View getManagerMarker(){
		return view;
	}
	
	/**
	 * 设置经纪人在地图上marker的信息
	 * @param 经纪人姓名
	 * @param 经纪人头像
	 * @param 经纪人综合评价
	 */
	public void setMarkerInfo(String name, Bitmap img, int ratingNums){
		mapManagerName.setText(name);
		mapManagerImg.setImageBitmap(img);
		mapManagerRating.setNumStars(ratingNums);
	}
}
