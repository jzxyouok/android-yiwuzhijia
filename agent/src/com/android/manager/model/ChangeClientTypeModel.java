package com.android.manager.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.BeeFramework.AppConst;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.manager.protocol.Customer;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class ChangeClientTypeModel  extends BaseModel {

	private Context myContext;
	
	
	BeeCallback<JSONObject> bcb=new  BeeCallback<JSONObject>(){

		@Override
		public void callback(String url, JSONObject object, AjaxStatus status) {
			
			try {
				ChangeClientTypeModel.this.OnMessageResponse(url, object, status);
			} catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
	};
	public ChangeClientTypeModel(Context context) {
		super(context);
		this.myContext=context;
	}
	
	
	
	public void ajaxChangeClientType(Customer customer ,int type)
	{
		Map<String ,String >params=new HashMap<String,String>();
		params.put("status",type+"");
		params.put("serveRecordId",customer.getId()+"");
		bcb.cookie("PHPSESSID", UserInfoCacheUtil.getCacheInfo(myContext).getSessionId());
		//bcb.url(ProtocolConst.)
		bcb.url(ProtocolConst.CHANGE_CUSTOMER_RELATION).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog=new MyProgressDialog(myContext, "修改中....");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}
	
	
	
	
	
	
	
	
}
