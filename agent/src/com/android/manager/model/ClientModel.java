package com.android.manager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.provider.SyncStateContract.Constants;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.manager.protocol.ClientRecord;
import com.android.manager.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;

public class ClientModel extends BaseModel {

	
	List<ClientRecord> list=new ArrayList<ClientRecord>();
	private Context myContext;
	
	
	BeeCallback<JSONObject>  bcb=new BeeCallback<JSONObject>(){

		@Override
		public void callback(String url, JSONObject object, AjaxStatus status) {
			
			try {
				ClientModel.this.OnMessageResponse(url, object, status);
			} catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
		
	};
	
	
	public ClientModel(Context context) {
		super(context);
		myContext=context;
	}
	
	
	public void  ajaxGetClientRecord(int serverRecordId,String sessionid)
	{
		Map<String,String > params=new HashMap<String,String>();
		params.put("serveRecordId", serverRecordId+"");
		params.put("PHPSESSID", sessionid);
		this.bcb.url(ProtocolConst.GET_CLIENT_RECORD).type(JSONObject.class).params(params)
		.method(com.external.androidquery.util.Constants.METHOD_POST);
		MyProgressDialog dialog=new MyProgressDialog(myContext, "刷新中...");
		this.aq.progress(dialog.mDialog).ajax(bcb);
	}
}
