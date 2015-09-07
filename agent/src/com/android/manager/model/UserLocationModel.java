package com.android.manager.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.protocol.UserLocation;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class UserLocationModel extends BaseModel{
	private Context mContext;

	private UserLocation location = new UserLocation();
	
	public static final String URL = ProtocolConst.UPDATE_USERLBS;
	
	public UserLocationModel(Context context) {
		super(context);
		mContext = context;
	}

	public void postUserInfo(){
		BeeCallback<JSONObject>  bcb = new BeeCallback<JSONObject>(){
			@Override
			public void callback(String url, JSONObject object, AjaxStatus status) {
				try {
					UserLocationModel.this.OnMessageResponse(url, object, status);
				} catch (JSONException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		};
		
		Map<String, String> params = new HashMap<String, String>();
		CacheInfo cacheInfo=UserInfoCacheUtil.getCacheInfo(mContext);
		params.put("lat", location.getLatitude() + "");
		params.put("lng", location.getLongtitude() + "");
//		params.put("PHPSESSION", location.getSessionId());
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(URL).params(params).type(JSONObject.class).method(Constants.METHOD_POST).type(JSONObject.class);
		this.aq.ajax(bcb);
	}
	
	public void setUserLocationInfo(double latitude,double longtitude){
		location.setLatitude(latitude);
		location.setLongtitude(longtitude);
		
	}
}
