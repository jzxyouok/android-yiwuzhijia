package com.android.manager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.House;
import com.android.manager.protocol.HouseManage;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class HouseManageModel extends BaseModel {

	public ArrayList<HouseManage> houseList = new ArrayList<HouseManage>();
	public List<House> houseList2 = new ArrayList<House>();
	public Context mContext;

	public HouseManageModel(Context context) {
		super(context);
		mContext = context;
	}

	public void getHouseList(int cityId) {

		String url = ProtocolConst.GET_AGENTLIST_BYGPS;

		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				HouseManageModel.this.callback(url, jo, status);
				if (jo.optString("status").equals("200")) {

					try {
						JSONArray accountArray = jo.optJSONArray("entities");
						houseList.clear();
						houseList2.clear();
						if (accountArray != null && accountArray.length() > 0) {

							for (int i = 0; i < accountArray.length(); i++) {

								JSONObject itemJsonObject = accountArray.getJSONObject(i);
								HouseManage house = new HouseManage();
								House house2 = new House();
								house.fromJson(itemJsonObject);
								houseList.add(house);
								house2.fromJson(itemJsonObject);
								houseList2.add(house2);

							}
						}
						HouseManageModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};

		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityId", cityId);
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}

}
