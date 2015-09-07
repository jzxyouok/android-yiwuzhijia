package com.android.house.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.Agent;
import com.android.house.protocol.House;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class KeywordSearchModel extends BaseModel {

	private int currentCityId=1;
	List<House> houseList=new ArrayList<House>();
	List<Agent> agentList=new ArrayList<Agent>();
	private Context myContext;
	
	private String path="m/user/searchHouseAndAgentList";
	
	
	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					Log.d("mao",object.toString());
				}
					
			};
	public KeywordSearchModel(Context context) {
		super(context);
		this.myContext=context;
	}
	
	
	
	
	public  void search(String keyword)
	{
		Map<String,String> params=new HashMap<String,String>();
		params.put("name", keyword);
		params.put("cityId", currentCityId+"");
		
		this.bcb.url(path).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog=new MyProgressDialog(myContext, "查询中....");
		this.aq.progress(dialog.mDialog).ajax(this.bcb);
		
	}
	
	
	
}
