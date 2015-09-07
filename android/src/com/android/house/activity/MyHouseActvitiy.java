package com.android.house.activity;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.house.adapter.MyHouseSeenListAdapter;
import com.android.house.events.OnMyHouseActivityOk;
import com.android.house.model.GetMyHouseModel;
import com.android.house.protocol.CacheInfo;
import com.android.house.protocol.House;
import com.android.house.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.funmi.house.R;

public class MyHouseActvitiy extends BaseActivity implements IXListViewListener{
	private TextView title;

	private ImageView back;

	private XListView xlistView;
	private MyHouseSeenListAdapter adapter;

	private static final int SUCCESS = 0;
	private static final int UNDONE = 1;
	private static final int FAIL = 2;

	private GetMyHouseModel model;
	private CacheInfo cacheInfo;
	
	private boolean isPullToRefresh=false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		setContentView(R.layout.activity_myhouse);
		cacheInfo = UserInfoCacheUtil.getCacheInfo(this);
		EventBus.getDefault().register(this);
		initView();

	}

	private void initView() {
		title = (TextView) findViewById(R.id.title_text);
		title.setText("我的楼盘");

		back = (ImageView) findViewById(R.id.title_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		xlistView = (XListView) findViewById(R.id.my_house_seen_list);
		xlistView.setPullLoadEnable(false);
		xlistView.setRefreshTime();
		xlistView.setXListViewListener(this, 1);
		xlistView.setPullRefreshEnable(true);
		
	
		model = new GetMyHouseModel(this);
		model.addResponseListener(new MyHouseResponse());
		model.getMyHouse(cacheInfo);

	}

	/**
	 * 从服务器取到数据以后填充列表
	 */
	private void setContent() {
		if (model.houseList.size()==0) {
			Toast.makeText(MyHouseActvitiy.this, "还没有看过的楼盘哦", Toast.LENGTH_SHORT).show();
		}
		adapter = new MyHouseSeenListAdapter(this);
		adapter.bindData(model.houseList);
		if (isPullToRefresh) {
			xlistView.stopRefresh();
			Toast.makeText(MyHouseActvitiy.this, "刷新完成", Toast.LENGTH_LONG).show();
		}
		xlistView.setAdapter(adapter);
		xlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				Log.d("mao","map 键值对 postion="+position);
				Map<String,Integer> map =adapter.getIdMap();
				if(map.containsKey(String.valueOf(position)))
				{
					Log.d("mao","含有");
				}else
				{
					Log.d("mao","未含有");
				}
//				int status = adapter.getIdMap().get(String.valueOf(position-1));
				//都跳转到合同详情页面
				intent = new Intent(MyHouseActvitiy.this,
						MyHouseSuccessActivity.class);
				Log.d("huang", String.format("recordId = %d", model.houseList.get(position-1).getRecordId()));
				int recordId = model.houseList.get(position-1).getRecordId();
				intent.putExtra("recordId", recordId);
				startActivity(intent);
				
//				switch (status) {
//				case FAIL:
//					intent = new Intent(MyHouseActvitiy.this,
//							MyHouseFailActivity.class);
//					intent.putExtra("house_name", model.houseList.get(position-1).getHouse_name());
//					intent.putExtra("house_id",model.houseList.get(position-1).getHouse_id());
//					startActivity(intent);
//					break;
//
//				case UNDONE:
//					intent = new Intent(MyHouseActvitiy.this,
//							MyHouseUndoneActivity.class);
//					intent.putExtra("house_name", model.houseList.get(position-1).getHouse_name());
//					intent.putExtra("house_id",model.houseList.get(position-1).getHouse_id());
//					startActivity(intent);
//					break;
//
//				case SUCCESS:
//					intent = new Intent(MyHouseActvitiy.this,
//							MyHouseSuccessActivity.class);
//					intent.putExtra("house_name", model.houseList.get(position-1).getHouse_name());
//					intent.putExtra("house_id",model.houseList.get(position-1).getHouse_id());
//					startActivity(intent);
//					break;
//				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		EventBus.getDefault().post(new OnMyHouseActivityOk());
	}

	class MyHouseResponse implements BusinessResponse {

		@Override
		public void OnMessageResponse(String url, JSONObject jo,
				AjaxStatus status) throws JSONException {
			setContent();
			
		}

	}

	public void onEventMainThread(OnMyHouseActivityOk event) {

	}

	@Override
	public void onRefresh(int id) {
		model.getMyHouse(cacheInfo);
		isPullToRefresh=true;
	}

	@Override
	public void onLoadMore(int id) {
		
	}
}
