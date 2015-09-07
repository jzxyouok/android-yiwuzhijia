package com.android.house.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.funmi.house.R;


public class LoginActivity extends BaseActivity implements OnClickListener{
	private Button login;//��¼
	private Button register;//ע��
	private TextView username;//�û���
	private TextView password;//����
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		initView();
		setClickListener();
	}
	
	//��ʼ���ؼ�
	private void initView(){
		login = (Button)findViewById(R.id.login_btn);
		register = (Button)findViewById(R.id.login_register);
		
		username = (TextView)findViewById(R.id.login_username);
		password = (TextView)findViewById(R.id.login_password);
	}
	
	//��ʼ������¼�
	private void setClickListener(){
		login.setOnClickListener(this);
		register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		
		switch (v.getId()) {
		case R.id.login_btn:
			intent = new Intent(LoginActivity.this, HouseMainActivity.class);
			startActivity(intent);
			
			break;

		case R.id.login_register:
			intent = new Intent(LoginActivity.this, RegisterFillInfoActivity.class);
			startActivity(intent);
			
			break;
			
		default:
			break;
		}
	}
}
