package com.android.manager.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Paint.Join;
import android.util.Log;
import android.widget.Toast;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.CityArea;
import com.android.manager.protocol.Customer;
import com.android.manager.protocol.House;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.protocol.Record;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.activeandroid.query.From;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddRecordModel extends BaseModel {

	private Context myContext;

	public List<House> houseList = new ArrayList<House>();
	public List<CityArea> areaList = new ArrayList<CityArea>();
	public Record record = new Record();

	public AddRecordModel(Context context) {
		super(context);
		myContext = context;
	}

	/**
	 * 增加陪看
	 * 
	 * @param record
	 * @param customer
	 */
	public void addRecord(Record record, Customer customer, String path) {
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject object, AjaxStatus status) {

				try {
					AddRecordModel.this.OnMessageResponse(url, object, status);
				} catch (JSONException e) {

					e.printStackTrace();
				}

			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serveRecordId", customer.getId() + "");
		params.put("content", record.getContent());
		params.put("businessStatus", record.getBusinessStatus() + "");
		params.put("houseId", record.getHouseId() + "");
		params.put("houseName", record.getHouseName());
		bcb.cookie("PHPSESSID", UserInfoCacheUtil.getCacheInfo(myContext).getSessionId());
		bcb.url(ProtocolConst.ADD_BUSINESS_RECORD).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		if (path == null) {
			params.put("file", "");
		} else {

			File pic = new File(path);
			params.put("file", pic);
		}

		MyProgressDialog dialog = new MyProgressDialog(myContext, "看房记录提交中....");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}

	/**
	 * 增加成交
	 * 
	 * @param record
	 * @param customer
	 */
	public void addBusiness(Record record, Customer customer, String path) {
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject object, AjaxStatus status) {

				try {
					AddRecordModel.this.OnMessageResponse(url, object, status);
				} catch (JSONException e) {

					e.printStackTrace();
				}

			}

		};
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serveRecordId", customer.getId() + "");
		params.put("content", record.getContent());
		params.put("businessStatus", record.getBusinessStatus() + "");
		params.put("houseId", record.getHouseId() + "");
		params.put("houseName", record.getHouseName());
		params.put("buyer", record.getBuyer());
		params.put("money", record.getMoney());
		params.put("relation", record.getRelation());
		params.put("payWay", record.getPayWay());
		params.put("POS", record.getPOS());
		params.put("agreementNum", record.getAgreementNum());
		params.put("saleMan", record.getSaleMan());
		params.put("successTime", record.getSuccessTime());
		params.put("houseSourceNum", record.getHouseSourceNum());
		if (path == null) {
			params.put("file", "");
		} else {

			File pic = new File(path);
			params.put("file", pic);
		}

		bcb.cookie("PHPSESSID", UserInfoCacheUtil.getCacheInfo(myContext).getSessionId());
		bcb.url(ProtocolConst.ADD_BUSINESS_RECORD).params(params).type(JSONObject.class).method(Constants.METHOD_POST);

		MyProgressDialog dialog = new MyProgressDialog(myContext, "看房记录提交中....");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}

	/**
	 * 获取区域列表
	 * 
	 * @param cityId
	 */
	public void getCityAreaList(int cityId) {
		String url = ProtocolConst.GET_AREA_LIST;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject object, AjaxStatus status) {
				try {
					JSONArray accountArray = object.optJSONArray("entities");
					areaList.clear();
					if (accountArray != null && accountArray.length() > 0) {

						for (int i = 0; i < accountArray.length(); i++) {

							JSONObject itemJsonObject = accountArray.getJSONObject(i);
							CityArea area = new CityArea();
							area.fromJson(itemJsonObject);
							areaList.add(area);
						}
					}
					AddRecordModel.this.OnMessageResponse(url, object, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityId", cityId);
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}

	/**
	 * 获取区域内的房源
	 * 
	 * @param areaId
	 * @param page
	 */
	public void getAreaHouseList(int areaId, int page) {
		String url = ProtocolConst.GET_HOUSE_LIST_BYAREA;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject object, AjaxStatus status) {
				try {
					JSONArray accountArray = object.optJSONArray("entities");
					houseList.clear();
					if (accountArray != null && accountArray.length() > 0) {

						for (int i = 0; i < accountArray.length(); i++) {

							JSONObject itemJsonObject = accountArray.getJSONObject(i);
							House house = new House();
							house.fromJson(itemJsonObject);
							houseList.add(house);

						}
					}
					AddRecordModel.this.OnMessageResponse(url, object, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaId", areaId);
		params.put("page", page);
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}

	private void ajaxUploadImage(String filePath) {
		boolean isOk = false;
		AsyncHttpClient asyncClient = new AsyncHttpClient();
		RequestParams requestParams = new RequestParams();
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		String url = ProtocolConst.WEB_DIR + ProtocolConst.ADD_BUSINESS_RECORD;
		File pic = new File(filePath);
		Log.d("mao", "上传图片地址:" + pic.getAbsolutePath());
		if (!pic.exists()) {
			Log.d("mao", "文件不存在");
			return;
		}
		try {
			requestParams.put("file", pic);
			isOk = true;
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if (isOk) {
			requestParams.put("PHPSESSID", cacheInfo.getSessionId());
			requestParams.put("userId", cacheInfo.getUid());
			asyncClient.post(url, requestParams, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					// TODO 自动生成的方法存根
					String responseBody = null;
					try {
						responseBody = new String(arg2, "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					if (responseBody != null) {
						JSONObject jo = null;
						try {
							jo = new JSONObject(responseBody);
						} catch (JSONException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						if (jo != null) {
							int reply = jo.optInt("status");
							if (reply == 200) {
								Toast.makeText(mContext, "图片上传成功", Toast.LENGTH_SHORT).show();
								Log.d("mao", "修改资料返回信息:" + jo.toString());
							} else {
								Toast.makeText(mContext, jo.optString("msg"), Toast.LENGTH_SHORT).show();
							}

						}
					}
				}

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					// TODO 自动生成的方法存根
					Toast.makeText(mContext, "修改出错了哟，请重试~", Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			Toast.makeText(mContext, "图像路径出错", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 查看记录详情
	 * 
	 * @param businessRecordId
	 */
	public void getRecordDetail(int businessRecordId) {

		String url = ProtocolConst.GET_RECORD_DETAIL;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject object, AjaxStatus status) {
				try {
					if (object.optString("status").equals("200")) {
						record.fromJson(object.optJSONObject("entity"));
						AddRecordModel.this.OnMessageResponse(url, object, status);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("businessRecordId", businessRecordId);
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}

}
