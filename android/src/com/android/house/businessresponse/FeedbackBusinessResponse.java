package com.android.house.businessresponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.external.androidquery.callback.AjaxStatus;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class FeedbackBusinessResponse  extends AbstractBusinessResponse{

	
	
	public FeedbackBusinessResponse(Context context, Handler handler) {
		super(context, handler);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		Log.d("mao", jo.toString());
		if(jo!=null)
		{
			int replyCode =jo.optInt("status");
			
			handleMessage(replyCode);
				
		}
	}

}
