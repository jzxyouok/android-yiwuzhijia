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

public class MyHouseActvitiy extends BaseActivity implements OnClickListener{
	private Button fail;
	private Button undone;
	private Button success;
	
	private TextView edit;
	private TextView title;
	
	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myhouse);
		
		initView();
	}
	
	private void initView(){
		edit = (TextView)findViewById(R.id.house_title_edit);
		title = (TextView)findViewById(R.id.house_title_text);
		title.setText("我的房源");
		
		fail = (Button)findViewById(R.id.fail);
		undone = (Button)findViewById(R.id.undone);
		success = (Button)findViewById(R.id.success);
		
		back = (ImageView)findViewById(R.id.house_title_back);
		
		setClickListener();
	}
	
	private void setClickListener(){
		edit.setOnClickListener(this);
		fail.setOnClickListener(this);
		undone.setOnClickListener(this);
		success.setOnClickListener(this);
		
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		
		switch(v.getId()){
		case R.id.house_title_back:
			finish();
			break;
			
		case R.id.house_title_edit:
			break;
			
		case R.id.fail:
			intent = new Intent(MyHouseActvitiy.this, MyHouseFailActivity.class);
			startActivity(intent);
			break;
			
		case R.id.undone:
			intent = new Intent(MyHouseActvitiy.this, MyHouseUndoneActivity.class);
			startActivity(intent);
			break;
			
		case R.id.success:
			intent = new Intent(MyHouseActvitiy.this, MyHouseSuccessActivity.class);
			startActivity(intent);
			break;
		}
	}
}
