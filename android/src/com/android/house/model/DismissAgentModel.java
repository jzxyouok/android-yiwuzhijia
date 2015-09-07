package com.android.house.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.activeandroid.util.Log;
import com.external.androidquery.callback.AjaxStatus;

public class DismissAgentModel  extends BaseModel{
	/*
	 * 解约经纪人 ?添加评论？
	 */
	
	private Context myContext;
	private String path="m/user/addCommentOfAgent";
	
	

	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					
					
					
				}
			};
	public DismissAgentModel(Context context) {
		super(context);
	}
	
	
	public void dismissAgent(int agentId,int userId,String sessionid)
	{
		
	}
}
