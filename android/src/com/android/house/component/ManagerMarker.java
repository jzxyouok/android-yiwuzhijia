package com.android.house.component;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.BeeFramework.view.WebCircleImageView;
import com.android.house.costants.AppConstants;
import com.android.house.protocol.Agent;
import com.android.house.util.JSONUtil;
import com.android.house.util.Util;
import com.android.house.view.CircleImageView;
import com.external.androidquery.auth.GoogleHandle;
import com.funmi.house.R;

public class ManagerMarker{
	
	public static LayoutInflater inflater;
	public static int res=R.layout.item_manager_map_marker;
	private TextView mapManagerName;
	private CircleImageView mapManagerImg;
	private RatingBar mapManagerRating;
	
	private ManagerMarker()
	{
		
	}
	public static View  getManagerMarker(Context context,Agent agent, boolean isChangedCity){
		
		if(inflater==null)
		{
			inflater=LayoutInflater.from(context);
		}
		
		View view=inflater.inflate(res,null);
		TextView mapManagerName = (TextView)view.findViewById(R.id.map_manager_name);
		RatingBar mapManagerRating = (RatingBar)view.findViewById(R.id.map_manager_ratingbar);
		CircleImageView mapManagerImg = (CircleImageView) view.findViewById(R.id.map_manager_img);
		Log.d("huang - agent.getPic()", agent.getPic());
//		Drawable imageHolder = Resources.getSystem().getDrawable(R.drawable.ic_launcher);
//		mapManagerImg.setImageWithURL(context, AppConstants.WEBHOME+agent.getPic(),imageHolder);
//		mapManagerImg.setImageWithURL(context, AppConstants.WEBHOME+agent.getPic());
//		mapManagerImg.setImageBitmap(agent.getBitmap());
//		mapManagerImg.setImageWithURL(context, AppConstants.WEBHOME + JSONUtil.getImagePath(agent.getPic()));
		Log.d("huang", AppConstants.WEBHOME + JSONUtil.getImagePath(agent.getPic()));
//		if (agent.getBitmap() != null) {
//			mapManagerImg.setImageBitmap(agent.getBitmap());
//		} else {
//			try {
//				byte[] data = Util.getImage(AppConstants.WEBHOME + JSONUtil.getImagePath(agent.getPic()));  
//		        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);  //生成位图  
//		        Log.d("huang", "get bitmap");
////		        mapManagerImg.setImageBitmap(bitmap);   //显示图片
//		        mapManagerImg.setImageBitmap(bitmap);
//		        Log.d("huang", "set bitmap");
//		        agent.setBitmap(bitmap);
//		        bitmap = null;
//		        data = null;
//			}catch(IOException e){
//				  
		
		
		
//			}
//		}
		
		if (agent.getBitmap() != null) {
			mapManagerImg.setImageBitmap(agent.getBitmap());
		}
		
		mapManagerName.setText(agent.getAgent_name());
		float score=agent.getAve_score();
		if(score==0)
		{
//			score=1;
			Log.d("mao","评分为0");
		}
		mapManagerRating.setRating(score);
		return view;
	}
	
	/**
	 * 设置经纪人在地图上marker的信息
	 * @param 经纪人姓名
	 * @param 经纪人头像
	 * @param 经纪人综合评价
	 */
}
