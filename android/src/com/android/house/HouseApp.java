package com.android.house;

import java.io.File;

import android.os.Environment;
import cn.jpush.android.api.JPushInterface;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.view.WebImageView;
import com.baidu.mapapi.SDKInitializer;

public class HouseApp extends BeeFrameworkApp {
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
		// ActiveAndroid.initialize(this);
		//WebImageView.setDiskCachingEnabled(true);// 开启图片缓存
		WebImageView.setMemoryCachingEnabled(true);
//		 Jpush推送
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);

	}
	
	public void initImageConfif()
	{
	}

}
