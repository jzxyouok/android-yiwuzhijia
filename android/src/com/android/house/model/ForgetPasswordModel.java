package com.android.house.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class ForgetPasswordModel  extends BaseModel{

	public String path="m/base/forgetPwd";
	private Context myContext;
	
	
	
	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>(){

		@Override
		public void callback(String url, JSONObject object, AjaxStatus status) {
		
			try {
				ForgetPasswordModel.this.OnMessageResponse(url, object, status);
			} catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
	};
	public ForgetPasswordModel(Context context) {
		super(context);
		this.myContext=context;
	}
	
	
	
	public void forgetPwd(String phone)
	{
		this.bcb.url(path).param("phone", phone).method(Constants.METHOD_POST).type(JSONObject.class);
		
		MyProgressDialog dialog=new MyProgressDialog(this.myContext, "请求发送中");
		this.aq.progress(dialog.mDialog).ajax(this.bcb);
	}
	

}
