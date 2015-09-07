package com.android.manager.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.adapter.SeeClientListAdapter;
import com.android.manager.model.ReceiveCustomerModel;
import com.android.manager.protocol.Customer;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.view.SeeDialog;
import com.android.manager.view.SeeDialog.OnDialogClickListener;
import com.external.androidquery.callback.AjaxStatus;

public class SeeClientActivity extends BaseActivity implements BusinessResponse,OnItemClickListener{
	private TextView title;
	
	private ImageView back;
	
	private ListView list;
	private SeeClientListAdapter adapter;
	
	private ReceiveCustomerModel receiveCustomerMode;
	
	public ArrayList<Customer> customerList;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_see_client);
		
		initView();
	}
	
	public void initView(){
		title = (TextView)findViewById(R.id.title_text);
		title.setText("接待客户");
		
		back = (ImageView)findViewById(R.id.title_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		list = (ListView)findViewById(R.id.see_client_list);
		
		receiveCustomerMode=new ReceiveCustomerModel(this);
		receiveCustomerMode.addResponseListener(this);
		
		
	}
	@Override
	protected void onResume() {
		
		receiveCustomerMode.getCustomerList(3, 1);
		super.onResume();
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.GET_MY_CUSTOMER_LIST)) {
			adapter = new SeeClientListAdapter(this);
			customerList=receiveCustomerMode.customerList;
			adapter.bindDate(customerList);
			list.setAdapter(adapter);
			list.setOnItemClickListener(this);
			
		}else if (url.endsWith(ProtocolConst.DO_RECEIVECUSTOMER)) {
			Toast.makeText(SeeClientActivity.this, jo.optString("msg")+"", Toast.LENGTH_LONG).show();
			receiveCustomerMode.getCustomerList(3, 1);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position,
			long id) {
		final SeeDialog dialog = new SeeDialog(SeeClientActivity.this, R.style.customTheme);
		dialog.show();
		dialog.setClickListener(new OnDialogClickListener() {

			@Override
			public void onPositiveButtonClick(int i) {
				
				receiveCustomerMode.doReceiveCustomer(customerList.get(position).getId(), 2);
				dialog.dismiss();
			}
			@Override
			public void onGiveUpButtonClick() {
				
				receiveCustomerMode.doReceiveCustomer(customerList.get(position).getId(), 6);
				dialog.dismiss();
			}
		});
	
		
	}

	

	
}
