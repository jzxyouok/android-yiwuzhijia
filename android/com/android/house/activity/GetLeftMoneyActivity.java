package com.android.house.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.funmi.house.R;

public class GetLeftMoneyActivity extends BaseActivity{
	private TextView title;
	
	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_left_money);
		
		title = (TextView)findViewById(R.id.title_text);
		title.setText("”‡∂ÓÃÂœ÷");
		
		back = (ImageView)findViewById(R.id.title_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
