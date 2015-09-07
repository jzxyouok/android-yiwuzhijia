package com.android.house;

import com.BeeFramework.BeeFrameworkApp;
import com.baidu.mapapi.SDKInitializer;

public class HouseApp extends BeeFrameworkApp{
	@Override
	public void onCreate() {
		// TODO 自动生成的方法存根
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
	}

}
