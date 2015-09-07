package com.android.manager.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.adapter.ManageHouseListAdapter;
import com.android.manager.model.HouseManageModel;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.House;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;

public class ManageHouseActivity extends BaseActivity implements BusinessResponse,OnItemClickListener{
	private TextView title;

	private ImageView back;
	
	private ListView list;
	private ManageHouseListAdapter adapter;
	
	private HouseManageModel houseManageModel;
	
	private CacheInfo cachInfo;
	
	private List<House> houseList = new ArrayList<House>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_house);
		
		initView();
	}
	
	private void initView() {
		title = (TextView)findViewById(R.id.title_text);
		title.setText("房源查看");
		
		back = (ImageView)findViewById(R.id.title_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		cachInfo=UserInfoCacheUtil.getCacheInfo(this);
		
		houseManageModel=new HouseManageModel(this);
		houseManageModel.addResponseListener(this);
		houseManageModel.getHouseList(1);
		
		list = (ListView)findViewById(R.id.check_house_list);
		
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.GET_AGENTLIST_BYGPS)) {
			adapter = new ManageHouseListAdapter(this);
			houseList = houseManageModel.houseList2;
			adapter.bindData(houseManageModel.houseList,cachInfo.getStype());
			list.setAdapter(adapter);
			list.setOnItemClickListener(this);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent=new Intent(ManageHouseActivity.this,HouseDetailActivity.class);
		Bundle bundle=new Bundle();
		bundle.putSerializable("house",this.houseList.get(position));
		intent.putExtras(bundle);
		startActivity(intent);
		
	}
}
