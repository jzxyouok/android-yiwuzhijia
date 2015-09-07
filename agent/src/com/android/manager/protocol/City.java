package com.android.manager.protocol;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class City implements Serializable{
	
	private int city_id=-1;
	private String city_name;
	private double city_lat;
	private double city_long;
	private int is_deleted;	
	private int province_id=-1;
	
	
	
	
	public void fromJson(JSONObject jsonObject) throws JSONException
	{
		this.city_id=jsonObject.optInt("id");
		this.city_name=jsonObject.optString("name");
		this.city_long=jsonObject.optDouble("lng");
		this.city_lat=jsonObject.optDouble("lat");
		this.is_deleted=jsonObject.optInt("is_delete");

	}
	
	public JSONObject toJson(City city) throws JSONException
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("id", city.city_id);
		jsonObject.put("name",city.city_name);
		jsonObject.put("provice_id", city.province_id);
		jsonObject.put("lng", city.city_long);
		jsonObject.put("lat", city.city_lat);
		jsonObject.put("is_delete", city.is_deleted);
		return jsonObject;
	}

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public double getCity_lat() {
		return city_lat;
	}

	public void setCity_lat(double city_lat) {
		this.city_lat = city_lat;
	}

	public double getCity_long() {
		return city_long;
	}

	public void setCity_long(double city_long) {
		this.city_long = city_long;
	}

	public int getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}

	public int getProvince_id() {
		return province_id;
	}

	public void setProvince_id(int province_id) {
		this.province_id = province_id;
	}
	
	
	
	
}
