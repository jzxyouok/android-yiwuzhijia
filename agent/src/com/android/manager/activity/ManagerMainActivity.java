package com.android.manager.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.WebImageView;
import com.android.manager.ManagerConst;
import com.android.manager.R;
import com.android.manager.adapter.RankListAdapter;
import com.android.manager.costants.AppConstants;
import com.android.manager.event.OnChangeUserInfoEvent;
import com.android.manager.model.GetCompanyInfoModel;
import com.android.manager.model.GetRankListModel;
import com.android.manager.model.GetUserInfoModel;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.JSONUtil;
import com.android.manager.util.LocateService;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;

public class ManagerMainActivity extends BaseActivity implements OnClickListener,BusinessResponse{
	private View seeClient;
	private View checkHouse;
	private View manageClient;
	
	private TextView id;
	private TextView getMoney;
	private TextView giveMoney;
	private TextView manageMoney;

	private TextView notification;
	private TextView seeClientText;
	
	private TextView loginOut;
	private TextView changePsd;

	private WebImageView managerImg;
	
	private GridView rankList;
	private RankListAdapter adapter;
	
	private Intent intent = null;
	
	private boolean isLogin=false;
	
	private CacheInfo cacheInfo;
	
	private boolean isYeNei;
	
	private GetRankListModel getRankListModel;
	
	private GetUserInfoModel getUserInfoModel;
	
	private String imageUrl;
	
	private TextView name;
	private TextView invitationCode;
	private TextView invitationCodeText;
	
	private GetCompanyInfoModel getCompanyInfomodel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
		setContentView(R.layout.activity_main);
		initLoginInfo();
		
		initView();
		//上传经纪人定位坐标有问题
		launchService();
	}
	
	private void initLoginInfo() {
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		if(bundle==null)
		{
			this.isLogin=false;
			this.cacheInfo=new CacheInfo();
			cacheInfo.clearSelf();
		}
		else {
			this.isLogin=true;
			this.cacheInfo=(CacheInfo) bundle.getSerializable("cacheInfo");
		}
	}
	
	private void initView(){
		manageClient = findViewById(R.id.home_manage_client);
		seeClient = findViewById(R.id.home_manage_see_client);
		checkHouse = findViewById(R.id.home_manage_house_check);

		id = (TextView)findViewById(R.id.home_manager_id);
		manageMoney = (TextView)findViewById(R.id.home_manager_money);
		getMoney = (TextView)findViewById(R.id.home_manager_get_money);
		giveMoney = (TextView)findViewById(R.id.home_manager_give_money);

		notification = (TextView)findViewById(R.id.notification_text);
		
		seeClientText = (TextView)findViewById(R.id.home_manage_see_client_text);
		name=(TextView)findViewById(R.id.home_manager_name_text);
		invitationCode=(TextView)findViewById(R.id.home_manager_recommend);
		invitationCodeText = (TextView)findViewById(R.id.home_manager_recommend_text);
		
		cacheInfo=UserInfoCacheUtil.getCacheInfo(this);
		if (cacheInfo.getStype()==2) {
			isYeNei=true;
			ManagerConst.isManager=false;
		}
		if (cacheInfo.getStype()==3) {
			isYeNei=false;
			ManagerConst.isManager=true;
		}
		if(!isYeNei){
			seeClientText.setText("接待客户");
		}else{
			invitationCode.setVisibility(View.GONE);
			invitationCodeText.setVisibility(View.GONE);
			seeClientText.setText("增加用户");
		}
		
		changePsd = (TextView)findViewById(R.id.change_psd);
		loginOut = (TextView)findViewById(R.id.login_out_text);
		
		managerImg = (WebImageView)findViewById(R.id.home_manager_img);
		
		rankList = (GridView)findViewById(R.id.rank_list);
		
		
		getRankListModel=new GetRankListModel(this);
		getRankListModel.addResponseListener(this);
		getRankListModel.getRankList(cacheInfo.getStype());
		
		getUserInfoModel=new GetUserInfoModel(this);
		getUserInfoModel.addResponseListener(this);
		getUserInfoModel.getUserinfo(cacheInfo.getUid(), cacheInfo.getSessionId());
		
		getCompanyInfomodel=new GetCompanyInfoModel(this);
		getCompanyInfomodel.addResponseListener(this);
		getCompanyInfomodel.getAnnocement();
		
		setClickListener();
	}
	
	private void setClickListener(){
		seeClient.setOnClickListener(this);
		checkHouse.setOnClickListener(this);
		manageClient.setOnClickListener(this);
		
		managerImg.setOnClickListener(this);
		manageMoney.setOnClickListener(this);

		loginOut.setOnClickListener(this);
		changePsd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.home_manager_img:
			intent = new Intent(ManagerMainActivity.this, ChangeUserInfoActivity.class);
			startActivity(intent);
			break;
			
		case R.id.home_manage_client:
			intent = new Intent(ManagerMainActivity.this, ManageClientActivity.class);
			Bundle bundle=new Bundle();
			bundle.putSerializable("cacheInfo", cacheInfo);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
			
		case R.id.home_manage_see_client:
			if(!isYeNei){
				intent = new Intent(ManagerMainActivity.this, SeeClientActivity.class);				
			}else{
				intent = new Intent(ManagerMainActivity.this, AddClientActivity.class);	
			}
			startActivity(intent);
			break;
			
		case R.id.home_manage_house_check:
			intent = new Intent(ManagerMainActivity.this, ManageHouseActivity.class);
			startActivity(intent);
			break;
			
		case R.id.home_manager_money:
			intent = new Intent(ManagerMainActivity.this, ManageMoneyActivity.class);
			startActivity(intent);
			break;
			
		case R.id.change_psd:
			intent = new Intent(ManagerMainActivity.this, ChangePsdActivity.class);
			startActivity(intent);
			break;
			
		case R.id.login_out_text:
//			if(true)
//			{
//				
//			}
//			else
//			{
				Toast.makeText(this, "记得回来哦", Toast.LENGTH_SHORT).show();
				finish();
//			}
			break;
		}
	}
	
	private void launchService(){
		Intent intent = new Intent();  
        intent.setClass(ManagerMainActivity.this, LocateService.class);
        intent.putExtra("sessionId", cacheInfo.getSessionId());
		this.startService(intent);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.GET_RANK_LIST)) {
			adapter = new RankListAdapter(ManagerMainActivity.this);
			adapter.bindData(getRankListModel.userList);
			rankList.setAdapter(adapter);
		}else if (url.endsWith(ProtocolConst.GET_USERINFO)) {
			getMoney.setText(getUserInfoModel.user.getTotal_money()+"元");
			giveMoney.setText(getUserInfoModel.user.getTotal_money()-getUserInfoModel.user.getArrived_money()+"元");
			id.setText(getUserInfoModel.user.getId()+"");
			 imageUrl=jo.optJSONObject("entity").optJSONObject("user").optString("pic","");
				
				if(imageUrl!=null&&!("".equals(imageUrl)))
				{
					managerImg.setImageWithURL(ManagerMainActivity.this, AppConstants.WEBHOME+JSONUtil.getImagePath(imageUrl));
				}
				
				name.setText(getUserInfoModel.user.getName());
				invitationCode.setText(jo.optJSONObject("entity").optJSONObject("profile").optString("invitation_code"));
		}else if (url.endsWith(ProtocolConst.GET_MASTER_VALUE)) {
			notification.setText(getCompanyInfomodel.annocement);
		}
		
	}

	public void onEventMainThread(OnChangeUserInfoEvent event)
	{
		getUserInfoModel.getUserinfo(cacheInfo.getUid(), cacheInfo.getSessionId());
	}

	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	
	
}
