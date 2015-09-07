package com.android.manager.adapter;

import java.util.List;
import java.util.jar.Attributes.Name;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.BeeFramework.model.BaseModel;
import com.android.manager.R;
import com.android.manager.component.CommentHolder;
import com.android.manager.protocol.ClientRecord;
import com.android.manager.protocol.Record;

public class RecordListAdapter extends BaseAdapter{
	
	
	List<ClientRecord> list;
	LayoutInflater layoutInflater;
	public RecordListAdapter(List<ClientRecord> list,Context context) {
		this.list=list;
		layoutInflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		ClientRecord clientRecord = list.get(position);
		CommentHolder holder;
		if(convertView==null)
		{
			convertView = layoutInflater.inflate(
					R.layout.item_comment, null);
			holder=new CommentHolder();
			holder.initHolder(convertView, R.id.comment_record_text,
					R.id.comment_record_housename_text,R.id.comment_record_time_text);
			convertView.setTag(holder);
		}
		else {
			holder=(CommentHolder)convertView.getTag();
		}
		String time = clientRecord.getCreate_time().substring(0, 10);
		String record = clientRecord.getContent();
		String name=clientRecord.getHouse_name();
		holder.setHolderInfo(record, time,name);
		return convertView;
	}
	
}
