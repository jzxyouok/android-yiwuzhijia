package com.android.manager.businessresponse;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.external.androidquery.callback.AjaxStatus;

public class ResetBusinessResponse  extends AbstractBusinessResponse{

	public ResetBusinessResponse(Context context, Handler handler) {
		super(context, handler);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		
		Log.d("mao",jo.toString());
		if(jo!=null)
		{
			int statusCode=jo.optInt("status");
			this.handleMessage(statusCode);
		}
		
		
	}

	
	@Override
	void handleMessage(int statusCode) {
		super.handleMessage(statusCode);
	}

	

	
}
