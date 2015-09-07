package com.android.manager.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.adapter.ManageMoneyListAdapter;
import com.android.manager.model.AccountModel;
import com.android.manager.model.GetUserInfoModel;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;

public class ManageMoneyActivity extends BaseActivity implements OnClickListener,BusinessResponse{
	private TextView title;

	private ImageView back;

	private ListView list;

	private RelativeLayout bindAccount;
	private RelativeLayout getUserMoney;

	private ManageMoneyListAdapter adapter;

	private AccountModel model;
	
	
	
	private TextView totalMoney;
	private TextView accountBalance;
	
	private GetUserInfoModel getUserInfoModel;
	
	private CacheInfo cachInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_money);
        
		initView();
		model = new AccountModel(this);
		model.addResponseListener(this);
	}

	private void initView() {
		title = (TextView) findViewById(R.id.title_text);
		title.setText("我的钱包");

		bindAccount = (RelativeLayout) findViewById(R.id.bind_account);
		getUserMoney = (RelativeLayout) findViewById(R.id.get_left_money);

		back = (ImageView) findViewById(R.id.title_back);
		back.setOnClickListener(this);

		list = (ListView) findViewById(R.id.wallet_detail_list);
		
		
		
		totalMoney=(TextView) findViewById(R.id.money_sum);
		accountBalance=(TextView) findViewById(R.id.money_sum_ava);
		
		
		cachInfo=UserInfoCacheUtil.getCacheInfo(this);
		
		getUserInfoModel=new GetUserInfoModel(this);
		getUserInfoModel.addResponseListener(this);
		
		
		
		setClickListener();
	}

	private void setClickListener() {
		back.setOnClickListener(this);
		bindAccount.setOnClickListener(this);
		getUserMoney.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;

		case R.id.bind_account:
			Intent intent = new Intent(ManageMoneyActivity.this,
					BindAccountActivity.class);
			startActivity(intent);
			break;

		case R.id.get_left_money:
			Intent askIntent=new Intent(ManageMoneyActivity.this,GetLeftMoneyActivity.class);
			askIntent.putExtra("money", accountBalance.getText().toString());
			startActivity(askIntent);
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		model.GetHistory();
		getUserInfoModel.getUserinfo(cachInfo.getUid(), cachInfo.getSessionId());
	}

	
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		//获取提现收入历史列表
		if (url.endsWith(ProtocolConst.GETHISTORY)) {
			adapter = new ManageMoneyListAdapter(this);
			adapter.bindData(model.accountList);
			list.setAdapter(adapter);
		}else if (url.endsWith(ProtocolConst.GET_USERINFO)) {
			totalMoney.setText(getUserInfoModel.user.getTotal_money()+"");
			accountBalance.setText(getUserInfoModel.user.getAccount_balance()+"");
		}

	}
}
