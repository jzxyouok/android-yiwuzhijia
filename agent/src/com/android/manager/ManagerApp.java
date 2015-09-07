package com.android.manager;



import cn.jpush.android.api.JPushInterface;

import com.BeeFramework.BeeFrameworkApp;

public class ManagerApp extends BeeFrameworkApp {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
	}
}
