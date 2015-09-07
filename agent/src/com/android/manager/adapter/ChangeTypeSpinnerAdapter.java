package com.android.manager.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.manager.R;

public class ChangeTypeSpinnerAdapter extends BaseAdapter {
	List<String> list=new ArrayList<String >();
	
	
	Context mContext;
	/*
	 *  1：预约
		2：意向
		3：重点
		4：成交
		5：失效

	 */
	public ChangeTypeSpinnerAdapter(Context context)
	{
		this.mContext=context;
		String appoint="预约客户";
		String trending="意向客户";
		String important="重点客户";
		String done="成交客户";
		String failure="失败客户";
		
		list.add(appoint);
		list.add(trending);
		list.add(important);
		list.add(done);
		list.add(failure);
		
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return this.list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO 自动生成的方法存根
		TextView tv=null;

		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner, null);
			
			tv = (TextView)convertView.findViewById(R.id.item_spinner_text);
			convertView.setTag(tv);
		}else{
			tv = (TextView)convertView.getTag();
		}
		
		tv.setText(this.list.get(position));
		
		return convertView;
	}
	
	
	
}
