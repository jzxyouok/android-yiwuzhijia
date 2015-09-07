package com.android.house.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.CacheInfo;
import com.external.activeandroid.util.Log;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

import android.content.Context;

public class GetMyAgentModel extends BaseModel {
	public GetMyAgentModel(Context context) {
		super(context);
		this.myContext=context;
	}

	
	private String path="m/user/getViewedHouseList";
	private Context myContext;
	
	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					Log.d("mao",object.toString());
					try {
						GetMyAgentModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
						
						e.printStackTrace();
					}
				}
			};
	

	
	
	public void getMyAgentList(CacheInfo info)
	{
		Map<String,String> params=new HashMap<String,String>();
		
		params.put("userId", info.getUid()+"");
		params.put("PHPSESSID", info.getSessionId());
		
		this.bcb.url(this.path).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog=new MyProgressDialog(myContext, "浏览历史加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
		
	}
}
