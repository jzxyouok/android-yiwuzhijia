package com.android.house.adapter;

import java.util.ArrayList;

import com.android.house.component.ManagerSearchListHolder;
import com.android.house.protocol.Agent;
import com.android.house.protocol.House;
import com.baidu.platform.comapi.map.s;
import com.funmi.house.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ManagerSearchListAdapter extends BaseAdapter{
	private Context mContext;
	public ArrayList<House> houseList;

	
	public ManagerSearchListAdapter(Context context) {
		mContext = context;
	}
	public void bindData(ArrayList<House> houseList){
		this.houseList=houseList;
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
		ManagerSearchListHolder holder = null;
		
		if(convertView == null){
			holder = new ManagerSearchListHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_list_manager, null);
			
			holder.name = (TextView)convertView.findViewById(R.id.item_search_list_manager_name);
			holder.position = (TextView)convertView.findViewById(R.id.item_search_list_manager_pos);
			
			convertView.setTag(holder);
		}else{
			holder = (ManagerSearchListHolder) convertView.getTag();
		}
		
		

		House  house=new House();
		house=houseList.get(position);
		String name=house.getName();
		String pos=house.getLocation_info()+house.getArea_name();
		
		
		
		holder.setManagerSearchInfo(name,pos);
		
		return convertView;
	}
}
