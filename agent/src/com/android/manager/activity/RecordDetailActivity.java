package com.android.manager.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.wifi.WifiConfiguration.Protocol;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.WebImageView;
import com.android.manager.R;
import com.android.manager.costants.AppConstants;
import com.android.manager.model.AddRecordModel;
import com.android.manager.protocol.Customer;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.JSONUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;

public class RecordDetailActivity extends BaseActivity implements BusinessResponse{

	private View header;
	private TextView topTitle;
	private ImageView topBack;
	private XListView mListView;

	private TextView name;
	private TextView sex;
	private TextView phone;
	private TextView type;
	private WebImageView img;
	private TextView record;

	private TextView recordArea;
	private TextView recordHouse;
	private TextView recordContent;
	private WebImageView recordImg;
	private TextView recordState;
	private Customer customer;
	
	private AddRecordModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_client);

		initView();
	}

	private void initView() {

		customer = (Customer) getIntent().getExtras().getSerializable("customer");
		mListView = (XListView) findViewById(R.id.my_client_list);
		topTitle = (TextView) findViewById(R.id.title_text);
		topBack = (ImageView) findViewById(R.id.title_back);
		header = LayoutInflater.from(this).inflate(R.layout.activity_record_detail, null);

		name = (TextView) header.findViewById(R.id.my_client_name);
		sex = (TextView) header.findViewById(R.id.my_client_sex);
		phone = (TextView) header.findViewById(R.id.my_client_phone);
		type = (TextView) header.findViewById(R.id.my_client_kind_spinner_text);
		img = (WebImageView) header.findViewById(R.id.my_client_img);

		recordArea = (TextView) header.findViewById(R.id.my_client_select_area_spinner_text);
		recordHouse = (TextView) header.findViewById(R.id.my_client_select_house_spinner_text);
		recordState = (TextView) header.findViewById(R.id.my_client_select_state_spinner_text);
		recordContent = (TextView) header.findViewById(R.id.my_client_fill_record);
		recordImg = (WebImageView) header.findViewById(R.id.record_pic);
		record = (TextView) header.findViewById(R.id.record);
		model =new AddRecordModel(this);
		model.addResponseListener(this);
		model.getRecordDetail(getIntent().getIntExtra("id", 0));
		header.setClickable(false);
		header.setFocusable(false);
		mListView.addHeaderView(header);
		mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(false);
		mListView.setClickable(false);
		mListView.setAdapter(null);
		
		topTitle.setText("看房记录");
		topBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		name.setText(customer.getUser_name());
		phone.setText(customer.getUser_phone());
//		if (customer.getRelation_status() == 2) {
//			type.setText("意向");
//		}
//		if (customer.getRelation_status() == 3) {
//			type.setText("重要");
//		}
//		if (customer.getRelation_status() == 5) {
//			type.setText("失效");
//		}
		img.setImageWithURL(this, AppConstants.WEBHOME + JSONUtil.getImagePath(customer.getUser_head_pic()));
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.GET_RECORD_DETAIL)) {
			recordArea.setText(model.record.getAreaName());
			recordHouse.setText(model.record.getHouseName());
			recordContent.setText(model.record.getContent());
			recordState.setText(model.record.getStatus());
			recordImg.setImageWithURL(this,ProtocolConst.PIC_DIR+model.record.getPic(),R.drawable.default_image);
			record.setText(model.record.getAddress()+" "+model.record.getHouseName());
			type.setText(model.record.getStatus());
		}
		
	}

}
