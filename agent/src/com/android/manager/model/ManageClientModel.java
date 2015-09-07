package com.android.manager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.manager.protocol.ClientRecord;
import com.android.manager.protocol.Customer;
import com.android.manager.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class ManageClientModel extends BaseModel{

	Context myContext;
	public List<Customer> lists=new ArrayList<Customer>();
	public Map<Integer ,List<ClientRecord>> maps=new HashMap<Integer, List<ClientRecord>>();
	BeeCallback<JSONObject> bcbGetCustomer=new BeeCallback<JSONObject>()
			{
				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {

					Log.d("mao",object.toString());
					JSONArray entities=object.optJSONArray("entities");
					
					if(entities.length()!=0)
					{
						addToList(lists, entities);
						try {
							ManageClientModel.this.OnMessageResponse(url, object, status);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			};
	
	public ManageClientModel(Context context) {
		super(context);
		myContext=context;
	}
	
	public void ajaxGetCustomer(int stype,int relationType,String sessionid)
	{
		Map<String,String> params=new HashMap<String, String>();
		params.put("stype", stype+"");
		params.put("relationType", relationType+"");
		params.put("page", 1+"");
		params.put("PHPSESSID", sessionid);
		
		bcbGetCustomer.url(ProtocolConst.GET_MY_CUSTOMER_LIST).params(params)
		.cookie("PHPSESSID", sessionid)
		.type(JSONObject.class).method(Constants.METHOD_POST);
		
		MyProgressDialog dialog=new MyProgressDialog(myContext, "刷新中");
		this.aq.progress(dialog.mDialog).ajax(bcbGetCustomer);
	}
	
	
	private void addToList(List<Customer> list ,JSONArray jsonArray)
	{
		synchronized (list) {
			list.clear();
			for(int i=0;i<jsonArray.length();i++)
			{
				Customer customer=new Customer();
				customer.fromJSON(jsonArray.optJSONObject(i));
				list.add(customer);
			}
		}
	}

	public List<Customer> getCustomerLists()
	{
		synchronized (lists) {
			return  lists;
		}
	}
	
	
	public void asycGetCustomerRecord()
	{
		for(Customer customer:lists)
		{
			
		}
	}
}
