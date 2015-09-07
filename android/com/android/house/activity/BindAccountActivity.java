package com.android.house.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.funmi.house.R;

public class BindAccountActivity extends BaseActivity implements OnClickListener{
	private View wxWrapper;
	private View alipayWrapper;
	
	private Button bindNow;
	
	private TextView title;
	
	private EditText wxAccount;
	private EditText alipayAccount;
	
	private ImageView back;
	
	private String wxAccountStr;
	private String alipayAccountStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_account);
		
		initView();
	}
	
	private void initView(){
		wxWrapper = findViewById(R.id.bind_wx_wrapper);
		alipayWrapper = findViewById(R.id.bind_alipay_wrapper);
		
		bindNow = (Button)findViewById(R.id.bind_now);
		
		title = (TextView)findViewById(R.id.title_text);
		title.setText("∞Û∂®’Àªß");
		
		wxAccount = (EditText)findViewById(R.id.bind_wx_account);
		alipayAccount = (EditText)findViewById(R.id.bind_alipay_account);
		
		back = (ImageView)findViewById(R.id.title_back);
		
		setClickListener();
	}
	
	private void setClickListener(){
		wxWrapper.setOnClickListener(this);
		alipayWrapper.setOnClickListener(this);
		
		bindNow.setOnClickListener(this);
		
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.title_back:
			finish();
			break;
		
		case R.id.bind_now:
			wxAccountStr = wxAccount.getText().toString();
			alipayAccountStr = alipayAccount.getText().toString();
			
			finish();
			break;
			
		case R.id.bind_alipay_wrapper:
			break;
			
		case R.id.bind_wx_wrapper:
			break;
		}
	}
}
