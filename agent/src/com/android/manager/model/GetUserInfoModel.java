package com.android.manager.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Path.Op;
import android.util.Log;
import android.widget.Toast;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.android.manager.protocol.Agent;
import com.android.manager.protocol.User;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

/*
 * 查看个人信息
 */
public class GetUserInfoModel extends BaseModel {

	private Context myContext;
	private String path = "m/base/getUserInfo";
	public User user;
	public Agent agent;

	public GetUserInfoModel(Context context) {
		super(context);
	}

	BeeCallback<JSONObject> bcb = new BeeCallback<JSONObject>() {

		@Override
		public void callback(String url, JSONObject object, AjaxStatus status) {

			Log.d("mao", object.toString());
			if (object.optString("status").equals("200")) {
				user = new User();
				agent = new Agent();
				try {
					try {
						agent.fromJson(object.optJSONObject("entity")
								.optJSONObject("profile"));

					} catch (Exception e) {
						e.printStackTrace();
					}

					user.fromJson(object.optJSONObject("entity").optJSONObject(
							"user"));

				} catch (JSONException e) {

					e.printStackTrace();
				}
				try {
					GetUserInfoModel.this
							.OnMessageResponse(url, object, status);
				} catch (JSONException e) {

					e.printStackTrace();
				}
			} else if (object.optString("status").equals("300")) {
				Toast.makeText(mContext, object.optString("msg"),
						Toast.LENGTH_SHORT).show();
			}

		}

	};

	public void getUserinfo(int uid, String sessionid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", uid + "");
		params.put("PHPSESSID", sessionid);
		bcb.cookie("PHPSESSID", sessionid);
		this.bcb.url(this.path).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		this.aq.ajax(this.bcb);
	}
}
