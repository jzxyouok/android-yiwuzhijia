package com.android.manager.adapter;

import com.android.manager.R;
import com.android.manager.component.CommentHolder;
import com.android.manager.component.ImportantClientHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;


public class ImportantClientAdapter extends BaseExpandableListAdapter{
	private Context mContext;

	public ImportantClientAdapter(Context context) {
		mContext = context;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ImportantClientHolder holder = null;
		
		if(convertView == null){
			holder = new ImportantClientHolder(mContext);
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_important_list, null);
			
			holder.initHolder(convertView
					, R.id.important_client_add, R.id.important_client_name
					, R.id.important_client_house, R.id.important_client_phone
					, R.id.important_client_img);
			
			convertView.setTag(holder);
		}else{
			holder = (ImportantClientHolder) convertView.getTag();
		}
		
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		CommentHolder holder = null;
		
		if(convertView == null){
			holder = new CommentHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment, null);
			
			holder.initHolder(convertView
					, R.id.comment_record_text, R.id.comment_record_time_text,R.id.comment_record_housename);
			
			convertView.setTag(holder);
		}else{
			holder = (CommentHolder) convertView.getTag();
		}
		
//		holder.setHolderInfo(time, record);
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
}
