package com.android.manager.businessresponse;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.manager.costants.AppConstants;
import com.external.androidquery.callback.AjaxStatus;

public class ForgetPwdBusinessResponse extends AbstractBusinessResponse {

	public ForgetPwdBusinessResponse(Context context, Handler handler) {
		super(context, handler);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
			Log.d("mao", "forget pwd:"+jo.toString());
			if(jo!=null)
			{
				int relpyCode =jo.optInt("status");
				if(relpyCode==AppConstants.JSON_REPLY_OK)
				{
					Toast.makeText(ForgetPwdBusinessResponse.this.context, "密码已经已短信形式发送至您的手机，请注意查收", Toast.LENGTH_SHORT).show();
				}else
				{
					Toast.makeText(ForgetPwdBusinessResponse.this.context, "网络出错，请重试", Toast.LENGTH_SHORT	).show();
				}
			}
	}
	
}
