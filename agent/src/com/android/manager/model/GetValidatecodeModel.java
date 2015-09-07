package com.android.manager.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.manager.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class GetValidatecodeModel extends BaseModel {

	Context myContext;
	
	public GetValidatecodeModel(Context context) {
		super(context);
		myContext=context;
	}

	
	BeeCallback<JSONObject > bc=new BeeCallback<JSONObject>()
			{
				
				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					GetValidatecodeModel.this.callback(url, object, status);
					try {
						GetValidatecodeModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
		
				
			};
			
	public void registUser(String phone)
	{
		Map<String,String> params=new HashMap<String,String>();
		params.put("phone", phone);
		bc.url(ProtocolConst.GET_VALIDDATE_CODE).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog=new MyProgressDialog(GetValidatecodeModel.this.myContext, "请稍后...");
		this.aq.progress(dialog.mDialog).ajax(this.bc);
	}
}
