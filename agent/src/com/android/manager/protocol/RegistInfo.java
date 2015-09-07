package com.android.manager.protocol;

import java.io.Serializable;

public class RegistInfo implements Serializable{
	
	private int city_id=1;
	private  String phone;
	private  String pwd;
	private  int sex=1;
	private String validateCode;
	private String sessionId;
	
	private String cityName;
	private String registcodecode;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public int getCity_id() {
		return city_id;
	}
	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getRegistcodecode() {
		return registcodecode;
	}
	public void setRegistcodecode(String registcodecode) {
		this.registcodecode = registcodecode;
	}
	
	
	
	
}
