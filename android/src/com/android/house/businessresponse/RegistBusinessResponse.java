package com.android.house.businessresponse;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.house.costants.AppConstants;
import com.external.androidquery.callback.AjaxStatus;

public class RegistBusinessResponse extends AbstractBusinessResponse {

	
	public RegistBusinessResponse(Context context, Handler handler) {
		super(context, handler);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		Log.d("mao", jo.toString());
		if(jo!=null)
		{
			Message msg=new Message();
			int replyCode=jo.optInt("status");
			String PHPSESSID=jo.optString("PHPSESSID");
			if(replyCode==AppConstants.JSON_REPLY_OK)
			{
				msg.what=AppConstants.JSON_VALID_OK;
				Bundle bundle=new Bundle();
				bundle.putString("PHPSESSID", PHPSESSID);
				msg.setData(bundle);
				this.handler.sendMessage(msg);
			}else
			{
				msg.what=AppConstants.JSON_VALID_ERR;
				msg.obj=jo.opt("msg");
				this.handler.sendMessage(msg);
			}
		}
	}

	
		
}
