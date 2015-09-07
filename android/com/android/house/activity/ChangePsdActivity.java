package com.android.house.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.funmi.house.R;

public class ChangePsdActivity extends BaseActivity implements OnClickListener{
	private Button submitPsd;
	
	private TextView title;
	
	private EditText oldPsd;
	private EditText newPsd;
	private EditText confirmPsd;
	
	private ImageView back;
	
	private String oldPsdStr;
	private String newPsdStr;
	private String confirmPsdStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actvitiy_changepsd);
		
		initView();
	}
	
	private void initView(){
		back = (ImageView)findViewById(R.id.title_back);
		submitPsd = (Button)findViewById(R.id.submit_change_psd);
		
		title = (TextView)findViewById(R.id.title_text);
		title.setText("ÐÞ¸ÄÃÜÂë");
		
		oldPsd = (EditText)findViewById(R.id.old_psd);
		newPsd = (EditText)findViewById(R.id.new_psd);
		confirmPsd = (EditText)findViewById(R.id.confirm_psd);
		
		setClickListener();
	}
	
	private void setClickListener(){
		back.setOnClickListener(this);
		submitPsd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.submit_change_psd:
			oldPsdStr = oldPsd.getText().toString();
			newPsdStr = newPsd.getText().toString();
			confirmPsdStr = confirmPsd.getText().toString();
		
			finish();
			break;
			
		case R.id.title_back:
			finish();
			break;
		}
	}
}
