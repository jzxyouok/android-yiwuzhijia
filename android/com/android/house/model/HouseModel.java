package com.android.house.model;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.House;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class HouseModel extends BaseModel {
	private List<House> aroundHouse=new ArrayList<House>();
	private List<House> historyHouse=new ArrayList<House>();
	private int HouseTotalCount;
	public HouseModel(Context context) {
		super(context);
	}
	
	public void initHouseData()
	{
		String url="/gethouse";
		
		BeeCallback<JSONObject> cb=new BeeCallback<JSONObject>()
				{

					@Override
					public void callback(String url, JSONObject jo,
							AjaxStatus status) {
						HouseModel.this.callback(url, jo, status);
						if(null!=jo)
						{
							//HouseModel.this.historyHouse.clear();
							try {
								JSONObject jsonRoot=jo;
								JSONArray jsonList=jo.optJSONArray("houses");
								for(int i=0;i<jsonList.length();i++)
								{
									JSONObject jsonItem=jsonList.optJSONObject(i);
									House house=new House();
									house.fromJson(jsonItem);
									if(HouseModel.this.historyHouse.contains(house))
									{
										break;
									}
									HouseModel.this.historyHouse.add(house);
								}
								HouseModel.this.HouseTotalCount=jsonRoot.optInt("list_size");
								
								//当处理完所有的json对象之后，在调用父类BaseModel的OnMessageResponse执行业务消息链
								HouseModel.this.OnMessageResponse(url, jo, status);
							} catch (Exception e) {
								Log.d("HouseModel ajax aq error", e.getMessage());
							}
						}
						
					}
			
				};
		MyProgressDialog mpd=new MyProgressDialog(mContext, "房源信息正在加载中......");
		cb.url(url).type(JSONObject.class).method(Constants.METHOD_GET);
		aq.progress(mpd.mDialog).ajax(cb);
		
	}

	public List<House> getHistoryHouse() {
		return historyHouse;
	}

	@Override
	public void addResponseListener(BusinessResponse listener) {
		// TODO 自动生成的方法存根
		if(businessResponseArrayList.contains(listener))
			return;
		super.addResponseListener(listener);
	}
	
	
}
