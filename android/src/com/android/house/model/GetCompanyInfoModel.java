package com.android.house.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Paint.Join;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class GetCompanyInfoModel extends BaseModel {

	public String weixin;
	public String phone;

	public GetCompanyInfoModel(Context context) {
		super(context);

	}

	public void getCompanyInfo() {
		this.getCompanyPhone();
		this.getCompanyWx();
	}

	public void getCompanyPhone() {
		String url = ProtocolConst.GET_MASTER_VALUE;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject object,
					AjaxStatus status) {
				try {
					GetCompanyInfoModel.this.callback(url, object, status);
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				Log.d("mao", object.toString());
				if (object.optString("status").equals("200")) {
					try {
						phone=object.optString("msg");
						GetCompanyInfoModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("commonKey", "SERVICE_PHONE");
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);

		this.aq.ajax(bcb);
	}

	public void getCompanyWx() {
		String url = ProtocolConst.GET_MASTER_VALUE;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {
			
			@Override
			public void callback(String url, JSONObject object,
					AjaxStatus status) {
				try {
					GetCompanyInfoModel.this.callback(url, object, status);
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				Log.d("mao", object.toString());
				if (object.optString("status").equals("200")) {
					try {
						weixin=object.optString("msg");
						GetCompanyInfoModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("commonKey", "SERVICE_WEIXIN");
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);

		this.aq.ajax(bcb);
	}

}
