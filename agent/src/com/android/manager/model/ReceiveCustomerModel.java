package com.android.manager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.Customer;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class ReceiveCustomerModel  extends BaseModel{

	public ArrayList<Customer> customerList=new ArrayList<Customer>();
	private Context mContext;
	
	public ReceiveCustomerModel(Context context) {
		super(context);
		mContext=context;
		
	}
	
	/**
	 * 获取我的客户列表
	 * @param stype 经纪人类型：2：业内经纪人，3：经纪人
	 * @param relationType 交易关系：1：预约，2：意向，3：重点,4:成交,5:失败
	 */
	public void getCustomerList(int stype,int relationType){
		
		String url =  ProtocolConst.GET_MY_CUSTOMER_LIST;
		
		BeeCallback<JSONObject> beeCallback=new BeeCallback<JSONObject>(){
			
			@Override
			public void callback(String url, JSONObject jsonObject,
					AjaxStatus status) {
				ReceiveCustomerModel.this.callback(url, jsonObject, status);
				Log.i("dsff", jsonObject.toString());
				if (jsonObject.optString("status").equals("200")) {
					JSONArray customerArray=jsonObject.optJSONArray("entities");
					customerList.clear();
					if (customerArray!=null&&customerArray.length()>0) {
						for (int i = 0; i <customerArray.length(); i++) {
							 try {
								JSONObject customerObject=customerArray.getJSONObject(i);
								Customer customer=new Customer();
								customer.fromJSON(customerObject);
								customerList.add(customer);
		
							} catch (JSONException e) {			
								e.printStackTrace();
							}
						}
					}
					try {
						ReceiveCustomerModel.this.OnMessageResponse(url, jsonObject, status);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String ,Integer> params=new HashMap<String, Integer>();
		params.put("stype", stype);
		params.put("relationType", relationType);
		beeCallback.cookie("PHPSESSID", cacheInfo.getSessionId());
		beeCallback.url(url).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
        MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
        this.aq.progress(dialog.mDialog).ajax(beeCallback);
	}
	
	/**
	 * 处理接待客户
	 * @param serveRecordId 记录Id
	 * @param uaStatus 2：接待，6：拒绝
	 */
	public void doReceiveCustomer(int userId,int status){
		String url=ProtocolConst.DO_RECEIVECUSTOMER;
		
		BeeCallback<JSONObject> beeCallback = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				ReceiveCustomerModel.this.callback(url, jo, status);
				Log.i("shen",jo.toString());
				if (jo.optString("status").equals("200")) {
					try {
						
						ReceiveCustomerModel.this.OnMessageResponse(url, jo, status);
					
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				if (jo.optString("status").equals("300")){
					Toast.makeText(mContext, jo.optString("msg")+"", Toast.LENGTH_LONG).show();
				}
			}
		};

		Map<String, Object> params = new HashMap<String, Object>();
		
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		
		params.put("serveRecordId", userId);
		params.put("uaStatus", status);
		beeCallback.cookie("PHPSESSID", cacheInfo.getSessionId());
		beeCallback.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "处理中...");
		this.aq.progress(dialog.mDialog).ajax(beeCallback);
	
	}

}
