package com.android.house.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.funmi.house.R;

public class RegisterFillInfoActivity extends BaseActivity implements OnClickListener{
	private View maleView;
	private View femaleView;
	private View regionPickView;
	
	private ImageView back;
	private Button registerBtn;//注册按钮
	
	private TextView title;
	
	private ImageView maleImg;
	private ImageView femaleImg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_fill_info);
		
		initView();
	}

	//初始化控件
	private void initView(){
		maleView = findViewById(R.id.register_male_wrapper);
		femaleView = findViewById(R.id.register_female_wrapper);
		regionPickView = findViewById(R.id.register_region_wrapper);
		
		back = (ImageView)findViewById(R.id.title_back);
		registerBtn = (Button)findViewById(R.id.register_btn);
		
		title = (TextView)findViewById(R.id.title_text);
		
		maleImg = (ImageView)findViewById(R.id.register_male_img);
		femaleImg = (ImageView)regionPickView.findViewById(R.id.register_female_img);
		
		title.setText("手机验证");
		
		setClickListener();
	}
		
	private void setClickListener(){
		maleView.setOnClickListener(this);
		femaleView.setOnClickListener(this);
		regionPickView.setOnClickListener(this);
		
		back.setOnClickListener(this);
		registerBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.title_back:
			finish();
			break;
		
		case R.id.register_male_wrapper:
			break;
			
		case R.id.register_female_wrapper:
			break;
			
		case R.id.register_region_wrapper:
			break;
			
		case R.id.register_btn:
			Intent intent = new Intent(RegisterFillInfoActivity.this, RegisterConfirmActivity.class);
			startActivity(intent);
			
			break;
		}
	}
}
