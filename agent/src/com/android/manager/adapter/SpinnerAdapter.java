package com.android.manager.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.manager.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends BaseAdapter{
	private Context mContext;
	
	private List<String> data = new ArrayList<String>();
	
	public SpinnerAdapter(Context context,List<String> data) {
		mContext = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
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
		TextView tv = null;
		
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner, null);
			
			tv = (TextView)convertView.findViewById(R.id.item_spinner_text);
			convertView.setTag(tv);
		}else{
			tv = (TextView)convertView.getTag();
		}
		
		tv.setText(data.get(position));
		
		return convertView;
	}
}
