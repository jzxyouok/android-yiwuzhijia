package com.android.house.activity;

import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.BeeFramework.activity.BaseActivity;
import com.android.house.businessresponse.LoginBusinessResponse;
import com.android.house.costants.AppConstants;
import com.android.house.events.OnReloginEvent;
import com.android.house.model.LoginModel;
import com.android.house.util.UserInfoCacheUtil;
import com.android.house.view.ForgetPsdDialog;
import com.external.eventbus.EventBus;
import com.funmi.house.R;

public class LoginActivity extends BaseActivity implements OnClickListener, OnTouchListener {
	private Button login;
	private Button register;
	private EditText username;
	private EditText password;
	private TextView forgetPsd;
	private TextView guest;
	SharedPreferences log_save;
	private InputMethodManager manager;

	private LoginModel mLoginModel;
	private LoginBusinessResponse loginResponse;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			if (msg.what == AppConstants.LOGIN_OK) {
				// 更改登录界面，提示用户点击登录
				Toast.makeText(getApplicationContext(), "欢迎回来!", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LoginActivity.this, HouseMainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				Bundle bundle = new Bundle();
				bundle.putInt("isLogin", AppConstants.EVENT_LOGIN);
				intent.putExtras(bundle);
				startActivity(intent);
			} else if (msg.what == AppConstants.LOGIN_ERR) {
				Toast.makeText(getApplicationContext(), "用户密码错误", Toast.LENGTH_SHORT).show();
			} else if (msg.what == AppConstants.JSON_NETWORK_ERROR) {
				Toast.makeText(getApplicationContext(), "网络出错", Toast.LENGTH_SHORT).show();
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initView();
		this.mLoginModel = new LoginModel(this);
		this.loginResponse = new LoginBusinessResponse(this, handler);
		this.mLoginModel.addResponseListener(this.loginResponse);
		EventBus.getDefault().register(this);

		setClickListener();
	}

	private void initView() {
		login = (Button) findViewById(R.id.login_btn);
		register = (Button) findViewById(R.id.login_register);
		username = (EditText) findViewById(R.id.login_username);
		password = (EditText) findViewById(R.id.login_password);
		forgetPsd = (TextView) findViewById(R.id.login_forget_psd);
		guest = (TextView) findViewById(R.id.login_walk);
		log_save = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		username.setText(log_save.getString("username", null));
		password.setText(log_save.getString("password", null));
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		JPushInterface.setAlias(LoginActivity.this, UserInfoCacheUtil.getCacheInfo(this).getPhone(), new TagAliasCallback() {
			@Override
			public void gotResult(int arg0, String arg1, Set<String> arg2) {

			}
		});
	}

	private void setClickListener() {
		login.setOnClickListener(this);
		register.setOnClickListener(this);
		forgetPsd.setOnClickListener(this);
		guest.setOnClickListener(this);
	}

	public static boolean isMobileNO(String mobiles) {
		/**
		 * 验证手机格式 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */

		String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		String strUser = username.getText().toString();
		String strPassword = password.getText().toString();

		String telRegex = "[1][3578]\\d{9}";
		switch (v.getId()) {
		case R.id.login_btn:
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo == null) {
				Toast.makeText(LoginActivity.this, "无网络连接", Toast.LENGTH_LONG).show();
			} else {

				if (strUser.equals("")) {
					Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_LONG).show();
				} else if (strUser.length() != 11) {
					Toast.makeText(getApplicationContext(), "请输入11位手机号", Toast.LENGTH_LONG).show();
				} else if (!strUser.matches(telRegex)) {
					Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_LONG).show();
				} else if (strPassword.equals("")) {
					Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_LONG).show();
				} else if (strPassword.length() < 6) {
					Toast.makeText(getApplicationContext(), "请输入至少6位密码", Toast.LENGTH_LONG).show();
				} else {
					this.mLoginModel.login(username.getText().toString(), password.getText().toString());
					Editor editor = log_save.edit();
					editor.putString("username", username.getText().toString());
					editor.putString("password", password.getText().toString());
					editor.commit();
				}
			}
			break;

		case R.id.login_register:
			intent = new Intent(LoginActivity.this, RegisterFillInfoActivity.class);
			startActivityForResult(intent, 200);
			break;

		case R.id.login_forget_psd: {
			intent = new Intent(LoginActivity.this, ForgetPsdActivity.class);
			startActivity(intent);
		}
			break;
		case R.id.login_walk: {
			intent = new Intent(this, HouseMainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
		}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.mLoginModel = null;
		this.loginResponse = null;
		EventBus.getDefault().unregister(this);
		System.gc();
	}

	public void onEventMainThread(OnReloginEvent event) {

		if (event.getMsg() == AppConstants.EVENT_RELOGIN) {
			Toast.makeText(getApplicationContext(), "亲，想体验更多功能，请重新登录哟", Toast.LENGTH_SHORT).show();
		} else if (event.getMsg() == AppConstants.EVENT_LOGIN) {
			Toast.makeText(getApplicationContext(), "注册成功，亲欢迎回来", Toast.LENGTH_SHORT).show();
			log_save = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
			username.setText(log_save.getString("username", null));
			password.setText(log_save.getString("password", null));
		}
	}

	/*
	 * @Override public boolean onTouch(View v, MotionEvent event) { int iAction
	 * = event.getAction(); if (iAction == MotionEvent.ACTION_CANCEL || iAction
	 * == MotionEvent.ACTION_DOWN || iAction == MotionEvent.ACTION_MOVE) {
	 * return false; } return false; }
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 200 && resultCode == 1) {
			if (data != null) {
				Toast.makeText(getApplicationContext(), "注册成功，亲欢迎回来", Toast.LENGTH_SHORT).show();
				username.setText(data.getStringExtra("phone"));
				password.setText(data.getStringExtra("password"));
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

}
