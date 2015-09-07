package com.android.manager.protocol;

import org.json.JSONObject;

import android.R.string;

public class Record {
	
	private int serveRecordId;
	private String content;
	
	private int businessStatus;
	/*
	 *  1：有意向
		2：无意向
		3：购房成功

	 */
	private int houseId;
	private String houseName;
	
	
	
	private int id;
	private int house_id;
	private String house_name;
	private String house_address;
	private int user_id;
	private int agent_id;
	private int sub_agent_id;
	private int business_status;
	private String create_time;
	private int  agreement_id;
	
	private String buyer;
	private String money;
	private String relation;
	private String payWay;
	private String POS;
	private String agreementNum;
	private String saleMan;
	private String successTime ;
	private String houseSourceNum;
	
	
	private String areaName;
	private String address;
	private String status;
	private String pic;
	

	
	
	
	
	
	
	public void fromJson(JSONObject jsonObject){
		
		content=jsonObject.optString("content","");
		house_name=jsonObject.optString("house_name","");
		house_address=jsonObject.optString("house_address","");
		create_time=jsonObject.optString("create_time");
		areaName=jsonObject.optString("areaName");
		address=jsonObject.optString("address");
		status=jsonObject.optString("status");
		pic=jsonObject.optString("pic");
		houseName=jsonObject.optString("houseName");
		
		
		
		
		
	}
	
	
	
	
	
	
	public String getPic() {
		return pic;
	}






	public void setPic(String pic) {
		this.pic = pic;
	}






	public String getAreaName() {
		return areaName;
	}





	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}





	public String getAddress() {
		return address;
	}





	public void setAddress(String address) {
		this.address = address;
	}





	public String getStatus() {
		return status;
	}





	public void setStatus(String status) {
		this.status = status;
	}





	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
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




	public int getSub_agent_id() {
		return sub_agent_id;
	}




	public void setSub_agent_id(int sub_agent_id) {
		this.sub_agent_id = sub_agent_id;
	}




	public int getBusiness_status() {
		return business_status;
	}




	public void setBusiness_status(int business_status) {
		this.business_status = business_status;
	}




	public String getCreate_time() {
		return create_time;
	}




	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}




	public int getAgreement_id() {
		return agreement_id;
	}




	public void setAgreement_id(int agreement_id) {
		this.agreement_id = agreement_id;
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




	public String getRelation() {
		return relation;
	}




	public void setRelation(String relation) {
		this.relation = relation;
	}




	public String getPayWay() {
		return payWay;
	}




	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}




	public String getPOS() {
		return POS;
	}




	public void setPOS(String pOS) {
		POS = pOS;
	}




	public String getAgreementNum() {
		return agreementNum;
	}




	public void setAgreementNum(String agreementNum) {
		this.agreementNum = agreementNum;
	}




	public String getSaleMan() {
		return saleMan;
	}




	public void setSaleMan(String saleMan) {
		this.saleMan = saleMan;
	}




	public String getSuccessTime() {
		return successTime;
	}




	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}




	public String getHouseSourceNum() {
		return houseSourceNum;
	}




	public void setHouseSourceNum(String houseSourceNum) {
		this.houseSourceNum = houseSourceNum;
	}




	public int getServeRecordId() {
		return serveRecordId;
	}
	public void setServeRecordId(int serveRecordId) {
		this.serveRecordId = serveRecordId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getBusinessStatus() {
		return businessStatus;
	}
	public void setBusinessStatus(int businessStatus) {
		this.businessStatus = businessStatus;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	
	
	
	
}
