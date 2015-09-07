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
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.BeeFramework.fragment.BaseFragment;
import com.android.manager.R;
import com.android.manager.activity.ClientRecordActivity;
import com.android.manager.adapter.SuccessClientAdapter;
import com.android.manager.adapter.TrendingManagerAdapter;
import com.android.manager.component.ImportantClientHolder;
import com.android.manager.component.SuccessClientHolder;
import com.android.manager.component.TrendingClientHolder;
import com.android.manager.model.ClientModel;
import com.android.manager.model.ManageClientModel;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.ClientRecord;
import com.android.manager.protocol.Customer;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;

public class SuccessFragment extends BaseFragment implements OnGroupClickListener{
	
	public static final int mCurrentType=4;
	
	private Context mContext;
	private ExpandableListView list;
	private ManageClientModel model;
	private List<Customer> customerList;
	private CacheInfo cacheInfo;
	private SuccessAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_success, null);
		
		list = (ExpandableListView)view.findViewById(R.id.success_client_list);
		adapter=new SuccessAdapter();
		list.setOnGroupClickListener(this);
		return view;
	}
	
	public void initFragment(Context mContext)
	{
		this.mContext=mContext;
		model=new ManageClientModel(mContext);
		model.addResponseListener(this);
		cacheInfo=UserInfoCacheUtil.getCacheInfo(mContext);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		Log.d("mao",jo.toString());
		Log.d("mao",jo.toString());
		this.customerList=model.getCustomerLists();
		if(customerList.size()>0)
		{
			this.list.setAdapter(adapter);
		}else
		{
			Toast.makeText(mContext, "暂无数据", Toast.LENGTH_SHORT).show();
		}
		
		/*
		 	adapter = new SuccessClientAdapter(getActivity(), customerList,list,subMap);
		if(customerList!=null&&customerList.size()!=0)
		{
			adapter=new SuccessClientAdapter(getActivity(), customerList,list,subMap);
			list.setAdapter(adapter);
			list.setOnGroupClickListener(adapter);
		}
		list.setAdapter(adapter);
		 */
	}
	
	@Override
	public void onResume() {
		super.onResume();
		model.ajaxGetCustomer(cacheInfo.getStype(),mCurrentType, cacheInfo.getSessionId());
	}


	class  SuccessAdapter extends BaseExpandableListAdapter
	{
		@Override
		public Object getChild(int arg0, int arg1) {
			// TODO 自动生成的方法存根
			return null;
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			// TODO 自动生成的方法存根
			return 0;
		}

		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {
			// TODO 自动生成的方法存根
			return null;
		}

		@Override
		public int getChildrenCount(int arg0) {
			// TODO 自动生成的方法存根
			return 0;
		}

		@Override
		public Object getGroup(int arg0) {
			// TODO 自动生成的方法存根
			return customerList.get(arg0);
		}

		@Override
		public int getGroupCount() {
			// TODO 自动生成的方法存根
			return customerList.size();
		}

		@Override
		public long getGroupId(int arg0) {
			// TODO 自动生成的方法存根
			return arg0;
		}

		@Override
		public View getGroupView(int arg0, boolean arg1, View convertView,
				ViewGroup arg3) {
			// TODO 自动生成的方法存根
			SuccessClientHolder  holder = null;
			
			if(convertView == null){
				holder = new SuccessClientHolder(mContext);
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_success_list, null);
				
				holder.initHolder(convertView
						, R.id.success_client_img, R.id.success_client_date
						, R.id.success_client_time, R.id.success_client_name
						, R.id.success_client_phone, R.id.success_client_house
						, R.id.success_client_house_money, R.id.success_client_manager_money);
				
				convertView.setTag(holder);
			}else{
				holder = (SuccessClientHolder) convertView.getTag();
			}
			Customer customer=customerList.get(arg0);
			holder.setHolderInfo(customer);
			holder.setCustomer(customer);
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			// TODO 自动生成的方法存根
			return false;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO 自动生成的方法存根
			return false;
		}
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


