package com.android.house.businessresponse;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.BeeFramework.model.BusinessResponse;
import com.android.house.costants.AppConstants;
import com.android.house.protocol.CacheInfo;
import com.android.house.protocol.User;
import com.android.house.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;

public class LoginBusinessResponse implements BusinessResponse{

	Context context;
	Handler handler;
	public LoginBusinessResponse(Context context,Handler handler)
	{
		this.context=context;
		this.handler=handler;
	}
	
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		Message msg=new Message();
		Log.d("mao",jo.toString());
		if(jo!=null)
		{
			int repleyCode=jo.optInt("status");
			if(AppConstants.JSON_REPLY_OK==repleyCode)
			{
				JSONObject entity=jo.optJSONObject("entities");
				String sessionId=entity.optString("PHPSESSID");
				User user=new User();
				JSONObject jsonUser=entity.optJSONObject("user");
				user.fromJson(jsonUser);
				Log.d("mao", "user ="+ user.getUser_id()+" "+user.getUsername()+" "+user.getCity());
				msg.what=AppConstants.LOGIN_OK;
				CacheInfo cacheInfo=new CacheInfo();
				cacheInfo.setUid(user.getUser_id());
				cacheInfo.setCity_id(user.getCity());
				cacheInfo.setLogin(true);
				cacheInfo.setNick_name(user.getUsername());
				cacheInfo.setPhone(user.getPhone());
				cacheInfo.setSessionId(sessionId);
				UserInfoCacheUtil.saveCacheInfo(context, cacheInfo);
				this.handler.sendMessage(msg);                                        
			}
			else if(AppConstants.JSON_REPLY_ERROR==repleyCode)
			{
				msg.what=AppConstants.LOGIN_ERR;
				msg.obj=jo.opt("msg");
				Toast.makeText(context, jo.optString("msg"), Toast.LENGTH_LONG).show();
				this.handler.sendMessage(msg);
			}
		}
		
	}



}
