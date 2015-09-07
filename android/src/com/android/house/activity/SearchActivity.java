package com.android.house.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.house.adapter.HouseSearchListAdapter;
import com.android.house.adapter.ManagerSearchListAdapter;
import com.android.house.model.KeywordSearchModel;
import com.android.house.model.SearchHouseAndAgentModel;
import com.android.house.protocol.Agent;
import com.android.house.protocol.CacheInfo;
import com.android.house.protocol.House;
import com.android.house.protocol.ProtocolConst;
import com.android.house.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.house.R;

public class SearchActivity extends BaseActivity implements OnClickListener,BusinessResponse{
	private TextView searchConfirm;
	
	private EditText search;
	
	private ImageView back;
	
	private ListView searchList;

	private RelativeLayout searchManager;
	private RelativeLayout searchNewHouse;
	
	private HouseSearchListAdapter houseAdapter;
	private ManagerSearchListAdapter managerAdapter;
	
	private String searchStr;
	
	private SearchHouseAndAgentModel searchHouseAndAgentModel;
	
	private ArrayList<House> houseList;
	private ArrayList<Agent> agentList;
	
	private static Boolean isManager = false;
	
	private CacheInfo cachInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		initView();
		searchHouseAndAgentModel=new SearchHouseAndAgentModel(this);
		searchHouseAndAgentModel.addResponseListener(this);
	}
	
	private void initView(){
		searchManager = (RelativeLayout)findViewById(R.id.search_manager);
		searchNewHouse = (RelativeLayout)findViewById(R.id.search_new_house);
		
		searchConfirm = (TextView)findViewById(R.id.search_confirm);
		
		search = (EditText)findViewById(R.id.search_text);
		
		back = (ImageView)findViewById(R.id.search_back);
		
		searchList = (ListView)findViewById(R.id.search_list);
		houseAdapter = new HouseSearchListAdapter(this);
		managerAdapter = new ManagerSearchListAdapter(this);
		cachInfo=UserInfoCacheUtil.getCacheInfo(this);
		setClickListener();
		
	}

	private void setContent(){
		
		
		if(isManager)
		{
			searchList.setAdapter(houseAdapter);
			searchManager.setBackgroundColor(Color.WHITE);
			searchNewHouse.setBackgroundColor(getResources().getColor(R.color.bg_light_gray));
			
		}else{
			searchList.setAdapter(managerAdapter);
			searchNewHouse.setBackgroundColor(Color.WHITE);
			searchManager.setBackgroundColor(getResources().getColor(R.color.bg_light_gray));
		}
		
		searchList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(isManager){
					Intent in=new Intent(SearchActivity.this, ManagerDetailActivity.class);
					Bundle extra=new Bundle();
					extra.putSerializable("agent", agentList.get(position));
					extra.putSerializable("cacheInfo", cachInfo);
					in.putExtras(extra);
					startActivity(in);
				}else{
					
					Intent in=new Intent(SearchActivity.this, HouseDetailActivity.class);
					Bundle extra=new Bundle();
					extra.putSerializable("house", houseList.get(position));
					extra.putSerializable("cacheInfo", cachInfo);
					in.putExtras(extra);
					startActivity(in);
				}
			}
		});

	}
	private void setClickListener(){
		back.setOnClickListener(this);
		searchManager.setOnClickListener(this);
		searchNewHouse.setOnClickListener(this);
		searchConfirm.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.search_back:
			finish();
			break;
			
		case R.id.search_manager:
			isManager = true;
			searchManager.setBackgroundColor(Color.WHITE);
			searchNewHouse.setBackgroundColor(getResources().getColor(R.color.bg_light_gray));
             if (houseList!=null) {
				searchList.setAdapter(houseAdapter);
			}else {
				searchList.setAdapter(null);
			}
			break;
			
		case R.id.search_new_house:
			isManager = false;
			searchManager.setBackgroundColor(getResources().getColor(R.color.bg_light_gray));
			searchNewHouse.setBackgroundColor(Color.WHITE);
			
			if (agentList!=null) {
				searchList.setAdapter(managerAdapter);
			}else {
				searchList.setAdapter(null);
			}

			break;
			
		case R.id.search_confirm:
			searchStr = search.getText().toString();
			searchHouseAndAgentModel.getSearchList(searchStr);
			break;
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.SEARCH_HOUSE_AGENT)) {
			houseList=searchHouseAndAgentModel.houseList;
			agentList=searchHouseAndAgentModel.userList;
			
			managerAdapter.bindData(houseList);
			houseAdapter.bindData(agentList);
			setContent();
			
		}
		
	}
}
