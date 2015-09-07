package com.android.manager.protocol;


public class UserLocation {
	private String sessionId;
	private double latitude;
	private double longtitude;
	
	public String getSessionId(){
		return sessionId;
	}
	
	public double getLatitude(){
		return latitude;
	}
	
	public double getLongtitude(){
		return longtitude;
	}
	
	public void setSessionId(String sessionId){
		this.sessionId = sessionId;
	}
	
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	
	public void setLongtitude(double longtitude){
		this.longtitude = longtitude;
	}
}
