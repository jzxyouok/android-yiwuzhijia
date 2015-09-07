package com.android.manager.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.android.manager.protocol.House;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;


//根据房源ID 获得推荐经纪人
public class GetRecAgentModel extends BaseModel{

	public interface  HandlerBitmap
	{
		public void done(String url,Bitmap bitmap,AjaxStatus status);
	}
	
	private HandlerBitmap handlerBitmap;
	
	public void setHandlerBitmap(HandlerBitmap handlerBitmap) {
		this.handlerBitmap = handlerBitmap;
	}


	private String path="m/user/getHouseAgentInfo";
	private Context myContext;
	private BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>(){

		@Override
		public void callback(String url, JSONObject object, AjaxStatus status) {
			try {
				GetRecAgentModel.this.OnMessageResponse(url, object, status);
			} catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
	};
	
	
	public GetRecAgentModel(Context context) {
		super(context);
		this.myContext=context;
	}
	
	
	public void getRecommendAgent(House house)
	{
		Map<String,String> params=new HashMap<String,String>();
		params.put("houseId", house.getHouse_id()+"");
		this.bcb.url(path).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		this.aq.ajax(bcb);
	}
	
	
	
}
