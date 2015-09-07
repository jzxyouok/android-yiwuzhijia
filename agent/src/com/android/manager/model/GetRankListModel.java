package com.android.manager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.manager.protocol.Agent;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.protocol.User;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class GetRankListModel extends BaseModel{

	private Context mContext;
	public ArrayList<Agent> userList=new ArrayList<Agent>();
	public GetRankListModel(Context context) {
		super(context);
		mContext=context;
	}
	
	public void getRankList(int stype){
		
		String url=ProtocolConst.GET_RANK_LIST;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				GetRankListModel.this.callback(url, jo, status);
				if (jo.optString("status").equals("200")) {

					try {
						JSONArray rankArray = jo.optJSONArray("entity");
						userList.clear();
						if (rankArray != null && rankArray.length() > 0) {

							for (int i = 0; i < rankArray.length(); i++) {

								JSONObject itemJsonObject = rankArray
										.getJSONObject(i);
								Agent agent = new Agent();
								agent.fromJson(itemJsonObject);
								userList.add(agent);

							}
						}
						GetRankListModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};

		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stype", stype);
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		this.aq.ajax(bcb);

	}
	

}
