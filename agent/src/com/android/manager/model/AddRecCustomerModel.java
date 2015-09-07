package com.android.manager.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.manager.activity.AddClientActivity;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class AddRecCustomerModel extends BaseModel {

	public AddRecCustomerModel(Context context) {
		super(context);

	}

	public void addCustomer(String userName, String CityName, int userCityId,
			String phone, int sex, String preferPrice, String preferArea,
			String preferHouseType) {
		String url = ProtocolConst.ADD_REC_CUSTOMER;

		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				AddRecCustomerModel.this.callback(url, jo, status);
				if (jo.optString("status").equals("200")) {
					try {
						AddRecCustomerModel.this.OnMessageResponse(url, jo,
								status);
					} catch (JSONException e) {

						e.printStackTrace();
					}
				}else if (jo.optString("status").equals("300")) {
					Toast.makeText(mContext, jo.optString("msg"), Toast.LENGTH_LONG)
					.show();
				}

			}
		};
		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("userCityName", CityName);
		params.put("userCityId", userCityId);
		params.put("phone", phone);
		params.put("sex", sex);
		params.put("preferPrice", preferPrice);
		params.put("preferArea", preferArea);
		params.put("preferHouseType", preferHouseType);

		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "发送中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}

}
