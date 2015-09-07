package com.android.house.view;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.WebImageView;
import com.android.house.activity.HouseDetailActivity;
import com.android.house.costants.AppConstants;
import com.android.house.events.OnFocusEvent;
import com.android.house.model.FreeAppointmentModel;
import com.android.house.model.GetRecAgentModel;
import com.android.house.protocol.CacheInfo;
import com.android.house.protocol.House;
import com.android.house.util.JSONUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.funmi.house.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class MapDialog extends Dialog implements OnClickListener{
	
	
	private Context mContext;
	
	DisplayImageOptions options;
	
	private String stataPre="交房状态：";
	
	private View header;
	
	private ImageView view_group;
	private LinearLayout appointWrapper;
	private LinearLayout appointedWrapper;
    
	private TextView car;
	private TextView state;
	private TextView getMoney;
	
	private TextView appointNow;
	private TextView houseDetail;
	private TextView managerName;
	
	private TextView houseName;
	private TextView housePrice;
	private TextView houseLocation;
	
    private TextView indexText;
    
    private ImageView managerImg;
    
    private int sumIndex = 0;
    private int currentIndex = 0;

    private boolean isNowHouse = false;
    
    private boolean isFirstShown=true;
    
    private CacheInfo cacheInfo;
    private int currentHouseTag=-1;
    
    private String currentImageUrl;
    
    GetRecAgentModel getRecAgentModel;
    int agentId;
    FreeAppointmentModel appointmentModel;
    public void setCacheInfo(CacheInfo cacheInfo) {
		this.cacheInfo = cacheInfo;
	}

	List<House> houseList;
    
	public MapDialog(Context context) {
		super(context);
		mContext = context;
	}
	
	public MapDialog(Context context,int resId) {
		super(context, resId);
		mContext = context;
		
		initImageLoaderOption();
		
		this.getRecAgentModel=new GetRecAgentModel(mContext);
		RecommendAgentResponse response=new RecommendAgentResponse();
		this.getRecAgentModel.addResponseListener(response);
		this.appointmentModel=new FreeAppointmentModel(mContext);
		this.appointmentModel.addResponseListener(new AppointAgentResponse());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mydialog_test);
		Log.d("mao", "dialog oncreate");
		initView();
		initDialog();
		//根据被设定好的值来显示
		if(isFirstShown)
		{
			House house=this.houseList.get(this.currentIndex);
			this.currentHouseTag=house.getHouse_id();
			this.setHouseInfo(house);
			this.setAgentInfo(house);
			isFirstShown=false;
		}
	}

	private void initView(){
		header = findViewById(R.id.view_pager_index_wrapper);
		
		view_group=(ImageView)findViewById(R.id.map_dialog_viewgroup);
		appointWrapper = (LinearLayout)findViewById(R.id.map_dialog_component_appoint_wrapper);
		appointedWrapper = (LinearLayout)findViewById(R.id.map_dialog_component_after_appoint);
		
		indexText = (TextView)findViewById(R.id.view_pager_index);
        
		car = (TextView)appointWrapper.findViewById(R.id.map_dialog_component_car);
		state = (TextView)appointWrapper.findViewById(R.id.map_dialog_component_state);
		getMoney = (TextView)appointWrapper.findViewById(R.id.map_dialog_component_getmoney);
		
		appointNow = (TextView)findViewById(R.id.map_dialog_component_appoint);
		houseDetail = (TextView)findViewById(R.id.map_dialog_component_detail);
		managerName = (TextView)findViewById(R.id.map_dialog_component_managername);
		
		houseName = (TextView)findViewById(R.id.map_dialog_component_housename);
		housePrice = (TextView)findViewById(R.id.map_dialog_component_price);
		houseLocation = (TextView)findViewById(R.id.map_dialog_component_location);
		
        managerImg = (ImageView)findViewById(R.id.map_dialog_component_img);
        
        setClickListener();
	}
	
	
	public void initHouseData(List<House> list)
	{
		this.houseList=list;
		this.sumIndex=list.size();
	}
	
	private void initDialog(){
		Window dialogWindow = this.getWindow();
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                
		Display display = wm.getDefaultDisplay();
		
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		
		dialogWindow.setGravity(Gravity.BOTTOM);
		
		lp.dimAmount = 0f;
		lp.width = display.getWidth();
		lp.height = (int) (display.getHeight()*0.43);
		
		dialogWindow.setAttributes(lp);
		
		this.setCanceledOnTouchOutside(true);
	}
	
	private void setClickListener(){
		header.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					if(event.getRawX() > 2*v.getWidth()/3){
						currentIndex++;
						
						if(currentIndex == sumIndex){
							Toast.makeText(mContext, "已经到最后一项了哦", Toast.LENGTH_SHORT).show();
							currentIndex = sumIndex-1;
						}else
						{
							House house=houseList.get(currentIndex);
							currentHouseTag=house.getHouse_id();
							agentId=-1;
							setHouseInfo(house);
							setAgentInfo(house);
							EventBus.getDefault().post(new OnFocusEvent(house.getLat(), house.getLng()));
						}
					} else if (event.getRawX() < v.getWidth()/3){
						currentIndex--;
						
						if(currentIndex < 0){
							Toast.makeText(mContext, "已经到第一项了哦", Toast.LENGTH_SHORT).show();
							currentIndex = 0;
						}
						else
						{
							House house=houseList.get(currentIndex);
							currentHouseTag=house.getHouse_id();
							agentId=-1;
							setHouseInfo(house);
							setAgentInfo(house);
							EventBus.getDefault().post(new OnFocusEvent(house.getLat(), house.getLng()));
						}
					}
				}
				return true;
			}
		});
		houseDetail.setOnClickListener(this);
		appointNow.setOnClickListener(this);
		indexText.setOnClickListener(this);
	}
	
	public void setHouseInfo(House house){
		
		//对字段做映射
		getMoney.setText("易屋返现：" + house.getPayback()+" 元");
		this.houseName.setText(house.getName());
		this.housePrice.setText(house.getAverage_price()+"");
		this.houseLocation.setText(house.getLocation_info()+house.getArea_name());
		indexText.setText(house.getName());
		String str;
		if(house.getHouse_making_status()!=null)
			str=house.getHouse_making_status().equals("1")?"期房":"现房";
		else 
			str="现房";
		this.state.setText(stataPre+str);
		
	
		String path=house.getHead_pic();
		String url=JSONUtil.getImagePath(path);
		view_group.setTag(AppConstants.WEBHOME+url);
		ImageLoader.getInstance().displayImage(AppConstants.WEBHOME+url, view_group, options,
				new SimpleImageLoadingListener(){

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						if(view.getTag().equals(imageUri))
								{
									((ImageView)view).setImageBitmap(loadedImage);
								}
					}
			
		}
				);
	}
	
	public void setManagerInfo(boolean isCar,boolean isNowHouse,String managerName,Bitmap managerImg){
		if(isCar){
			car.setText("专车看房：提供");
		}else{
			car.setText("专车看房：不提供");
		}
		
		if(isNowHouse){
			state.setText("交房状态：现房");
		}else{
			state.setText("交房状态：期房");
		}
		
		this.managerName.setText(managerName);
		this.managerImg.setImageBitmap(managerImg);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.map_dialog_component_appoint:
			{
				String msg1=cacheInfo.isLogin()?"已经登录":"未登录 ";
				Log.d("mao", "ManagerDetailActivity: session="+cacheInfo.getSessionId()
						+msg1+"id="+cacheInfo.getUid()
						);
				if(this.cacheInfo.isLogin())
				{
					if(agentId==-1)
					{
						Toast.makeText(mContext, "经纪人信息尚在加载中...", Toast.LENGTH_SHORT).show();
						break;
					}
					this.appointmentModel.appointAgent(cacheInfo.getUid(),agentId, cacheInfo.getSessionId());
				}
				else
				{
					Toast.makeText(mContext, "亲，请先登录哟", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.map_dialog_component_detail:
			{
				
					Log.d("mao", "enter dialog house detail");
					Intent intent=new Intent(mContext,HouseDetailActivity.class);
					Bundle bundle=new Bundle();
					bundle.putSerializable("house", this.houseList.get(this.currentIndex));
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				
			}
			break;
			
		case R.id.view_pager_index:
			Intent intent=new Intent(mContext,HouseDetailActivity.class);
			Bundle bundle=new Bundle();
			bundle.putSerializable("house", this.houseList.get(this.currentIndex));
			intent.putExtras(bundle);
			mContext.startActivity(intent);
		}
		
		
	}

	public void setCurrentIndex(int position)
	{
		this.currentIndex=position;
		this.currentHouseTag=this.houseList.get(position).getHouse_id();
		Log.d("mao",this.currentHouseTag+",house id");
		if(this.isFirstShown)
		{
			return ;
		}else
		{
			House house=this.houseList.get(this.currentIndex);
			this.setHouseInfo(house);
			this.setAgentInfo(house);
		
		}
		
	}
	
	class RecommendAgentResponse implements BusinessResponse 
	{
		

			@Override
			public void OnMessageResponse(String url, JSONObject jo,
					AjaxStatus status) throws JSONException {
				//相应数据,拿到 图片地址
				Log.d("mao",jo.toString());
				int house_id=jo.optInt("houseId");
				Log.d("mao","json 返回的house_id"+house_id);
				if(house_id!=MapDialog.this.currentHouseTag)
				{
					return ;
				}
				JSONObject agent=jo.optJSONObject("agent");
				String agentName=agent.optString("nick_name");
				String imageUrl=agent.optString("pic");
				agentId=agent.optInt("id");
				if(imageUrl!=null&&!("".equals(imageUrl)))
				{
					//MapDialog.this.managerImg.setImageWithURL(mContext, AppConstants.WEBHOME+JSONUtil.getImagePath(imageUrl));
					ImageLoader.getInstance().displayImage(AppConstants.WEBHOME+JSONUtil.getImagePath(imageUrl), managerImg);
				}
				MapDialog.this.managerName.setText(agentName);
			}
	}
	
	class AppointAgentResponse implements BusinessResponse
	{
		@Override
		public void OnMessageResponse(String url, JSONObject jo,
				AjaxStatus status) throws JSONException {
			if(jo!=null)
			{
				int msg=jo.optInt("status");
				if(msg==300)
				{
					Toast.makeText(mContext, jo.optString("msg"), Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(mContext, "预约成功！", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
	
	private void setAgentInfo(House house)
	{
		this.getRecAgentModel.getRecommendAgent(house);
	}

	@Override
	public void dismiss() {
		this.agentId=-1;
		super.dismiss();
	}
	
	private void initImageLoaderOption()
	{
			options = new DisplayImageOptions.Builder()  
	        .showImageForEmptyUri(R.drawable.user_info_background) // resource or drawable  
	        .showImageOnFail(R.drawable.user_info_background) // resource or drawable  
	        .resetViewBeforeLoading(false)  // default  
	        .delayBeforeLoading(500)  
	        .cacheOnDisc(true)
	        .cacheInMemory(true)
	        .bitmapConfig(Bitmap.Config.RGB_565)
	        .build();  
	}
	
	
}
