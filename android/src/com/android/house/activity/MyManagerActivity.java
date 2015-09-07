package com.android.house.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.WebImageView;
import com.android.house.adapter.ManagerDetailListAdapter;
import com.android.house.costants.AppConstants;
import com.android.house.model.GetUserInfoModel;
import com.android.house.model.MyAgentModel;
import com.android.house.protocol.CacheInfo;
import com.android.house.protocol.ProtocolConst;
import com.android.house.util.JSONUtil;
import com.android.house.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.house.R;

public class MyManagerActivity extends BaseActivity implements OnClickListener,
		BusinessResponse {
	private Button fire;// 解约
	private Button evaluate;// 评价

	private TextView title;

	private TextView age;
	private TextView car;
	private TextView from;
	private TextView selling;

	private ListView evaluateList;
	private ManagerDetailListAdapter adapter;

	private WebImageView img;
	private ImageView back;
	private String imageUrl;
	
	private boolean isCanScore=false;

	// 1、通过获取个人信息，查看是否有经济人
	// 2.如果有，再获得经纪人详情评价列表
	// 3.得到用户个人信息

	
	private GetUserInfoModel getUserInfoModel;
	private CacheInfo cacheInfo;
	private MyAgentModel myAgentModel;// 经纪人信息
	boolean isCheckUserInfo = false;
	
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	private int agent_id;
	
	private AlertDialog.Builder builder;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_manager);
		initView();
		
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		myAgentModel.getMyAgentInfo();
	}

	private void initView() {
		fire = (Button) findViewById(R.id.my_manager_fire_manager);
		evaluate = (Button) findViewById(R.id.my_manager_evaluate_manager);

		age = (TextView) findViewById(R.id.my_manager_age);
		car = (TextView) findViewById(R.id.my_manager_car);
		from = (TextView) findViewById(R.id.my_manager_from);
		selling = (TextView) findViewById(R.id.my_manager_selling);

		title = (TextView) findViewById(R.id.title_text);
		title.setText("我的经纪人");
		evaluateList = (ListView) findViewById(R.id.my_manager_evaluate_list);

		back = (ImageView) findViewById(R.id.title_back);
		img = (WebImageView) findViewById(R.id.my_manager_img);
		
		shared=getSharedPreferences("user", 0);
		editor=shared.edit();
		agent_id=shared.getInt("agent_id", 0);
		

		setClickListener();
		
		cacheInfo = UserInfoCacheUtil.getCacheInfo(this);
		getUserInfoModel = new GetUserInfoModel(this);
		getUserInfoModel.addResponseListener(this);
		getUserInfoModel.getUserinfo(cacheInfo.getUid(),cacheInfo.getSessionId());
		myAgentModel = new MyAgentModel(this);
		myAgentModel.addResponseListener(this);
		

			myAgentModel.getMyAgentInfo();
			
		
	}

	private void setClickListener() {
		back.setOnClickListener(this);
		fire.setOnClickListener(this);
		evaluate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent ;
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;

		case R.id.my_manager_evaluate_manager:
			 intent = new Intent(MyManagerActivity.this,
					EvaluateManagerActivity.class);
			intent.putExtra("sale_long",age.getText().toString());
			intent.putExtra("native_place", from.getText().toString());
			intent.putExtra("car_type", car.getText().toString());
			intent.putExtra("sale_num", selling.getText().toString());
			intent.putExtra("image", imageUrl);
			startActivity(intent);
			break;

		case R.id.my_manager_fire_manager:
			 intent = new Intent(MyManagerActivity.this,
					EvaluateManagerActivity.class);
			intent.putExtra("sale_long",age.getText().toString());
			intent.putExtra("native_place", from.getText().toString());
			intent.putExtra("car_type", car.getText().toString());
			intent.putExtra("sale_num", selling.getText().toString());
			intent.putExtra("image", imageUrl);
			intent.putExtra("cancel", true);
//			startActivity(intent);
			startActivityForResult(intent, 1);

			
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==1) {
			boolean isCancel=data.getBooleanExtra("cancel",false);
			if (isCancel) {
				finish();
			}
		}
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// 获取经纪人详细信息
		if (url.endsWith(ProtocolConst.GET_AGNETINFO)) {
			age.setText(myAgentModel.agent.getSale_long());
			from.setText(myAgentModel.agent.getNative_place());
			car.setText(myAgentModel.agent.getCar_type());
			selling.setText(myAgentModel.agent.getSale_num());
		} else if (url.endsWith(ProtocolConst.GET_AGENTCOMMENT)) {
			//获取评论列表
			adapter = new ManagerDetailListAdapter(this);
			adapter.bindData(myAgentModel.commentList);
			evaluateList.setAdapter(adapter);

		}else if (url.endsWith(ProtocolConst.CANCEL_AGENT)) {
			Toast.makeText(MyManagerActivity.this, "解约经纪人成功", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(MyManagerActivity.this,
					EvaluateManagerActivity.class);
			
			editor.remove("agent_id");
			editor.commit();
		}else if (url.endsWith(ProtocolConst.GET_USERINFO)) {
			editor.remove("agent_id");
			editor.putInt("agent_id", getUserInfoModel.user.agent_id);
			editor.commit();
		}else if (url.endsWith(ProtocolConst.GET_MYAGENTINFO)) {
			if (jo.optInt("canScore")==0) {
				evaluate.setVisibility(View.GONE);
				isCanScore=false;
			}else {
				isCanScore=true;
				evaluate.setVisibility(View.VISIBLE);
			}
			age.setText(myAgentModel.myAgent.getSale_long()+"年");
			from.setText(myAgentModel.myAgent.getNative_place());
			car.setText(myAgentModel.myAgent.getCar_type());	
			selling.setText(myAgentModel.myAgent.getSale_num()+"套");
			 imageUrl=jo.optJSONObject("user").optString("pic");
			
			if(imageUrl!=null&&!("".equals(imageUrl)))
			{
				img.setImageWithURL(MyManagerActivity.this, AppConstants.WEBHOME+JSONUtil.getImagePath(imageUrl));
			}
			adapter = new ManagerDetailListAdapter(this);
			adapter.bindData(myAgentModel.myAgentCommentList);
			evaluateList.setAdapter(adapter);
			title.setText("我的经纪人："+myAgentModel.myAgent.getAgent_name());
			
		}

	}

}
