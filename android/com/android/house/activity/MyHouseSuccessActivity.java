package com.android.house.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.funmi.house.R;

public class MyHouseSuccessActivity extends BaseActivity implements OnClickListener{
	private TextView title;
	
	private TextView manager;
	private TextView serviceTime;
	
	private TextView houseNo;
	private TextView housePrice;
	
	private TextView bonus;
	private TextView buyers;
	
	
	private ImageView back;
	
	private RatingBar pickUpRating;
	private RatingBar evaluateRating;
	private RatingBar dreassRating;
	
	private String suggestStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myhouse_success);
		
		initView();
	}
	
	private void initView(){
		title = (TextView)findViewById(R.id.title_text);
		title.setText("xxxx");
		
		manager = (TextView)findViewById(R.id.success_manager);
		serviceTime = (TextView)findViewById(R.id.success_service_time);
		
		houseNo = (TextView)findViewById(R.id.success_house_no);
		housePrice = (TextView)findViewById(R.id.success_house_price);
		
		bonus = (TextView)findViewById(R.id.success_bonus);
		buyers = (TextView)findViewById(R.id.success_buyers);
		
		back = (ImageView)findViewById(R.id.title_back);
		
		dreassRating = (RatingBar)findViewById(R.id.success_dress_ratingbar);
		pickUpRating = (RatingBar)findViewById(R.id.success_pick_up_ratingbar);
		evaluateRating = (RatingBar)findViewById(R.id.success_evaluate_ratingbar);
		
		setClickListener();
	}
	
	private void setClickListener(){
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.title_back:
			finish();
			break;
		}
	}
}
