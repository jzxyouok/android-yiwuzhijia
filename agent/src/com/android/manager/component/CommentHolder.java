package com.android.manager.component;

import org.w3c.dom.Text;

import android.view.View;
import android.widget.TextView;

public class CommentHolder {
	private TextView time;
	private TextView record;
	private TextView name;
	
	/**
	 * 
	 * @param view 子View
	 * @param time 陪同时间
	 * @param record 陪同记录
	 */
	public void initHolder(View view,int time,int record, int name){
		this.time = (TextView)view.findViewById(time);
		this.record = (TextView)view.findViewById(record);
		this.name=(TextView) view.findViewById(name);
	}
	
	/**
	 * 
	 * @param view 子View
	 * @param time 陪同时间
	 * @param record 陪同记录
	 */
	public void setHolderInfo(String time,String record,String name){
		this.time.setText(time);
		this.record.setText(record);
		this.name.setText(name);
	}
}
