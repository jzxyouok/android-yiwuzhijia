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
import com.android.manager.protocol.RegistInfo;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class RegistModel extends BaseModel {

	private Context myContext;
	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					try {
						RegistModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				
			};
			
			
			
	public RegistModel(Context context) {
		super(context);
		myContext=context;
	}
	
	
	public void getValidateCode(String phone)
	{
		Map<String,String> params=new HashMap<String,String>();
		params.put("phone", phone);
		bcb.url(ProtocolConst.GET_VALIDDATE_CODE).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog=new MyProgressDialog(myContext, "验证码获取中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}
	
	public void registUser(RegistInfo registInfo)
	{
		Map<String,String> params=new HashMap<String,String>();
		params.put("code", registInfo.getValidateCode());
		params.put("phone",registInfo.getPhone());
		params.put("password", registInfo.getPwd());
		params.put("stype", 3+"");
		params.put("sex", 1+"");
		params.put("cityId",1+"");
		params.put("cityName","成都");
		this.bcb.url(ProtocolConst.REGIST_USER).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		this.bcb.cookie("PHPSESSID", registInfo.getSessionId());
		MyProgressDialog dialog=new MyProgressDialog(this.myContext, "请稍等...");
		this.aq.progress(dialog.mDialog).ajax(this.bcb);
	}
}
