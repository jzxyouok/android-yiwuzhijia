package com.android.house.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.house.component.MyHouseSeenListHolder;
import com.android.house.protocol.House;
import com.funmi.house.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyHouseSeenListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<House> houseList;

	// idMap 用于存储View对应的状态
	private HashMap<String, Integer> idMap = new HashMap<String, Integer>();

	public MyHouseSeenListAdapter(Context context) {
		mContext = context;
	}

	public  void bindData(ArrayList<House> houseList){
		this.houseList = houseList;
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
		MyHouseSeenListHolder holder = null;

		if (convertView == null) {
			holder = new MyHouseSeenListHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_my_house_list, null);

			holder.name = (TextView) convertView
					.findViewById(R.id.my_house_list_name);
			holder.time = (TextView) convertView
					.findViewById(R.id.my_house_list_time);
			holder.position = (TextView) convertView
					.findViewById(R.id.my_house_list_pos);

			convertView.setTag(holder);
		} else {
			holder = (MyHouseSeenListHolder) convertView.getTag();
		}

		House house = houseList.get(position);
		Log.d("mao","添加的键名:"+String.valueOf(position));
		idMap.put(String.valueOf(position), house.getBusiness_status());
		String  dateStr=house.getCreate_time();
		if(dateStr.length()>10)
			dateStr=dateStr.substring(0, 10);
		holder.setSeenListInfo(house.getHouse_name(),
				house.getCreate_time().substring(0, 10),house.getHouse_address());
		return convertView;
	}

	public HashMap<String, Integer> getIdMap() {
		return idMap;
	}

}
