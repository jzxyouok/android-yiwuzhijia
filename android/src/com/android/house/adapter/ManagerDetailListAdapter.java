package com.android.house.adapter;

import java.util.ArrayList;

import com.android.house.component.ManagerDetailListHolder;
import com.android.house.protocol.AGENTCOMMENT;
import com.baidu.android.bbalbs.common.a.c;
import com.funmi.house.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;


public class ManagerDetailListAdapter extends BaseAdapter{
	private Context mContext;
	public ArrayList<AGENTCOMMENT> dataList;
	public boolean isEmpty()
	{
		return dataList.size()==0;
	}
	public ManagerDetailListAdapter(Context context) {
		mContext = context;
	}

	public void bindData(ArrayList<AGENTCOMMENT> dataList){
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
		ManagerDetailListHolder holder = null;
		
		if(convertView == null){
			holder = new ManagerDetailListHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_manager_detail_evaluate_list, null);
			
			holder.time = (TextView)convertView.findViewById(R.id.item_manager_detail_time);
			holder.phone = (TextView)convertView.findViewById(R.id.item_manager_detail_phone);
			holder.house = (TextView)convertView.findViewById(R.id.item_manager_detail_house);
			holder.customer = (TextView)convertView.findViewById(R.id.item_manager_detail_customer);
			holder.evaluate = (TextView)convertView.findViewById(R.id.item_manager_detail_evaluate);
			
			holder.evaluateRate = (RatingBar)convertView.findViewById(R.id.item_manager_detail_rating);
			
			convertView.setTag(holder);
		}else{
			holder = (ManagerDetailListHolder) convertView.getTag();
		}
		
		AGENTCOMMENT comment=dataList.get(position);
		String time;
		if (comment.getCreate_time().length()>0) {
			 time=comment.getCreate_time().substring(0,10)+" "+comment.getCreate_time().substring(11,19);
			
		}else {
			time=comment.getCreate_time();
		}
		String phone=comment.getUser_phone().substring(7,11);
		
		holder.setDetail(time ,phone, "", "", comment.getContent(), comment.getAvg_score());
		
		return convertView;
	}
}
