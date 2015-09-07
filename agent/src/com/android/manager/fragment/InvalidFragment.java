package com.android.manager.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnGroupClickListener;
import com.BeeFramework.fragment.BaseFragment;
import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.activity.ClientRecordActivity;
import com.android.manager.adapter.InvalidClientAdapter;
import com.android.manager.adapter.TrendingClientAdapter;
import com.android.manager.adapter.TrendingManagerAdapter;
import com.android.manager.model.ClientModel;
import com.android.manager.model.ManageClientModel;
import com.android.manager.model.ServeRecordModel;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.ClientRecord;
import com.android.manager.protocol.Customer;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.UserInfoCacheUtil;
import com.external.androidquery.callback.AjaxStatus;

public class InvalidFragment extends BaseFragment implements BusinessResponse,OnGroupClickListener{
	
	public static final int mCurrentType=5;
	private List<Customer> customerList;
	private View view;
	private Map<Customer,List<ClientRecord>> subMap=new HashMap<Customer, List<ClientRecord>>();
	private ExpandableListView list;

	
	private InvalidClientAdapter adapter;
	
	private Context mContext;
	private ManageClientModel model;
	private CacheInfo cacheInfo;
	LayoutInflater layoutInflater;
	private ServeRecordModel serveRecordModel;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_invalid, null);
		
		initView(view);
		
		return view;
	}
	
	@SuppressLint("NewApi")
	private void initView(View root){
		list = (ExpandableListView)root.findViewById(R.id.invalid_client_list);
		adapter = new InvalidClientAdapter(this.getActivity());
		
	}
	
//	/**
//	 * 设置经纪人评价信息
//	 * @param evaluate 经纪人评价
//	 */
//	private void setManagerEvaluation(String evaluate){
//		evaluateManager.setText(evaluate);
//	}
	
//	/**
//	 * 设置评分信息
//	 * @param dressLevel 着装整洁
//	 * @param pickupLevel 接送服务
//	 * @param evaluateHouseLevel 房屋评估服务
//	 */
//	private void setRatingLevel(int dressLevel,int pickupLevel,int evaluateHouseLevel){
//		this.dressLevel.setText(dressLevel);
//		this.pickupLevel.setText(pickupLevel);
//		this.evaluateLevel.setText(evaluateHouseLevel);
//		
//		this.dressRatingbar.setNumStars(dressLevel);
//		this.pickupRatingbar.setNumStars(pickupLevel);
//		this.evaluateHouseRatingbar.setNumStars(evaluateHouseLevel);
//	}


	
	@Override
	public void onResume() {
		super.onResume();
		model.ajaxGetCustomer(cacheInfo.getStype(),mCurrentType, cacheInfo.getSessionId());
	}
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		Log.d("mao","InvalidFragment  response");
		this.customerList=model.getCustomerLists();
		adapter.bindData(model.lists);
		list.setAdapter(adapter);
		list.setOnGroupClickListener(this);
	}


	public void initFragment(Context mContext)
	{
		this.mContext=mContext;
		model=new ManageClientModel(mContext);
		model.addResponseListener(this);
		serveRecordModel=new ServeRecordModel(mContext);
		serveRecordModel.addResponseListener(this);
		cacheInfo=UserInfoCacheUtil.getCacheInfo(mContext);
		layoutInflater=LayoutInflater.from(mContext);
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		Customer customer=customerList.get(groupPosition);
		Intent intent=new Intent(getActivity(),ClientRecordActivity.class);
		Bundle bundle=new Bundle();
		bundle.putSerializable("customer", customer);
		intent.putExtras(bundle);
		startActivity(intent);
		/*Intent intent=new Intent(getActivity(),ClientRecordActivity.class);
		startActivity(intent);*/
		return true;
	}

	
}
