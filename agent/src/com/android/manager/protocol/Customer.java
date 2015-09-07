package com.android.manager.protocol;

import java.io.Serializable;

import org.json.JSONObject;

public class Customer implements Serializable {
	
	private int id;
	private int user_id;
	private String user_name;
	private String agent_name;
	private String user_head_pic;
	private String agent_head_pic;
	private String user_phone;
	private String agent_phone;	
	private int agent_id;
	private int sub_agent_id;
	private String create_time;
	private int is_active;
	private int relation_status;;
	private String score_time;
	private int is_scored;
	private String latest_success_time;
	private String latest_user_payback;
	private String latest_agent_payment;
	private String latest_sub_agent_payment;
	private String latest_success_priceString;
	
	private String house_area_prefer;
	private String house_price_prefer;
	
	public String getHouse_area_prefer() {
		return house_area_prefer;
	}


	public String getHouse_price_prefer() {
		return house_price_prefer;
	}


	private String content;
	
	
	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public void fromJSON(JSONObject jsonObject)
	{
		this.id=jsonObject.optInt("id");
		this.user_id=jsonObject.optInt("user_id");
		this.user_name=jsonObject.optString("user_name","未知");
		this.agent_name=jsonObject.optString("agent_name");
		this.user_head_pic=jsonObject.optString("user_head_pic");
		this.agent_head_pic=jsonObject.optString("agent_head_pic");
		this.user_phone=jsonObject.optString("user_phone","未知");
		this.agent_id=jsonObject.optInt("agent_id");
		this.sub_agent_id=jsonObject.optInt("sub_agent_id");
		this.agent_phone=jsonObject.optString("agent_phone");
		this.create_time=jsonObject.optString("create_time");
		this.is_active=jsonObject.optInt("is_active");
		this.relation_status=jsonObject.optInt("relation_status");
		this.is_scored=jsonObject.optInt("is_scored");
		this.score_time=jsonObject.optString("score_time");
		this.latest_success_time=jsonObject.optString("latest_success_time");
		this.latest_user_payback=jsonObject.optString("latest_user_payback");
		this.latest_agent_payment=jsonObject.optString("latest_agent_payment");
		this.latest_success_priceString=jsonObject.optString("latest_success_priceString");
		this.content=jsonObject.optString("content");
		this.house_area_prefer=jsonObject.optString("house_area_prefer","未知");
		this.house_price_prefer=jsonObject.optString("house_price_prefer","未知");
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getAgent_name() {
		return agent_name;
	}


	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}


	public String getUser_head_pic() {
		return user_head_pic;
	}


	public void setUser_head_pic(String user_head_pic) {
		this.user_head_pic = user_head_pic;
	}


	public String getAgent_head_pic() {
		return agent_head_pic;
	}


	public void setAgent_head_pic(String agent_head_pic) {
		this.agent_head_pic = agent_head_pic;
	}


	public String getUser_phone() {
		return user_phone;
	}


	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}


	public String getAgent_phone() {
		return agent_phone;
	}


	public void setAgent_phone(String agent_phone) {
		this.agent_phone = agent_phone;
	}


	public int getAgent_id() {
		return agent_id;
	}


	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}


	public int getSub_agent_id() {
		return sub_agent_id;
	}


	public void setSub_agent_id(int sub_agent_id) {
		this.sub_agent_id = sub_agent_id;
	}


	public String getCreate_time() {
		return create_time;
	}


	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}


	public int getIs_active() {
		return is_active;
	}


	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}


	public int getRelation_status() {
		return relation_status;
	}


	public void setRelation_status(int relation_status) {
		this.relation_status = relation_status;
	}


	public String getScore_time() {
		return score_time;
	}


	public void setScore_time(String score_time) {
		this.score_time = score_time;
	}


	public int getIs_scored() {
		return is_scored;
	}


	public void setIs_scored(int is_scored) {
		this.is_scored = is_scored;
	}


	public String getLatest_success_time() {
		return latest_success_time;
	}


	public void setLatest_success_time(String latest_success_time) {
		this.latest_success_time = latest_success_time;
	}


	public String getLatest_user_payback() {
		return latest_user_payback;
	}


	public void setLatest_user_payback(String latest_user_payback) {
		this.latest_user_payback = latest_user_payback;
	}


	public String getLatest_agent_payment() {
		return latest_agent_payment;
	}


	public void setLatest_agent_payment(String latest_agent_payment) {
		this.latest_agent_payment = latest_agent_payment;
	}


	public String getLatest_sub_agent_payment() {
		return latest_sub_agent_payment;
	}


	public void setLatest_sub_agent_payment(String latest_sub_agent_payment) {
		this.latest_sub_agent_payment = latest_sub_agent_payment;
	}


	public String getLatest_success_priceString() {
		return latest_success_priceString;
	}


	public void setLatest_success_priceString(String latest_success_priceString) {
		this.latest_success_priceString = latest_success_priceString;
	}
	
	
}
