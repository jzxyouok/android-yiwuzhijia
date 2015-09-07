package com.android.house.adapter;

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
		ViewHolder holder = null;
		
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.buyhouse_fail_list_item, null);
			
			holder.date = (TextView)convertView.findViewById(R.id.fail_buyhouse_date);
			holder.time = (TextView)convertView.findViewById(R.id.fail_buyhouse_time);
			holder.action = (TextView)convertView.findViewById(R.id.fail_buyhouse_action);
			holder.manager = (TextView)convertView.findViewById(R.id.fail_buyhouse_manager);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.date.setText("测试日期");
		holder.time.setText("测试时间");
		holder.action.setText("测试行为");
		holder.manager.setText("测试经纪人");
		
		return convertView;
	}
	
	private class ViewHolder{
		TextView date;
		TextView time;
		TextView action;
		TextView manager;
	}
}
