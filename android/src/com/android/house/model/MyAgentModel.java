package com.android.house.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.AGENTCOMMENT;
import com.android.house.protocol.Agent;
import com.android.house.protocol.CacheInfo;
import com.android.house.protocol.ProtocolConst;
import com.android.house.protocol.User;
import com.android.house.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class MyAgentModel extends BaseModel {

	private Context mContext;
	public Agent agent;
	public ArrayList<AGENTCOMMENT> commentList = new ArrayList<AGENTCOMMENT>();
	
	public Agent myAgent=new Agent();
	public ArrayList<AGENTCOMMENT> myAgentCommentList=new ArrayList<AGENTCOMMENT>();

	public MyAgentModel(Context context) {
		super(context);
		mContext = context;
	}

	/**
	 * 获取经纪人详细信息
	 * 
	 * @param agentId
	 *            经纪人id
	 */
	public void getAgentInfo(int agentId) {
		
		String url = ProtocolConst.GET_AGNETINFO;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				//MyAgentModel.this.callback(url, jo, status);
				if (jo.optString("status").equals("200")) {
					try {
						agent = new Agent();
						agent.fromJson(jo.getJSONObject("agent"));
						MyAgentModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};

		Map<String, String> params = new HashMap<String, String>();
		params.put("agentId", agentId + "");

		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);

	}

	/**
	 * 获取经纪人评论列表
	 * 
	 * @param agentid
	 * @param sessionid
	 */
	public void getCommentList(int user_id, String sessionid) {
		
		String url = ProtocolConst.GET_AGENTCOMMENT;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					MyAgentModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
					return ;
				}
				if (jo.optString("status").equals("200")) {
					try {
						JSONArray commentArray = jo.optJSONArray("entities");
						commentList.clear();
						if (commentArray != null && commentArray.length() > 0) {
							for (int i = 0; i < commentArray.length(); i++) {
								JSONObject commentJsonObject = commentArray
										.getJSONObject(i);
								AGENTCOMMENT comment = new AGENTCOMMENT();
								comment.fromJson(commentJsonObject);
								commentList.add(comment);
							}
						}

						MyAgentModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};

		Map<String, String> params = new HashMap<String, String>();
		params.put("agentId", user_id + "");
		params.put("PHPSESSID", sessionid );
		bcb.cookie("PHPSESSID", sessionid );
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}
	
	/**
	 * 评论经纪人
	 * @param userId 用户id
	 * @param agentId 经纪人id
	 */
	public void commentAgent(String judge,float car,float suggest,float dress){
		
		String url=ProtocolConst.COMMENT_AGNET;
		BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>(){
			@Override
			public void callback(String url, JSONObject jo,
					AjaxStatus status) {
				try {
					MyAgentModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
					return ;
				}
				if (jo.optString("status").equals("200")) {
				   try {
					   
					MyAgentModel.this.OnMessageResponse(url, jo, status);
					
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				}else {
					Toast.makeText(mContext, "评论失败", Toast.LENGTH_LONG).show();
				}
			}
		};
		
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("content", judge);
		params.put("car", car);
		params.put("suggest", suggest);
		params.put("dress", dress);
		
		
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "评论中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
		
	}
	
	/**
	 * 解约经纪人
	 */
	public void cancelAgent(String judge,float car,float suggest,float dress){
		
		String url=ProtocolConst.CANCEL_AGENT;
		BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>(){
			@Override
			public void callback(String url, JSONObject jo,
					AjaxStatus status) {
				try {
					MyAgentModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
					return ;
				}
				if (jo.optString("status").equals("200")) {
				   try {
					   
					MyAgentModel.this.OnMessageResponse(url, jo, status);
					
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				}else if(jo.optString("status").equals("300")) {
					Toast.makeText(mContext, jo.optString("msg"), Toast.LENGTH_LONG).show();
				}
			}
		};
		
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("content", judge);
		params.put("car", car);
		params.put("suggest", suggest);
		params.put("dress", dress);
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "解约中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
		
	}
	/**
	 * 获取我的经纪人
	 * 
	 * @param agentId
	 *            经纪人id
	 */
	public void getMyAgentInfo() {
		
		String url = ProtocolConst.GET_MYAGENTINFO;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					MyAgentModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
					return ;
				}
				if (jo.optString("status").equals("200")) {
					try {
					
						myAgent.fromJson(jo.optJSONObject("agent"));
						
						JSONArray commentArray = jo.optJSONArray("commentList");
						myAgentCommentList.clear();
						if (commentArray != null && commentArray.length() > 0) {
							for (int i = 0; i < commentArray.length(); i++) {
								JSONObject commentJsonObject = commentArray
										.getJSONObject(i);
								AGENTCOMMENT comment = new AGENTCOMMENT();
								comment.fromJson(commentJsonObject);
								myAgentCommentList.add(comment);
							}
						}else {
							 Toast.makeText(mContext, "暂无评论记录", Toast.LENGTH_LONG).show();
						}
						
						MyAgentModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
		CacheInfo cacheInfo=UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, String> params = new HashMap<String, String>();
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);

	}
}
