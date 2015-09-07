package com.android.manager.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.component.ClientEvaluationHolder;
import com.android.manager.component.CommentHolder;
import com.android.manager.component.TrendingClientHolder;
import com.android.manager.component.TrendingManagerHolder;
import com.android.manager.model.ServeRecordModel;
import com.android.manager.protocol.ClientRecord;
import com.android.manager.protocol.Customer;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.protocol.Record;
import com.external.androidquery.callback.AjaxStatus;

import android.R.integer;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnGroupClickListener;

public class TrendingClientAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private List<Customer> customerList;
	private ServeRecordModel serveRecordModel;
	public  List<List<ClientRecord>> recrodsList = new ArrayList<List<ClientRecord>>();

	public TrendingClientAdapter(Context context) {
		mContext = context;
	}

	public void bindDate(List<Customer> customerList) {
		this.customerList = customerList;
	}
	

	@Override
	public int getGroupCount() {

		return customerList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (recrodsList== null
				||recrodsList.size() <=groupPosition) {
			Toast.makeText(mContext, "正在加载中...", Toast.LENGTH_SHORT).show();
			return 0;
		}else {
			return recrodsList.get(groupPosition).size();
	}
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
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
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
        
		// holder.setHolderInfo(date, time, name, phone, houseName, housePrice,
		// managerMoney, img);	
//		if (recrodsList.size()<=groupPosition) {
//			serveRecordModel.getServeRecordList(customer.getId());				
//			
//		}
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
			
			CommentHolder commentHolder = null;
			ClientEvaluationHolder evaluateHolder = null;
			List<ClientRecord> list = recrodsList.get(groupPosition);
			ClientRecord clientRecord = list.get(childPosition);
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

				// evaluateHolder.setHolder(evaluateText, dress, pickUp,
				// evaluate, dressLevel, pickUpLevel, evaluateLevel);
			} else {
				
				if (commentHolder==null) {
				commentHolder = new CommentHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item_comment, null);
				
				commentHolder.initHolder(convertView, R.id.comment_record_text,
						R.id.comment_record_time_text,R.id.comment_record_housename);
				
				convertView.setTag(commentHolder);
				}else {
					commentHolder=(CommentHolder) convertView.getTag();
				}		
				String time = clientRecord.getCreate_time().substring(0, 10);
				String record = clientRecord.getContent();
//				commentHolder.setHolderInfo(record, time);
			}
			return convertView;
	}
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		
		return false;
	}

	
	

	

	

	
	
	

}
