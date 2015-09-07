package com.android.house.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.house.protocol.User;
import com.baidu.location.w;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

public class ResetUserInfoModel extends BaseModel {
	
	
	private Context myContext;
	private String path="m/base/uploadPic";
	
	BeeCallback<JSONObject> bcv=new BeeCallback<JSONObject>(){

		@Override
		public void callback(String url, JSONObject object, AjaxStatus status) {
			Log.d("mao",object.toString());
		}
	};
	
	public ResetUserInfoModel(Context context) {
		super(context);
		this.myContext=context;
	}
	
	public  void resetUserInfo(User user)
	{
		
	}
	
	
	public void uploadImage(File file)
	{
		WorkThread workThread=new WorkThread(file);
		workThread.start();
	}
	
	public void asyncUploadImage(File file)
	{
		if(!file.exists())
		{
			Toast.makeText(myContext, "图片不存在", Toast.LENGTH_SHORT).show();
			return ;
		}
		bcv.url(path).param("picUpLoad",file).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog=new MyProgressDialog(myContext, "文件上传中");
		this.aq.progress(dialog.mDialog).ajax(this.bcv);
	}
	
	
	class WorkThread extends Thread
	{
		File file=null;
		StringBuffer sb=new StringBuffer();
		public WorkThread(File file)
		{
			this.file=file;
		}
			@Override
			public void run() {
					HttpPost request = new HttpPost("http://115.28.209.87:8074/yiwuzhijia/web/app_dev.php"+path);   
			        HttpClient httpClient = new DefaultHttpClient(); 
			        FileEntity entity;
			        HttpResponse response; 
			try { 
					Log.d("mao",request.toString());
					entity= new FileEntity(file,"multipart/form-data"); 
			        request.setEntity(entity); 
			        response=httpClient.execute(request);
			            if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
			            {
			            	Log.d("mao","上传成功");
			            	HttpEntity replyEntity=response.getEntity();
			            	InputStream is=replyEntity.getContent();
			            	byte buffer[]=new byte[1024];
			            	int i=-1;
			            	while(( i=is.read(buffer))!=-1)
			            	{
			            		this.sb.append(new String(buffer,"utf-8"));
			            	}
			            	Log.d("mao",sb.toString());
			            }
			            else
			            {
			            	Log.d("mao", response.getStatusLine().toString());
			            	HttpEntity replyEntity=response.getEntity();
			            	InputStream is=replyEntity.getContent();
			            	BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
			            	String str=null;
			            	while((str=bufferedReader.readLine())!=null)
			            	{
			            		Log.d("mao", str);
			            	}
			            }
			}catch(Exception e)
				{
					e.printStackTrace();
					Log.d("mao","网络出错");
				}
			finally
			{
				
			}
		}
	}
}
