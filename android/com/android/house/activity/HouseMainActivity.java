package com.android.house.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.house.model.HouseModel;
import com.android.house.protocol.House;
import com.android.house.util.Util;
import com.android.house.view.DragLayout;
import com.android.house.view.DragLayout.DragListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.house.R;
import com.nineoldandroids.view.ViewHelper;

public class HouseMainActivity extends BaseActivity implements OnClickListener{
	private DragLayout dl;
	
	private TextView userName;
	private TextView userMoney;
	private TextView getUserMoney;
	
	private TextView funcMyHouse;
	private TextView funcMyWallet;
	private TextView funcChangePsd;
	
	private TextView share;
	private TextView about;
	private TextView feedback;
	
	private TextView house_main_manager;
	private TextView house_main_new_house;
	private TextView house_main_free_car;
	
	private TextView loginOut;
	
	private ImageView userImg;
	private ImageView profile_icon;
	
	private ImageView carImg;
	
	/*
	 * 地图相关
	 */
	private MapView mHouseMainMapView;
	private BaiduMap mHouseMainMap;
	private HouseModel mHouseModel;
	// BitmapDescriptor是sdk中用来生成标记物背景的元素
	private BitmapDescriptor mHouseMarkerDrawable;//=BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
	private boolean isFirstLocate=true;
	private MyLocationData mLocationData;
	private LocationClient mLocationClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initDragLayout();
		initHouseMainMap();
	}

	private void initView() {
		userName = (TextView)findViewById(R.id.user_name);
		userMoney = (TextView)findViewById(R.id.user_money);
		getUserMoney = (TextView)findViewById(R.id.user_get_money);
		
		funcMyHouse = (TextView)findViewById(R.id.my_house);
		funcMyWallet = (TextView)findViewById(R.id.my_wallet);
		funcChangePsd = (TextView)findViewById(R.id.change_psd);
		
		share = (TextView)findViewById(R.id.share);
		about = (TextView)findViewById(R.id.about);
		feedback = (TextView)findViewById(R.id.feedback);
		
		house_main_manager = (TextView)findViewById(R.id.house_main_manager);
		house_main_new_house = (TextView)findViewById(R.id.house_main_new_house);
		house_main_free_car = (TextView)findViewById(R.id.house_main_free_car);
		
		loginOut = (TextView)findViewById(R.id.login_out);
		
		userImg = (ImageView)findViewById(R.id.user_img);
		profile_icon = (ImageView) findViewById(R.id.profile_icon);
		carImg = (ImageView)LayoutInflater.from(this)
				.inflate(R.layout.item_freecar_map_marker, null).findViewById(R.id.map_freecar_marker);
		
		setClickListener();
	}

	private void setClickListener(){
		getUserMoney.setOnClickListener(this);
		
		funcMyHouse.setOnClickListener(this);
		funcMyWallet.setOnClickListener(this);
		funcChangePsd.setOnClickListener(this);
		
		share.setOnClickListener(this);
		about.setOnClickListener(this);
		feedback.setOnClickListener(this);
		
		house_main_manager.setOnClickListener(this);
		house_main_new_house.setOnClickListener(this);
		house_main_free_car.setOnClickListener(this);
		
		loginOut.setOnClickListener(this);
		
		profile_icon.setOnClickListener(this);
	}
	
	private void initDragLayout() {
		dl = (DragLayout) findViewById(R.id.dl);
		dl.setDragListener(new DragListener() {
			@Override
			public void onOpen() {
			}

			@Override
			public void onClose() {
			}

			@Override
			public void onDrag(float percent) {
				ViewHelper.setAlpha(profile_icon, 1 - percent);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		
		switch(v.getId()){
		case R.id.house_main_manager:
			break;
			
		case R.id.house_main_free_car:
			break;
			
		case R.id.house_main_new_house:
			break;
			
		case R.id.house_main_search:
			break;
		
		case R.id.user_get_money:
			break;
			
		case R.id.my_wallet:
			break;
			
		case R.id.my_house:
			break;
			
		case R.id.change_psd:
			intent = new Intent(HouseMainActivity.this, ChangePsdActivity.class);
			startActivity(intent);
			break;
			
		case R.id.share:
			intent = new Intent(HouseMainActivity.this, ShareActivity.class);
			startActivity(intent);
			break;
			
		case R.id.about:
			intent = new Intent(HouseMainActivity.this, AboutActivity.class);
			startActivity(intent);
			break;
			
		case R.id.feedback:
			intent = new Intent(HouseMainActivity.this, FeedbackActivity.class);
			startActivity(intent);
			break;
			
		case R.id.login_out:
			break;
			
		case R.id.profile_icon:
			dl.open();
			break;
		case R.id.house_main_menu:
			{
				System.out.println("onClick  on view id="+R.id.house_main_menu);
				this.mHouseModel.initHouseData();
			}
			break;
		}
	}

	public ImageView getFreeCarMarker(){
		return carImg;
	}

	/*
	 * 复写on方法
	 */
	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		this.mHouseMainMapView.onResume();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		this.mHouseMainMapView.onPause();
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		this.mHouseMainMapView.onDestroy();
		super.onDestroy();
	}

	 class HouseMainBusinessResponse implements BusinessResponse
	 {
		
		@Override
		public void OnMessageResponse(String url, JSONObject jo,
				AjaxStatus status) throws JSONException {
			// TODO 自动生成的方法存根
			List<House> houseTotalList=HouseMainActivity.this.mHouseModel.getHistoryHouse();
			Toast.makeText(HouseMainActivity.this, "获得了"+houseTotalList.size()+"条房源信息", Toast.LENGTH_SHORT).show();
			for(int i=0;i<houseTotalList.size();i++)
			{
				
				House house=houseTotalList.get(i);
				System.out.println(house.getHouse_name());
				LatLng ll=new LatLng(house.getHouse_latitude(), house.getHouse_longtitude());
				OverlayOptions olo=new MarkerOptions().position(ll).icon(mHouseMarkerDrawable);
				mHouseMainMap.addOverlay(olo);
				if(i ==houseTotalList.size()-1)
				{
					//将焦点移动过去
					MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(ll);
					mHouseMainMap.setMapStatus(msu);
				}
			}
		}
		@Override
		public boolean equals(Object o) {
			// TODO 自动生成的方法存根
			return o.getClass().equals(this.getClass());
		}
		
		
		 
	 }
	
	 @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 getMenuInflater().inflate(R.menu.main, menu);
			return true;
	}
	 
	 @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO 自动生成的方法存根
		 switch (item.getItemId()) {
			case R.id.mainview_menu_normal:
				this.mHouseMainMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
				break;
			case R.id.mainview_menu_satellite:
				this.mHouseMainMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
				break;
			case R.id.mainview_menu_traffic:
					{
						if(this.mHouseMainMap.isTrafficEnabled())
						{
							item.setTitle("街道模式(off)");
							this.mHouseMainMap.setTrafficEnabled(false);
						}
						else
						{
							item.setTitle("街道模式(on)");
							this.mHouseMainMap.setTrafficEnabled(true);
						}
					}
				break;
			case R.id.mainview_menu_findhouse:
				{
					
					this.mHouseModel.initHouseData();
				}
				break;
			case R.id.mainview_menu_locate:
				{
					if(mLocationClient!=null&&!mLocationClient.isStarted())
					{
						mLocationClient.start();
					}
					MapStatusUpdate msu =MapStatusUpdateFactory.newLatLngZoom(
							new LatLng(mLocationData.latitude, mLocationData.longitude), 16);
					mHouseMainMap.setMapStatus(msu);
				}
				break;
			default:
				break;
			}
			return true;
	}

	 private void initHouseMainMap()
	{
		this.mHouseMainMapView=(MapView)findViewById(R.id.house_main_mapview);
		
		MapStatusUpdate msu_DefualtZoom=MapStatusUpdateFactory.zoomTo(14);

		this.mHouseMainMap=this.mHouseMainMapView.getMap();
		this.mHouseMainMap.setMapStatus(msu_DefualtZoom);
		
		this.mHouseModel=new HouseModel(this);
		this.mHouseModel.addResponseListener(new HouseMainBusinessResponse());
		
		LayoutInflater layoutInflater=LayoutInflater.from(this);
		View view=layoutInflater.inflate(R.layout.item_manager_map_marker, null);
		//这里就是用一个布局文件 来加载View来生成BitmapDescriptor
		mHouseMarkerDrawable=BitmapDescriptorFactory.fromView(view);
		
		startLocate();
	}
		
		
	private void startLocate()
	{
		//自动设置当前的位置
		this.mHouseMainMap.setMyLocationEnabled(true);
		
		mLocationClient=new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(new MyLocationListner());
		
		LocationClientOption llo=new LocationClientOption();
		
		llo.setOpenGps(true);
		llo.setAddrType("all");
		llo.setScanSpan(6000);
		
		mLocationClient.setLocOption(llo);
		mLocationClient.start();
	}
	 
	class MyLocationListner implements BDLocationListener
	{
	
		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO 自动生成的方法存根
			System.out.println("定位回调方法");
			
				mLocationData=new MyLocationData.Builder()//
							  .latitude(location.getLatitude())//
							  .longitude(location.getLongitude())//
							  .accuracy(location.getRadius())//
							  .build();
				mHouseMainMap.setMyLocationData(mLocationData);
				if(isFirstLocate)
				{
					MapStatusUpdate msu=MapStatusUpdateFactory.newLatLngZoom(
							new LatLng(location.getLatitude(), location.getLongitude()),16);
					mHouseMainMap.setMapStatus(msu);
					isFirstLocate=false;
					mLocationClient.stop();
				}
				
				
		}
		
	}
}
