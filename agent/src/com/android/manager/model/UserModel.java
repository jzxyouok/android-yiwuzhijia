package com.android.manager.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
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
import com.android.manager.protocol.City;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 个人用户相关：修改个人资料
 * 
 * @author Administrator
 *
 */
public class UserModel extends BaseModel {
	
	MyProgressDialog dialog;
	public ArrayList<City> cityList=new ArrayList<City>();
	
	public UserModel(Context context) {
		super(context);
		dialog=new MyProgressDialog(context, "资料上传中...");
	}

	/**
	 * 提意见 
	 * @param content
	 */
	public void addSuggestion(String content) {
		String url = ProtocolConst.SUGGESTION;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				UserModel.this.callback(url, jo, status);
				if (jo.optString("status").equals("200")) {
					try {
						UserModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {

						e.printStackTrace();
					}
				}

			}
		};
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, String> params = new HashMap<String, String>();
		params.put("content", content);
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "发送中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);

	}

	/**
	 * 修改密码
	 * @param phone
	 * @param password
	 * @param newPassword
	 */
	public void resetPwd( String password, String newPassword) {

		String url = ProtocolConst.RESETPWD;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				UserModel.this.callback(url, jo, status);
				if (jo.optString("status").equals("200")) {
					try {
						UserModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {

						e.printStackTrace();
					}
				}else if (jo.optString("status").equals("300")) {
					Toast.makeText(mContext, jo.optString("msg"), Toast.LENGTH_SHORT).show();
				}

			}
		};
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, String> params = new HashMap<String, String>();
		params.put("phone", cacheInfo.getPhone());
		params.put("password", password);
		params.put("newPassword", newPassword);
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "修改中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);

	}
	
	public void getUserInfo(){
		String url = ProtocolConst.GET_USERINFO;
		
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>(){
			@Override
			public void callback(String url, JSONObject jo,
					AjaxStatus status) {
				Log.d("mao","areaList:"+jo.toString());
			}
		};
	}
	
	/**
	 *  修改个人信息
	 * @param nick_name 昵称
	 * @param sex 0：女，1：男
	 * @param city_id 城市ID
	 * @param pic 头像图片
	 */
	public void  updateUserInfo(String nick_name,int sex,int city_id,File pic ){
		
		String url = ProtocolConst.UPDATE_USERINFO;
		
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				UserModel.this.callback(url, jo, status);
				if (jo.optString("status").equals("200")) {
					try {
						UserModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {

						e.printStackTrace();
					}
				}

			}
		};
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nickName", nick_name);
		params.put("sex", sex);
		params.put("cityId", city_id);
		params.put("pic","");
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "修改中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}
	
	
	/**
	 * 修改个人信息中获取城市列表
	 */
	
	public void getCityList(){
		String url = ProtocolConst.GET_CITY_LIST;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				UserModel.this.callback(url, jo, status);
				if (jo.optString("status").equals("200")) {
					try {
						JSONArray cityArray=jo.optJSONArray("entities");
						cityList.clear();
						if (cityArray!=null&&cityArray.length()>0) {
							for (int i = 0; i < cityArray.length(); i++) {
								JSONObject cityObject=cityArray.getJSONObject(i);
								City city=new City();
								city.fromJson(cityObject);
								cityList.add(city);
							}
						}
						
						UserModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {

						e.printStackTrace();
					}
				}

			}
		};
		Map<String, Object> params = new HashMap<String, Object>();
		
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "获取城市列表中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
		
		
	}
	
	/**
	 * 忘记密码
	 * @param phone 用户登录手机
	 */
	public void forgetPsd(String phone){
		
		String url=ProtocolConst.FORGETPWD;
		
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				UserModel.this.callback(url, jo, status);
					try {
						if (jo.optString("status").equals("200")) {
						UserModel.this.OnMessageResponse(url, jo, status);
						Toast.makeText(mContext, jo.optString("msg"), Toast.LENGTH_SHORT).show();
						}else {
							Toast.makeText(mContext, jo.optString("msg"), Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {

						e.printStackTrace();
				}

			}
		};
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", phone);
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "提交中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	
	}
	
	
	/*
	 * 带头像上传的
	 */
	
	public  void uploadUserInfo(String username,int sex ,int city_id,String areas,
			String carType,String salelong,String salenum,String nativePlace,
			File pic)
	{
		Log.d("mao","修改的名字:"+username);
		Log.d("mao","修改的cityid:"+city_id);
		
		AsyncHttpClient asyncClient=new AsyncHttpClient();
		RequestParams  requestParams=new RequestParams();
		CacheInfo cacheInfo=UserInfoCacheUtil.getCacheInfo(mContext);
		requestParams.put("nickName",username);
		requestParams.put("sex",sex+"");
		requestParams.put("cityId", city_id+"");
		requestParams.put("areas", areas);
		requestParams.put("cityName", areas);
		requestParams.put("carType", carType);
		requestParams.put("saleLong", salelong);
		requestParams.put("saleNum", salenum);	
		requestParams.put("nativePlace", nativePlace);
		requestParams.put("userId", cacheInfo.getUid());
		Log.d("mao","上传图片地址:"+pic.getAbsolutePath());
		if(!pic.exists())
		{
			Log.d("mao","文件不存在");
		}
		try {
			requestParams.put("file",pic);
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			requestParams.put("file", "");
			
		}
		//requestParams.put("PHPSESSID", cacheInfo.getSessionId());
		String url=ProtocolConst.WEB_DIR+ProtocolConst.UPDATE_USERINFO;
		this.dialog.mDialog.show();
		asyncClient.post(url,requestParams,new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO 自动生成的方法存根
				String responseBody=null;
					try {
						 responseBody=new String(arg2,"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				if(responseBody!=null)
				{	
					JSONObject jo=null;
					try {
						 jo=new JSONObject(responseBody);
					} catch (JSONException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					if(jo!=null)
					{
						int reply=jo.optInt("status");
						if(reply==200)
						{
							Toast.makeText(mContext, "资料修改成功", Toast.LENGTH_SHORT).show();
							Log.d("mao","修改资料返回信息:"+jo.toString());
							UserModel.this.dialog.mDialog.dismiss();
						}
						else
						{
							UserModel.this.dialog.mDialog.dismiss();
							Toast.makeText(mContext, jo.optString("msg"), Toast.LENGTH_SHORT).show();
						}
						
					}
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO 自动生成的方法存根
				UserModel.this.dialog.mDialog.dismiss();
				Log.d("mao","上传出错，服务器返回="+new String(arg2));
				Toast.makeText(mContext, "修改出错了哟，请重试~", Toast.LENGTH_SHORT).show();
			}
		});
	}
	


}
