package com.android.manager.component;

import java.util.ArrayList;
import java.util.List;

import com.android.manager.R;
import com.android.manager.adapter.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class SpinnerHolder implements OnItemClickListener{
	private Context mContext;
	private int type;
	private View view;
	private ListView list;
	private SpinnerAdapter adapter;

	private SpinnerHolderListener listener;
	
	private String selectedStr = null;
	private List<String> data = new ArrayList<String>();
	
	public interface SpinnerHolderListener
	{
		public void onItemClickListener(int position,String name,int type);
		public void onItemClickListener();
	}
	
	public void setAdapter(SpinnerAdapter adpter)
	{
		this.adapter= adpter;
	}
	
	public void setType(int type)
	{
		this.type=type;
	}
	
	public SpinnerHolder(Context context,List<String> data,SpinnerHolderListener listner) {
		mContext = context;
		this.data = data;
		this.listener=listner;
		initView();
	}
	
	private void initView(){
		view = LayoutInflater.from(mContext).inflate(R.layout.layout_spinner, null);
		
		list = (ListView)view.findViewById(R.id.spinner_list);
		SpinnerAdapter adpter1=new SpinnerAdapter(mContext,data);
		
		if(adapter==null)
		{
			list.setAdapter(adpter1);
		}
		else{
			list.setAdapter(adapter);
		}
		list.setOnItemClickListener(this);
	}
	
	public ListView getList(){
		return list;
	}
	
	public List<String> getData(){
		return data;
	}
	
	public View getView(){
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(listener!=null)
		{
			listener.onItemClickListener(arg2, arg0.getItemAtPosition(arg2).toString(),this.type);
		}
		
	}
}
