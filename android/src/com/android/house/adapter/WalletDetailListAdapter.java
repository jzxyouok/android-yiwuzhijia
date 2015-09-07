package com.android.house.adapter;

import java.util.ArrayList;

import com.android.house.component.WalletDetailListHolder;
import com.android.house.protocol.UserAccount;
import com.funmi.house.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class WalletDetailListAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<UserAccount> dataList;
	
	public WalletDetailListAdapter(Context context) {
		mContext = context;
	}

	public void bindData(ArrayList<UserAccount> dataList){
		this.dataList=dataList;
	}
	@Override
	public int getCount() {
		return dataList.size();
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
		WalletDetailListHolder holder = null;
		
		if(convertView == null){
			holder = new WalletDetailListHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_wallet_detail_list, null);
			
			holder.time = (TextView)convertView.findViewById(R.id.wallet_time);
			holder.thing = (TextView)convertView.findViewById(R.id.wallet_activity);
			holder.money = (TextView)convertView.findViewById(R.id.wallet_money);
			holder.status=(TextView)convertView.findViewById(R.id.wallet_status);
			convertView.setTag(holder);
		}else{
			holder = (WalletDetailListHolder) convertView.getTag();
		}
		
		UserAccount account=dataList.get(position);
		String time=account.operation_time.substring(0, 10);
		int thing=account.stype;
		String money=account.money+"元";
		String status=account.status_info;
		
		holder.setWalletInfo(time, thing, money,status);
		
		return convertView;
	}
}
