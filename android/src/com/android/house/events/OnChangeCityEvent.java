package com.android.house.events;


public class OnChangeCityEvent {
	
	private String cityName;
	public OnChangeCityEvent(String cityName)
	{
		this.cityName=cityName;
	}
	
	public String  getCityName()
	{
		return cityName;
	}
}
