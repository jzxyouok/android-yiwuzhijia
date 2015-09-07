package com.android.house.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import cn.jpush.android.api.JPushInterface;

import com.BeeFramework.activity.BaseActivity;

public class EnterActivity extends BaseActivity{
	
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		shared = getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
		
		boolean isFirstRun = shared.getBoolean("isFirstRun", true);  
		if(!isFirstRun) {
			Intent it = new Intent(this,LoginActivity.class);
			startActivity(it);
			finish();
			
		}else {
			Intent intent=new Intent(this,GalleryImageActivity.class);
			startActivity(intent);
			finish();
		}
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
}
