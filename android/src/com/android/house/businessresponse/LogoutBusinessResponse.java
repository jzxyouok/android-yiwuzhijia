package com.android.house.businessresponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.house.costants.AppConstants;
import com.android.house.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class LogoutBusinessResponse  extends AbstractBusinessResponse{

	
	public LogoutBusinessResponse(Context context, Handler handler) {
		super(context, handler);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		
		Log.d("mao", jo.toString());
		
		if(jo!=null)
		{
			int replyCode=jo.optInt("status");
			if(replyCode==AppConstants.JSON_REPLY_OK)
			{
				UserInfoCacheUtil.clearCahce(this.context);
				this.handleMessage(replyCode);
			}else
			{
				this.handleMessage(replyCode);
			}
		}
		
	}

	@Override
	void handleMessage(int replyStatus) {
		
		Message message=new Message();
		switch (replyStatus) {
		case AppConstants.JSON_NETWORK_ERROR:
			
			{
				message.what=AppConstants.LOGOUT_ERR;
				this.handler.sendMessage(message);
			}
		case AppConstants.JSON_REPLY_OK:
			{
				message.what=AppConstants.LOGOUT_OK;
				this.handler.sendMessage(message);
			}
			break;
		case AppConstants.JSON_REPLY_ERROR:
			{
				message.what=AppConstants.LOGOUT_ERR;
				this.handler.sendMessage(message);
			}
		default:
			break;
		}
	}
	
	

}
