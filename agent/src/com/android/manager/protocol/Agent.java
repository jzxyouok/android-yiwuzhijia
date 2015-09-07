package com.android.manager.protocol;

import org.json.JSONObject;

public class Agent {

	private int id;
	private int agent_id;
	private double total_payment;
	private String agent_name;
	
	private String sale_long;
	private String sale_num;
	private String car_type;
	private String native_place;
	
	
	
	
	public void fromJson(JSONObject jsonObject){
		
		id=jsonObject.optInt("id");
		agent_id=jsonObject.optInt("agent_id");
		total_payment=jsonObject.optDouble("total_payment",0);
		agent_name=jsonObject.optString("agent_name");
		sale_long=jsonObject.optString("sale_long","");
		sale_num=jsonObject.optString("sale_num","");
		car_type=jsonObject.optString("car_type");
		native_place=jsonObject.optString("native_place");
		
		
		
	}




	public String getSale_num() {
		return sale_num;
	}




	public void setSale_num(String sale_num) {
		this.sale_num = sale_num;
	}




	public String getSale_long() {
		return sale_long;
	}




	public void setSale_long(String sale_long) {
		this.sale_long = sale_long;
	}




	public String getCar_type() {
		return car_type;
	}




	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}




	public String getNative_place() {
		return native_place;
	}




	public void setNative_place(String native_place) {
		this.native_place = native_place;
	}




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public int getAgent_id() {
		return agent_id;
	}




	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}




	public double getTotal_payment() {
		return total_payment;
	}




	public void setTotal_payment(double total_payment) {
		this.total_payment = total_payment;
	}




	public String getAgent_name() {
		return agent_name;
	}




	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	
	
	
	
}
