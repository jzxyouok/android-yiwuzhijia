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
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class GetCompanyInfoModel extends BaseModel {

	public String annocement;
	public GetCompanyInfoModel(Context context) {
		super(context);
		
	}
	
	/**
	 * 获取公告
	 */
	public void getAnnocement(){
		
		String url = ProtocolConst.GET_MASTER_VALUE;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject object,
					AjaxStatus status) {
				
				Log.d("mao", object.toString());
				if (object.optString("status").equals("200")) {
					try {
						annocement=object.optString("msg");
						GetCompanyInfoModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("commonKey", "SYSTEM_ANNOCEMENT");
		CacheInfo cacheInfo=UserInfoCacheUtil.getCacheInfo(mContext);
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);

		this.aq.ajax(bcb);
		
		
	}

}
