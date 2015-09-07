package com.android.house.model;
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
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.House;
import com.baidu.mapapi.model.LatLng;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;


public class HouseModel extends BaseModel {
	
	
	public static final int REPLY_TYPE_SHOWFILTER=2;
	public static final int REPLY_TYPE_SHOWCITY=1;
	
	private String PATH_GET_CITY_HOUSE="m/lbs/getHouseListByGPS";
	private String PATH_GET_FILTED_HOUSE="m/user/searchHouseList";
	private List<House> currentCityHouses=new ArrayList<House>();

	private List<House> filterCityHouses=new ArrayList<House>();
	
	private int mHouseTotalCount;
	
	private Context myContext;
	
	private boolean isCurrCityLoaded=false;
	
	public static enum SEARCH_TYPE{
			PRICE,
			AREA,
			APARTMENT,
			DECORATE
	};
	
	BeeCallback<JSONObject> bcb_getHouseByCity=new BeeCallback<JSONObject>()
			{
			
				@Override
				public void callback(String url, JSONObject jo,
						AjaxStatus status) {
					try {
						HouseModel.this.callback(url, jo, status);
					} catch (Exception e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
						return ;
					}
					Log.d("mao", "请求房源url="+url);
					Log.d("mao","获取房源信息:" +jo.toString());
					if(null!=jo)
					{
						try {
							JSONArray jsonList=jo.optJSONArray("entities");
							for(int i=0;i<jsonList.length();i++)
							{
								JSONObject jsonItem=jsonList.optJSONObject(i);
								House house=new House();
								house.fromJson(jsonItem);
								HouseModel.this.currentCityHouses.add(house);
							}
							JSONObject replyStatus=new JSONObject();
							replyStatus.put("status", jo.optString("status"));
							replyStatus.put("type",REPLY_TYPE_SHOWCITY);
							jo=null;
							HouseModel.this.OnMessageResponse(url, replyStatus, status);
						} catch (Exception e) {
							Log.d("HouseModel ajax aq error", e.getMessage());
						}
					}
					
				}
		
			};
	

			
	
	BeeCallback<JSONObject> bcb_getFilterCallback=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject jo,
						AjaxStatus status) {
					//这个回调用来请求  房源筛选接口
					Log.d("mao",jo.toString());
					if(null!=jo)
					{
						try {
							JSONArray jsonList=jo.optJSONArray("entities");
							filterCityHouses.clear();
							if(jsonList.length() > 0){
								for(int i=0;i<jsonList.length();i++)
								{
									JSONObject jsonItem=jsonList.optJSONObject(i);
									House house=new House();
									house.fromJson(jsonItem);
									HouseModel.this.filterCityHouses.add(house);
								}
							}
							
							JSONObject replyStatus=new JSONObject();
							replyStatus.put("status", jo.optString("status"));
							replyStatus.put("type",REPLY_TYPE_SHOWFILTER);
							jo=null;
							HouseModel.this.OnMessageResponse(url, replyStatus, status);
						} catch (Exception e) {
							Log.d("HouseModel ajax aq error", e.getMessage());
						}
					}
				}
			};
			
	public HouseModel(Context context) {
		super(context);
		this.myContext=context;
	}
	
	
	public void ajaxLoadCurrentHouse(int city_id,LatLng cityLatlng) 
	{
		Map<String,String> params=new HashMap<String, String>();
		params.put("cityId", city_id+"");
		this.bcb_getHouseByCity.url(this.PATH_GET_CITY_HOUSE).params(params).method(Constants.METHOD_POST).type(JSONObject.class);
		MyProgressDialog mpd=new MyProgressDialog(myContext, "数据加载中......");
		aq.progress(mpd.mDialog).ajax(bcb_getHouseByCity);
	}
	
	
	public void ajaxLoadFilterHouse(int cityId,SEARCH_TYPE type ,int priceId, int areaId, int apartmentId, int decorationId)
	{
		Map<String,String> params=new HashMap<String, String>();
		params.put("cityId", cityId+"");
//		switch (type) {
//		case PRICE:
//			params.put("priceId", id+"");
//			break;
//		case AREA:
//			params.put("areaId", id+"");
//			break;
//		case APARTMENT:
//			params.put("apartmentId", id+"");
//			break;
//		case DECORATE:
//			params.put("decorationId", id+"");
//			break;
//		default:
//			break;
//		}

		if (areaId >= 0) {
			params.put("areaId", areaId+"");
		}
		
		params.put("apartmentId", apartmentId+"");
		params.put("decorationId", decorationId+"");
		params.put("priceId", priceId+"");
		
		this.bcb_getFilterCallback.url(PATH_GET_FILTED_HOUSE).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog =new MyProgressDialog(myContext, "查询中，请稍后...");
		this.aq.progress(dialog.mDialog).ajax(bcb_getFilterCallback);
	}
	
	public List<House> getCurrentCityHouse() {
		return this.currentCityHouses;
	}
	
	public List<House> getFiltedHouse()
	{
		return this.filterCityHouses;
	}
	
	public int getHouseTotalCount() {
		return mHouseTotalCount;
	}
	public void clearData()
	{
		//if(!isLoaded)//如果用户切换城市过快，我们要取消前一次 获取房源信息的请求，防止数据错乱
		for(House house:this.currentCityHouses)
		{
			house=null;
		}
		this.currentCityHouses.clear();
	}
	
}
