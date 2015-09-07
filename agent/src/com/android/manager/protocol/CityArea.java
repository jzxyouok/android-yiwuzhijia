package com.android.manager.protocol;

import org.json.JSONObject;

import android.R.integer;

public class CityArea {
	
	private int id;
	private String name;
	
	
	public void fromJson(JSONObject jsonObject){
		id=jsonObject.optInt("id",0);
		name=jsonObject.optString("name","未知");
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
}
