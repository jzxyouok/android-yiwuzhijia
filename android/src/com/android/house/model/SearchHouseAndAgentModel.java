package com.android.house.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.Agent;
import com.android.house.protocol.CacheInfo;
import com.android.house.protocol.House;
import com.android.house.protocol.ProtocolConst;
import com.android.house.protocol.User;
import com.android.house.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class SearchHouseAndAgentModel extends BaseModel {

	public ArrayList<House> houseList=new ArrayList<House>();
	public ArrayList<Agent> userList=new ArrayList<Agent>();
	public SearchHouseAndAgentModel(Context context) {
		super(context);
		
	}
	
	public void getSearchList(String keyWords){
		String url=ProtocolConst.SEARCH_HOUSE_AGENT;
		BeeCallback<JSONObject> beeCallback=new BeeCallback<JSONObject>(){
			
			@Override
			public void callback(String url, JSONObject jo,
					AjaxStatus status) {
				//SearchHouseAndAgentModel.this.callback(url, jo, status);
				
				if (jo.optString("status").equals("200")) {

					try {
						JSONArray houseArray = jo.optJSONArray("house");
						houseList.clear();
						if (houseArray != null && houseArray.length() > 0) {
							for (int i = 0; i < houseArray.length(); i++) {

								JSONObject itemJsonObject = houseArray
										.getJSONObject(i);
								House house=new House();
								house.fromJson(itemJsonObject);
								houseList.add(house);
							}
						}else {
							//Toast.makeText(mContext, "没有搜到相关房源哦", Toast.LENGTH_LONG).show();
						}
						JSONArray userArray = jo.optJSONArray("agent");
						userList.clear();
						if (userArray != null && userArray.length() > 0) {
							for (int i = 0; i < userArray.length(); i++) {

								JSONObject itemJsonObject2 = userArray
										.getJSONObject(i);
								Agent agent=new Agent();
								agent.fromJson(itemJsonObject2);
								userList.add(agent);
							}
						}else {
							//Toast.makeText(mContext, "没有搜到相关经纪人哦", Toast.LENGTH_LONG).show();
						}
						SearchHouseAndAgentModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};

		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, String> params = new HashMap<String, String>();
		params.put("keyWords", keyWords);
		beeCallback.cookie("PHPSESSID", cacheInfo.getSessionId());
		beeCallback.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(beeCallback);

				
			}

	}



