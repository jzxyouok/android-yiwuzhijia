package com.android.manager.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.manager.R;
import com.android.manager.component.ManageHouseHolder;
import com.android.manager.protocol.HouseManage;


public class ManageHouseListAdapter extends BaseAdapter{
	private Context mContext;
	
	private int type = 0;
	
	private ArrayList<HouseManage> houseList;
	
	public ManageHouseListAdapter(Context context) {
		mContext = context;
	}

	public void bindData(ArrayList<HouseManage> houseList){
		this.houseList=houseList;
	}
	
	public void bindData(ArrayList<HouseManage> houseList,int type){
		this.houseList=houseList;
		this.type = type;
	}
	
	@Override
	public int getCount() {
		return houseList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ManageHouseHolder holder = null;
		
		if(convertView == null){
			holder = new ManageHouseHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_check_house_list, null);
			
			holder.initHolder(convertView
					, R.id.check_house_name, R.id.check_house_money
					, R.id.check_house_state, R.id.check_house_end_time
					, R.id.check_house_sell_time);
			
			convertView.setTag(holder);
		}else{
			holder = (ManageHouseHolder) convertView.getTag();
		}
		
		
		HouseManage house=houseList.get(position);
		
		String name=house.getName();
		
		String money="";
		if(type == 2){
			money ="佣金：" +house.getSub_agent_payment() + "元";
		}else if(type == 3){
			money ="佣金：" + house.getAgent_payment() + "元";
		}
		
		String status="";
		String endTime="结束时间："+house.getSale_time_end().substring(0, 10);
		String sellTime="开始时间："+house.getSale_time_start().substring(0, 10);
		
		
		
		holder.setHolderInfo(name, money, status, endTime, sellTime);
		
		return convertView;
	}
}
