package com.android.manager.activity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.adapter.HouseDetailAdapter;
import com.android.manager.adapter.HouseDetailHeaderAdapter;
import com.android.manager.adapter.TitleAdapter;
import com.android.manager.animations.DepthPageTransformer;
import com.android.manager.component.HouseMarker;
import com.android.manager.costants.AppConstants;
import com.android.manager.model.FreeAppointmentModel;
import com.android.manager.model.GetHouseImageModel;
import com.android.manager.model.GetRecAgentModel;
import com.android.manager.protocol.Agent;
import com.android.manager.protocol.House;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.JSONUtil;
import com.android.manager.util.UserInfoCacheUtil;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapTouchListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapFragment;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.external.androidquery.callback.AjaxStatus;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HouseDetailActivity extends Activity implements OnClickListener, BusinessResponse {

	private TextView title;
	private TextView price;
	private TextView area;

	private TextView decorate;
	private TextView plotRatio;
	private TextView houseManage;
	private TextView greeningRate;

	private TextView fee;
	private TextView phone;
	private TextView house_position;
	private TextView apointAgent;
	private TextView house_detail_imagescount;

	private ImageView back;
	private ImageView dial;

	private Gallery innerListView;
	private ViewPager viewPager;

	boolean isFirstIn = true;

	private House house;

	private List<String> headUrls = new ArrayList<String>();
	private List<String> imageUrls = new ArrayList<String>();

	private HouseDetailAdapter adapter;
	private HouseDetailHeaderAdapter headerAdapter;
	private TitleAdapter titleAdapter;

	private ImageView view;
	private GetHouseImageModel model;
	GetRecAgentModel getRecAgentModel;
	FreeAppointmentModel appointmentModel;

	private Agent agent = new Agent();

	private ImageView navigateImg;

	private LooperThread looperThread;
	MapFragment mMapFragment;
	BaiduMap mMap;
	MapView mMapView;
	Marker marker;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Log.d("mao", "图片url加载成功");
				if (imageUrls.size() != 0) {
					adapter.setData(imageUrls);
					innerListView.setAdapter(adapter);

					innerListView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							Log.d("mao", "点击出大图");
							// bigImagePopup.showBigImage(imageUrls.get(position),
							// price,mMapFragment.getView().getWidth(),mMapFragment.getView().getWidth());
							Intent intent = new Intent(HouseDetailActivity.this, HousePicDetailActivity.class);
							intent.putStringArrayListExtra("picUrl", (ArrayList<String>) imageUrls);
							startActivity(intent);
						}

					});
				}
				if (headUrls.size() != 0) {
					titleAdapter.bindData(headUrls);
					// headerAdapter.bindData(headUrls);
					viewPager.setAdapter(titleAdapter);
					if (headUrls.size() > 1) {
						looperThread.start();
					}
				}
			} else if (msg.what == 0) {
				Toast.makeText(HouseDetailActivity.this, "房屋图片加载失败", Toast.LENGTH_SHORT).show();
			} else if (msg.what == 2) {
				HouseDetailActivity.this.model.getHouseDetail(house);
			} else if (msg.what == 3) {

				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_house_detail);
		this.house = (House) getIntent().getExtras().getSerializable("house");
		initView();
		initData(house);
		initNetwork();

		looperThread = new LooperThread();
		initMap();
	}

	private void initNetwork() {
		model = new GetHouseImageModel(this);
		model.addResponseListener(new HouseDetailResponse());
		getRecAgentModel = new GetRecAgentModel(this);
		appointmentModel = new FreeAppointmentModel(this);
		getRecAgentModel.addResponseListener(this);
		getRecAgentModel.getRecommendAgent(house);
		appointmentModel.addResponseListener(this);

	}

	@SuppressWarnings("deprecation")
	private void initView() {
		title = (TextView) findViewById(R.id.title_text);
		price = (TextView) findViewById(R.id.house_detail_price);
		area = (TextView) findViewById(R.id.area_name);

		plotRatio = (TextView) findViewById(R.id.floor_rate);
		decorate = (TextView) findViewById(R.id.decorate_type);
		greeningRate = (TextView) findViewById(R.id.green_rate);
		houseManage = (TextView) findViewById(R.id.property_type);
		apointAgent = (TextView) findViewById(R.id.house_detail_dialog_apoint);
		apointAgent.setVisibility(View.GONE);
		house_detail_imagescount = (TextView) findViewById(R.id.house_detail_imagescount);
		fee = (TextView) findViewById(R.id.property_fee);
		house_position = (TextView) findViewById(R.id.location_address);
		phone = (TextView) findViewById(R.id.house_detail_dialog_phone);

		back = (ImageView) findViewById(R.id.title_back);
		dial = (ImageView) findViewById(R.id.house_detail_dialog_icon);

		innerListView = (Gallery) findViewById(R.id.house_detail_list);

		viewPager = (ViewPager) findViewById(R.id.house_detail_outter_gallery);
		viewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
		viewPager.setPageTransformer(true, new DepthPageTransformer());

		navigateImg = (ImageView) findViewById(R.id.house_detail_navi);
		try {
			Field field = ViewPager.class.getDeclaredField("mScroller");
			field.setAccessible(true);
			FixedSpeedScroller fixedSpeedScroller = new FixedSpeedScroller(this, new AccelerateDecelerateInterpolator());
			field.set(viewPager, fixedSpeedScroller);
		} catch (Exception e) {

		}
		mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.house_detail_fragment);

		adapter = new HouseDetailAdapter(this);
		headerAdapter = new HouseDetailHeaderAdapter(this);
		titleAdapter = new TitleAdapter(this);

		setClickListener();
	}

	private void initMap() {
		LatLng mapCenter = new LatLng(house.getLat(), house.getLng());
		MapStatus mapStatus = new MapStatus.Builder().target(mapCenter).zoom(17).build();
		mMapView = mMapFragment.getMapView();
		mMap = this.mMapFragment.getBaiduMap();
		mMapView.showZoomControls(false);
		mMapView.showScaleControl(false);
		mMapView.setClickable(false);
		mMap.setOnMapTouchListener(new OnMapTouchListener() {
			@Override
			public void onTouch(MotionEvent arg0) {

			}
		});
		MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mapStatus);
		mMap.setMapStatus(msu);
		View view = null;
		BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(HouseMarker.getHouseMarker(this, house, view));
		OverlayOptions olo = new MarkerOptions().position(mapCenter).icon(bitmapDescriptor);
		marker = (Marker) mMap.addOverlay(olo);
		bitmapDescriptor.recycle();
		view = null;
	}

	private void setClickListener() {
		back.setOnClickListener(this);
		dial.setOnClickListener(this);
		apointAgent.setOnClickListener(this);
		navigateImg.setOnClickListener(this);
	}

	// 1 住宅 //2是商业
	private void initData(House house) {
		title.setText(house.getName());
		area.setText(area.getText() + house.getArea_name());
		decorate.setText(decorate.getText() + "精装");
		fee.setText(fee.getText().toString() + (house.getProperty_price()));
		this.house_position.setText(house_position.getText() + house.getArea_name() + house.getHouse_address());
		this.plotRatio.setText(plotRatio.getText().toString() + house.getFloor_area_ratio());
		this.greeningRate.setText(greeningRate.getText().toString() + house.getGreen_rate());
		int property_type = house.getProperty_type();
		String property_type_name = property_type == 1 ? "住宅" : "商业";
		this.houseManage.setText(houseManage.getText().toString() + property_type_name);
		this.phone.setText(house.getOffice_phone());
		this.price.setText("" + house.getAverage_price());

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.house_detail_navi: {

		}
			break;

		case R.id.house_detail_dialog_icon:
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone.getText().toString()));
			startActivity(intent);
			break;
		case R.id.house_detail_dialog_apoint: {

			appointmentModel.appointAgent(0, agent.getId(), UserInfoCacheUtil.getCacheInfo(this).getSessionId());

		}
			break;
		}
	}

	class HouseDetailResponse implements BusinessResponse {

		@Override
		public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

			Log.d("mao", jo.toString());
			if (jo != null) {
				JSONArray headimages = jo.optJSONArray("headPicList");
				JSONArray images = jo.optJSONArray("apartMentPicList");
				for (int i = 0; i < headimages.length(); i++) {
					JSONObject entity = headimages.getJSONObject(i);
					String path = entity.optString("pic_path");
					Log.d("mao", path);
					if (path != null && !("".equals(path)))
						;
					{
						headUrls.add(AppConstants.WEBHOME + JSONUtil.getImagePath(path));
					}
				}

				for (int i = 0; i < images.length(); i++) {
					JSONObject entity = images.getJSONObject(i);
					String path = entity.optString("pic_path");
					if (path != null && !("".equals(path)))
						;
					{
						imageUrls.add(AppConstants.WEBHOME + JSONUtil.getImagePath(path));
					}
				}
				int images_count = images.length();
				if (images_count == 0)
					house_detail_imagescount.setText(house_detail_imagescount.getText() + "暂无户型图");
				else
					house_detail_imagescount.setText(house_detail_imagescount.getText().toString() + images_count + "张");
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}

		}

	}

	@Override
	protected void onDestroy() {
		looperThread.shutDown();
		ImageLoader.getInstance().clearMemoryCache();
		System.gc();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		new Thread() {
			@Override
			public void run() {
				if (isFirstIn) {
					int i = 1;
					while (i > 0) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
							Log.d("mao", "thread interrupter");
						}
						i--;
					}

					Message msg = new Message();
					msg.what = 2;
					HouseDetailActivity.this.handler.sendMessage(msg);
					isFirstIn = false;
				}
			}
		}.start();
	}

	private class LooperThread extends Thread {
		private boolean isShutdown = false;

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(5000);
					synchronized (this) {
						if (isShutdown) {
							break;
						}
					}
					Message message = new Message();
					message.what = 3;
					handler.sendMessage(message);

				} catch (Exception e) {
					Log.d("mao", "thread error " + e.getMessage());
				}
			}
			Log.d("mao", "thread shutdown when activity is destroyed");
		}

		public void shutDown() {
			synchronized (this) {
				this.isShutdown = true;
			}
		}
	}

	class FixedSpeedScroller extends Scroller {
		private int mDuration = 1000;

		public FixedSpeedScroller(Context context) {
			super(context);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		public void setmDuration(int time) {
			mDuration = time;
		}

		public int getmDuration() {
			return mDuration;
		}

	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		// 处理房源推荐经纪人的回调
		Log.d("mao", "house detail :" + jo.toString());
		int replyCode = jo.optInt("status");
		if (url.endsWith(ProtocolConst.GET_HOUSEAGENT_INFO)) {

			if (replyCode != 200) {
				toast(jo.optString("msg"));
			} else {
				JSONObject entity = jo.optJSONObject("agent");
				agent.fromJson(entity);
			}
		} else if (url.endsWith("m/user/reserveAgent")) {
			toast(jo.optString("msg"));
		}

	};

	private void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
