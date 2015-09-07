package com.android.house.protocol;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * 购房合同信息
 * @author huangchengda
 *
 */
public class AgreementInfo {
	private int id;
	private String buyer;//买家姓名
	private String money;//房款
	private String buyer_relation;//买家关系
	private String house_name;//房源名
	private String pay_way;//付款方式
	private String success_time;//成交时间
	private String posId;//POS单号
	private String agreement_num;//合同号
	private String sale_man;//销售员
	private String house_source_num;//房源号
	private int business_record_id;
	private int agent_id;
	private int user_id;
	private int house_id;
	private int sub_agnet_id;
	
	
	public void fromJson(JSONObject jsonObject)  throws JSONException
	{
		if(jsonObject==null)
		{
			Log.d("mao","json对象为空");
			return ;
		}
		this.id=jsonObject.optInt("id");
		this.buyer=jsonObject.optString("buyer");
		this.money=jsonObject.optString("money");
		this.buyer_relation=jsonObject.optString("buyer_relation");
		this.house_name=jsonObject.optString("house_name");
		this.pay_way=jsonObject.optString("pay_way");
		this.success_time=jsonObject.optString("success_time");
		this.posId=jsonObject.optString("p_o_s_id");
		this.agreement_num=jsonObject.optString("agreement_num");
		this.sale_man=jsonObject.optString("sale_man");
		this.house_source_num=jsonObject.optString("house_source_num");
		this.business_record_id=jsonObject.optInt("business_record_id");
		this.agent_id=jsonObject.optInt("agent_id");
		this.user_id=jsonObject.optInt("user_id");
		this.house_id=jsonObject.optInt("house_id");
		this.sub_agnet_id=jsonObject.optInt("sub_agnet_id");
	}
	
	public JSONObject toJSON() throws JSONException
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("id", this.id);
		jsonObject.put("buyer", this.buyer);
		jsonObject.put("money", this.money);
		jsonObject.put("buyer_relation", this.buyer_relation);
		jsonObject.put("house_name", this.house_name);
		jsonObject.put("pay_way", this.pay_way);
		jsonObject.put("success_time", this.success_time);
		jsonObject.put("p_o_s_id", this.posId);
		jsonObject.put("agreement_num", this.agreement_num);
		jsonObject.put("sale_man", this.sale_man);
		jsonObject.put("house_source_num", this.house_source_num);
		jsonObject.put("business_record_id", this.business_record_id);
		jsonObject.put("agent_id", this.agent_id);
		jsonObject.put("user_id", this.user_id);
		jsonObject.put("house_id", this.house_id);
		jsonObject.put("sub_agnet_id", this.sub_agnet_id);
		return jsonObject;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getBuyer_relation() {
		return buyer_relation;
	}
	public void setBuyer_relation(String buyer_relation) {
		this.buyer_relation = buyer_relation;
	}
	public String getHouse_name() {
		return house_name;
	}
	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}
	public String getPay_way() {
		return pay_way;
	}
	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}
	public String getSuccess_time() {
		return success_time;
	}
	public void setSuccess_time(String success_time) {
		this.success_time = success_time;
	}
	public String getPosId() {
		return posId;
	}
	public void setPosId(String posId) {
		this.posId = posId;
	}
	public String getAgreement_num() {
		return agreement_num;
	}
	public void setAgreement_num(String agreement_num) {
		this.agreement_num = agreement_num;
	}
	public String getSale_man() {
		return sale_man;
	}
	public void setSale_man(String sale_man) {
		this.sale_man = sale_man;
	}
	public String getHouse_source_num() {
		return house_source_num;
	}
	public void setHouse_source_num(String house_source_num) {
		this.house_source_num = house_source_num;
	}
	public int getBusiness_record_id() {
		return business_record_id;
	}
	public void setBusiness_record_id(int business_record_id) {
		this.business_record_id = business_record_id;
	}
	public int getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getHouse_id() {
		return house_id;
	}
	public void setHouse_id(int house_id) {
		this.house_id = house_id;
	}
	public int getSub_agnet_id() {
		return sub_agnet_id;
	}
	public void setSub_agnet_id(int sub_agnet_id) {
		this.sub_agnet_id = sub_agnet_id;
	}
	
	
}
