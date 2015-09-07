package com.android.manager.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import com.BeeFramework.fragment.BaseFragment;
import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.activity.ClientRecordActivity;
import com.android.manager.adapter.TrendingClientAdapter;
import com.android.manager.adapter.TrendingManagerAdapter;
import com.android.manager.model.ManageClientModel;
import com.android.manager.model.ServeRecordModel;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.ClientRecord;
import com.android.manager.protocol.Customer;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;

public class TrendingFragment extends BaseFragment implements BusinessResponse,OnGroupClickListener{
	
	public static final int mCurrentType=2;
	
	private View view;
	
	private List<Customer> customerList;
	private Map<Customer,List<ClientRecord>> subMap=new HashMap<Customer, List<ClientRecord>>();
	private ExpandableListView list;

	private TrendingClientAdapter trendingClientAapter = null;//业内
	private TrendingManagerAdapter trendingManagerAdapter = null;//经纪人
		
	private Context mContext;
	ManageClientModel model;
	CacheInfo cacheInfo;
	LayoutInflater layoutInflater;
	
	private ServeRecordModel serveRecordModel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d("mao","fragment onCreateView");
		view = inflater.inflate(R.layout.fragment_trending, null);
		list = (ExpandableListView)view.findViewById(R.id.trending_client_list);
		trendingClientAapter=new TrendingClientAdapter(getActivity());
		
		return view;
	}
	
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		
		if (url.endsWith(ProtocolConst.GET_MY_CUSTOMER_LIST)) {
			
			Log.d("mao",jo.toString());
			this.customerList=model.getCustomerLists();
			if(customerList.size()>0)
			{
				trendingClientAapter.bindDate(customerList);
				list.setAdapter(trendingClientAapter);
				list.setOnGroupClickListener(this);
			}else
			{
				Toast.makeText(mContext, "暂无数据", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		model.ajaxGetCustomer(cacheInfo.getStype(),mCurrentType, cacheInfo.getSessionId());
	}

	public void initFragment(Context mContext)
	{
		this.mContext=mContext;
		model=new ManageClientModel(mContext);
		model.addResponseListener(this);
		serveRecordModel=new ServeRecordModel(mContext);
		serveRecordModel.addResponseListener(this);
		
		cacheInfo=UserInfoCacheUtil.getCacheInfo(mContext);
		layoutInflater=LayoutInflater.from(mContext);
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		
		Customer customer=customerList.get(groupPosition);
		Intent intent=new Intent(getActivity(),ClientRecordActivity.class);
		Bundle bundle=new Bundle();
		bundle.putSerializable("customer", customer);
		intent.putExtras(bundle);
		startActivity(intent);
		return true;
	}

	

}
