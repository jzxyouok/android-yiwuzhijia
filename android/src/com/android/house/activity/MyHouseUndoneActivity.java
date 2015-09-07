package com.android.house.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.funmi.house.R;

public class MyHouseUndoneActivity extends BaseActivity implements OnClickListener{
	private Button submit;
	
	private TextView title;
	
	private TextView manager;
	private TextView serviceTime;
	
	private TextView houseNo;
	private TextView housePrice;
	
	private TextView bonus;
	private TextView buyers;
	
	private TextView pickupLevel;
	private TextView evaluateLevel;
	private TextView dressLevel;
	
	private EditText suggest;
	
	private ImageView back;
	
	private RatingBar pickUpRating;
	private RatingBar evaluateRating;
	private RatingBar dressRating;
	
	private String suggestStr;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myhouse_undone);
		
		initView();
	}
	
	private void initView(){
		submit = (Button)findViewById(R.id.undone_submit_suggest);
		
		title = (TextView)findViewById(R.id.title_text);
		intent=getIntent();
		title.setText(intent.getStringExtra("house_name"));
		
		manager = (TextView)findViewById(R.id.undone_manager);
		serviceTime = (TextView)findViewById(R.id.undone_service_time);
		
		houseNo = (TextView)findViewById(R.id.undone_house_no);
		housePrice = (TextView)findViewById(R.id.undone_house_price);
		
		bonus = (TextView)findViewById(R.id.undone_bonus);
		buyers = (TextView)findViewById(R.id.undone_buyers);
		
		pickupLevel = (TextView)findViewById(R.id.undone_pick_up_ratingbar_level);
		evaluateLevel = (TextView)findViewById(R.id.undone_evaluate_ratingbar_level);
		dressLevel = (TextView)findViewById(R.id.undone_dress_ratingbar_level);
		
		suggest = (EditText)findViewById(R.id.undone_suggest);
		
		back = (ImageView)findViewById(R.id.title_back);
		
		dressRating = (RatingBar)findViewById(R.id.undone_dress_ratingbar);
		pickUpRating = (RatingBar)findViewById(R.id.undone_pick_up_ratingbar);
		evaluateRating = (RatingBar)findViewById(R.id.undone_evaluate_ratingbar);
		
		setClickListener();
	}
	
	private void setClickListener(){
		submit.setOnClickListener(this);
		
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.undone_submit_suggest:
			suggestStr = suggest.getText().toString();
			finish();
			break;
			
		case R.id.title_back:
			finish();
			break;
		}
	}
}
