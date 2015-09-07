package com.android.manager.adapter;

import java.util.List;

import com.android.manager.R;
import com.android.manager.component.ClientEvaluationHolder;
import com.android.manager.component.CommentHolder;
import com.android.manager.component.InvalidClientHolder;
import com.android.manager.component.TrendingClientHolder;
import com.android.manager.protocol.ClientRecord;
import com.android.manager.protocol.Customer;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class InvalidClientAdapter extends BaseExpandableListAdapter {
	private Context mContext;

	private List<Customer> customerList;

	public InvalidClientAdapter(Context context) {
		mContext = context;
	}

	public void bindData(List<Customer> customerList) {
		this.customerList = customerList;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return customerList.size();
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
		// InvalidClientHolder holder = null;
		//
		// if(convertView == null){
		// holder = new InvalidClientHolder();
		// convertView =
		// LayoutInflater.from(mContext).inflate(R.layout.item_invalid_list,
		// null);
		//
		// holder.initHolder(convertView, R.id.invalid_client_add
		// , R.id.invalid_client_name, R.id.invalid_client_house
		// , R.id.invalid_client_phone,
		// R.id.invalid_client_img,R.id.invalid_client_add);
		//
		// convertView.setTag(holder);
		// }else{
		// holder = (InvalidClientHolder) convertView.getTag();
		// }
		//
		// Customer customer=customerList.get(groupPosition);
		// String name=customer.getUser_name();
		// String house="";
		// String phone=customer.getUser_phone();
		// String reason="暂缓买房计划";
		//
		// holder.setHolderInfo(name, house, phone, reason, null);
		TrendingClientHolder holder = null;

		if (convertView == null) {
			holder = new TrendingClientHolder(mContext);
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_trending_list, null);

			holder.initHolder(convertView, R.id.trending_client_add,
					R.id.trending_client_name, R.id.trending_client_house,
					R.id.trending_client_phone, R.id.trending_client_img);

			convertView.setTag(holder);
		} else {
			holder = (TrendingClientHolder) convertView.getTag();
		}
		final Customer customer = customerList.get(groupPosition);
		holder.setHolderInfo(customer.getUser_name(), customer.getIs_active(),
				customer.getUser_phone(), customer.getUser_head_pic());
		holder.setCustomer(customer);

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		CommentHolder commentHolder = null;
		ClientEvaluationHolder evaluateHolder = null;

		if (isLastChild) {
			evaluateHolder = new ClientEvaluationHolder();

			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.layout_list_footer, null);
			evaluateHolder.initHolder(convertView,
					R.id.invalid_evaluate_manager_text,
					R.id.invalid_dress_ratingbar_level,
					R.id.invalid_pick_up_ratingbar_level,
					R.id.invalid_evaluate_ratingbar_level,
					R.id.invalid_dress_ratingbar,
					R.id.invalid_pick_up_ratingbar,
					R.id.invalid_evaluate_ratingbar);
			// evaluateHolder.setHolder(evaluateText, dress, pickUp, evaluate,
			// dressLevel, pickUpLevel, evaluateLevel);
		} else {
			commentHolder = new CommentHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_comment, null);

			commentHolder.initHolder(convertView, R.id.comment_record_text,
					R.id.comment_record_time_text,
					R.id.comment_record_housename);

			convertView.setTag(commentHolder);

			// commentHolder.setHolderInfo(time, record);
		}

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
}
