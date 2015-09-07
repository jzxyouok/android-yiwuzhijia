package com.android.house.events;

public class OnReloginEvent {
	private int msg;
	
	public OnReloginEvent(int msg)
	{
		this.msg=msg;
	}
	
	
	public int  getMsg()
	{
		return this.msg;
	}
}
