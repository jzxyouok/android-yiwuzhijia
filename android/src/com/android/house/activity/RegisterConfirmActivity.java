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

public class RegisterConfirmActivity extends BaseActivity implements OnClickListener{
	private Button resend;
	private Button submit;
	
	private TextView title;

	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_confirm);
		
		initView();
	}
	
	private void initView(){
		resend = (Button)findViewById(R.id.register_resend);
		submit = (Button)findViewById(R.id.register_submit);
		
		title = (TextView)findViewById(R.id.title_text);

		back = (ImageView)findViewById(R.id.title_back);

		title.setText("ע��");
		
		setClickListener();
	}
		
	private void setClickListener(){
		back.setOnClickListener(this);
		resend.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.title_back:
			finish();
			break;
		case R.id.register_resend:
			break;
			
		case R.id.register_submit:
			Intent intent = new Intent(RegisterConfirmActivity.this, HouseMainActivity.class);
			startActivity(intent);
			break;
		}
	}
}
