package com.android.house.activity;

import org.json.JSONException;
import org.json.JSONObject;

import m.framework.network.ResponseCallback;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.house.costants.AppConstants;
import com.android.house.model.AgreementInfoModel;
import com.android.house.protocol.AgreementInfo;
import com.android.house.protocol.ProtocolConst;
import com.android.house.util.JSONUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.house.R;

public class MyHouseSuccessActivity extends BaseActivity implements
		OnClickListener, BusinessResponse {
	private TextView title;
	private ImageView back;
	
	private TextView tvBuyerName;
	private TextView tvMoney;
	private TextView tvBuyerRelation;
	private TextView tvHouseName;
	private TextView tvPayWay;
	private TextView tvdelaTime;
	private TextView tvPosNumber;
	private TextView tvAgreementNum;
	private TextView tvSaler;
	private TextView tvHouseNum; 
	
	private Intent intent;
	private int recordId;// 合同记录id
	private AgreementInfoModel agreementModel;
	
	private LinearLayout agreementLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myhouse_success);
		agreementModel = new AgreementInfoModel(this);
		agreementModel.addResponseListener(this);
		initView();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.title_text);
		title.setText("我的楼盘-合同详情");

		tvBuyerName = (TextView)findViewById(R.id.buyer_name_text);
		tvMoney = (TextView)findViewById(R.id.money_text);
		tvBuyerRelation = (TextView)findViewById(R.id.buyer_relation_text);
		tvHouseName = (TextView)findViewById(R.id.house_name_text);
		tvPayWay = (TextView)findViewById(R.id.pay_way_text);
		tvdelaTime = (TextView)findViewById(R.id.deal_time_text);
		tvPosNumber = (TextView)findViewById(R.id.pos_number_text);
		tvAgreementNum = (TextView)findViewById(R.id.agreement_number_text);
		tvSaler = (TextView)findViewById(R.id.saler_name_text);
		tvHouseNum = (TextView)findViewById(R.id.house_number_text);
		
		back = (ImageView) findViewById(R.id.title_back);

		intent = getIntent();
		recordId = intent.getIntExtra("recordId", 0);
		agreementModel.getAgreementInfoFormInternet(recordId);

		agreementLayout = (LinearLayout)findViewById(R.id.agreement_layout);
		agreementLayout.setVisibility(View.GONE);
		setClickListener();
	}

	private void setClickListener() {
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.GET_AGREEMENT_INFO)) {
			
			if (jo.optString("status").equals("200")) {
				agreementLayout.setVisibility(View.VISIBLE);
				tvAgreementNum.setText(agreementModel.agreementInfo.getAgreement_num());
				tvBuyerName.setText(agreementModel.agreementInfo.getBuyer());
				tvBuyerRelation.setText(agreementModel.agreementInfo.getBuyer_relation());
				tvHouseName.setText(agreementModel.agreementInfo.getHouse_name());
				tvPayWay.setText(agreementModel.agreementInfo.getPay_way());
				tvdelaTime.setText(agreementModel.agreementInfo.getSuccess_time());
				tvPosNumber.setText(agreementModel.agreementInfo.getPosId());
				tvBuyerName.setText(agreementModel.agreementInfo.getBuyer());
				tvSaler.setText(agreementModel.agreementInfo.getSale_man());
				tvHouseNum.setText(agreementModel.agreementInfo.getHouse_source_num());
				tvMoney.setText(agreementModel.agreementInfo.getMoney());
			}
			else if(jo.optString("status").equals("300")) {
				agreementLayout.setVisibility(View.GONE);
			}
		}
	}
}
