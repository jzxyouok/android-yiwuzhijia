package com.android.house.activity;

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
import com.android.house.model.UserModel;
import com.android.house.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.house.R;

public class ForgetPsdActivity extends BaseActivity implements OnClickListener,
		BusinessResponse {
	private Button send;

	private TextView title;

	private EditText phone;

	private ImageView back;

	private String phoneStr;

	private UserModel userModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_psd);

		initView();
	}

	private void initView() {
		send = (Button) findViewById(R.id.forget_psd_send);

		title = (TextView) findViewById(R.id.title_text);
		title.setText("忘记密码");

		phone = (EditText) findViewById(R.id.forget_psd_phone);

		back = (ImageView) findViewById(R.id.title_back);

		userModel = new UserModel(this);
		userModel.addResponseListener(this);

		setClickListener();
	}

	private void setClickListener() {
		send.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;

		case R.id.forget_psd_send:
			phoneStr = phone.getText().toString();
			if (phoneStr.length() == 11
					&& phoneStr.subSequence(0, 1).equals("1")
					||phoneStr.subSequence(1, 2).equals("3")
					|| phoneStr.subSequence(1, 2).equals("5")
					|| phoneStr.subSequence(1, 2).equals("8")) {
				userModel.forgetPsd(phoneStr);
			} else {
				Toast.makeText(ForgetPsdActivity.this, "请输入11位有效手机号",
						Toast.LENGTH_SHORT).show();
			}

			break;
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.FORGETPWD)) {
			finish();
		}

	}
}
