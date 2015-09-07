package com.android.house.businessresponse;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.BeeFramework.model.BusinessResponse;
import com.android.house.costants.AppConstants;

public abstract class AbstractBusinessResponse implements BusinessResponse{
	
	
	protected Context context;
	protected Handler handler;
	
	public  AbstractBusinessResponse(Context context,Handler handler)
	{
		this.context=context;
		this.handler=handler;
	}
	
	void handleMessage(int replyStatus)
	{
		//这里是公共的处理方法
		Message message=new Message();
		switch (replyStatus) {
		case AppConstants.JSON_NETWORK_ERROR:
			
			{
				message.what=AppConstants.JSON_NETWORK_ERROR;
				this.handler.sendMessage(message);
			}
		case AppConstants.JSON_REPLY_OK:
			{
				message.what=AppConstants.JSON_REPLY_OK;
				this.handler.sendMessage(message);
			}
			break;
		case AppConstants.JSON_REPLY_ERROR:
			{
				message.what=AppConstants.JSON_REPLY_ERROR;
				this.handler.sendMessage(message);
			}
		default:
			break;
		}
	}
	
}
