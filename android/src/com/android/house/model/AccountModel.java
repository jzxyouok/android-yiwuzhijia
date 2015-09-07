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
import com.android.house.protocol.CacheInfo;
import com.android.house.protocol.ProtocolConst;
import com.android.house.protocol.User;
import com.android.house.protocol.UserAccount;
import com.android.house.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

/**
 * 我的钱包相关
 * 
 * @author Administrator
 *
 */
public class AccountModel extends BaseModel {

	private Context mContext;
	public ArrayList<UserAccount> accountList = new ArrayList<UserAccount>();

	public AccountModel(Context context) {
		super(context);
		mContext = context;

	}

	/**
	 * 获取提现收入列表
	 */
	public void GetHistory() {

		String url = ProtocolConst.GETHISTORY;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					AccountModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
					return ;
				}
				if (jo.optString("status").equals("200")) {

					try {
						JSONArray accountArray = jo.optJSONArray("entities");
						accountList.clear();
						if (accountArray != null && accountArray.length() > 0) {

							for (int i = 0; i < accountArray.length(); i++) {

								JSONObject itemJsonObject = accountArray
										.getJSONObject(i);
								UserAccount account = new UserAccount();
								account.fromJson(itemJsonObject);
								accountList.add(account);

							}
						}
						AccountModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};

		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, String> params = new HashMap<String, String>();
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);

	}

	/**
	 * 提现请求
	 * 
	 * @param cash
	 *            金额
	 */
	public void askCash(double cash) {
		String url = ProtocolConst.ASK_WITHDRAWAL;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					AccountModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
					return ;
				}
				if (jo!=null) {
					try {
						
							
							AccountModel.this.OnMessageResponse(url, jo, status);
						
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		};

		Map<String, String> params = new HashMap<String, String>();
		
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		
		params.put("cash", cash+"");
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);

	}
	
	/**
	 * 绑定账户
	 * @param account 支付宝账户名称
	 */
	public void bindAccount(String account,String code){
		
		String url=ProtocolConst.BIND_ACCOUNT;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					AccountModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
					return ;
				}
				if (jo.optString("status").equals("200")) {
					try {
						AccountModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}else if (jo.optString("status").equals("300")) {
					Toast.makeText(mContext, jo.optString("msg"), Toast.LENGTH_LONG).show();
				}
			}
		};

		Map<String, String> params = new HashMap<String, String>();
		
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		params.put("account", account);
		params.put("code", code);
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);

	}
	

}
