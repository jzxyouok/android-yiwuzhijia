package com.android.house.protocol;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

import com.external.activeandroid.Model;

public class Agent   implements Serializable{
	private int agent_id;
	
	private int id;
	
	private String agent_name;
	
	private String agent_phone;

	private String agent_score;
	
	private int sex;
	
	private double lat;
	
	private double lng;
	
	private String pic;
	
	private Bitmap bitmap;
	
	public Bitmap getBitmap() {
		return bitmap;
	}



	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}



	public int getAgent_id() {
		return agent_id;
	}



	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}



	public String getPic() {
		return pic;
	}





	public void setPic(String pic) {
		this.pic = pic;
	}


	private String arrived_payment;
	private String can_draw_money;
	private String car_type;
	private  String sale_long;
	private String sale_num;
	private String native_place;
	private double  judge_car_sorce;
	private int  judge_car_times;
	private double judge_suggest_score;
	private int judge_suggest_times;
	private double judge_dress_score;
	private int judge_dress_time;
	private float ave_score;
	private int user_id;
	public void fromJson(JSONObject jsonObject) throws JSONException
	{
		this.agent_id=jsonObject.optInt("agent_id");
		this.agent_name=jsonObject.optString("nick_name");
		this.agent_phone=jsonObject.optString("phone");
		this.sex=jsonObject.optInt("sex");
		this.lng=jsonObject.optDouble("lng");
		this.lat=jsonObject.optDouble("lat");
		this.pic=jsonObject.optString("user_pic_path");
		this.arrived_payment=jsonObject.optString("arrived_payment");
		this.can_draw_money=jsonObject.optString("can_draw_money");
		this.car_type=jsonObject.optString("car_type");
		this.sale_long=jsonObject.optString("sale_long");
		this.sale_num=jsonObject.optString("sale_num");
		this.native_place=jsonObject.optString("native_place");
		this.judge_car_sorce=jsonObject.optDouble("judge_car_score");
		this.judge_suggest_score=jsonObject.optDouble("judge_suggest_score");
		this.judge_dress_score=jsonObject.optDouble("judge_dress_score");
		this.judge_car_times=jsonObject.optInt("judge_car_times");
		this.judge_suggest_times=jsonObject.optInt("judge_suggest_times");
		this.judge_dress_time=jsonObject.optInt("judge_dress_time");
		this.ave_score=jsonObject.optInt("ave_score");
		this.id=jsonObject.optInt("id");
		this.user_id=jsonObject.optInt("user_id");
	}



	public int getUser_id() {
		return user_id;
	}



	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public float getAve_score() {
		return ave_score;
	}



	public void setAve_score(float ave_score) {
		this.ave_score = ave_score;
	}



	public String getArrived_payment() {
		return arrived_payment;
	}


	public void setArrived_payment(String arrived_payment) {
		this.arrived_payment = arrived_payment;
	}


	public String getCan_draw_money() {
		return can_draw_money;
	}


	public void setCan_draw_money(String can_draw_money) {
		this.can_draw_money = can_draw_money;
	}


	public String getCar_type() {
		return car_type;
	}


	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}


	public String getSale_long() {
		return sale_long;
	}


	public void setSale_long(String sale_long) {
		this.sale_long = sale_long;
	}


	public String getSale_num() {
		return sale_num;
	}


	public void setSale_num(String sale_num) {
		this.sale_num = sale_num;
	}


	public String getNative_place() {
		return native_place;
	}


	public void setNative_place(String native_place) {
		this.native_place = native_place;
	}


	public double getJudge_car_sorce() {
		return judge_car_sorce;
	}


	public void setJudge_car_sorce(double judge_car_sorce) {
		this.judge_car_sorce = judge_car_sorce;
	}


	public int getJudge_car_times() {
		return judge_car_times;
	}


	public void setJudge_car_times(int judge_car_times) {
		this.judge_car_times = judge_car_times;
	}


	public double getJudge_suggest_score() {
		return judge_suggest_score;
	}


	public void setJudge_suggest_score(double judge_suggest_score) {
		this.judge_suggest_score = judge_suggest_score;
	}


	public int getJudge_suggest_times() {
		return judge_suggest_times;
	}


	public void setJudge_suggest_times(int judge_suggest_times) {
		this.judge_suggest_times = judge_suggest_times;
	}


	public double getJudge_dress_score() {
		return judge_dress_score;
	}


	public void setJudge_dress_score(double judge_dress_score) {
		this.judge_dress_score = judge_dress_score;
	}


	public int getJudge_dress_time() {
		return judge_dress_time;
	}


	public void setJudge_dress_time(int judge_dress_time) {
		this.judge_dress_time = judge_dress_time;
	}


	public int getAgentId() {
		return agent_id;
	}


	public void setAgentId(int id) {
		this.agent_id = id;
	}


	public String getAgent_name() {
		return agent_name;
	}


	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}


	public String getAgent_phone() {
		return agent_phone;
	}


	public void setAgent_phone(String agent_phone) {
		this.agent_phone = agent_phone;
	}


	public String getAgent_score() {
		return agent_score;
	}


	public void setAgent_score(String agent_score) {
		this.agent_score = agent_score;
	}


	public int getSex() {
		return sex;
	}


	public void setSex(int sex) {
		this.sex = sex;
	}


	public double getLat() {
		return lat;
	}


	public void setLat(double lat) {
		this.lat = lat;
	}


	public double getLng() {
		return lng;
	}


	public void setLng(double lng) {
		this.lng = lng;
	}
	
	
	
	
}
