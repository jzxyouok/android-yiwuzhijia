package com.android.manager.protocol;

import org.json.JSONObject;

public class ClientRecord {
	String content;
	int id;
	int serve_record_id;
	int business_state;
	int user_id;
	int agent_id;
	int house_id;
	String house_name;
	String house_address;
	private String create_time;
	
	
	public void fromJSON(JSONObject	jo)
	{
		this.content=jo.optString("content");
		this.id=jo.optInt("id");
		this.serve_record_id=jo.optInt("serve_record_id");
		this.business_state=jo.optInt("business_status");
		this.user_id=jo.optInt("user_id");
		this.agent_id=jo.optInt("agent_id");
		this.house_id=jo.optInt("house_id");
		this.house_name=jo.optString("house_name");
		this.house_address=jo.optString("house_address");
		this.create_time=jo.optString("create_time");
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getServe_record_id() {
		return serve_record_id;
	}


	public void setServe_record_id(int serve_record_id) {
		this.serve_record_id = serve_record_id;
	}


	public int getBusiness_state() {
		return business_state;
	}


	public void setBusiness_state(int business_state) {
		this.business_state = business_state;
	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public int getAgent_id() {
		return agent_id;
	}


	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}


	public int getHouse_id() {
		return house_id;
	}


	public void setHouse_id(int house_id) {
		this.house_id = house_id;
	}


	public String getHouse_name() {
		return house_name;
	}


	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}


	public String getHouse_address() {
		return house_address;
	}


	public void setHouse_address(String house_address) {
		this.house_address = house_address;
	}
	
}
