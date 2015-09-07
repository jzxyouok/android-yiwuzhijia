package com.android.manager.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;


public class LoginModel extends BaseModel{

	
	public  String path="m/base/login";
	private Context myContext;
	public SharedPreferences shared;
	public SharedPreferences.Editor editor;
	
	BeeCallback<JSONObject> cb=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					LoginModel.this.callback(url, object, status);
					try {
						int statusCode=object.optInt("status");
						if(statusCode==200)
						{
						JSONObject userJsonObject=object.optJSONObject("entities");
						}
						LoginModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
					   
						e.printStackTrace();
					}
				}
				
			};
			
	public LoginModel(Context context) {
				super(context);
				myContext=context;
			}
	public void login(String tel,String pwd,int stype)
	{
		shared=myContext.getSharedPreferences("user",0);
		editor=shared.edit();
		Map<String, String> params=new HashMap<String ,String>();
		params.put("stype", 0+"");
		params.put("phone", tel);
		params.put("password", pwd);
		cb.url(path).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog=new MyProgressDialog(myContext, "登录中....");
		aq.progress(dialog.mDialog).ajax(cb);
		
	}
	
	
}
