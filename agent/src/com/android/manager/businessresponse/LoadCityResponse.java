package com.android.manager.businessresponse;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.manager.costants.AppConstants;
import com.external.androidquery.callback.AjaxStatus;

public class LoadCityResponse extends AbstractBusinessResponse {

	public LoadCityResponse(Context context, Handler handler) {
		super(context, handler);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		Log.d("mao","获取城市列表  回调信息："+jo.toString());
		if(jo!=null)
		{
			int replyCode=jo.optInt("status");
			if(replyCode==AppConstants.JSON_REPLY_OK)
			{
				replyCode=AppConstants.LOAD_CITYLIST_OK;
			}else
			{
				replyCode=AppConstants.LOAD_CITYLIST_ERR;
			}
			this.handleMessage(replyCode);
		}
	}

	@Override
	void handleMessage(int replyStatus) {
		
		
		Message message=new Message();
		message.what=replyStatus;
		this.handler.sendMessage(message);
	}
	
	
	
}
