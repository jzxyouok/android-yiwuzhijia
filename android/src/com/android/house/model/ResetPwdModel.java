package com.android.house.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.CacheInfo;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

import android.content.Context;
import android.util.Log;

public class ResetPwdModel extends BaseModel {
	

	public   String path="m/base/resetPwd";
	
	private Context myContext;
	
	
	public ResetPwdModel(Context context) {
		super(context);
		this.myContext=context;
	}



	
	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					try {
						ResetPwdModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				
			};
			
	 public void resetPwd(String oldPwd,String newPwd,CacheInfo cacheInfo)
	 {
		 Log.d("mao", "sesssionid="+cacheInfo.getSessionId());
		 Map<String,String> params=new HashMap<String,String>();
		 params.put("password", oldPwd);
		 params.put("newPassword",newPwd);
		 params.put("PHPSESSID",cacheInfo.getSessionId());
		 params.put("phone", cacheInfo.getPhone());
		 
		 this.bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		 bcb.params(params).url(this.path).type(JSONObject.class).method(Constants.METHOD_POST);
		 MyProgressDialog dialog=new MyProgressDialog(this.myContext, "修改提交中...");
		 this.aq.progress(dialog.mDialog).ajax(this.bcb);
		 
	 }
}
