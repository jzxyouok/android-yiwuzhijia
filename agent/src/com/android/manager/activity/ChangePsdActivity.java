package com.android.manager.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.model.UserModel;
import com.android.manager.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;


/**
 * 修改密码
 * @author Administrator
 *
 */
public class ChangePsdActivity extends BaseActivity implements OnClickListener,BusinessResponse{
	private Button submitPsd;
	
	private TextView title;
	
	private EditText oldPsd;
	private EditText newPsd;
	private EditText confirmPsd;
	
	private ImageView back;
	
	private String oldPsdStr;
	private String newPsdStr;
	private String confirmPsdStr;
	
	private UserModel userModel;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_psd);
		
		initView();
	}
	
	private void initView(){
		back = (ImageView)findViewById(R.id.title_back);
		submitPsd = (Button)findViewById(R.id.submit_change_psd);
		
		title = (TextView)findViewById(R.id.title_text);
		title.setText("修改密码");
		
		oldPsd = (EditText)findViewById(R.id.old_psd);
		newPsd = (EditText)findViewById(R.id.new_psd);
		confirmPsd = (EditText)findViewById(R.id.confirm_psd);
		
		userModel = new UserModel(this);
		userModel.addResponseListener(this);
		
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
			
			if (newPsdStr.equals(confirmPsdStr)) {

				if (newPsd.length() >= 6) {
					userModel.resetPwd(oldPsdStr, newPsdStr);

				} else {
					Toast.makeText(ChangePsdActivity.this, "新密码不能小于6位",
							Toast.LENGTH_LONG).show();

				}
			} else {
				Toast.makeText(ChangePsdActivity.this, "两次输入的密码不一致~",
						Toast.LENGTH_LONG).show();
			}
			
			break;
			
		case R.id.title_back:
			finish();
			break;
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.RESETPWD)) {
			Toast.makeText(ChangePsdActivity.this, "修改成功", Toast.LENGTH_LONG).show();
			finish();
		}
		
	}
}
