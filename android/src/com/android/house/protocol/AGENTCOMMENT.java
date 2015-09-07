package com.android.house.protocol;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.R.string;

import com.external.activeandroid.Model;

/**
 * 经纪人评论
 * @author shinelw
 *
 */
public class AGENTCOMMENT {
	
	private int id;
	private int user_id;
	private int agent_id;
	private String create_time;
	private int is_active;
	private int relation_status;
	private int totol_score;
	private String score_time;
	private int is_scored;
	private String content;
	
	private String user_phone;
	
	private int avg_score;
	
	
	public void fromJson(JSONObject jsonObject) throws JSONException{
		this.id=jsonObject.optInt("id");
		this.user_id=jsonObject.optInt("agent_id");
		this.agent_id=jsonObject.optInt("agent_id");
		this.create_time=jsonObject.optString("create_time");
		this.is_active=jsonObject.optInt("is_active");
		this.relation_status=jsonObject.optInt("relation_status");
		this.totol_score=jsonObject.optInt("totol_score");
		this.is_scored=jsonObject.optInt("is_scored");
		this.score_time=jsonObject.optString("score_time");
		this.content=jsonObject.optString("content");
		this.user_phone=jsonObject.optString("user_phone");
		this.avg_score=jsonObject.optInt("avg_score",0);
		
		
	}
	


	public int getAvg_score() {
		return avg_score;
	}



	public void setAvg_score(int avg_score) {
		this.avg_score = avg_score;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getUser_phone() {
		return user_phone;
	}



	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}



	public int getID() {
		return id;
	}


	public void setID(int id) {
		this.id = id;
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


	public int getTotol_score() {
		return totol_score;
	}


	public void setTotol_score(int totol_score) {
		this.totol_score = totol_score;
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


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	

}
