package com.android.manager.fragment;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import com.BeeFramework.fragment.BaseFragment;
import com.android.manager.R;
import com.android.manager.activity.ClientRecordActivity;
import com.android.manager.adapter.ImportantClientAdapter;
import com.android.manager.component.ImportantClientHolder;
import com.android.manager.component.TrendingClientHolder;
import com.android.manager.model.ClientModel;
import com.android.manager.model.ManageClientModel;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.Customer;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;

public class ImportantFragment extends BaseFragment implements
		OnGroupClickListener {

	public static final int mCurrentType = 3;
	private ExpandableListView list;

	private List<Customer> customerList;

	ImportantAdapter adapter;
	private Context mContext;
	private ManageClientModel model;
	private CacheInfo cacheInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_important, null);

		list = new ExpandableListView(this.getActivity());
		list = (ExpandableListView) view
				.findViewById(R.id.important_client_list);
		adapter = new ImportantAdapter();
		list.setOnGroupClickListener(this);
		return view;
	}

	public void initFragment(Context mContext) {
		this.mContext = mContext;
		model = new ManageClientModel(mContext);
		model.addResponseListener(this);
		cacheInfo = UserInfoCacheUtil.getCacheInfo(mContext);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		Log.d("mao", jo.toString());

		this.customerList = model.getCustomerLists();
		if (customerList.size() > 0) {
			this.list.setAdapter(adapter);
		} else {
			Toast.makeText(mContext, "暂无数据", Toast.LENGTH_SHORT).show();
		}
		/*
		 * adapter = new ImportantClientAdapter(this.getActivity());
		 * list.setAdapter(adapter); list.setOnItemClickListener(new
		 * OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) {
		 * 
		 * } });
		 */
	}

	@Override
	public void onResume() {
		super.onResume();
		model.ajaxGetCustomer(cacheInfo.getStype(), mCurrentType,
				cacheInfo.getSessionId());
	}

	class ImportantAdapter extends BaseExpandableListAdapter {
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
			ImportantClientHolder holder = null;

			if (convertView == null) {
				holder = new ImportantClientHolder(mContext);
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item_important_list, null);

				holder.initHolder(convertView, R.id.important_client_add,
						R.id.important_client_name,
						R.id.important_client_house,
						R.id.important_client_phone, R.id.important_client_img);

				convertView.setTag(holder);
			} else {
				holder = (ImportantClientHolder) convertView.getTag();
			}
			Customer customer = customerList.get(arg0);
			holder.setHolderInfo(customer.getUser_name(),
					customer.getIs_active(), customer.getUser_phone(),
					customer.getUser_head_pic());
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
		Customer customer = customerList.get(groupPosition);
		Intent intent = new Intent(getActivity(), ClientRecordActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("customer", customer);
		intent.putExtras(bundle);
		startActivity(intent);
		return true;
	}
}
