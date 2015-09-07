package com.android.house.protocol;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.baidu.platform.comapi.map.s;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;
@Table(name="USER")
public class User extends Model implements Serializable{
	
	@Column(name="USER_ID",unique=true)
	private int user_id;
	
	@Column(name="USER_NAME")
	private String username=null;
	
	@Column(name="USER_PWD")
	private String password;

	@Column(name="USER_TEL")
	private  String phone;
	
	@Column(name="USER_CITY")
	private int  city;
	
	@Column(name="USER_SEX")
	private int sex;
	
	public  int agent_id;
	
	public double total_money;
	
	private String account;
	
	private String pic;
	
	private String city_name;

	
	public int getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getAccount(){
		return account;
	}
	
	public void setAccount(String account){
		this.account = account;
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

	public double account_balance;

	
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

	

	public int getSex() {
		return sex;
	}

	public String getPic() {
		return pic;
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
	
	
	public void  fromJson(JSONObject jsonObject)  throws JSONException
	{
		if(jsonObject==null)
		{
			Log.d("mao","json对象为空");
			return ;
		}
		this.account=jsonObject.optString("account");
		this.user_id=jsonObject.optInt("id");
		this.username=jsonObject.optString("nick_name");
		this.password=jsonObject.optString("password");
		this.city_name=jsonObject.optString("city_name");
		this.phone=jsonObject.optString("phone");
		this.city=jsonObject.optInt("city_id");
		this.sex=jsonObject.optInt("sex");
		this.agent_id=jsonObject.optInt("agent_id",0);
		this.pic=jsonObject.optString("pic");
		this.account_balance=jsonObject.optDouble("account_balance",0);
		this.total_money=jsonObject.optDouble("total_money",0);
	}
	
	public JSONObject toJSON() throws JSONException
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("id", this.user_id);
		jsonObject.put("nick_name", this.username);
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
	
}
