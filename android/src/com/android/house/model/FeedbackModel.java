package com.android.house.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.CacheInfo;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class FeedbackModel extends BaseModel {

	Context myContext;
	public   String path="m/base/addSuggestion";
	
	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>()
			{
				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					
					
					try {
						FeedbackModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			};
			
			
			
	public FeedbackModel(Context context) {
		super(context);
		this.myContext=context;
	}
	
	
	public void feedBack(CacheInfo cacheInfo,String content)
	{
		Map<String, String > params=new HashMap<String, String>();
		params.put("phone", cacheInfo.getPhone());
		params.put("PHPSESSID", cacheInfo.getSessionId());
		params.put("content", content);
		params.put("stype", 1+"");
		this.bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		this.bcb.url(path).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog=new MyProgressDialog(this.myContext, "建议发送中");
		this.aq.progress(dialog.mDialog).ajax(this.bcb);
	}
}
