package com.android.house.jpush;

import cn.jpush.android.api.JPushInterface;

import com.funmi.house.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends Activity{
	private TextView textView;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		textView=(TextView) findViewById(R.id.Notification);
		Intent intent=getIntent();
		if(null!=intent){
			Bundle bundle=getIntent().getExtras();
			String content=bundle.getString(JPushInterface.EXTRA_ALERT);
			textView.setText(content);
		}
	}
	
	
}
