package com.android.house.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.House;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

import android.content.Context;

public class GetHouseImageModel extends  BaseModel{

	private Context myContext;
	private String path="m/user/getHousePicList";
	
	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>()
			{
				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					
					try {
						GetHouseImageModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
		
			};
	public GetHouseImageModel(Context context) {
		super(context);
		this.myContext=context;
	}
	
	
	public void getHouseDetail(House house)
	{
		Map<String,String> params=new HashMap<String, String>();
		params.put("houseId", house.getHouse_id()+"");
		
		this.bcb.url(path).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog=new MyProgressDialog(myContext, "户型图加载中");
		this.aq.progress(dialog.mDialog).ajax(this.bcb);
	}
}


	


