package com.android.manager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.Customer;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.protocol.UserAccount;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class SearchMyCustomerModel extends BaseModel{

	public ArrayList<Customer> customerList=new ArrayList<Customer>();
	private Context mContext;
	
	public SearchMyCustomerModel(Context context) {
		super(context);
		mContext=context;
	}
	
	public void getCustomerList(String keyWords){
		String url=ProtocolConst.SEARCH_MY_CUSTOMER;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				SearchMyCustomerModel.this.callback(url, jo, status);
				if (jo.optString("status").equals("200")) {

					try {
						JSONArray customerArray = jo.optJSONArray("entities");
						customerList.clear();
						if (customerArray != null && customerArray.length() > 0) {

							for (int i = 0; i < customerArray.length(); i++) {

								JSONObject itemJsonObject = customerArray
										.getJSONObject(i);
								Customer customer=new Customer();
								customer.fromJSON(itemJsonObject);
								customerList.add(customer);

							}
						}
						SearchMyCustomerModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};

		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, String> params = new HashMap<String, String>();
		params.put("keyWords",keyWords);
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "努力搜索中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}
	
	
}
