package com.android.house.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.Area;
import com.android.house.protocol.City;
import com.android.house.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class LoadCitylistModel extends BaseModel{

	private Context myContext;
	private String path="m/lbs/getCityList";
	
	private List<Area> areaList = new ArrayList<Area>();
	
	private Map<String ,City> mCityMap=new HashMap<String, City>();
	
	
	private boolean isLoaded=false;
	
	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					Log.d("mao","userlist:"+object.toString());
					try{
						if(object!=null)
						{
							int replyCode=object.optInt("status");
							if(replyCode==200)
							{
								JSONArray jsonArray=object.optJSONArray("entities");
								JSONObject jsonCity;
								Log.d("mao", "jsonArray长度:"+jsonArray.length());
								for(int i=0;i<jsonArray.length();i++)
								{
									jsonCity=jsonArray.getJSONObject(i);
									City city=new City();
									city.fromJson(jsonCity);
									Log.d("mao", city.getCity_name());
									mCityMap.put(city.getCity_name(), city);
								}
							}
							
							LoadCitylistModel.this.isLoaded=true;
						}
						
					}catch(JSONException e)
					{
						e.printStackTrace();
					}
					
					
				}
				
			};
	
			
			
	public LoadCitylistModel(Context context) {
		super(context);
		this.myContext=context;
	}
	
	public void loadAreaList(int cityId) //在业务回调方法中，去处理adapter，回调统一用Handler机制
	{
		String url = ProtocolConst.GET_AREA_LIST;
		BeeCallback<JSONObject> bcbFilter = new BeeCallback<JSONObject>()
				{

					@Override
					public void callback(String url, JSONObject jo,
							AjaxStatus status) {
						Log.d("mao","areaList:"+jo.toString());
						if(jo != null){
							try{
								areaList.clear();
								int replyCode = jo.optInt("status");
								if(replyCode == 200){
									JSONArray jsonArray = jo.optJSONArray("entities");
									for(int i = 0;i < jsonArray.length();i++){
										JSONObject item = jsonArray.getJSONObject(i);
										Area area = new Area();
										area.fromJson(item);
										areaList.add(area);
									}
								}
								LoadCitylistModel.this.OnMessageResponse(url, jo, status);
							} catch (Exception e) {
								Log.d("mao LoadCitylistModel exception:", e.getMessage());
							}
						}
					}
				};
		Map<String, String> params = new HashMap<String, String>();
		params.put("cityId", cityId+"");
		bcbFilter.url(url).params(params).type(JSONObject.class).method(Constants.METHOD_POST).type(JSONObject.class);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcbFilter);
	}
	
	public List<String> getAreaList(){
		List<String> areaList = new ArrayList<String>();
		
		for(int i = 0;i < this.areaList.size();i++){
			Log.d("mao","区域名："+this.areaList.get(i).getAreaName());
			String str = this.areaList.get(i).getAreaName();
			areaList.add(str);
		}
		return areaList;
	}
	
	public List<Area> getAreaLists()
	{
		return areaList;
	}
	
	public List<String> getCityNameList() //在业务回调方法中，去处理adapter，回调统一用Handler机制
	{
		List<String> cityNameList=new ArrayList<String>();
		Set<String> keys=this.mCityMap.keySet();
		Log.d("mao","map大小:"+keys.size()+"");
		for(String cityName:keys)
		{
			cityNameList.add(cityName);
		}
		return cityNameList;
	}
	
	public void loadCityList()	
	{
	
			this.bcb.url(this.path).type(JSONObject.class).method(Constants.METHOD_POST);
			this.aq.ajax(this.bcb);
			Log.d("mao", "加载城市列表");
		
	}

	public boolean isLoaded() {
		return isLoaded;
	}
	
	public City  getCity(String cityName)
	{
		City city=null;
		if(this.mCityMap.containsKey(cityName))
		{
			 city=mCityMap.get(cityName);
		}
		return city;
	}
	
}
