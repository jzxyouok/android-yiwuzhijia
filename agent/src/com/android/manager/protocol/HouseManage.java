package com.android.manager.protocol;

import org.json.JSONObject;

public class HouseManage {

	private int id;
	private String name;
	private String average_price;
	private double agent_payment;
	private double sub_agent_payment;
	private double payback;
	private int area_id;
	private String area_name;
	private String location_info;
	private String apartment_id;
	private int property_type;
	private String property_price;
	private String decoration_id;
	private String price_id;
	private String floor_area_ratio;
	
	private String greening_rate;
	private String office_phone;
	private String sale_time_start;
	private String sale_time_end;
	private int lng;
	private int lat;
	private int city_id;
	private int is_delete;
	
	private String head_pic;
	
	public void fromJson(JSONObject jsonObject){
		
		id=jsonObject.optInt("id");
		area_id=jsonObject.optInt("area_id");
		property_type=jsonObject.optInt("property_type");
		lng=jsonObject.optInt("lng");
		lat=jsonObject.optInt("lat");
		city_id=jsonObject.optInt("city_id");
		is_delete=jsonObject.optInt("is_delete");
		
		name=jsonObject.optString("name");
		average_price=jsonObject.optString("average_price");
		area_name=jsonObject.optString("area_name");
		location_info=jsonObject.optString("location_info");
		apartment_id=jsonObject.optString("apartment_id");
		property_price=jsonObject.optString("property_price");
		decoration_id=jsonObject.optString("decoration_id");
		price_id=jsonObject.optString("price_id");
		floor_area_ratio=jsonObject.optString("floor_area_ratio");
	
		office_phone=jsonObject.optString("office_phone");
		office_phone=jsonObject.optString("office_phone");
		sale_time_start=jsonObject.optString("sale_time_start");
		sale_time_end=jsonObject.optString("sale_time_end");
		
		agent_payment=jsonObject.optDouble("agent_payment");
		sub_agent_payment=jsonObject.optDouble("sub_agent_payment");
		payback=jsonObject.optDouble("payback");
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

	public String getAverage_price() {
		return average_price;
	}

	public void setAverage_price(String average_price) {
		this.average_price = average_price;
	}

	public double getAgent_payment() {
		return agent_payment;
	}

	public void setAgent_payment(double agent_payment) {
		this.agent_payment = agent_payment;
	}

	public double getSub_agent_payment() {
		return sub_agent_payment;
	}

	public void setSub_agent_payment(double sub_agent_payment) {
		this.sub_agent_payment = sub_agent_payment;
	}

	public double getPayback() {
		return payback;
	}

	public void setPayback(double payback) {
		this.payback = payback;
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

	public String getApartment_id() {
		return apartment_id;
	}

	public void setApartment_id(String apartment_id) {
		this.apartment_id = apartment_id;
	}

	public int getProperty_type() {
		return property_type;
	}

	public void setProperty_type(int property_type) {
		this.property_type = property_type;
	}

	public String getProperty_price() {
		return property_price;
	}

	public void setProperty_price(String property_price) {
		this.property_price = property_price;
	}

	public String getDecoration_id() {
		return decoration_id;
	}

	public void setDecoration_id(String decoration_id) {
		this.decoration_id = decoration_id;
	}

	public String getPrice_id() {
		return price_id;
	}

	public void setPrice_id(String price_id) {
		this.price_id = price_id;
	}

	public String getFloor_area_ratio() {
		return floor_area_ratio;
	}

	public void setFloor_area_ratio(String floor_area_ratio) {
		this.floor_area_ratio = floor_area_ratio;
	}

	public String getGreening_rate() {
		return greening_rate;
	}

	public void setGreening_rate(String greening_rate) {
		this.greening_rate = greening_rate;
	}

	public String getOffice_phone() {
		return office_phone;
	}

	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}

	public String getSale_time_start() {
		return sale_time_start;
	}

	public void setSale_time_start(String sale_time_start) {
		this.sale_time_start = sale_time_start;
	}

	public String getSale_time_end() {
		return sale_time_end;
	}

	public void setSale_time_end(String sale_time_end) {
		this.sale_time_end = sale_time_end;
	}

	public int getLng() {
		return lng;
	}

	public void setLng(int lng) {
		this.lng = lng;
	}

	public int getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}

	public String getHead_pic() {
		return head_pic;
	}

	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
	}
	
	
	
	
	
	
}
