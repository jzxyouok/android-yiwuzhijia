package com.android.house.events;

public class OnChangeLoginEvent {

	private int isLogin;//1代表登录，0代表未登录
	
	public OnChangeLoginEvent(int isLogin)
	{
		this.isLogin=isLogin;
	}

	public int getIsLogin() {
		return isLogin;
	}
	
}
