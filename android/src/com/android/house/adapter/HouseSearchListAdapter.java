package com.android.house.adapter;

import java.util.ArrayList;

import com.android.house.component.HouseSearchListHolder;
import com.android.house.protocol.Agent;
import com.android.house.protocol.House;
import com.funmi.house.R;

import android.R.string;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class HouseSearchListAdapter extends BaseAdapter{
	private Context mContext;
	
	private ArrayList<Agent> agentList;
	public HouseSearchListAdapter(Context context) {
		mContext = context;
	}
	
	
	public void bindData(ArrayList<Agent> agentList){
		this.agentList=agentList;
	}
	@Override
	public int getCount() {
		if (agentList!=null&&agentList.size()!=0) {
			
			return agentList.size();
		}
		return 0;
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
		HouseSearchListHolder holder = null;
		
		if(convertView == null){
			holder = new HouseSearchListHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_list_house, null);
			
			holder.age = (TextView)convertView.findViewById(R.id.item_search_list_house_age);
			holder.car = (TextView)convertView.findViewById(R.id.item_search_list_house_car);
			holder.name = (TextView)convertView.findViewById(R.id.item_search_list_house_name);
			holder.from = (TextView)convertView.findViewById(R.id.item_search_list_house_from);
			holder.selling = (TextView)convertView.findViewById(R.id.item_search_list_house_selling);
			
			convertView.setTag(holder);
		}else{
			holder = (HouseSearchListHolder) convertView.getTag();
		}
		String age;
		String car;
		String from;
		String selling;
		Agent agent=agentList.get(position);
		String name=agent.getAgent_name();
		if(agent.getSale_long().equals("")){
			 age="销龄：未知"+"   ";
		}else{
			 age="销龄："+agent.getSale_long()+"   ";
		}
		if(agent.getCar_type().equals("")){
			car="专车：未知"+"   ";
		}else{
			car="专车："+agent.getCar_type()+"   ";
		}
		if(agent.getNative_place().equals("")){
			from="籍贯：未知"+"   ";
		}else{
			from="籍贯："+agent.getNative_place()+"   ";
		}
		if(agent.getSale_num().equals("")){
			selling="销售：未知"+"   ";
		}else{
			selling="销售："+agent.getSale_num()+"套"+"   ";
		}
		
		
		//String age="销龄："+agent.getSale_long();
		//String car="专车："+agent.getCar_type();
		//String from="籍贯："+agent.getNative_place();
		
		//String selling="销售："+agent.getSale_num()+"套";
		
		
		holder.setSearchListInfo(age,name,car,from,selling);
		
		return convertView;
	}
}
