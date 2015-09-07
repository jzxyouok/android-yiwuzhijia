package com.android.manager.protocol;


import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

public class House implements Serializable {
	private int house_id=-1;

	private String name=null;

	private  double average_price =-1;
	
	private int area_id =-1; 
	
	private String area_name =null;
	
	private String location_info =null;
	
	private float house_price =-1;
	
	private int apartment_id=-1;
	
	private int property_type;
	
	private String property_price;
	
	private String decoration_id;
	
	private int price_id=-1;
	
	private String floor_area_ratio;
	
	private String office_phone=null;
	
	private double lat;
	
	private double lng;
	
	private int city_id=-1;
	
	private String green_rate;
	
	private int payback=-1;
	
	private String head_pic =null;
	
	private String house_making_status ;
	
	
	private String house_name;
	private String house_address;
	private int user_id;
	private int agent_id;
	private int business_status;
	private String create_time;
	private int recordId;
	
	
	
	
	
	



	public void  fromJson(JSONObject jsonObject)  throws JSONException
	{
		
		this.house_id=jsonObject.optInt("id");
		this.recordId=jsonObject.optInt("id");
		this.name=jsonObject.optString("name");
		this.area_name=jsonObject.optString("area_name");
		this.location_info=jsonObject.optString("location_info");
		this.lng=jsonObject.optDouble("lng");
		this.lat=jsonObject.optDouble("lat");
		this.apartment_id=jsonObject.optInt("apartment_id");
		this.average_price=jsonObject.optDouble("average_price",0);
		this.payback=jsonObject.optInt("payback");
		this.area_id=jsonObject.optInt("area_id");
		this.property_type=jsonObject.optInt("property_type");
		this.property_price=jsonObject.optString("property_price");
		this.decoration_id=jsonObject.optString("decoration_id");
		this.price_id=jsonObject.optInt("price_id");
		this.floor_area_ratio=jsonObject.optString("floor_area_ratio");
		this.green_rate=jsonObject.optString("greening_rate");
		this.office_phone=jsonObject.optString("office_phone");
		this.head_pic=jsonObject.optString("head_pic");
		this.city_id=jsonObject.optInt("city_id");
		this.house_making_status=jsonObject.optString("house_making_status");
		
		
		//this.house_id=jsonObject.optInt("house_id");
		this.house_name=jsonObject.optString("house_name");
		this.house_address=jsonObject.optString("house_address");
		this.create_time=jsonObject.optString("create_time");
		
	}
	
	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getHouse_name() {
		return house_name;
	}


	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}


	public String getHouse_address() {
		return house_address;
	}


	public void setHouse_address(String house_address) {
		this.house_address = house_address;
	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public int getAgent_id() {
		return agent_id;
	}


	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}


	public int getBusiness_status() {
		return business_status;
	}


	public void setBusiness_status(int business_status) {
		this.business_status = business_status;
	}


	public String getCreate_time() {
		return create_time;
	}


	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}


	public String getGreen_rate() {
		return green_rate;
	}


	public int getPayback() {
		return payback;
	}

	public void setPayback(int payback) {
		this.payback = payback;
	}

	public String getHead_pic() {
		return head_pic;
	}

	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
	}

	public void setAverage_price(double average_price) {
		this.average_price = average_price;
	}

	

	public String getHouse_making_status() {
		return house_making_status;
	}


	public int getHouse_id() {
		return house_id;
	}

	public void setHouse_id(int house_id) {
		this.house_id = house_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAverage_price() {
		return average_price;
	}

	public void setAverage_price(int average_price) {
		this.average_price = average_price;
	}

	public int getArea_id() {
		return area_id;
	}

	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getLocation_info() {
		return location_info;
	}

	public void setLocation_info(String location_info) {
		this.location_info = location_info;
	}

	public float getHouse_price() {
		return house_price;
	}

	public void setHouse_price(float house_price) {
		this.house_price = house_price;
	}

	public int getApartment_id() {
		return apartment_id;
	}

	public void setApartment_id(int apartment_id) {
		this.apartment_id = apartment_id;
	}

	public int getProperty_type() {
		return property_type;
	}

	public String getProperty_price() {
		return property_price;
	}

	public String getDecoration_id() {
		return decoration_id;
	}

	public int getPrice_id() {
		return price_id;
	}

	public void setPrice_id(int price_id) {
		this.price_id = price_id;
	}

	public String getFloor_area_ratio() {
		return floor_area_ratio;
	}

	public String getOffice_phone() {
		return office_phone;
	}

	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}
	
	
}
