package com.android.house.adapter;

import java.util.ArrayList;
import java.util.List;

import com.funmi.house.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


public class SelectLocationDialogAdapter extends BaseAdapter{
	private Context mContext;
	
	private List<String> locateInfo = new ArrayList<String>();
	
	public SelectLocationDialogAdapter(Context context, List<String> locateInfo) {
		mContext = context;
		this.locateInfo = locateInfo;
	}

	@Override
	public int getCount() {
		return locateInfo.size();
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
		TextView text = null;
		
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select_location_list, null);
			
			text = (TextView)convertView.findViewById(R.id.select_location_list_text);
			text.setFocusable(false);
			
			convertView.setTag(text);
		}else{
			text = (TextView) convertView.getTag();
		}
		
		text.setText(locateInfo.get(position));
		
		return convertView;
	}
}
