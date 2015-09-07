package com.android.house.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.android.house.adapter.MyHouseFailListAdapter;
import com.funmi.house.R;

public class MyHouseFailActivity extends BaseActivity{
	private TextView title;
	private TextView manager;
	private TextView serviceTime;

	private ImageView back;
	
	private ListView buyHouseList;
	private MyHouseFailListAdapter adapter;
	
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myhouse_fail);
		
		initView();
	}

	private void initView(){
		title = (TextView)findViewById(R.id.title_text);
		intent=getIntent();
		title.setText(intent.getStringExtra("house_name"));
		
		manager = (TextView)findViewById(R.id.fail_manager);
		serviceTime = (TextView)findViewById(R.id.fail_service_time);
		
		back = (ImageView)findViewById(R.id.title_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		buyHouseList = (ListView)findViewById(R.id.fail_buyhouse_list);
		adapter = new MyHouseFailListAdapter(this);
		buyHouseList.setAdapter(adapter);
	}
}
