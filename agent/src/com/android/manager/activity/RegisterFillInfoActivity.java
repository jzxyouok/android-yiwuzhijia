package com.android.manager.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.android.manager.R;
import com.android.manager.businessresponse.LoginBusinessResponse;
import com.android.manager.businessresponse.RegistBusinessResponse;
import com.android.manager.costants.AppConstants;
import com.android.manager.model.GetValidatecodeModel;
import com.android.manager.model.RegistUserModel;
import com.android.manager.protocol.RegistInfo;
import com.android.manager.view.DealDialog;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;

public class RegisterFillInfoActivity extends BaseActivity implements
		OnClickListener, IXListViewListener {
	private LinearLayout linearLayout;
	boolean refreshReg=false;
	private View header;
	private View maleView;
	private View femaleView;
	private View regionPickView;

	private Button registerBtn;
	private Button sendCodeBtn;

	private TextView deal;// 注册协议
	private TextView title;

	private TextView error;

	private TextView city;
	private int city_id=1;
	private int sex_id = 1;

	private EditText psd;
	private EditText phone;
	private EditText certifyCode;

	private ImageView back;
	private ImageView seePsd;
	private ImageView maleImg;
	private ImageView femaleImg;

	private XListView xlistView;

	private String psdStr;
	private String phoneStr;
	private String certifyCodeStr;

	private RegistInfo registInfo = new RegistInfo();
	private GetValidatecodeModel getValidateModel;
	private RegistBusinessResponse response;
	private RegistUserModel registUserMode;
	private LoginBusinessResponse loginBusinessResponse;

	private EditText registcodecode;

	private String PHPSESSID;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case AppConstants.JSON_VALID_OK: {
				Bundle bundle = msg.getData();
				PHPSESSID = bundle.getString("PHPSESSID");
				Log.d("mao", "session=" + PHPSESSID);
				Toast.makeText(RegisterFillInfoActivity.this,
						"验证码获取成功，请留意您的短信哦，亲~", Toast.LENGTH_SHORT).show();
				}
				break;
			case AppConstants.JSON_VALID_ERR: {
				refreshReg=true;
				String message=(String) msg.obj;
				Toast.makeText(RegisterFillInfoActivity.this,
						message, Toast.LENGTH_SHORT).show();
				}
				break;
			case AppConstants.LOGIN_OK: {
				Toast.makeText(RegisterFillInfoActivity.this, "注册成功,欢迎登录~",Toast.LENGTH_SHORT).show();
				Intent intent =new Intent();
				intent.putExtra("username", registInfo.getPhone());
				intent.putExtra("password",registInfo.getPwd());
				setResult(1, intent);
				finish();
				}
				break;
			case AppConstants.LOGIN_ERR: {
				Toast.makeText(RegisterFillInfoActivity.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				}
				break;
			case AppConstants.JSON_NETWORK_ERROR: {
				Toast.makeText(RegisterFillInfoActivity.this, "网络出错",
						Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
			}
		}

	};

	private int count = 60;
	private Handler handlerBtn = new Handler();
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			if (count > 0) {
				if(refreshReg)
				{
					refresh();
					return ;
				}
				count--;
				sendCodeBtn.setText("(" + count + ")秒后重发");
				handlerBtn.postDelayed(runnable, 1000);
			} else if (count == 0) {
				refresh();
				return ;
			}
		}
		private void refresh()
		{
			count = 60;
			sendCodeBtn.setText("发送验证码");
			sendCodeBtn.setClickable(true);
			sendCodeBtn.setTextColor(Color.WHITE);
			sendCodeBtn
					.setBackgroundResource(R.drawable.btn_shape_heavy_red);
			handlerBtn.removeCallbacks(runnable);
			return ;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_fill_info);
		linearLayout=(LinearLayout)findViewById(R.id.linearLayout_register);
		linearLayout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
				return imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
				
			}
		});
		initView();
		this.getValidateModel = new GetValidatecodeModel(this);
		this.response = new RegistBusinessResponse(this, handler);
		this.getValidateModel.addResponseListener(response);

		this.registUserMode = new RegistUserModel(this);
		this.loginBusinessResponse = new LoginBusinessResponse(this, handler);
		registUserMode.addResponseListener(loginBusinessResponse);
	}

	private void initView() {

		maleView = findViewById(R.id.register_male_wrapper);
		femaleView = findViewById(R.id.register_female_wrapper);
		regionPickView = findViewById(R.id.register_region_wrapper);
		city = (TextView) findViewById(R.id.register_region_pick);

		back = (ImageView) findViewById(R.id.title_back);
		registerBtn = (Button)findViewById(R.id.register_btn);
		sendCodeBtn = (Button) findViewById(R.id.activity_register_send_cerifycode);

		title = (TextView) findViewById(R.id.title_text);
		deal = (TextView) findViewById(R.id.register_deal);
		error = (TextView)findViewById(R.id.activity_register_filinfo_error);

		psd = (EditText) findViewById(R.id.activity_register_filinfo_psd);
		phone = (EditText)findViewById(R.id.login_username);
		certifyCode = (EditText)findViewById(R.id.activity_register_cerifycode);

		seePsd = (ImageView) findViewById(R.id.activity_register_filinfo_see_psd);

		maleImg = (ImageView)findViewById(R.id.register_male_img);
		femaleImg = (ImageView) findViewById(R.id.register_female_img);
		registcodecode = (EditText)findViewById(R.id.activity_register_registcodecode);

		title.setText("注册");
		city.setText("成都");

		setClickListener();
	}

	private void setClickListener() {
		maleView.setOnClickListener(this);
		femaleView.setOnClickListener(this);
		regionPickView.setOnClickListener(this);

		deal.setOnClickListener(this);
		back.setOnClickListener(this);
		registerBtn.setOnClickListener(this);
		sendCodeBtn.setOnClickListener(this);

		seePsd.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					psd.setTransformationMethod(HideReturnsTransformationMethod
							.getInstance());
				else if (event.getAction() == MotionEvent.ACTION_UP)
					psd.setTransformationMethod(PasswordTransformationMethod
							.getInstance());

				return true;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;

		case R.id.register_deal:
			DealDialog dialog = new DealDialog(this, R.style.customTheme);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			break;

		case R.id.register_male_wrapper:
			sex_id = 1;
			maleImg.setImageResource(R.drawable.icon_select);
			femaleImg.setImageResource(R.drawable.icon_unselect);
			break;

		case R.id.register_female_wrapper:
			sex_id = 0;
			maleImg.setImageResource(R.drawable.icon_unselect);
			femaleImg.setImageResource(R.drawable.icon_select);
			break;

		case R.id.register_region_wrapper:

			Intent intent = new Intent(RegisterFillInfoActivity.this,
					SelectLocationActivity.class);
			startActivityForResult(intent, 1111);
			break;

		case R.id.register_btn:

			psdStr = psd.getText().toString();
			phoneStr = phone.getText().toString();
			certifyCodeStr = certifyCode.getText().toString();

			if (psdStr.length() < 6) {
				error.setVisibility(View.VISIBLE);
			} else {
				error.setVisibility(View.GONE);
				this.registInfo.setValidateCode(this.certifyCode.getText()
						.toString());
				this.registInfo.setSessionId(this.PHPSESSID);
				this.registInfo.setPwd(this.psd.getText().toString());
				this.registInfo.setCity_id(city_id);
				this.registInfo.setCityName(city.getText().toString());
				this.registInfo.setSex(sex_id);
				this.registInfo.setRegistcodecode(registcodecode.getText().toString());
				this.registUserMode.registUser(this.registInfo);
			}
			break;

		case R.id.activity_register_send_cerifycode:
			if (count == 60) {
				String userPhone=phone.getText().toString();
				String telRegex = "[1][3578]\\d{9}";
				if (userPhone.length() != 11) {
					Toast.makeText(getApplicationContext(), "请输入 11位手机号",
							Toast.LENGTH_LONG).show();
				} else if (!userPhone.matches(telRegex)) {
					Toast.makeText(getApplicationContext(), "请输入正确的手机号",
							Toast.LENGTH_LONG).show();
				} else {
				sendCodeBtn.setClickable(false);
				sendCodeBtn
						.setBackgroundResource(R.drawable.btn_shape_light_gray);
				sendCodeBtn.setTextColor(0xfff5f5f5);
				{
					this.registInfo.setPhone(this.phone.getText().toString());
					this.registInfo.setPwd(this.psd.getText().toString());
					this.registInfo.setSessionId(this.PHPSESSID);
					this.getValidateModel.registUser(this.phone.getText()
							.toString());
					refreshReg=false;
				}
				handler.post(runnable);
			}
			break;
			}
		}
	}

	@Override
	public void onRefresh(int id) {

	}

	@Override
	public void onLoadMore(int id) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1111 && resultCode == 2222) {
			city.setText(data.getExtras().getString("city"));
			city_id = data.getIntExtra("city_id", 0);
		}
	}
}
