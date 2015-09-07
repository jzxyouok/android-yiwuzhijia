package com.android.manager.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.adapter.RecordListAdapter;
import com.android.manager.model.ServeRecordModel;
import com.android.manager.protocol.ClientRecord;
import com.android.manager.protocol.Customer;
import com.external.androidquery.callback.AjaxStatus;

public class ClientRecordActivity extends Activity implements BusinessResponse ,OnItemClickListener{
	View footer;
	ListView list;
	RatingBar bar1;
	RatingBar bar2;
	RatingBar bar3;
	TextView bar1rat;
	TextView bar2rat;
	TextView bar3rat;
	TextView content;
	ServeRecordModel serveRecordModel;
	Customer customer;
	private ImageView titleBack;
	private TextView titleText;
	private List<ClientRecord> clientList = new ArrayList<ClientRecord>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_record);
		customer = (Customer) getIntent().getExtras().getSerializable(
				"customer");
		initView();
		serveRecordModel = new ServeRecordModel(this);
		serveRecordModel.addResponseListener(this);
	}

	private void initView() {
		footer = LayoutInflater.from(ClientRecordActivity.this).inflate(R.layout.layout_list_footer, null);
		list = (ListView) findViewById(R.id.activity_record_list);
		bar1 = (RatingBar) footer.findViewById(R.id.invalid_pick_up_ratingbar);
		bar2 = (RatingBar) footer.findViewById(R.id.invalid_evaluate_ratingbar);
		bar3 = (RatingBar) footer.findViewById(R.id.invalid_dress_ratingbar);
		titleBack = (ImageView) findViewById(R.id.title_back);
		titleText = (TextView) findViewById(R.id.title_text);
		bar1rat=(TextView) footer.findViewById(R.id.invalid_pick_up_ratingbar_level);
		bar2rat=(TextView) footer.findViewById(R.id.invalid_evaluate_ratingbar_level);
		bar3rat=(TextView) footer.findViewById(R.id.invalid_dress_ratingbar_level);
		content=(TextView) footer.findViewById(R.id.invalid_evaluate_manager_text);
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		titleText.setText("陪同记录");
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		serveRecordModel.getServeRecordList(customer.getId());
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (serveRecordModel.recordList.size()==0) {
			Toast.makeText(ClientRecordActivity.this, "暂无陪看记录", Toast.LENGTH_LONG).show();
		}
		RecordListAdapter adapter = new RecordListAdapter(
				serveRecordModel.recordList, this);
		clientList = serveRecordModel.recordList;
		this.list.addFooterView(footer);
		this.list.setAdapter(adapter);
		this.list.setOnItemClickListener(this);
		if (serveRecordModel.score==null||serveRecordModel.score.getId()==0) {
//			footer.setVisibility(View.GONE);
			list.removeFooterView(footer);
		}else {
			
			bar1.setRating(serveRecordModel.score.getCar());
			bar2.setRating(serveRecordModel.score.getSuggest());
			bar3.setRating(serveRecordModel.score.getDress());
			bar1rat.setText(serveRecordModel.score.getCar()+"分");
			bar2rat.setText(serveRecordModel.score.getSuggest()+"分");
			bar3rat.setText(serveRecordModel.score.getDress()+"分");
			content.setText(serveRecordModel.score.getContent());
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(ClientRecordActivity.this,RecordDetailActivity.class);
		Bundle bundle=new Bundle();
		bundle.putSerializable("customer", customer);
		bundle.putInt("id", clientList.get(position).getId());
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
