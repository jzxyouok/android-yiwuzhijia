package com.android.house.adapter;

import java.util.ArrayList;
import java.util.List;

import com.funmi.house.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FilterAdapter extends BaseAdapter{
	private Context mContext;
	
	private List<String> list = new ArrayList<String>();
	
	public FilterAdapter(Context context,List<String> list) {
		mContext = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView text = null;
		
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_filter_spinner, null);
			
			text = (TextView)convertView.findViewById(R.id.filter_spinner_text);
			
			convertView.setTag(text);
		}else{
			text = (TextView) convertView.getTag();
		}
		
		text.setText(list.get(position));
		
		return convertView;
	}
}
