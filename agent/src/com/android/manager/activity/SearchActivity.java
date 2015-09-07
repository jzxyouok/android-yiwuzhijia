package com.android.manager.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.adapter.SearchAdapter;
import com.android.manager.model.SearchMyCustomerModel;
import com.android.manager.protocol.Customer;
import com.android.manager.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;

public class SearchActivity extends BaseActivity implements BusinessResponse,OnItemClickListener,OnClickListener{
	private TextView title;
	
	private ImageView back;
	
	private ListView list;
	private SearchAdapter adapter;
	
	private SearchMyCustomerModel searchMyCustomerModel;
	
	private ImageView  searchImage;
	private EditText search;
	
	private ArrayList<Customer> customerList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		initView();
	}
	
	private void initView(){
		title = (TextView)findViewById(R.id.title_text);
		title.setText("搜索");
		
		back = (ImageView)findViewById(R.id.title_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		search=(EditText) findViewById(R.id.search_search);
		searchImage=(ImageView) findViewById(R.id.search_search_img);
		searchImage.setOnClickListener(this);
		
		list = (ListView)findViewById(R.id.search_list);
		searchMyCustomerModel=new SearchMyCustomerModel(this);
		searchMyCustomerModel.addResponseListener(this);
		adapter = new SearchAdapter(this);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.SEARCH_MY_CUSTOMER)) {
			if (searchMyCustomerModel.customerList.size()==0||searchMyCustomerModel.customerList==null) {
				Toast.makeText(SearchActivity.this, "没有相关的客户哦", Toast.LENGTH_LONG).show();
			}else {
			customerList=searchMyCustomerModel.customerList;
			adapter.bindData(searchMyCustomerModel.customerList);
			list.setAdapter(adapter);
			list.setOnItemClickListener(this);
			}
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_search_img:
			String searchStr= search.getText().toString();
			searchMyCustomerModel.getCustomerList(searchStr);
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Customer customer=customerList.get(position);
		Intent intent=new Intent(SearchActivity.this,ClientRecordActivity.class);
		Bundle bundle=new Bundle();
		bundle.putSerializable("customer", customer);
		intent.putExtras(bundle);
		startActivity(intent);
		
	}
}
