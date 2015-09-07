package com.android.house.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.costants.AppConstants;
import com.android.house.protocol.Agent;
import com.android.house.protocol.House;
import com.android.house.util.JSONUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class AgentModel extends BaseModel {

	private boolean isCurrCityLoaded = false;
	Context myContext;
	List<Agent> currentCityAgents = new ArrayList<Agent>();
	private String path = "m/lbs/getAgentListByGPS";

	public AgentModel(Context context) {
		super(context);
		this.myContext = context;
	}

	BeeCallback<JSONObject> bcb_getAgents = new BeeCallback<JSONObject>() {
		@Override
		public void callback(String url, JSONObject object, AjaxStatus status) {
			Log.d("mao", object.toString());
			if (object != null) {
				try {
					//currentCityAgents.clear();
					JSONArray jsonArray = object.optJSONArray("entities");
					for (int i = 0; i < jsonArray.length(); i++) {
						final Agent agent = new Agent();
						agent.fromJson(jsonArray.optJSONObject(i));
						// ImageLoadingListener listener=new
						// SimpleImageLoadingListener(){
						//
						// @Override
						// public void onLoadingComplete(
						// String imageUri, View view,
						// Bitmap loadedImage) {
						// agent.setBitmap(loadedImage);
						// }
						//
						// };
						// String urlPath = AppConstants.WEBHOME +
						// JSONUtil.getImagePath(agent.getPic());
						// ImageLoader.getInstance().loadImage(urlPath,
						// listener);
						currentCityAgents.add(agent);
					}
					JSONObject reply = new JSONObject();
					reply.put("status", object.optInt("status"));
					object = null;
					AgentModel.this.OnMessageResponse(url, reply, status);
					AgentModel.this.isCurrCityLoaded = true;
				} catch (Exception e) {
					Log.d("mao AgentModel exception:", e.getMessage());
				}

			}
		}
	};

	public void ajaxLoadCurrrentCityAgents(int cityid,double lng,double lat) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityId", cityid + "");
		params.put("lng", lng);
		params.put("lat", lat);
		this.bcb_getAgents.url(path).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		aq.ajax(this.bcb_getAgents);
	}

	public List<Agent> getAgentLists() {
		return this.currentCityAgents;
	}

	public Agent getAgent(int index) {
		return this.currentCityAgents.get(index);
	}

	public void clearData() {
		for (Agent agent : this.currentCityAgents) {
			agent = null;
		}
		this.currentCityAgents.clear();
	}

	public void setAgentList(List<Agent> agents) {
		for (Agent agent : agents) {
			this.currentCityAgents.add(agent);
		}
	}
}
