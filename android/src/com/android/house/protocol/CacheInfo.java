package com.android.house.protocol;

import java.io.Serializable;

public class CacheInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5366530723044616889L;
	private String phone=null;
	private int uid=-1;
	private String sessionId=null;
	private boolean isLogin=false;
	private int city_id=-1;
	private String nick_name=null;
	
	private int agent_id;
	
	private double total_money;
	private double account_balance;
	
	
	
	public double getTotal_money() {
		return total_money;
	}
	public void setTotal_money(double total_money) {
		this.total_money = total_money;
	}
	public double getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(double account_balance) {
		this.account_balance = account_balance;
	}
	public int getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
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
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	
	public void clearSelf()
	{
		this.phone=null;
		this.uid=-1;
		this.sessionId=null;
		this.isLogin=false;
		this.city_id=-1;
		this.nick_name=null;
		this.agent_id=-1;
	}
	
}
