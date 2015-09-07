package com.android.manager.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.costants.AppConstants;
import com.android.manager.model.LoginModel;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.User;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private Button login;
	private Button register;

	private TextView username;
	private TextView password;
	private TextView forgetPsd;
	private LoginModel loginModel;

	private SharedPreferences shared;
	private SharedPreferences.Editor editor;

	private TextView loginWalk;
	private InputMethodManager manager;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			if (msg.what == AppConstants.LOGIN_OK) {
				Toast.makeText(LoginActivity.this, "欢迎回来", Toast.LENGTH_SHORT)
						.show();
				Intent intent = new Intent(LoginActivity.this,
						ManagerMainActivity.class);
				CacheInfo cacheInfo = UserInfoCacheUtil
						.getCacheInfo(LoginActivity.this);
				Bundle bundle = new Bundle();
				bundle.putSerializable("cacheInfo", cacheInfo);
				intent.putExtras(bundle);
				startActivity(intent);
			} else if (msg.what == AppConstants.LOGIN_ERR) {
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == AppConstants.JSON_NETWORK_ERROR) {
				Toast.makeText(getApplicationContext(), "网络出错",
						Toast.LENGTH_SHORT).show();
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initView();
		setClickListener();
		loginModel = new LoginModel(this);

		loginModel.addResponseListener(new LoginResponse());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}

	private void initView() {
		login = (Button) findViewById(R.id.login_btn);
		register = (Button) findViewById(R.id.login_register);

		username = (TextView) findViewById(R.id.login_username);
		password = (TextView) findViewById(R.id.login_password);
		forgetPsd = (TextView) findViewById(R.id.login_forget_psd);
		loginWalk = (TextView) findViewById(R.id.login_walk);
		loginWalk.setVisibility(View.GONE);

		shared = getSharedPreferences("userInfo", 0);
		editor = shared.edit();
		username.setText(shared.getString("userName", ""));
		password.setText(shared.getString("password", ""));

		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	private void setClickListener() {
		login.setOnClickListener(this);
		register.setOnClickListener(this);
		forgetPsd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		String strUser = username.getText().toString();
		String strPassword = password.getText().toString();
		String telRegex = "[1][3578]\\d{9}";
		switch (v.getId()) {
		case R.id.login_btn:
			if (strUser.equals("")) {
				Toast.makeText(getApplicationContext(), "请输入手机号",
						Toast.LENGTH_LONG).show();
			} else if (strUser.length() != 11) {
				Toast.makeText(getApplicationContext(), "请输入11位手机号",
						Toast.LENGTH_LONG).show();
			} else if (!strUser.matches(telRegex)) {
				Toast.makeText(getApplicationContext(), "请输入正确的手机号",
						Toast.LENGTH_LONG).show();
			} else if (strPassword.equals("")) {
				Toast.makeText(getApplicationContext(), "请输入密码",
						Toast.LENGTH_LONG).show();
			}
			/*
			 * else if (strPassword.length() < 6) {
			 * Toast.makeText(getApplicationContext(), "请输入至少6位密码",
			 * Toast.LENGTH_LONG).show();}
			 */
			else {

				loginModel.login(username.getText().toString(), password
						.getText().toString(), 2);
			}

			break;

		case R.id.login_register:
			intent = new Intent(LoginActivity.this,
					RegisterFillInfoActivity.class);
			startActivityForResult(intent, 200);
			break;

		case R.id.login_forget_psd:
			intent = new Intent(LoginActivity.this, ForgetPsdActivity.class);
			startActivity(intent);
			break;
		case R.id.textView1:
			intent = new Intent(this, ManagerMainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 200 && resultCode == 1) {
			String uName = data.getStringExtra("username");
			String uPwd = data.getStringExtra("password");
			username.setText(uName);
			password.setText(uPwd);
		}

	}

	class LoginResponse implements BusinessResponse {

		@Override
		public void OnMessageResponse(String url, JSONObject jo,
				AjaxStatus status) throws JSONException {
			Log.d("mao", jo.toString());
			int reply = jo.optInt("status");
			Message msg = new Message();
			if (reply == 200) {
				JSONObject entity = jo.optJSONObject("entities");
				String sessionId = entity.optString("PHPSESSID");
				User user = new User();
				JSONObject jsonUser = entity.optJSONObject("user");
				user.fromJson(jsonUser);
				Log.d("mao",
						"user =" + user.getUser_id() + " " + user.getUsername()
								+ " " + user.getCity());
				CacheInfo cacheInfo = new CacheInfo();
				cacheInfo.setUid(user.getUser_id());
				cacheInfo.setCity_id(user.getCity());
				cacheInfo.setLogin(true);
				cacheInfo.setNick_name(user.getUsername());
				cacheInfo.setPhone(user.getPhone());
				cacheInfo.setSessionId(sessionId);
				cacheInfo.setStype(user.getStype());
				cacheInfo.setSex(user.getSex());
				UserInfoCacheUtil.saveCacheInfo(LoginActivity.this, cacheInfo);

				editor.putString("userName", username.getText().toString());
				editor.putString("password", password.getText().toString());
				editor.commit();
				Toast.makeText(LoginActivity.this, "欢迎回来", Toast.LENGTH_SHORT)
						.show();
				msg.what = AppConstants.LOGIN_OK;
				handler.sendMessage(msg);

			} else {
				Toast.makeText(LoginActivity.this, jo.optString("msg"),
						Toast.LENGTH_SHORT).show();
				msg.what = AppConstants.LOGIN_ERR;
				msg.obj = jo.optString("msg");
				handler.sendMessage(msg);
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}
}
