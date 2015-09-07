package com.android.manager.adapter;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import cn.jpush.android.api.c;

import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.component.SeeClientHolder;
import com.android.manager.model.ReceiveCustomerModel;
import com.android.manager.protocol.Customer;
import com.android.manager.view.SeeDialog;
import com.android.manager.view.SeeDialog.OnDialogClickListener;
import com.baidu.location.f;
import com.external.androidquery.callback.AjaxStatus;
import com.external.stomp.Command;

public class SeeClientListAdapter extends BaseAdapter
		 {
	private Context mContext;
	private ArrayList<Customer> customerList;
	public int clickItem;

	

	public SeeClientListAdapter(Context context) {
		mContext = context;
	}

	public void bindDate(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}

	@Override
	public int getCount() {
		return customerList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SeeClientHolder holder = null;

		if (convertView == null) {
			holder = new SeeClientHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_see_client_list, null);

			holder.initHolder(convertView, R.id.see_client_sum,
					R.id.see_client_name, R.id.see_client_area,
					R.id.see_client_send_time, R.id.see_client_from,R.id.see_client_phone,
					R.id.see_client_confirm);

			convertView.setTag(holder);
		} else {
			holder = (SeeClientHolder) convertView.getTag();
		}
		

		final Customer customer = customerList.get(position);
		Log.d("mao","意向区域和总价："+customer.getHouse_price_prefer()+customer.getHouse_price_prefer());
		String sum = "意向总价："+customer.getHouse_price_prefer();
		String name = "客户："+customer.getUser_name();
		
		String area = "意向区域："+customer.getHouse_area_prefer();
		String customer_date=customer.getCreate_time();
		if(customer_date.length()>10);
			customer_date=customer_date.substring(0, 10);
		String time = "推送时间："+customer_date;
		int  is_active =customer.getIs_active(); 
		String phone="电话："+customer.getUser_phone();
		String from;
		if (is_active==0) {
			from="来源：平台推荐";
		}else {
			 from="来源：客户预约";
		}
		holder.setHolderInfo(sum, name, area, time, from,phone);

		
		           
		return convertView;
	}

	
}
