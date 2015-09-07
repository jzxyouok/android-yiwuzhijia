package com.android.house.model;

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
import com.android.house.protocol.AgreementInfo;
import com.android.house.protocol.CacheInfo;
import com.android.house.protocol.ProtocolConst;
import com.android.house.protocol.UserAccount;
import com.android.house.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class AgreementInfoModel extends BaseModel {

	private Context mContext;
	public AgreementInfo agreementInfo = new AgreementInfo();
	
	public AgreementInfoModel(Context context) {
		super(context);
		mContext = context;
	}

	public void getAgreementInfoFormInternet(int recordId)
	{
		String url = ProtocolConst.GET_AGREEMENT_INFO;
		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					AgreementInfoModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
					return ;
				}
				if (jo.optString("status").equals("200")) {
					try {
						agreementInfo.fromJson(jo.optJSONObject("entity"));
						AgreementInfoModel.this.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				else if(jo.optString("status").equals("300")) {
					//jo.optString("msg")
					Toast.makeText(mContext, jo.optString("msg"),
							Toast.LENGTH_SHORT).show();
				}
			}
		};

		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, String> params = new HashMap<String, String>();
		params.put("businessRecordId", Integer.toString(recordId));
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}
	
	public AgreementInfo getAgreementInfo() {
		return agreementInfo;
	}

	public void setAgreementInfo(AgreementInfo agreementInfo) {
		this.agreementInfo = agreementInfo;
	}
	

}
