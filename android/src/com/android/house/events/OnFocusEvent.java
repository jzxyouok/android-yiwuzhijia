package com.android.house.events;

import com.baidu.mapapi.model.LatLng;

public class OnFocusEvent {
	
	LatLng ll;
	public OnFocusEvent(double latitude,double longtitude)
	
	{
		this.ll=new LatLng(latitude,longtitude);
	}
	public LatLng getLl() {
		return ll;
	}
	public void setLl(LatLng ll) {
		this.ll = ll;
	}
	
	
}
