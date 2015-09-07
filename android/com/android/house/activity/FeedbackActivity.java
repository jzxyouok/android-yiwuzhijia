package com.android.house.activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.funmi.house.R;


public class FeedbackActivity extends BaseActivity implements OnClickListener{
	private TextView title;
	
	private ImageView back;
	
	private Button submitFeedback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		
		title = (TextView)findViewById(R.id.title_text);
		back = (ImageView)findViewById(R.id.title_back);
		submitFeedback = (Button)findViewById(R.id.submit_feedback);
		
		title.setText("建议与反馈");
		
		back.setOnClickListener(this);
		submitFeedback.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit_feedback:
			finish();
			break;

		case R.id.title_back:
			finish();
			break;
		}
	}
}
