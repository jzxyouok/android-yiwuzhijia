package com.android.house.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.CacheInfo;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

import android.content.Context;
import android.util.Log;

public class LogoutModel extends BaseModel{
	
	
	public   String path="m/base/logout";
	private Context myContext;

	public LogoutModel(Context context) {
		super(context);
		this.myContext=context;
	}
	
	
	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					
					try {
						LogoutModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			};
	
	public void logout(CacheInfo cacheInfo)
	{
		Log.d("mao", cacheInfo.getPhone());
		Map<String,String >params=new HashMap<String, String>();
		params.put("phone", cacheInfo.getPhone());
		params.put("PHPSESSID", cacheInfo.getSessionId());
		this.bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		this.bcb.url(path).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		
		MyProgressDialog dialog=new MyProgressDialog(this.myContext, "退出中...");
		this.aq.progress(dialog.mDialog).ajax(this.bcb);
	}
}
