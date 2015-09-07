package com.android.house.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.android.api.m;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.MyProgressDialog;
import com.BeeFramework.view.WebImageView;
import com.android.house.businessresponse.LogoutBusinessResponse;
import com.android.house.component.HouseMarker;
import com.android.house.component.ManagerMarker;
import com.android.house.component.SpinnerHolder;
import com.android.house.costants.AppConstants;
import com.android.house.events.OnChangeCityEvent;
import com.android.house.events.OnChangeLoginEvent;
import com.android.house.events.OnChangeTypeEvent;
import com.android.house.events.OnChangeUserInfoEvent;
import com.android.house.events.OnFilterItemClickedEvent;
import com.android.house.events.OnFocusEvent;
import com.android.house.events.OnLocatedEvent;
import com.android.house.events.OnRefreshEvent;
import com.android.house.events.OnReloginEvent;
import com.android.house.model.AgentModel;
import com.android.house.model.GetCompanyInfoModel;
import com.android.house.model.GetUserInfoModel;
import com.android.house.model.HouseModel;
import com.android.house.model.HouseModel.SEARCH_TYPE;
import com.android.house.model.LoadCitylistModel;
import com.android.house.model.LogoutModel;
import com.android.house.protocol.Agent;
import com.android.house.protocol.Area;
import com.android.house.protocol.CacheInfo;
import com.android.house.protocol.City;
import com.android.house.protocol.House;
import com.android.house.protocol.ProtocolConst;
import com.android.house.protocol.User;
import com.android.house.util.JSONUtil;
import com.android.house.util.UserInfoCacheUtil;
import com.android.house.util.headshot.HeadShotUtil;
import com.android.house.view.DragLayout;
import com.android.house.view.DragLayout.DragListener;
import com.android.house.view.DragLayout.Status;
import com.android.house.view.MapDialog;
import com.android.house.view.MyRelativeLayout;
import com.android.house.view.SelectLocationDialog;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.nplatform.comapi.map.MapController;
import com.baidu.nplatform.comapi.map.MapGLSurfaceView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.funmi.house.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class HouseMainActivity extends BaseActivity implements OnClickListener,
		BaiduMap.OnMapLoadedCallback, OnMarkerClickListener, BusinessResponse,
		OnMapClickListener {
	private View header;

	private View houseWrapper;
	private View managerWrapper;
	private View freecarWrapper;

	private DragLayout dl;

	private ImageView locateSelf;

	private TextView userName;
	private TextView userMoney;
	private TextView getUserMoney;

	private TextView price;
	private TextView location;
	private TextView houseType;
	private TextView decoration;
	private TextView wxcode;
	private TextView comanyphone;

	private RelativeLayout search;

	private WebImageView userImg;
	private ImageView areaPickBtn;
	private ImageView profile_icon;

	private ImageView houseImg;
	private ImageView managerImg;
	private ImageView freecarImg;

	private LinearLayout filter;
	private LinearLayout funcMyHouse;
	private LinearLayout funcMyWallet;
	private LinearLayout funcMyManager;

	private LinearLayout loginOut;

	private RelativeLayout share;
	private RelativeLayout about;
	private RelativeLayout feedback;
	private RelativeLayout changePsd;

	private RelativeLayout mapLayout;

	private TextView myManageText;

	private SpinnerHolder holder;
	private PopupWindow window;

	private MapDialog mMapDialog;// 弹窗对话框

	private static final int SEARCH_HOUSE = 0;
	private static final int SEARCH_MANAGER = 1;
	private static final int SEARCH_FREECAR = 2;

	/*
	 * 地图相关
	 */
	private MapView mHouseMainMapView;
	private BaiduMap mHouseMainMap;

	private MyLocationData mLocationData;
	private LocationClient mLocationClient;

	private MapController mapController;

	private boolean requestInfo = true;
	private boolean isMapLoaded = false;

	public static boolean isUserLogion = false;

	private boolean isfirstentry = true;
	public CacheInfo mCacheInfo;

	private int currentShowType = 0;// 0 经纪人 1楼盘 2免费车 3筛选

	private GetCompanyInfoModel getCompanyInfoModel;
	// 退出登录接口
	private LogoutModel mLogoutModel;
	private LogoutBusinessResponse logoutBusinessResponse;
	// 数据接口
	private LoadCitylistModel mLoadCitylistModel;
	private HouseModel mHouseModel;
	private HouseMainBusinessResponse houseResponse;
	private AgentModel mAgentModel;
	private AgentBusinessResponse agentResponse;

	private GetUserInfoModel getUserInfoModel;

	private int titleHeight;
	private int filterHeight;

	private String mLocatedCityName = null;
	private String mCurrentCityName = null;
	private City mCurrentCity = null;
	private City mLocalCity = null;//用户所在城市

	public static List<String> priceData;
	public static List<String> locationData;
	public static List<String> houseTypeData;
	public static List<String> decorationData;

	private static List<String> locateInfo = new ArrayList<String>();

	private HouseModel.SEARCH_TYPE FILTERCONST = null;

	private List<View> userWrapper = new ArrayList<View>();

	private CacheInfo cacheInfo;

	private boolean isHaveAgent = false;

	private User user;

	private MyRelativeLayout mapPageLayout;

	private MyProgressDialog dialog;

	private boolean isChanegedCity = false;
	
	private double currentLat;
	private double currentLng;

	// 筛选几个条件的id
	public int priceId = 0;
	public int areaId = -1;
	public int apartmentId = 0;
	public int decorationId = 0;

	ImageLoader loader;
	DisplayImageOptions option;

	private boolean isAgentsLoaded = false;
	
	private boolean isHaveOpen= true;

	
	Timer timer = new Timer();
	TimerTask task = new TimerTask(){    
        public void run() {
        	Log.d("huang", "REQUEST_AGENTS ");
        	Message msg=Message.obtain();
        	msg.what=AppConstants.REQUEST_AGENTS;
        	handler.sendMessage(msg);
        }            
    };
	
	/*
	 * 主界面handler
	 */
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case AppConstants.LOGOUT_OK: {
				// 退出登录成功,跳转到登录界面
				HouseMainActivity.this.isUserLogion = false;
				HouseMainActivity.this.mCacheInfo.clearSelf();
				UserInfoCacheUtil.clearCahce(HouseMainActivity.this);
				EventBus.getDefault().post(
						new OnReloginEvent(AppConstants.EVENT_RELOGIN));
				Intent intent = new Intent(HouseMainActivity.this,
						LoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				finish();
			}
				break;
			case AppConstants.LOGOUT_ERR: {
				Toast.makeText(HouseMainActivity.this, "网络出错,请重试",
						Toast.LENGTH_SHORT).show();
			}
				break;
			case AppConstants.LOAD_CITYLIST_OK: {

			}
				break;
			case AppConstants.LOAD_CITYLIST_ERR: {

			}
				break;

			case AppConstants.LOAD_MAP_AGENTS_OK: {
				showAgents();
				dialog.dismiss();
				break;
			}
			case AppConstants.LOAD_MAP_CARS_OK: {
				showFreeCarts();
				break;
			}
			case AppConstants.LOAD_MAP_HOUSE_OK: {
				break;
			}
			case AppConstants.REQUEST_AGENTS:
			{
				if (mCurrentCity != null && mAgentModel != null) {
					int id = mCurrentCity.getCity_id();
					mAgentModel.ajaxLoadCurrrentCityAgents(id,currentLng,currentLat);
				}
				break;
			}
			default:
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("mao", "House MAIN onCreate");
		setContentView(R.layout.activity_main);

		EventBus.getDefault().register(this);
		mLoadCitylistModel = new LoadCitylistModel(this);
		mLoadCitylistModel.addResponseListener(this);
		mLoadCitylistModel.loadCityList();
		initHeader();
		initFilterWrapper();
		initFilter();
		initView();
		initNetworkApi();
		initLoginInfo();
		initDragLayout();
		initMap();
		selectSearch(SEARCH_MANAGER);
		JPushInterface.setAlias(HouseMainActivity.this, cacheInfo.getPhone(),
				new TagAliasCallback() {

					@Override
					public void gotResult(int arg0, String arg1,
							Set<String> arg2) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void initView() {
		houseWrapper = findViewById(R.id.house_main_new_house_wrapper);
		managerWrapper = findViewById(R.id.house_main_manager_wrapper);
		freecarWrapper = findViewById(R.id.house_main_free_car_wrapper);

		userName = (TextView) findViewById(R.id.user_name);
		userMoney = (TextView) findViewById(R.id.user_money);
		getUserMoney = (TextView) findViewById(R.id.user_get_money);

		wxcode = (TextView) findViewById(R.id.login_out_weixin);
		comanyphone = (TextView) findViewById(R.id.login_out_kefu_phone);

		funcMyHouse = (LinearLayout) findViewById(R.id.my_house);
		funcMyWallet = (LinearLayout) findViewById(R.id.my_wallet);
		funcMyManager = (LinearLayout) findViewById(R.id.my_manager);

		share = (RelativeLayout) findViewById(R.id.share);
		about = (RelativeLayout) findViewById(R.id.about);
		feedback = (RelativeLayout) findViewById(R.id.feedback);
		changePsd = (RelativeLayout) findViewById(R.id.change_psd);

		search = (RelativeLayout) findViewById(R.id.house_main_search_wrapper);

		loginOut = (LinearLayout) findViewById(R.id.login_out);

		userImg = (WebImageView) findViewById(R.id.user_img);
		profile_icon = (ImageView) findViewById(R.id.profile_icon);
		areaPickBtn = (ImageView) findViewById(R.id.house_main_menu);

		houseImg = (ImageView) findViewById(R.id.house_main_new_house_img);
		managerImg = (ImageView) findViewById(R.id.house_main_manager_img);
		freecarImg = (ImageView) findViewById(R.id.house_main_free_car_img);

		myManageText = (TextView) findViewById(R.id.my_manager_text);

		managerImg.setImageResource(R.drawable.icon_small_red);
		houseImg.setImageResource(R.drawable.icon_small_gray);
		freecarImg.setImageResource(R.drawable.icon_small_gray);
		filter.setVisibility(View.VISIBLE);
		locateSelf = (ImageView) findViewById(R.id.house_main_locate_myself_img);

		this.mMapDialog = new MapDialog(this, R.style.dialog);

		getUserInfoModel = new GetUserInfoModel(this);
		getUserInfoModel.addResponseListener(this);

		cacheInfo = UserInfoCacheUtil.getCacheInfo(this);

		user = new User();

		setClickListener();
		getUserInfoModel.getUserinfo(cacheInfo.getUid(),
				cacheInfo.getSessionId());

		loader = ImageLoader.getInstance();
		option = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.user_info_background)
				// resource or drawable
				.showImageOnFail(R.drawable.user_info_background)
				// resource or drawable
				.resetViewBeforeLoading(false)
				// default
				.delayBeforeLoading(300).cacheOnDisc(true).cacheInMemory(true)
				.bitmapConfig(Bitmap.Config.ARGB_4444).build();
		
		//timer.schedule(task, 100, 30000);
		
		
	}

	private void initLoginInfo() {
		Log.d("mao", "初始化登录信息");
		Bundle bundle = getIntent().getExtras();
		int msg = AppConstants.EVENT_LOGOUT;
		if (bundle != null) {
			msg = bundle.getInt("isLogin");
		}
		if (msg == AppConstants.EVENT_LOGIN) {
			Log.d("mao", "Login success!!");
			this.isUserLogion = true;
			this.mCacheInfo = UserInfoCacheUtil.getCacheInfo(this);
			Log.d("mao",
					"!!!!!!!!!!!!!!登录成功后的Sessionid="
							+ mCacheInfo.getSessionId());
			// 更改左边栏的 头像和昵称
			Log.d("mao",
					mCacheInfo.getNick_name() + " phone="
							+ mCacheInfo.getPhone());
			userName.setText(("".equals(mCacheInfo.getNick_name()) || mCacheInfo
					.getNick_name() == null) ? mCacheInfo.getPhone()
					: mCacheInfo.getNick_name());
			Bitmap headShot = HeadShotUtil.getHeadShot(mCacheInfo.getPhone());
			if (headShot != null) {
				userImg.setImageBitmap(headShot);
			} else {
				Log.d("mao", "本地头像加载错误");
			}
		} else {
			this.mCacheInfo = new CacheInfo();
			this.mCacheInfo.clearSelf();
		}
		this.mMapDialog.setCacheInfo(this.mCacheInfo);
	}

	private void initHeader() {
		header = LayoutInflater.from(this).inflate(R.layout.titlebar, null);
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		header.measure(w, h);
		titleHeight = header.getMeasuredHeight();
	}

	public void initFilterWrapper() {
		filter = (LinearLayout) findViewById(R.id.house_main_filter);
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		filter.measure(w, h);
		filterHeight = filter.getMeasuredHeight();

	}

	// 筛选数组
	private void initFilter() {
		String[] priceArr = getResources().getStringArray(R.array.list_price);
		String[] positionArr = getResources().getStringArray(
				R.array.list_position);
		String[] decorateArr = getResources().getStringArray(
				R.array.list_decorate);
		String[] typeArr = getResources().getStringArray(R.array.list_type);
		Log.d("mao", priceArr.length + "");
		priceData = new ArrayList<String>();
		locationData = new ArrayList<String>();
		houseTypeData = new ArrayList<String>();
		decorationData = new ArrayList<String>();
		for (int i = 0; i < priceArr.length; i++) {
			priceData.add(priceArr[i]);
			locationData.add(positionArr[i]);
			houseTypeData.add(typeArr[i]);
			if (i != priceArr.length - 1) {
				decorationData.add(decorateArr[i]);
			}
		}

		price = (TextView) filter.findViewById(R.id.filter_price);
		location = (TextView) filter.findViewById(R.id.filter_location);
		houseType = (TextView) filter.findViewById(R.id.filter_type);
		decoration = (TextView) filter.findViewById(R.id.filter_decoration);
	}

	private void setClickListener() {
		locateSelf.setOnClickListener(this);
		houseWrapper.setOnClickListener(this);
		managerWrapper.setOnClickListener(this);
		freecarWrapper.setOnClickListener(this);

		getUserMoney.setOnClickListener(this);
		userWrapper.add(getUserMoney);

		funcMyHouse.setOnClickListener(this);
		funcMyWallet.setOnClickListener(this);
		funcMyManager.setOnClickListener(this);
		userWrapper.add(funcMyHouse);
		userWrapper.add(funcMyWallet);
		userWrapper.add(funcMyManager);

		share.setOnClickListener(this);
		about.setOnClickListener(this);
		feedback.setOnClickListener(this);
		changePsd.setOnClickListener(this);
		userWrapper.add(share);
		userWrapper.add(about);
		userWrapper.add(feedback);
		userWrapper.add(changePsd);

		price.setOnClickListener(this);
		location.setOnClickListener(this);
		houseType.setOnClickListener(this);
		decoration.setOnClickListener(this);
		if (mapLayout != null) {
			mapLayout.setOnClickListener(this);
		}

		areaPickBtn.setOnClickListener(this);

		search.setOnClickListener(this);

		loginOut.setOnClickListener(this);
		userWrapper.add(loginOut);

		profile_icon.setOnClickListener(this);
		userImg.setOnClickListener(this);
		userWrapper.add(userImg);

		// mapPageLayout.setOnClickListener(this);
	}

	private void initDragLayout() {
		dl = (DragLayout) findViewById(R.id.dl);
		dl.setDragListener(new DragListener() {
			@Override
			public void onOpen() {
				closeMenu(true);
				search.setClickable(false);
				price.setClickable(false);
				location.setClickable(false);
				houseWrapper.setClickable(false);

			}

			@Override
			public void onClose() {
				closeMenu(false);
				price.setClickable(true);
				location.setClickable(true);
				search.setClickable(true);
				houseWrapper.setClickable(true);

			}

			@Override
			public void onDrag(float percent) {
			}
		});
		closeMenu(false);
		dl.close();
	}

	private void selectSearch(int search) {
		dialog = new MyProgressDialog(this, "加载中...");
		dialog.show();
		currentShowType = search;

		// 切换前清除地图标记
		this.mHouseMainMap.clear();
		switch (search) {
		case SEARCH_HOUSE:
			initFilter();
			resetHouseFilter();
			filter.setVisibility(View.VISIBLE);
			houseImg.setImageResource(R.drawable.icon_small_red);
			managerImg.setImageResource(R.drawable.icon_small_gray);
			freecarImg.setImageResource(R.drawable.icon_small_gray);
			EventBus.getDefault().post(new OnChangeTypeEvent(SEARCH_HOUSE));
			break;

		case SEARCH_MANAGER:
			filter.setVisibility(View.GONE);
			houseImg.setImageResource(R.drawable.icon_small_gray);
			managerImg.setImageResource(R.drawable.icon_small_red);
			freecarImg.setImageResource(R.drawable.icon_small_gray);
			// EventBus.getDefault().post(new
			// OnChangeTypeEvent(SEARCH_MANAGER));
			handler.sendEmptyMessage(AppConstants.LOAD_MAP_AGENTS_OK);
			break;

		case SEARCH_FREECAR:
			filter.setVisibility(View.GONE);
			houseImg.setImageResource(R.drawable.icon_small_gray);
			managerImg.setImageResource(R.drawable.icon_small_gray);
			freecarImg.setImageResource(R.drawable.icon_small_red);
			EventBus.getDefault().post(new OnChangeTypeEvent(SEARCH_FREECAR));
			break;
		}

	}

	private void closeMenu(boolean isOpen) {
		for (int i = 0; i < userWrapper.size(); i++) {
			userWrapper.get(i).setClickable(isOpen);
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;

		switch (v.getId()) {
		case R.id.house_main_locate_myself_img:
			EventBus.getDefault().post(new OnRefreshEvent(0));
			break;
		case R.id.filter_price:
			showSpinner(price, priceData, SEARCH_TYPE.PRICE);
			break;

		case R.id.filter_location:
			if (mLoadCitylistModel.isLoaded()) {
				locationData.clear();
				Log.d("mao", mLoadCitylistModel.getCity(mCurrentCityName)
						.getCity_id() + "");
				mLoadCitylistModel.loadAreaList(mLoadCitylistModel.getCity(
						mCurrentCityName).getCity_id());
			}
			break;

		case R.id.filter_type:
			showSpinner(houseType, houseTypeData, SEARCH_TYPE.APARTMENT);
			break;

		case R.id.filter_decoration:
			showSpinner(decoration, decorationData, SEARCH_TYPE.DECORATE);
			break;

		case R.id.user_info:
			if (dl.getStatus() == Status.Close) {

			} else {
				if (isUserLogion) {
					intent = new Intent(HouseMainActivity.this,
							ChangeUserInfoActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(this, "亲，您还未登录哟", Toast.LENGTH_SHORT).show();
				}
			}
			break;

		case R.id.user_img: {
			Log.d("mao", "click user  img");
			if (isUserLogion) {
				intent = new Intent(this, ChangeUserInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", user);
				intent.putExtra("user", bundle);
				startActivityForResult(intent,
						AppConstants.INTENT_RESULT_CHANGUSERINFO_OK);
			} else {
				Toast.makeText(this, "亲，您还未登录哟", Toast.LENGTH_SHORT).show();
			}
		}
			break;
		case R.id.house_main_manager_wrapper:
			selectSearch(SEARCH_MANAGER);
			break;

		case R.id.house_main_free_car_wrapper:
			selectSearch(SEARCH_FREECAR);
			break;

		case R.id.house_main_new_house_wrapper:
			selectSearch(SEARCH_HOUSE);
			break;

		case R.id.house_main_search_wrapper:
			intent = new Intent(HouseMainActivity.this, SearchActivity.class);
			startActivity(intent);
			break;

		case R.id.user_get_money:
			if (isUserLogion) {
				intent = new Intent(HouseMainActivity.this,
						AskMoneyActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "亲，您还未登录哟", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.my_wallet:
			if (isUserLogion) {
				intent = new Intent(HouseMainActivity.this,
						MyWalletActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "亲，您还未登录哦~", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.my_house:
			if (isUserLogion) {
				intent = new Intent(HouseMainActivity.this,
						MyHouseActvitiy.class);
				Bundle bundle = new Bundle();
				startActivity(intent);
			} else {
				Toast.makeText(this, "亲，您还未登录哦~", Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.my_manager:
			if (isUserLogion) {
				if (isHaveAgent) {

					intent = new Intent(HouseMainActivity.this,
							MyManagerActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(HouseMainActivity.this, "您还没有绑定经纪人哦",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(this, "亲，您还未登录哦~", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.change_psd:
			if (isUserLogion) {
				intent = new Intent(HouseMainActivity.this,
						ChangePsdActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "亲，您还未登录哦~", Toast.LENGTH_SHORT).show();
			}
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
			if (isUserLogion) {
				intent = new Intent(HouseMainActivity.this,
						FeedbackActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "亲，您还未登录哟", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.login_out:
			if (this.isUserLogion) {

				ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				 NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo(); 
				 if (mNetworkInfo==null) {
					 Toast.makeText(getApplicationContext(), "亲，想体验更多功能，请重新登录哟",
								Toast.LENGTH_SHORT).show();
					 timer.cancel();
					finish();
				}else {
				CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(this);
				this.mLogoutModel.logout(cacheInfo);
				timer.cancel();
				}
			} else {
				Toast.makeText(this, "记得回来哦", Toast.LENGTH_SHORT).show();
				finish();

			}
			break;

		case R.id.profile_icon:
			if (isUserLogion) {
				if (dl.getStatus() == Status.Close) {
					dl.open();
				} else if (dl.getStatus() == Status.Open) {
					dl.close();
				}
			} else {

				Toast.makeText(this, "亲，您还未登录哟", Toast.LENGTH_SHORT).show();
				finish();
			}
			

			break;

		case R.id.house_main_menu:
			if (this.mLoadCitylistModel.isLoaded()) {
				SelectLocationDialog selectLocationDialog = new SelectLocationDialog(
						this, R.style.SampleTheme_Light);
				selectLocationDialog.setCanceledOnTouchOutside(true);
				// selectLocationDialog.setContentView(R.layout.item_select_location_dialog);
				selectLocationDialog.setTitleHeight(titleHeight);
				selectLocationDialog.initListView(
						mLoadCitylistModel.getCityNameList(), mCurrentCityName);
				Log.d("mao", "sdadasda"
						+ mLoadCitylistModel.getCityNameList().toString());
				selectLocationDialog.show();
			} else {
				Toast.makeText(this, "城市列表刷新失败", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.map_page:
			if (dl.getStatus() == Status.Open) {
				dl.close();
			}

			break;

		}
	}

	private void showSpinner(View spinner, List<String> data,
			HouseModel.SEARCH_TYPE filter) {
		FILTERCONST = filter;

		holder = new SpinnerHolder(HouseMainActivity.this, data);
		window = new PopupWindow(holder.getView());
		window.setAnimationStyle(R.anim.popup_list_ani);
		window.setWidth(spinner.getWidth());
		// window.setHeight(spinner.getHeight());
		if (data.size() <= 7 && data.size() > 0) {
			window.setHeight((int) (spinner.getHeight() * data.size() * 0.90));

		} else {
			window.setHeight((int) (HouseMainActivity.this.getWindowManager()
					.getDefaultDisplay().getHeight() * 0.35));

		}
		window.setBackgroundDrawable(new BitmapDrawable());
		window.setOutsideTouchable(true);
		window.setFocusable(true);
		window.setContentView(holder.getView());
		window.showAsDropDown(spinner, 0, 0);
		holder.getList().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (FILTERCONST != null) {
					if (FILTERCONST == HouseModel.SEARCH_TYPE.PRICE) {
						price.setText(holder.getData().get(position));
						postEvent(SEARCH_TYPE.PRICE, position);
						window.dismiss();
					} else if (FILTERCONST == HouseModel.SEARCH_TYPE.AREA) {
						location.setText(holder.getData().get(position));
						
						postEvent(SEARCH_TYPE.AREA, position);
						window.dismiss();

					} else if (FILTERCONST == HouseModel.SEARCH_TYPE.APARTMENT) {
						houseType.setText(holder.getData().get(position));
						postEvent(SEARCH_TYPE.APARTMENT, position);
						window.dismiss();

					} else if (FILTERCONST == HouseModel.SEARCH_TYPE.DECORATE) {
						decoration.setText(holder.getData().get(position));
						postEvent(SEARCH_TYPE.DECORATE, position);
						window.dismiss();

					}
				}
			}
		});
	}

	private void postEvent(SEARCH_TYPE type, int position) {
		EventBus.getDefault()
				.post(new OnFilterItemClickedEvent(type, position));
	}

	private void initNetworkApi() {
		this.mLogoutModel = new LogoutModel(this);
		this.logoutBusinessResponse = new LogoutBusinessResponse(this, handler);
		this.mLogoutModel.addResponseListener(this.logoutBusinessResponse);
		this.mHouseModel = new HouseModel(this);
		this.houseResponse = new HouseMainBusinessResponse();
		this.mHouseModel.addResponseListener(houseResponse);
		this.mAgentModel = new AgentModel(this);
		this.agentResponse = new AgentBusinessResponse();
		this.mAgentModel.addResponseListener(agentResponse);
		getCompanyInfoModel = new GetCompanyInfoModel(this);
		getCompanyInfoModel.addResponseListener(this);
	}

	@Override
	protected void onResume() {
		this.mHouseMainMapView.onResume();

		super.onResume();
		if (isfirstentry) {
			getCompanyInfoModel.getCompanyInfo();
		}

	}

	@Override
	protected void onPause() {
		this.mHouseMainMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		getUserInfoModel.getUserinfo(cacheInfo.getUid(),
				cacheInfo.getSessionId());
	}

	@Override
	protected void onDestroy() {
		Log.d("mao", "main house destroy");
		UserInfoCacheUtil.clearCahce(this);
		this.mHouseMainMapView.onDestroy();
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	private void initMap() {
		this.mHouseMainMapView = (MapView) findViewById(R.id.house_main_mapview);
		this.mHouseMainMapView.showZoomControls(false);
		this.mHouseMainMapView.showScaleControl(false);
		MapStatusUpdate msu_DefualtZoom = MapStatusUpdateFactory.zoomTo(16);
		mHouseMainMapView.setVisibility(View.GONE);
		this.mHouseMainMap = this.mHouseMainMapView.getMap();
		this.mHouseMainMap.setMaxAndMinZoomLevel(27, 12);
		this.mHouseMainMap.setMapStatus(msu_DefualtZoom);
		this.mHouseMainMap.setOnMapLoadedCallback(this);
		this.mHouseMainMap.setOnMarkerClickListener(HouseMainActivity.this);
		mHouseMainMap.setOnMapClickListener(this);

		
		startLocate();
	}

	private void startLocate() {
		// 自动设置当前的位置
		this.mHouseMainMap.setMyLocationEnabled(true);

		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(new MyLocationListner());
		
		LocationClientOption llo = new LocationClientOption();

		llo.setOpenGps(true);	
		llo.setAddrType("all");
		llo.setScanSpan(3000);
		
		llo.setLocationMode(LocationMode.Hight_Accuracy);
		llo.setCoorType("bd09ll");
		llo.setIsNeedAddress(true);

		mLocationClient.setLocOption(llo);
		mLocationClient.start();
	}

	@Override
	public void onMapLoaded() {
		Toast.makeText(this, "地图加载完成", Toast.LENGTH_SHORT).show();
		this.isMapLoaded = true;
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		Bundle bundle = marker.getExtraInfo();
		int index = bundle.getInt("marker_index");
		int marker_type = bundle.getInt("marker_type");
		switch (marker_type) {
		case 0:// 经纪人
		{
			Intent in = new Intent(this, ManagerDetailActivity.class);
			Bundle extra = new Bundle();
			Agent agent = this.mAgentModel.getAgent(index);
			agent.setBitmap(null);
			LatLng agentLL = new LatLng(agent.getLat(), agent.getLng());
			extra.putSerializable("agent", this.mAgentModel.getAgent(index));
			extra.putSerializable("cacheInfo", this.mCacheInfo);
			Log.i("huang agent:", agent + "");
			extra.putSerializable("distance", "");
			in.putExtras(extra);
			startActivity(in);
		}
			break;
		case 1:// 楼盘
		{
			this.mMapDialog.setCurrentIndex(index);
			this.mMapDialog.show();
		}
			break;
		case 2:// 免费车
		{
			Agent agent = this.mAgentModel.getAgent(index);

		}
			break;
		}

		return true;
	}

	class HouseMainBusinessResponse implements BusinessResponse {

		@Override
		public void OnMessageResponse(String url, JSONObject jo,
				AjaxStatus status) throws JSONException {
			int replyType = jo.optInt("type");
			int statusCode = jo.optInt("status");
			if (statusCode != 200) {
				toast("网络状况不好哟，请点击定位重新获取");
				return;
			} else {
				
			}
			switch (replyType) {
			case HouseModel.REPLY_TYPE_SHOWCITY: {
				int count = mHouseModel.getCurrentCityHouse().size();
				if (count == 0) {
					//toast("该城市尚未开通购房服务");
					isHaveOpen=false;
					EventBus.getDefault().post(new OnRefreshEvent(1));
					return;
				} else {
					isHaveOpen=true;
					if (currentShowType == SEARCH_HOUSE) {
						handleCityResponse(jo, new OnChangeTypeEvent(
								SEARCH_HOUSE));
					}
				}

			}
				break;
			case HouseModel.REPLY_TYPE_SHOWFILTER: {
				int count = mHouseModel.getFiltedHouse().size();
				mHouseMainMap.clear();
				if (count == 0) {
					toast("无相关数据");
					if (currentShowType == SEARCH_HOUSE) {
						handleCityResponse(jo, new OnChangeTypeEvent(3));
					}
					return;
				} else {
					toast("获得" + count + "条房源信息");
					if (currentShowType == SEARCH_HOUSE) {
						handleCityResponse(jo, new OnChangeTypeEvent(3));
					}
				}
			}
				break;
			default:
				break;
			}
		}

		private void handleCityResponse(JSONObject jsonStatus,
				OnChangeTypeEvent event) {
			EventBus.getDefault().post(event);
		}

		public void showHouses() {

			HouseMainActivity.this.mMapDialog.initHouseData(mHouseModel
					.getCurrentCityHouse());
			Log.d("mao", "show " + mHouseModel.getCurrentCityHouse().size() + " Houses" + "current City=" + mCurrentCity.getCity_name()+" "+mCurrentCity.getCity_lat());
			if (mHouseModel.getCurrentCityHouse().size() > 0) {
				HouseMainActivity.this.mHouseMainMap.clear();

			}
			List<House> houseTotalList = HouseMainActivity.this.mHouseModel
					.getCurrentCityHouse();
			for (int i = 0; i < houseTotalList.size(); i++) {
				House house = houseTotalList.get(i);
				View view = null;
				BitmapDescriptor discriptor = BitmapDescriptorFactory
						.fromView(HouseMarker.getHouseMarker(
								HouseMainActivity.this, house, view));
				LatLng ll = new LatLng(house.getLat(), house.getLng());
				OverlayOptions olo = new MarkerOptions().position(ll).icon(
						discriptor);
				Marker marker = (Marker) mHouseMainMap.addOverlay(olo);
				discriptor.recycle();
				Bundle bundle = new Bundle();
				bundle.putInt("marker_index", i);
				bundle.putInt("marker_type", 1);
				marker.setExtraInfo(bundle);
				
				//查看新楼盘定位到城市中心
				setMapCenter();
//				if (isChanegedCity) {
//					Log.i("huang", "changed City");
//					LatLng latlng = new LatLng(mCurrentCity.getCity_lat(), mCurrentCity.getCity_long());
//					MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(latlng, 12f);
//					mHouseMainMap.setMapStatus(msu);
//				} else {
//					setMapCenter();
//					Log.i("huang", "not changed City");
//				}
				
				// if (i == houseTotalList.size() / 2) {
				// MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(
				// ll, 12f);
				// mHouseMainMap.setMapStatus(msu);
				// }
//				setMapCenter();
			}
			isChanegedCity = false;
		}

		public void showFiltedHouses() {
			Log.d("mao", "show filted Houses");
			HouseMainActivity.this.mMapDialog.initHouseData(mHouseModel
					.getFiltedHouse());
			HouseMainActivity.this.mHouseMainMap.clear();
			
			List<House> list = HouseMainActivity.this.mHouseModel
					.getFiltedHouse();
			for (int i = 0; i < list.size(); i++) {
				House house = list.get(i);
				View view = null;
				BitmapDescriptor discriptor = BitmapDescriptorFactory
						.fromView(HouseMarker.getHouseMarker(
								HouseMainActivity.this, house, view));
				LatLng ll = new LatLng(house.getLat(), house.getLng());
				OverlayOptions olo = new MarkerOptions().position(ll).icon(
						discriptor);
				Marker marker = (Marker) mHouseMainMap.addOverlay(olo);
				discriptor.recycle();
				Bundle bundle = new Bundle();
				bundle.putInt("marker_index", i);
				bundle.putInt("marker_type", 1);
				marker.setExtraInfo(bundle);

				// if (i == list.size() / 2) {
				// MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(
				// ll, 12f);
				// mHouseMainMap.setMapStatus(msu);
				// }
				setMapCenter();
			}
		}
	}

	class AgentBusinessResponse implements BusinessResponse {

		@Override
		public void OnMessageResponse(String url, JSONObject jo,
				AjaxStatus status) throws JSONException {
			int repleyCode = jo.optInt("status");
			if (repleyCode == 200) {
				isAgentsLoaded = true;
				// OnChangeTypeEvent event=new
				// OnChangeTypeEvent(currentShowType);
				// EventBus.getDefault().post(event);
				
				if (currentShowType == SEARCH_MANAGER) {
					handler.sendEmptyMessage(AppConstants.LOAD_MAP_AGENTS_OK);
				} else if (currentShowType == SEARCH_FREECAR) {
//					handler.sendEmptyMessage(AppConstants.LOAD_MAP_CARS_OK);
					OnChangeTypeEvent event=new OnChangeTypeEvent(currentShowType);
					EventBus.getDefault().post(event);
				}

			} else {
				Toast.makeText(HouseMainActivity.this, "经济人信息获取失败",
						Toast.LENGTH_SHORT).show();
			}

		}

		// 因为要加载图片，弃用此方法，详见另一个showAgents

		public void showAgents() {
			if (isAgentsLoaded) {
				if (mAgentModel.getAgentLists().size() > 0) {

					HouseMainActivity.this.mHouseMainMap.clear();
				}
				List<Agent> lists = mAgentModel.getAgentLists();
				for (int i = 0; i < lists.size(); i++) {
					final Agent agent = lists.get(i);
					final int index = i;
					LatLng ll = new LatLng(agent.getLat(), agent.getLng());
					BitmapDescriptor discriptor = BitmapDescriptorFactory
							.fromView(ManagerMarker.getManagerMarker(
									HouseMainActivity.this, agent,
									isChanegedCity));
					if (agent.getBitmap() != null) {
						Log.d("huang", "设置有图片");
					}

					loader.loadImage(agent.getPic(),
							new ImageLoadingListener() {

								@Override
								public void onLoadingStarted(String imageUri,
										View view) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onLoadingFailed(String imageUri,
										View view, FailReason failReason) {
									// TODO Auto-generated method stub
									agent.setBitmap(null);
								}

								@Override
								public void onLoadingComplete(String imageUri,
										View view, Bitmap loadedImage) {
									// TODO Auto-generated method stub
									agent.setBitmap(loadedImage);
									mAgentModel.getAgentLists().set(index,
											agent);
								}

								@Override
								public void onLoadingCancelled(String imageUri,
										View view) {
									// TODO Auto-generated method stub
									agent.setBitmap(null);
								}
							});

					mAgentModel.getAgentLists().set(i, agent);
					OverlayOptions olo = new MarkerOptions().position(ll).icon(
							discriptor);
					Marker marker = (Marker) mHouseMainMap.addOverlay(olo);
					discriptor.recycle();
					Bundle bundle = new Bundle();
					bundle.putInt("marker_index", i);
					bundle.putInt("marker_type", 0);
					marker.setExtraInfo(bundle);
					// if (i == lists.size() / 2) {
					// MapStatusUpdate msu = MapStatusUpdateFactory
					// .newLatLngZoom(ll, 12f);
					// mHouseMainMap.setMapStatus(msu);
					// }
					setMapCenter();

				}
				// mAgentModel.clearData();
				// mAgentModel.setAgentList(lists);
			}
		}

		public void showFreeCarts() {
			if (isAgentsLoaded) {
				if (mAgentModel.getAgentLists().size() > 0) {

					HouseMainActivity.this.mHouseMainMap.clear();
				}
				List<Agent> lists = mAgentModel.getAgentLists();
				BitmapDescriptor carDescriptor = BitmapDescriptorFactory
						.fromResource(R.drawable.chezi1_03);
				for (int i = 0; i < lists.size(); i++) {
					Agent agent = lists.get(i);
					LatLng ll = new LatLng(agent.getLat(), agent.getLng());
					OverlayOptions olo = new MarkerOptions().position(ll).icon(
							carDescriptor);
					Marker marker = (Marker) mHouseMainMap.addOverlay(olo);
					Bundle bundle = new Bundle();
					bundle.putInt("marker_index", i);
					bundle.putInt("marker_type", 0);
					marker.setExtraInfo(bundle);
					// if (i == lists.size() / 2) {
					// MapStatusUpdate msu = MapStatusUpdateFactory
					// .newLatLngZoom(ll, 12f);
					// mHouseMainMap.setMapStatus(msu);
					// }
					setMapCenter();
					
				}
				carDescriptor.recycle();
			}
		}
	}

	class MyLocationListner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			mLocationData = new MyLocationData.Builder()//
					.latitude(location.getLatitude())//
					.longitude(location.getLongitude())//
					.accuracy(location.getRadius())//
					.build();
			SharedPreferences sharePreferences = getSharedPreferences(
					"userLocation", MODE_PRIVATE);
			SharedPreferences.Editor editor = sharePreferences.edit();
			editor.putFloat("lat", (float) mLocationData.latitude);
			editor.putFloat("lot", (float) mLocationData.longitude);
			editor.commit();
			currentLng = mLocationData.longitude;
			currentLat = mLocationData.latitude;

			mHouseMainMap.setMyLocationData(mLocationData);

			MapStatusUpdate msu = MapStatusUpdateFactory
					.newLatLngZoom(
							new LatLng(location.getLatitude(), location
									.getLongitude()), 15.0f);
			if (msu != null) {
				mHouseMainMap.setMapStatus(msu);
			}
			

			mLocationClient.stop();
			if (requestInfo) {

				mCurrentCityName = location.getCity();
				if (mCurrentCityName == null) {
					Log.d("mao", "定位城市失败");
					Toast.makeText(HouseMainActivity.this, "定位城市失败",
							Toast.LENGTH_SHORT).show();
					return;
				}
				int index = mCurrentCityName.indexOf("市");
				mCurrentCityName = mCurrentCityName.subSequence(0, index)
						.toString();
				mLocatedCityName = mCurrentCityName;
				String citycode = location.getCityCode();
				Log.d("mao", "city name=" + mCurrentCityName);
				Log.d("mao", "city code=" + citycode);
				mHouseMainMapView.setVisibility(View.VISIBLE);
				EventBus.getDefault().post(new OnLocatedEvent());
				requestInfo = false;
			}

		}
	}

	class GetUserinfoListner implements BusinessResponse {

		@Override
		public void OnMessageResponse(String url, JSONObject jo,
				AjaxStatus status) throws JSONException {

		}

	}

	// ------------------消息管道
	//定位成功
	public void onEventMainThread(OnLocatedEvent event) {
		Log.d("mao", "onEvent-----located");
		if (this.mLoadCitylistModel.isLoaded()) {
			mLocalCity = this.mLoadCitylistModel.getCity(mCurrentCityName);
			mCurrentCity = mLocalCity;
			if (mCurrentCity != null) {
				EventBus.getDefault().post(
						new OnChangeCityEvent(mCurrentCityName));
			}
		} else {
			Toast.makeText(this, "城市列表失败，请点击右上角重新定位", Toast.LENGTH_SHORT)
					.show();
		}
	}

	// 发起城市切换请求
	public void onEventMainThread(OnChangeCityEvent event) {
		String cityName = event.getCityName();
		this.mCurrentCityName = cityName;
		this.mCurrentCity = this.mLoadCitylistModel.getCity(mCurrentCityName);
		Log.d("mao", "定位城市名：" + this.mCurrentCity.getCity_name() + " 定位城市Id: "
				+ this.mCurrentCity.getCity_id());
		this.mHouseMainMap.clear();
		// this.currentShowType = 1;
		this.mHouseModel.clearData();
		this.mAgentModel.clearData();
		Log.d("mao", "onEvent-----change city");

		int id = mCurrentCity.getCity_id();
		LatLng cityLatlng = new LatLng(mCurrentCity.getCity_lat(),
				mCurrentCity.getCity_long());
		this.mHouseModel.ajaxLoadCurrentHouse(id, cityLatlng);
		this.mAgentModel.ajaxLoadCurrrentCityAgents(id,currentLng,currentLat);
		isChanegedCity = true;
		isAgentsLoaded = false;
	}

	// 发起map添加marker请求
	public void onEventAsync(OnChangeTypeEvent event) {
		Log.d("mao", "event showType=" + event.getShowType());
		int type = event.getShowType();
		// if (type == currentShowType) {
		// return;
		// } else {

		switch (type) {
		case SEARCH_MANAGER: {
			// currentShowType = type;
			this.agentResponse.showAgents();
		}
			break;
		case SEARCH_HOUSE: {
			// currentShowType = type;
			this.houseResponse.showHouses();
		}
			break;
		case SEARCH_FREECAR: {
			// currentShowType = type;
			this.agentResponse.showFreeCarts();
		}
			break;
		case 3: {
			this.houseResponse.showFiltedHouses();
		}
			break;
		}
		Log.d("huang", "currentShowType=" + currentShowType + "");
		Log.d("huang", "agents:" + mAgentModel.getAgentLists().size() + "");
		Log.d("huang", "houses:" + mHouseModel.getCurrentCityHouse().size()
				+ "");
		dialog.dismiss();
		// }
	}

	// 发起楼盘筛选请求
	public void onEventMainThread(OnFilterItemClickedEvent event) {
		Log.d("mao", "条件筛选事件触发,事件类型为" + event.getType());
		int cityId = this.mCurrentCity.getCity_id();
		switch (event.getType()) {
		case PRICE:
			priceId = event.getFilterId();
			break;
		case AREA:
			List<Area> areas = mLoadCitylistModel.getAreaLists();
			areaId = areas.get(event.getFilterId()).getAreaId();
			break;
		case APARTMENT:
			apartmentId = event.getFilterId();
			break;
		case DECORATE:
			decorationId = event.getFilterId();
			break;
		default:
			break;
		}
		this.mHouseModel.ajaxLoadFilterHouse(cityId, event.getType(), priceId,
				areaId, apartmentId, decorationId);
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 100) {

		}

	}

	public void onEventMainThread(OnRefreshEvent event) {
		int type = event.getType();
		if (type == 1)
			requestInfo = true;
		Log.d("mao", "---------------------重新定位请求------");
		if (!this.mLocationClient.isStarted()) {
			this.mLocationClient.start();
			this.mLocationClient.requestLocation();
			if (!isHaveOpen) {
				Toast.makeText(this, "该城市尚未开通购房服务,重新定位中...", Toast.LENGTH_SHORT).show();	
			}else {
				Toast.makeText(this, "重新定位中,请稍后...", Toast.LENGTH_SHORT).show();		
			}
		} 
		
	}

	public void onEventMainThread(OnFocusEvent event) {
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(
				event.getLl(), 13.5f);
		this.mHouseMainMap.setMapStatus(msu);
	}

	public void onEventMainThread(OnChangeUserInfoEvent event) {
		// Log.d("mao", "更新用户信息");
		// Bitmap headShot = HeadShotUtil.getHeadShot(cacheInfo.getPhone());
		// if (headShot != null) {
		// userImg.setImageBitmap(headShot);
		// // headShot.recycle();
		// }

	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.GET_AREA_LIST)) {
			locationData = mLoadCitylistModel.getAreaList();
			showSpinner(location, locationData, SEARCH_TYPE.AREA);
		} else if (url.endsWith(ProtocolConst.GET_MASTER_VALUE)) {
			wxcode.setText("微信账号：" + getCompanyInfoModel.weixin);
			comanyphone.setText("客服电话：" + getCompanyInfoModel.phone);
		} else if (url.endsWith(ProtocolConst.GET_USERINFO)) {

			String path = getUserInfoModel.user.getPic();
			Log.d("mao", "拿到的 用户图像地址:" + path);
			String pic_url = JSONUtil.getImagePath(path);
			Log.d("mao", "真实的服务器地址:" + pic_url);
			userImg.setImageWithURL(this, AppConstants.WEBHOME + pic_url);
			if (getUserInfoModel.user.agent_id == 0) {
				isHaveAgent = false;
			} else {
				isHaveAgent = true;
			}
			userMoney.setText(getUserInfoModel.user.getAccount_balance() + "");
			user = getUserInfoModel.user;
			userName.setText(getUserInfoModel.user.getUsername());

		}
	}

	@Override
	public void onMapClick(LatLng arg0) {
		if (dl.getStatus() == Status.Open) {
			dl.close();
		}else {
			mAgentModel.ajaxLoadCurrrentCityAgents(mCurrentCity.getCity_id(), arg0.longitude, arg0.latitude);
		}

	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setMapCenterAtUserLocation() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				"userLocation", MODE_PRIVATE);
		double lat = sharedPreferences.getFloat("lat", 100.0f);
		double lot = sharedPreferences.getFloat("lot", 90.0f);
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(new LatLng(
				lat, lot), 15.0f);
		mHouseMainMap.setMapStatus(msu);
	}

	public void resetHouseFilter() {
		priceId = 0;
		areaId = -1;
		apartmentId = 0;
		decorationId = 0;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.isUserLogion = false;
			timer.cancel();
			this.finish();
			
			
			
		}
		return super.onKeyDown(keyCode, event);
	}

	public void showAgents() {
		if (isAgentsLoaded) {
			if (mAgentModel.getAgentLists().size() > 0) {

				HouseMainActivity.this.mHouseMainMap.clear();
			}
			List<Agent> lists = mAgentModel.getAgentLists();
			for (int i = 0; i < lists.size(); i++) {
				final Agent agent = lists.get(i);
				final int index = i;
				final LatLng ll = new LatLng(agent.getLat(), agent.getLng());
				BitmapDescriptor discriptor = BitmapDescriptorFactory
						.fromView(ManagerMarker.getManagerMarker(
								HouseMainActivity.this, agent, isChanegedCity));
				mAgentModel.getAgentLists().set(i, agent);
				OverlayOptions olo = new MarkerOptions().position(ll).icon(
						discriptor);
				final Marker marker = (Marker) mHouseMainMap.addOverlay(olo);
				discriptor.recycle();
				Bundle bundle = new Bundle();
				bundle.putInt("marker_index", i);
				bundle.putInt("marker_type", 0);
				marker.setExtraInfo(bundle);

				if (agent.getBitmap() == null) {
					loader.loadImage(
							AppConstants.WEBHOME
									+ JSONUtil.getImagePath(agent.getPic()),
							new ImageLoadingListener() {

								@Override
								public void onLoadingStarted(String imageUri,
										View view) {
									

								}

								@Override
								public void onLoadingFailed(String imageUri,
										View view, FailReason failReason) {
								}

								@Override
								public void onLoadingComplete(String imageUri,
										View view, Bitmap loadedImage) {
									// TODO Auto-generated method stub

									agent.setBitmap(loadedImage);
									

									if (index < mAgentModel.getAgentLists()
											.size()) {
										mAgentModel.getAgentLists().set(index,
												agent);
									}

									BitmapDescriptor discriptor = BitmapDescriptorFactory.fromView(ManagerMarker
											.getManagerMarker(
													HouseMainActivity.this,
													agent, isChanegedCity));

									OverlayOptions olo = new MarkerOptions()
											.position(ll).icon(discriptor);

									marker.remove();
									if (mHouseMainMap != null) {
										Marker marker = (Marker) mHouseMainMap
												.addOverlay(olo);
										discriptor.recycle();
										Bundle bundle = new Bundle();
										bundle.putInt("marker_index", index);
										bundle.putInt("marker_type", 0);
										marker.setExtraInfo(bundle);
									}
								}

								@Override
								public void onLoadingCancelled(String imageUri,
										View view) {

								}
							});
				}
				
			}
		}
	}
	
	public void showFreeCarts() {
		if (isAgentsLoaded) {
			if (mAgentModel.getAgentLists().size() > 0) {

				HouseMainActivity.this.mHouseMainMap.clear();
			}
			List<Agent> lists = mAgentModel.getAgentLists();
			BitmapDescriptor carDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.chezi1_03);
			for (int i = 0; i < lists.size(); i++) {
				Agent agent = lists.get(i);
				LatLng ll = new LatLng(agent.getLat(), agent.getLng());
				OverlayOptions olo = new MarkerOptions().position(ll).icon(
						carDescriptor);
				Marker marker = (Marker) mHouseMainMap.addOverlay(olo);
				Bundle bundle = new Bundle();
				bundle.putInt("marker_index", i);
				bundle.putInt("marker_type", 0);
				marker.setExtraInfo(bundle);
				// if (i == lists.size() / 2) {
				// MapStatusUpdate msu = MapStatusUpdateFactory
				// .newLatLngZoom(ll, 12f);
				// mHouseMainMap.setMapStatus(msu);
				// }
				setMapCenter();
				
			}
			carDescriptor.recycle();
		}
	}

	public void setMapCenter()
	{
		if (mLocalCity.getCity_id() == mCurrentCity.getCity_id()) {
			setMapCenterAtUserLocation();
		} else {
			LatLng latlng = new LatLng(mCurrentCity.getCity_lat(), mCurrentCity.getCity_long());
			MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(latlng, 15.0f);
			mHouseMainMap.setMapStatus(msu);
		}
	}
}
