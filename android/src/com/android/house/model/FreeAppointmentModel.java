package com.android.house.model;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class FreeAppointmentModel extends BaseModel{

	
	private Context myContext;
	private String path="m/user/reserveAgent";
	
	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					
					Log.d("mao",object.toString());
					try {
						FreeAppointmentModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
						
						e.printStackTrace();
					}
				}
				
			};
	public FreeAppointmentModel(Context context) {
		super(context);
		this.myContext=context;
	}
	
	public void  appointAgent(int userId,int agentid,String sessionid)
	{
		Map<String ,String> params=new HashMap<String, String>();
//		params.put("userId", userId+"");
		params.put("agentId", agentid+"");
//		params.put("agentId", userId+"");
		params.put("PHPSESSID", sessionid);
		bcb.cookie("PHPSESSID", sessionid);
		this.bcb.url(this.path).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog=new MyProgressDialog(myContext, "预约申请中");
		this.aq.progress(dialog.mDialog).ajax(this.bcb);
	}
	
	
	

}
