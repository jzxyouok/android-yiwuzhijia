package com.android.manager.adapter;

import java.util.ArrayList;

import com.android.manager.R;
import com.android.manager.activity.ClientRecordActivity;
import com.android.manager.component.SuccessClientHolder;
import com.android.manager.protocol.Customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

public class SearchAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Customer> customerList;
	
	
	public SearchAdapter(Context context) {
		mContext = context;
	}
	
	public void bindData(ArrayList<Customer> customerList){
		this.customerList=customerList;
	}
	@Override
	public int getCount() {
		return customerList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SuccessClientHolder holder = null;
		
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
		
		Customer customer=customerList.get(position);
		String date=customer.getCreate_time().substring(0, 10);
		String name=customer.getUser_name();
		String phone=customer.getUser_phone();
		String house=customer.getCreate_time().substring(11,16);
		int  is_active=customer.getIs_active();
		String img=customer.getUser_head_pic();
		
		holder.setHolderInfo(date, house, name, phone, is_active, "", "", img);
		holder.setCustomer(customer);
		return convertView;
	}

}
