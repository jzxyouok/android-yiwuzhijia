package com.android.manager.protocol;

import org.json.JSONObject;

public class Score {

	private int id;
	private int car;
	private int suggest;
	private int dress;
	private int avg_score;
	private int user_id;
	private int agent_id;
	private int stype;
	private String create_time;
	private String content;
	
	public void fromJson(JSONObject jsonObject){
		
		id=jsonObject.optInt("id",0);
		car=jsonObject.optInt("car");
		suggest=jsonObject.optInt("suggest");
		dress=jsonObject.optInt("dress");
		avg_score=jsonObject.optInt("avg_score");
		user_id=jsonObject.optInt("user_id");
		agent_id=jsonObject.optInt("agent_id");
		stype=jsonObject.optInt("stype");
		create_time=jsonObject.optString("create_time");
		content=jsonObject.optString("content","");
		
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCar() {
		return car;
	}

	public void setCar(int car) {
		this.car = car;
	}

	public int getSuggest() {
		return suggest;
	}

	public void setSuggest(int suggest) {
		this.suggest = suggest;
	}

	public int getDress() {
		return dress;
	}

	public void setDress(int dress) {
		this.dress = dress;
	}

	public int getAvg_score() {
		return avg_score;
	}

	public void setAvg_score(int avg_score) {
		this.avg_score = avg_score;
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

	public int getStype() {
		return stype;
	}

	public void setStype(int stype) {
		this.stype = stype;
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
	
}
