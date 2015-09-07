package com.android.house.adapter;

import com.android.house.component.MyHouseFailHolder;
import com.funmi.house.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MyHouseFailListAdapter extends BaseAdapter{
	private Context mContext;
	
	public MyHouseFailListAdapter(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		return 3;
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
		MyHouseFailHolder holder = null;
		
		if(convertView == null){
			holder = new MyHouseFailHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_buyhouse_fail_list, null);
			
			holder.date = (TextView)convertView.findViewById(R.id.fail_buyhouse_date);
			holder.time = (TextView)convertView.findViewById(R.id.fail_buyhouse_time);
			holder.action = (TextView)convertView.findViewById(R.id.fail_buyhouse_action);
			holder.manager = (TextView)convertView.findViewById(R.id.fail_buyhouse_manager);
			
			convertView.setTag(holder);
		}else{
			holder = (MyHouseFailHolder) convertView.getTag();
		}
		
		holder.setFailListInfo("日期", "时间", "行为", "经纪人");
		
		return convertView;
	}
}
