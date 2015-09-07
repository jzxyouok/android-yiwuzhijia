package com.android.house.protocol;

import org.json.JSONException;
import org.json.JSONObject;

public class Area {
	private int id;
	private String name;
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getAreaId(){
		return id;
	}
	
	public String getAreaName(){
		return name;
	}
	
	public void fromJson(JSONObject jsonObject) throws JSONException{
		this.id = jsonObject.optInt("id");
		this.name = jsonObject.optString("name");
	}
}
