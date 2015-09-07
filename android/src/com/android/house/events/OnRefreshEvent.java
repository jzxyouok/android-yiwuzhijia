package com.android.house.events;

public class OnRefreshEvent {
	
	int type;
	public OnRefreshEvent(int type)
	{
		this.type=type;
	}
	
	public int getType()
	{
		return this.type;
	}
	
}
