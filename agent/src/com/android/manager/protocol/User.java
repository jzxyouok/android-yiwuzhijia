package com.android.manager.protocol;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class User  implements Serializable{
	
	private int user_id;
	
	private String username=null;
	
	private String password;

	private  String phone;
	
	private int  city;
	
	private int sex;
	
	private int age;
	
	public  int agent_id;
	
	public int stype;
	
	public double total_money;
	public double account_balance;
	
	private int id;
	
	private double arrived_money;
	
	private String name;
	
	private String city_name;
	
	private String account;
	
	private String pic;

	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getUser_id() {
		return user_id;
	}
	
	
	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public void  fromJson(JSONObject jsonObject)  throws JSONException
	{
		if(jsonObject==null)
		{
			Log.d("mao","json对象为空");
			return ;
		}
		this.user_id=jsonObject.optInt("id");
		this.username=jsonObject.optString("name");
		this.password=jsonObject.optString("password");
		this.phone=jsonObject.optString("phone");
		this.city=jsonObject.optInt("city_id");
		this.sex=jsonObject.optInt("sex");
		this.agent_id=jsonObject.optInt("agent_id",0);
		this.stype=jsonObject.optInt("stype");
		this.account_balance=jsonObject.optDouble("account_balance",0);
		this.total_money=jsonObject.optDouble("total_money",0);
		this.id=jsonObject.optInt("id");
		this.arrived_money=jsonObject.optDouble("arrived_money",0);
		this.name=jsonObject.optString("nick_name","");
		this.city_name=jsonObject.optString("city_name");
		this.account=jsonObject.optString("account");
		this.pic=jsonObject.optString("pic");
		
	}
	
	public JSONObject toJSON() throws JSONException
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("id", this.user_id);
		jsonObject.put("name", this.username);
		jsonObject.put("passsword", this.password);
		jsonObject.put("phone", this.phone);
		jsonObject.put("city_id", this.city);
		jsonObject.put("sex", this.sex);
		jsonObject.put("agent_id", this.agent_id);
		jsonObject.put("account_balance", this.account_balance);
		jsonObject.put("total_money", this.total_money);
		return jsonObject;
	}

	public int  getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}

	public int getStype() {
		return stype;
	}

	public void setStype(int stype) {
		this.stype = stype;
	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getArrived_money() {
		return arrived_money;
	}

	public void setArrived_money(double arrived_money) {
		this.arrived_money = arrived_money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
