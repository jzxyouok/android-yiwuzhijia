package com.android.house.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

/*
 * 获取经纪人评价列表
 */
public class AgentDetailModel extends BaseModel{

	
	
	private Context myContext;
	private String path="m/user/getAgentCommentList";
	
	
	BeeCallback<JSONObject> bcb=new BeeCallback<JSONObject>()
			{

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					
				
					
				}
				
			};
			
			
  public AgentDetailModel(Context context) {
		super(context);
		this.myContext=context;
	}
	
	public void  getAgentDetail(int agentid,String sessionid)
	{
		Map<String ,String> params=new HashMap<String, String>();
		params.put("agentId", agentid+"");
		params.put("PHPSESSID", sessionid);
		bcb.cookie("PHPSESSID", sessionid);
		this.bcb.url(this.path).params(params).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog=new MyProgressDialog(myContext, "信息加载中...");
		this.aq.progress(dialog.mDialog).ajax(this.bcb);
	}
}
