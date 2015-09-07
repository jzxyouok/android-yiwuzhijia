package com.android.house.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.house.model.UserModel;
import com.android.house.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.house.R;

public class SelectLocationActivity extends BaseActivity implements BusinessResponse{
	private TextView title;
	private TextView locating;
	
	private ImageView back;
	
	private ListView list;
	private ArrayAdapter<String> adapter;
	
	private List<String> locateInfo = new ArrayList<String>();
	
	private UserModel userModel;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_location);
		
		initView();
	}
	
	private void initView(){
		title = (TextView)findViewById(R.id.title_text);
		title.setText("所在地区");
		
		locating = (TextView)findViewById(R.id.select_location_locate);
		
		back = (ImageView)findViewById(R.id.title_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		list = (ListView)findViewById(R.id.select_location_list);
		
		userModel=new UserModel(this);
		userModel.addResponseListener(this);
		userModel.getCityList();
		locating.setText("成都");
		locating.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("city", "成都");
				intent.putExtra("city_id", 1);
				setResult(2222, intent);
				finish();
				
			}
		});
		
	}
	
	private void setContent(){
		
		for (int i = 0; i < userModel.cityList.size(); i++) {
			locateInfo.add(userModel.cityList.get(i).getCity_name());
		}
		
		adapter = new ArrayAdapter<String>(this, R.layout.item_select_location_list, locateInfo);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("city", locateInfo.get(position));
				intent.putExtra("city_id", userModel.cityList.get(position).getCity_id());
				setResult(2222, intent);
				finish();
			}
		});
	}
	
	

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.GET_CITY_LIST)) {
			setContent();
		}
		
	}
}
