package com.android.house.model;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.User;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

/*
 * 查看个人信息
 */
public class GetUserInfoModel extends BaseModel {

	private Context myContext;
	private String path="m/base/getUserInfo";
	public  User user;
	
	
	public GetUserInfoModel(Context context) {
		super(context);
	}
	
	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					
					Log.d("mao",object.toString());
					if (object.optString("status").equals("200")) {
						user=new User();
						try {
							user.fromJson(object.optJSONObject("entity").optJSONObject("user"));
						} catch (JSONException e) {
							e.printStackTrace();
							Log.d("mao","获取用户个人信息时，json解析出错");
						}
						try {
							GetUserInfoModel.this.OnMessageResponse(url, object, status);
						} catch (JSONException e) {
							
							e.printStackTrace();
						}
					}
					
				}
				
			};
	
	public void getUserinfo(int uid,String sessionid)
	{
		Map<String ,String> params=new HashMap<String, String>();
		params.put("userId", uid+"");
		params.put("PHPSESSID", sessionid);
		bcb.cookie("PHPSESSID", sessionid);
		this.bcb.url(this.path).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		this.aq.ajax(this.bcb);
	}
}
