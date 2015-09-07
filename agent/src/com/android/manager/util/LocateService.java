package com.android.manager.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.android.manager.model.UserLocationModel;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;


public class LocateService extends Service {
	private Context mContext;
	
	private final IBinder myBinder = new LocateBinder();  
	
	public LocationClient mLocationClient = null;
	public MyLocationListener mMyLocationListener;
	
	private UserLocationModel ulModel;
	
	private Intent intent;
	
	private String tempcoor="gcj02";
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	
	@Override
	public IBinder onBind(Intent intent) {
		return myBinder;
	}
	
	@Override
	public void onCreate() {
		mContext = this.getApplicationContext();
		ulModel = new UserLocationModel(mContext);
		
		initLocateFunc();
	}
	
	public void initLocateFunc(){
		mMyLocationListener = new MyLocationListener();
		mLocationClient = new LocationClient(mContext);
		
		mLocationClient.registerLocationListener(mMyLocationListener);
		
		InitLocation();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		this.intent = intent;
		mLocationClient.start();
	}
	
	@Override
	public void onDestroy() {
		mLocationClient.stop();
	}

	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
//		option.setLocationMode(tempMode);//设置定位模式
//		option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
		option.setOpenGps(true);	
		option.setAddrType("all");
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		int span = 30000;
		option.setScanSpan(span);
		mLocationClient.setLocOption(option);
	}
	
	 public class LocateBinder extends Binder {  
		 LocateService getService() {  
	            return LocateService.this;  
	        }  
	} 
	 
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			ulModel.setUserLocationInfo(location.getLatitude(), location.getLongitude());
			ulModel.postUserInfo();
		}
	}
}
