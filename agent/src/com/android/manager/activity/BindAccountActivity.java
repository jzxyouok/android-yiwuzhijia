package com.android.manager.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import com.android.manager.model.GetValidatecodeModel;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;

public class BindAccountActivity extends BaseActivity implements
		OnClickListener, BusinessResponse {

	private View alipayWrapper;

	private RelativeLayout bindNow;

	private TextView title;

	private EditText alipayAccount;

	private ImageView back;

	private boolean isAlipay = true;

	private String alipayAccountStr;

	private AccountModel model;
	private GetUserInfoModel userInfoModel;
	private CacheInfo cachInfo;

	private TextView bindText;

	private boolean isHaveAccount = false;

	private EditText ceficode;
	private Button sendCeficode;

	private EditText phone;
	private GetValidatecodeModel getValidateModel;
	
	private CountDownTimer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_account);
		initView();
	}

	private void initView() {

		alipayWrapper = findViewById(R.id.bind_alipay_wrapper);

		bindNow = (RelativeLayout) findViewById(R.id.bind_now);

		title = (TextView) findViewById(R.id.title_text);
		title.setText("账户绑定");

		alipayAccount = (EditText) findViewById(R.id.bind_alipay_account);

		bindText = (TextView) findViewById(R.id.bind_now_text);

		back = (ImageView) findViewById(R.id.title_back);

		cachInfo = UserInfoCacheUtil.getCacheInfo(BindAccountActivity.this);

		ceficode = (EditText) findViewById(R.id.activity_register_cerifycode);
		sendCeficode = (Button) findViewById(R.id.activity_register_send_cerifycode);
		sendCeficode.setOnClickListener(this);
		ceficode.setOnClickListener(this);

		phone = (EditText) findViewById(R.id.register_username);
		bindNow.setClickable(false);

		setClickListener();

		model = new AccountModel(this);
		model.addResponseListener(this);

		userInfoModel = new GetUserInfoModel(this);
		userInfoModel.addResponseListener(this);
		
		
		getValidateModel=new GetValidatecodeModel(this);
		getValidateModel.addResponseListener(this);
		
		timer=new CountDownTimer(60*1000,1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				sendCeficode.setText("("+millisUntilFinished/1000+")秒后重发");
				sendCeficode.setClickable(false);
				sendCeficode.setTextColor(Color.WHITE);
				sendCeficode
				.setBackgroundResource(R.drawable.btn_shape_heavy_gray);
				
			}
			
			@Override
			public void onFinish() {
				sendCeficode.setText("发送验证码");
				sendCeficode.setClickable(true);
				sendCeficode.setTextColor(0xfff5f5f5);
				sendCeficode.setBackgroundResource(R.drawable.btn_shape_heavy_red);
			}
		};
		

	}

	private void setClickListener() {

		alipayWrapper.setOnClickListener(this);

		bindNow.setOnClickListener(this);

		back.setOnClickListener(this);

		alipayAccount.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		userInfoModel.getUserinfo(cachInfo.getUid(), cachInfo.getSessionId());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;

		case R.id.bind_now:
			alipayAccountStr = alipayAccount.getText().toString();
			String code=ceficode.getText().toString();
//			if (code.equals("")) {
//				Toast.makeText(BindAccountActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
//			}else if (code.length()<=5) {
//				Toast.makeText(BindAccountActivity.this, "请输入6位验证码", Toast.LENGTH_SHORT).show();
//			}else {
//				model.bindAccount(alipayAccountStr,code);
//				
//			}
			model.bindAccount(alipayAccountStr,code);
		
			break;

		case R.id.bind_alipay_account:
			bindNow.setBackgroundResource(R.drawable.btn_shape_light_red);
			bindNow.setClickable(true);
			break;
		case R.id.activity_register_cerifycode:
			bindNow.setBackgroundResource(R.drawable.btn_shape_light_red);
			bindNow.setClickable(true);
			break;
		case R.id.activity_register_send_cerifycode:
			String telRegex= "[1][358]\\d{9}";
			String phoneStr=phone.getText().toString();
			if(phoneStr.length()!=11){
				Toast.makeText(getApplicationContext(), "请输入 11位手机号", Toast.LENGTH_LONG).show();
			}else if(!phoneStr.matches(telRegex)){
				Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_LONG).show();
				}else{
					getValidateModel.registUser(phoneStr);
				}
				
			
			break;
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.BIND_ACCOUNT)) {
			Toast.makeText(BindAccountActivity.this, "绑定成功", Toast.LENGTH_LONG)
					.show();

			// Intent intent = new Intent(BindAccountActivity.this,
			// MyWalletActivity.class);
			// startActivity(intent);

			finish();
		} else if (url.endsWith(ProtocolConst.GET_USERINFO)) {
			phone.setText(userInfoModel.user.getPhone());
			if (userInfoModel.user.getAccount().length() > 0) {
				alipayAccount.setText(userInfoModel.user.getAccount());
				bindNow.setBackgroundResource(R.drawable.btn_shape_light_red);
				bindNow.setClickable(true);
				bindText.setText("更换绑定的账号");
				isHaveAccount = true;
			} else {
				isHaveAccount = false;
				bindText.setText("立刻绑定");
				alipayAccount.setHint("请输入支付宝帐号");

			}
		}else if (url.endsWith(ProtocolConst.VALIDATECODE)) {
			if (jo.optString("status").equals("200")) {
				Toast.makeText(this, "验证码获取成功，请留意您的短信哦，亲~", Toast.LENGTH_LONG).show();
				timer.start();
			}else {
				Toast.makeText(this, jo.optString("msg"), Toast.LENGTH_LONG).show();
			}
		}

	}
}
