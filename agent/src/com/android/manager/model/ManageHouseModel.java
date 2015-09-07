package com.android.manager.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.manager.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class ManageHouseModel extends BaseModel{
	Context myContext;
	
	
	BeeCallback<JSONObject> bcbGetCustomer=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					Log.d("mao",object.toString());
					try {
						ManageHouseModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
		
			};
	public ManageHouseModel(Context context) {
		super(context);
		myContext=context;
	}
	
	public void ajaxGetCustomer(int stype,int relationType)
	{
		Map<String,String> params=new HashMap<String, String>();
		params.put("stype", stype+"");
		params.put("relationType", relationType+"");
		
		bcbGetCustomer.url(ProtocolConst.GET_MY_CUSTOMER_LIST).params(params)
		.type(JSONObject.class).method(Constants.METHOD_POST);
		
		MyProgressDialog dialog=new MyProgressDialog(myContext, "刷新中");
		this.aq.progress(dialog.mDialog).ajax(bcbGetCustomer);
	}
	
	
}
