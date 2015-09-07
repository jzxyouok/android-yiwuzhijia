package com.android.house.protocol;

import org.json.JSONObject;

public class HouseDetaiImage {

	private String image_url;
	
	public HouseDetaiImage()
	{
		
	}
	
	public void fromJson(JSONObject jo)
	{
		this.image_url=jo.optString("image1");
	}
	
	
	
}
