package com.android.house.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.User;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;


public class LoginModel extends BaseModel{

	
	public  String path="m/base/login";
	private Context myContext;
	public SharedPreferences shared;
	public SharedPreferences.Editor editor;
	private User user=new User();
	
	BeeCallback<JSONObject> cb=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
						Log.d("mao",object.toString());
					try {
						LoginModel.this.callback(url, object, status);
					} catch (Exception e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
						return ;
					}
					Log.d("mao","htpp 连接成功");
					try {
						int statusCode=object.optInt("status");
						if(statusCode==200)
						{
						JSONObject userJsonObject=object.optJSONObject("entities");
						
						user.fromJson(userJsonObject.optJSONObject("user"));
						editor.putInt("agent_id",user.agent_id);
						editor.putString("account_balance", user.account_balance+"");
						editor.putString("total_money", user.total_money+"");
						editor.putString("user_pic", "");
						editor.commit();
						}else if (object.optString("status").equals("300")) {
							Toast.makeText(mContext, object.optString("msg"), Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(mContext, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
						}
						LoginModel.this.OnMessageResponse(url, object, status);
					} catch (JSONException e) {
						Toast.makeText(mContext, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				}
				
			};
			
	public LoginModel(Context context) {
				super(context);
				myContext=context;
			}
	public void login(String tel,String pwd)
	{
		shared=myContext.getSharedPreferences("user",0);
		editor=shared.edit();
		Map<String, String> params=new HashMap<String ,String>();
		params.put("stype", 1+"");
		params.put("phone", tel);
		params.put("password", pwd);
		cb.url(path).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog=new MyProgressDialog(myContext, "登录中....");
		aq.progress(dialog.mDialog).ajax(cb);
	}
	
	
}
