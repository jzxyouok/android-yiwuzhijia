package com.android.house.events;

public class OnChangeTypeEvent {
	
	int showType;
	
	public OnChangeTypeEvent(int type)
	{
		this.showType=type;
	}

	public int getShowType() {
		return showType;
	}
	
	
}
