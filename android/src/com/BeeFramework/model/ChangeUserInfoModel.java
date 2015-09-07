package com.BeeFramework.model;

import org.json.JSONObject;

import com.android.house.protocol.User;
import com.external.androidquery.callback.AjaxStatus;

import android.content.Context;

public class ChangeUserInfoModel extends BaseModel{

	private String path="/m/base";
	private Context myContext;
	
	BeeCallback<JSONObject> bcb_ChangeUserInfo=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
				}
				
			};
	public ChangeUserInfoModel(Context context) {
		super(context);
	}
	
	
	public void updateUserInfo(User user)
	{
		
	}
}
