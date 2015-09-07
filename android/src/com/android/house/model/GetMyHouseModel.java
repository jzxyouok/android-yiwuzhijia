package com.android.house.model;

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
import com.android.house.protocol.CacheInfo;
import com.android.house.protocol.House;
import com.external.activeandroid.util.Log;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

/*
 * 查看  楼盘浏览历史
 */
public class GetMyHouseModel extends BaseModel {
	
	
	private String path = "m/user/getViewedHouseList";
	private Context myContext;
	public ArrayList<House> houseList=new ArrayList<House>();
	
	
	BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

		@Override
		public void callback(String url, JSONObject object, AjaxStatus status) {

			try {
				if (object.optString("status").equals("200")) {
					JSONArray houseArray=object.optJSONArray("entities");
					houseList.clear();
					if (null!=houseArray&&houseArray.length()>0) {
						for (int i = 0; i < houseArray.length(); i++) {
							JSONObject houseJsonObject=houseArray.getJSONObject(i);
							House house=new House();
							house.fromJson(houseJsonObject);
							houseList.add(house);
						}
					}
					GetMyHouseModel.this.OnMessageResponse(url, object, status);
				}
			} catch (JSONException e) {

				e.printStackTrace();
			}
		}
	};

	public GetMyHouseModel(Context context) {
		super(context);
		this.myContext = context;
	}

	public void getMyHouse(CacheInfo info)

	{

		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();

		params.put("userId", info.getUid() + "");
		params.put("PHPSESSID", info.getSessionId());

		bcb.url(this.path).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST).cookie("PHPSESSID", info.getSessionId());
		MyProgressDialog dialog = new MyProgressDialog(myContext, "浏览历史加载中...");
		aq.progress(dialog.mDialog).ajax(bcb);

	}

}
