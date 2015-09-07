package com.android.house.activity;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.house.model.UserModel;
import com.android.house.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.house.R;


public class FeedbackActivity extends BaseActivity implements OnClickListener,BusinessResponse{
	private TextView title;
	private EditText feedback;
	
	private ImageView back;
	
	private Button submitFeedback;
	
	private String feedbackStr;
	
	private UserModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		
		title = (TextView)findViewById(R.id.title_text);
		
		feedback = (EditText)findViewById(R.id.feedback_text);
		feedback.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(feedback.getText().toString() == null){
					submitFeedback.setClickable(false);
					submitFeedback.setBackgroundResource(R.drawable.btn_shape_light_gray);
				}else{
					submitFeedback.setClickable(true);
					submitFeedback.setBackgroundResource(R.drawable.btn_shape_heavy_red);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		back = (ImageView)findViewById(R.id.title_back);
		submitFeedback = (Button)findViewById(R.id.submit_feedback);
		
		title.setText("建议与反馈");
		
		back.setOnClickListener(this);
		submitFeedback.setOnClickListener(this);
		
		model=new UserModel(this);
		model.addResponseListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit_feedback:
			feedbackStr = feedback.getText().toString();
			model.addSuggestion(feedbackStr);
			
			break;

		case R.id.title_back:
			finish();
			break;
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.SUGGESTION)) {
			Toast.makeText(FeedbackActivity.this, "我们已经收到建议、谢谢反馈!",Toast.LENGTH_LONG).show();
			finish();
		}
		
	}
}
