package com.android.manager.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.activity.MyClientActivity;
import com.android.manager.component.ClientEvaluationHolder;
import com.android.manager.component.CommentHolder;
import com.android.manager.component.TrendingManagerHolder;
import com.android.manager.model.ClientModel;
import com.android.manager.protocol.ClientRecord;
import com.android.manager.protocol.Customer;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;

public class TrendingManagerAdapter extends BaseExpandableListAdapter implements OnGroupClickListener{
	private Context mContext;
	private List<Customer> customerList;
	private Map<Customer,List<ClientRecord>> customerMaps;
	private Map<Integer,Integer> userId_posision_map=new HashMap<Integer, Integer>();
	
	
	private ExpandableListView view;
	
	ClientModel model;
	String sessionId;
	
	public TrendingManagerAdapter(Context context,List<Customer> list,ExpandableListView view
			,final Map<Customer,List<ClientRecord>> maps) {
		mContext = context;
		this.customerList=list;
		this.customerMaps=maps;
		model=new ClientModel(mContext);
		model.addResponseListener(new ClientResponse());
		sessionId=UserInfoCacheUtil.getCacheInfo(mContext).getSessionId();
		this.view=view;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return customerList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		Customer customer=customerList.get(groupPosition);
		return customerMaps.get(customer)==null?0:customerMaps.get(customer).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return customerList.get(groupPosition);
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
		TrendingManagerHolder holder = null;
		
		if(convertView == null){
			holder = new TrendingManagerHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_trending_manager_list, null);
			holder.initHolder(convertView
					, R.id.trending_manager_client_date, R.id.trending_manager_client_time
					, R.id.trending_manager_client_name, R.id.trending_manager_client_phone
					, R.id.trending_manager_client_house, R.id.trending_manager_client_house_money
					, R.id.trending_manager_client_manager_money,R.id.important_client_add);
			
			convertView.setTag(holder);
		}else{
			holder = (TrendingManagerHolder) convertView.getTag();
		}
		
		Customer customer=customerList.get(groupPosition);
		holder.setHolderInfo(customer.getCreate_time(), customer.getScore_time(), customer.getUser_name(), customer.getUser_phone(), customer.getAgent_name(), null, null);
		holder.currentPostion=groupPosition;
		holder.addRecord.setOnClickListener(new AddClientRecordListener(groupPosition));
		
//		holder.setHolderInfo(date, time, name, phone, houseName, housePrice, managerMoney);
		
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		CommentHolder commentHolder = null;
		ClientEvaluationHolder evaluateHolder = null;
		
		if(childPosition == 2){
			evaluateHolder = new ClientEvaluationHolder();
			
			convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_footer, null);
			evaluateHolder.initHolder(convertView
					, R.id.invalid_evaluate_manager_text, R.id.invalid_dress_ratingbar_level
					, R.id.invalid_pick_up_ratingbar_level, R.id.invalid_evaluate_ratingbar_level
					, R.id.invalid_dress_ratingbar, R.id.invalid_pick_up_ratingbar
					, R.id.invalid_evaluate_ratingbar);
//			evaluateHolder.setHolder(evaluateText, dress, pickUp, evaluate, dressLevel, pickUpLevel, evaluateLevel);
		}else{
			commentHolder = new CommentHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment, null);
			
			commentHolder.initHolder(convertView
					, R.id.comment_record_text, R.id.comment_record_time_text,R.id.comment_record_housename);
			
			convertView.setTag(commentHolder);
			
//			commentHolder.setHolderInfo(time, record);
		}
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2,
			long arg3) {
		Customer customer=customerList.get(arg2);
		List<ClientRecord> list=customerMaps.get(customer);
		Integer key=Integer.valueOf(customer.getUser_id());
		Integer value=Integer.valueOf(arg2);
		userId_posision_map.put(key, value);
		if(list==null ||list.size()==0)
		{
			Log.d("mao","group clicked +userid="+customer.getUser_id());
			this.model.ajaxGetClientRecord(customer.getId(), sessionId);
			return true;
		}else
		{
			
		}
		return false;
	}

	class ClientResponse implements BusinessResponse
	{
		@Override
		public void OnMessageResponse(String url, JSONObject jo,
				AjaxStatus status) throws JSONException {
			Log.d("mao",jo.toString());
			if(jo!=null)
			{
				JSONArray entities=jo.optJSONArray("entities");
				if(entities.length()==0)
				{
					Toast.makeText(mContext, "暂无陪同记录", Toast.LENGTH_SHORT).show();
				}else
				{
					//先拿到 Customer 
					JSONObject testObj=entities.optJSONObject(0);
					int user_id=testObj.optInt("user_id");
					Log.d("mao",user_id+"");
					if(userId_posision_map==null)
					{
						Log.d("mao","userid-position-map 为空");
					}
					Log.d("mao","是否有键:"+user_id+":"+userId_posision_map.containsKey(Integer.valueOf(user_id)));
					int position=userId_posision_map.get(Integer.valueOf(user_id));
					Log.d("mao", "position="+position);
					Customer target=customerList.get(position);
					final List<ClientRecord> customRecords=new ArrayList<ClientRecord>();
					for(int i=0;i<entities.length();i++)
					{
						ClientRecord record=new ClientRecord();
						record.fromJSON(entities.getJSONObject(i));
						customRecords.add(record);
					}
					customerMaps.put(target, customRecords);
					view.expandGroup(position);
				}
			}
			
		}
	}
		
	class AddClientRecordListener implements OnClickListener
	{
		
		int pos;
		
		public AddClientRecordListener(int position)
		{
			pos=position;
		}
		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent(mContext, MyClientActivity.class);
			Customer customer=customerList.get(pos);
			Bundle bundle=new Bundle();
			bundle.putSerializable("customer", customer);
			intent.putExtras(bundle);
			mContext.startActivity(intent);
		}
		
	}
}
