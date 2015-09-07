package com.android.house.protocol;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
/**
 * 我的钱包相关
 * @author shinelw
 *
 */
public class UserAccount {
	
	public int id;
	public int user_id;
	public String operation_detail;
	public String operation_time;
	public int is_delete;
	public int stype;
	public int status;
	public double money; 
	
	public  String status_info;
	
	
	
	public void fromJson(JSONObject jsonObject) throws JSONException{
		this.id=jsonObject.optInt("id");
		this.user_id=jsonObject.optInt("user_id");
		this.operation_detail=jsonObject.optString("operation_detail");
		this.operation_time=jsonObject.optString("operation_time");
		this.status_info=jsonObject.optString("status_info");
		this.is_delete=jsonObject.optInt("is_delete");
		this.stype=jsonObject.optInt("stype");
		this.status=jsonObject.optInt("status");
		this.money=jsonObject.optDouble("money");
		
	}
	
}
