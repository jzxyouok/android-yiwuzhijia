package com.android.manager.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.model.AccountModel;
import com.android.manager.model.GetUserInfoModel;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;



public class GetLeftMoneyActivity extends BaseActivity implements OnClickListener,BusinessResponse{
	private TextView title;
	private ImageView back;
	private TextView accountMoney;
	private EditText askMoney;
	private RelativeLayout askNow;
	private String money;
	
	private Intent intent;
	private AccountModel model;
	private GetUserInfoModel getUserInfoModel; 
	private CacheInfo cacheInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_left_money);
		init();

	}

	private void init() {

		title = (TextView) findViewById(R.id.title_text);
		title.setText("申请提现");
		back = (ImageView) findViewById(R.id.title_back);
		back.setOnClickListener(this);
		
		accountMoney=(TextView) findViewById(R.id.account_balance);
		askMoney=(EditText) findViewById(R.id.ask_money);
		askNow=(RelativeLayout) findViewById(R.id.ask_now);
		askMoney.setOnClickListener(this);
		askNow.setOnClickListener(this);
		askNow.setClickable(false);
		
		intent=getIntent();
		money=intent.getStringExtra("money");
//		accountMoney.setText(money);
		
		cacheInfo=UserInfoCacheUtil.getCacheInfo(this);
		getUserInfoModel=new GetUserInfoModel(this);
		getUserInfoModel.addResponseListener(this);
		getUserInfoModel.getUserinfo(cacheInfo.getUid(), cacheInfo.getSessionId());
		
		
		model=new AccountModel(this);
		model.addResponseListener(this);
		
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.ASK_WITHDRAWAL)) {
			if (jo.optString("status").equals("200")) {
				Toast.makeText(GetLeftMoneyActivity.this, jo.optString("msg"), Toast.LENGTH_SHORT).show();
				finish();
				
			}else if (jo.optString("status").equals("300")) {
				Toast.makeText(GetLeftMoneyActivity.this, jo.optString("msg"), Toast.LENGTH_SHORT).show();
				startActivity(new Intent(GetLeftMoneyActivity.this,BindAccountActivity.class));
			}
			
		}else if(url.endsWith(ProtocolConst.GET_USERINFO)){
			accountMoney.setText(getUserInfoModel.user.getAccount_balance()+"元");
		}
		
	}

	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.title_back:
		finish();
		break;
	case R.id.ask_now:
		
		double ask=Double.valueOf(askMoney.getText().toString()).intValue();
		if (ask>Double.valueOf(getUserInfoModel.user.getAccount_balance())) {
			Toast.makeText(GetLeftMoneyActivity.this, "您的提现金额不可超过可提现金额~", Toast.LENGTH_SHORT).show();
		}else if(ask < 0){
			Toast.makeText(GetLeftMoneyActivity.this, "提现金额不能是负数哦～", Toast.LENGTH_SHORT).show();
		}else {
			model.askCash(ask);
		}
		break;
	case R.id.ask_money:
		askNow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_shape_light_red));
		askNow.setClickable(true);
		break;
	default:
		break;
	}
		
	}
}
