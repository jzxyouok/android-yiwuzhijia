package com.android.house.adapter;

import com.funmi.house.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class WalletDetailListAdapter extends BaseAdapter{
	private Context mContext;
	
	public WalletDetailListAdapter(Context context) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.wallet_detail_list_item, null);
			
			holder.thing = (TextView)convertView.findViewById(R.id.wallet_thing);
			holder.money = (TextView)convertView.findViewById(R.id.wallet_money);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.thing.setText("≤‚ ‘ ˝æ›");
		holder.money.setText("500");
		
		return convertView;
	}
	
	private class ViewHolder{
		TextView thing;
		TextView money;
	}
}
