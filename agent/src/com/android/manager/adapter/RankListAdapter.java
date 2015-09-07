package com.android.manager.adapter;

import java.util.ArrayList;

import com.android.manager.R;
import com.android.manager.component.ManageMoneylListHolder;
import com.android.manager.component.RankListHolder;
import com.android.manager.protocol.Agent;
import com.android.manager.protocol.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class RankListAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<Agent> rankList;
	public RankListAdapter(Context context) {
		mContext = context;
	}

	public void bindData(ArrayList<Agent> rankList){
		this.rankList=rankList;
	}
	@Override
	public int getCount() {
		return rankList.size();
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
		RankListHolder holder = null;
		
		if(convertView == null){
			holder = new RankListHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_rank_list, null);
			
			holder.initHolder(convertView, R.id.rank_list_pos, R.id.rank_list_name, R.id.rank_list_money);
			
			convertView.setTag(holder);
		}else{
			holder = (RankListHolder) convertView.getTag();
		}
		
		Agent agent=rankList.get(position);
		String name=agent.getAgent_name();
		double payment=agent.getTotal_payment();
		
		
		holder.setWalletInfo("第" + (position + 1) + "名：", name, "佣金："+payment+"元");
		
		return convertView;
	}
}
