package com.android.manager.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.protocol.RegistInfo;
import com.android.manager.protocol.User;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class RegistUserModel extends BaseModel{

	private Context myContext;
	public SharedPreferences shared;
	public SharedPreferences.Editor editor;
	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					{
						try {
							String statusCode=object.optString("status");
							if(statusCode.equals("200"))
							{
							JSONObject entity=object.optJSONObject("entities");
							User user=new User();
							user.fromJson(entity.optJSONObject("user"));
							editor.putInt("agent_id",user.agent_id);
							editor.putString("account_balance", user.account_balance+"");
							editor.putString("total_money", user.total_money+"");
							editor.commit();
							//Toast.makeText(mContext, "注册成功~", Toast.LENGTH_LONG).show();
							
							}else if(statusCode.equals("300")){
								//Toast.makeText(mContext, "验证码错误", Toast.LENGTH_LONG).show();
								
							}
							RegistUserModel.this.OnMessageResponse(url, object, status);
						} catch (JSONException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}
				}
				
			};
	public RegistUserModel(Context context) {
		super(context);
		this.myContext=context;
		
	}
	
	public void registUser(RegistInfo registInfo)
	{
		shared=myContext.getSharedPreferences("user",0);
		editor=shared.edit();
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("code", registInfo.getValidateCode());
		params.put("phone",registInfo.getPhone());
		params.put("password", registInfo.getPwd());
		params.put("stype", 2+"");
		params.put("sex",registInfo.getSex());
		params.put("cityId",registInfo.getCity_id());
		params.put("cityName",registInfo.getCityName());
		params.put("invitationCode", registInfo.getRegistcodecode());
		this.bcb.url(ProtocolConst.REGIST_USER).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		this.bcb.cookie("PHPSESSID", registInfo.getSessionId());
		MyProgressDialog dialog=new MyProgressDialog(this.myContext, "请稍等...");
		this.aq.progress(dialog.mDialog).ajax(this.bcb);
		
	}

	
	
	
	
	
	
}
