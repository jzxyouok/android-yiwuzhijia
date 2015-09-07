package com.android.house.component;

import java.util.ArrayList;
import java.util.List;

import com.android.house.adapter.SpinnerAdapter;
import com.android.house.events.OnFilterItemClickedEvent;
import com.android.house.model.HouseModel;
import com.android.house.model.HouseModel.SEARCH_TYPE;
import com.external.eventbus.EventBus;
import com.funmi.house.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class SpinnerHolder {
	private Context mContext;
	
	private View view;
	private ListView list;
	private SpinnerAdapter innerAdapter;

	private SpinnerHolderListener listener;
	
	private List<String> data ;
	
	public interface SpinnerHolderListener
	{
		public void onItemClickListener(int position,String name);
	}
	
	public void setAdapter(SpinnerAdapter adpter)
	{
		this.innerAdapter= adpter;
	}
	
	public void setListener(SpinnerHolderListener listener)
	{
		this.listener=listener;
	}
	
	public SpinnerHolder(Context context,List<String> data) {
		mContext = context;
		this.data = data;
		
		initView();
	}
	
	private void initView(){
		view = LayoutInflater.from(mContext).inflate(R.layout.layout_spinner, null);
		
		list = (ListView)view.findViewById(R.id.spinner_list);
		SpinnerAdapter adpter1=new SpinnerAdapter(mContext,data);
		if(innerAdapter==null)
		{
			list.setAdapter(adpter1);
		}
		else
			list.setAdapter(innerAdapter);
	}
	
	public List<String> getData(){
		return data;
	}
	
	public ListView getList(){
		return list;
	}

	public View getView(){
		return view;
	}
}
