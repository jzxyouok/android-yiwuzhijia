package com.android.manager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.ClientRecord;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.protocol.Record;
import com.android.manager.protocol.Score;
import com.android.manager.protocol.UserAccount;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class ServeRecordModel extends BaseModel {

	public ArrayList<ClientRecord> recordList = new ArrayList<ClientRecord>();
	public Score score = new Score();

	public ServeRecordModel(Context context) {
		super(context);

	}

	public void getServeRecordList(int serveRecordId) {

		String url = ProtocolConst.GET_RECORD_DETAIL;

		BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				ServeRecordModel.this.callback(url, jo, status);
				if (jo.optString("status").equals("200")) {

					try {
						JSONObject jsonObject = jo.optJSONObject("entity");
						JSONArray recordArray = jsonObject
								.optJSONArray("businessRecords");
						if (jsonObject.optJSONObject("score") != null) {

							score.fromJson(jsonObject.optJSONObject("score"));
						}
						recordList.clear();
						if (recordArray != null && recordArray.length() > 0) {

							for (int i = 0; i < recordArray.length(); i++) {

								JSONObject itemJsonObject = recordArray
										.getJSONObject(i);
								ClientRecord record = new ClientRecord();
								record.fromJSON(itemJsonObject);
								recordList.add(record);

							}
						}

						ServeRecordModel.this
								.OnMessageResponse(url, jo, status);
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		};

		CacheInfo cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serveRecordId", serveRecordId);
		bcb.cookie("PHPSESSID", cacheInfo.getSessionId());
		bcb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);

	}

}
