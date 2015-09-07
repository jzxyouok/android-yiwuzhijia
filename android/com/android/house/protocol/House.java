package com.android.house.protocol;

import org.json.JSONException;
import org.json.JSONObject;

import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name="house")
public class House {
	@Column(name="house_id")
	private int id;
	@Column(name="house_name")
	private String house_name;
	@Column(name="house_price")
	private float house_price;
	@Column(name="house_detail")
	private String house_detail;
	@Column(name="house_imgurl")
	private String house_imgurl;
	@Column(name="house_latitude")
	private double house_latitude;
	@Column(name="house_longtitude")
	private double house_longtitude;
	
	public void  fromJson(JSONObject jsonObject)  throws JSONException
	{
		if(null==jsonObject)
		{
			return ;
		}
		
		this.id=jsonObject.optInt("id");
		this.house_name=jsonObject.optString("house_name");
		this.house_price=(float) jsonObject.optDouble("house_price");
		this.house_detail=jsonObject.optString("house_detail");
		this.house_imgurl=jsonObject.optString("house_imurl");
		this.house_latitude=jsonObject.optDouble("house_latitude");
		this.house_longtitude=jsonObject.optDouble("house_longtitude");
		
		
	}
	
	public JSONObject  toJson() throws JSONException
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("id", this.id);
		jsonObject.put("house_name", this.house_name);
		jsonObject.put("house_price", this.house_price);
		jsonObject.put("house_detail", this.house_detail);
		jsonObject.put("house_imgurl", this.house_imgurl);
		jsonObject.put("house_latitude", this.house_latitude);
		jsonObject.put("house_longtidue", this.house_longtitude);
		return jsonObject;
	}

	public String getHouse_name() {
		return house_name;
	}

	public float getHouse_price() {
		return house_price;
	}

	public String getHouse_detail() {
		return house_detail;
	}

	public String getHouse_imgurl() {
		return house_imgurl;
	}

	public double getHouse_latitude() {
		return house_latitude;
	}

	public double getHouse_longtitude() {
		return house_longtitude;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if(o.getClass().equals(this.getClass()))
		{
			return this.getId()==((House)o).getId();
		}
		else
			return false;
	}
	
	
	
}
