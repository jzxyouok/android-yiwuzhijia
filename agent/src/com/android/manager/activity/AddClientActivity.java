package com.android.manager.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.manager.R;
import com.android.manager.model.AddRecCustomerModel;
import com.android.manager.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;

public class AddClientActivity extends BaseActivity implements OnClickListener,
		BusinessResponse, IXListViewListener {
	private View header;

	private View male;
	private View female;

	private Button confirm;
	private Button contacts;

	private TextView title;

	private EditText name;
	private EditText phone;
	private EditText dreamPrice;
	private EditText suggestCode;
	private EditText trendingContext;

	private ImageView back;
	private ImageView maleIcon;
	private ImageView femaleIcon;

	private XListView xlistView;

	private AddRecCustomerModel addRecCustomerModel;
	private InputMethodManager manager;

	private int sex_id;

	private boolean sex = true;// true 为男，false为女

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_client);
		initView();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.title_text);
		title.setText("添加客户");

		male = findViewById(R.id.add_client_male);
		female = findViewById(R.id.add_client_female);

		confirm = (Button) findViewById(R.id.add_client_confirm);
		contacts = (Button) findViewById(R.id.add_client_contacts);

		name = (EditText) findViewById(R.id.add_client_name);
		phone = (EditText) findViewById(R.id.add_client_phone);
		dreamPrice = (EditText) findViewById(R.id.add_client_dream_price);
		suggestCode = (EditText) findViewById(R.id.add_client_suggest_code);
		trendingContext = (EditText) findViewById(R.id.add_client_trending_context);

		back = (ImageView) findViewById(R.id.title_back);
		maleIcon = (ImageView) findViewById(R.id.add_client_male_img);
		femaleIcon = (ImageView) findViewById(R.id.add_client_female_img);

		addRecCustomerModel = new AddRecCustomerModel(this);
		addRecCustomerModel.addResponseListener(this);

		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		setClickListener();
	}

	private void setClickListener() {
		back.setOnClickListener(this);
		male.setOnClickListener(this);
		female.setOnClickListener(this);
		confirm.setOnClickListener(this);
		contacts.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;

		case R.id.add_client_male:
			sex = true;
			maleIcon.setImageResource(R.drawable.icon_select);
			femaleIcon.setImageResource(R.drawable.icon_unselect);
			break;

		case R.id.add_client_female:
			sex = false;
			maleIcon.setImageResource(R.drawable.icon_unselect);
			femaleIcon.setImageResource(R.drawable.icon_select);
			break;

		case R.id.add_client_confirm:
			String userName = name.getText().toString();
			String userPhone = phone.getText().toString();
			String priceHouseType = trendingContext.getText().toString();
			String priceArea = trendingContext.getText().toString();
			if (sex) {
				sex_id = 1;
			} else {
				sex_id = 0;
			}
			String preferPrice = dreamPrice.getText().toString();

			String telRegex = "[1][3578]\\d{9}";
			if (userPhone.length() != 11) {
				Toast.makeText(getApplicationContext(), "请输入 11位手机号",
						Toast.LENGTH_LONG).show();
			} else if (!userPhone.matches(telRegex)) {
				Toast.makeText(getApplicationContext(), "请输入正确的手机号",
						Toast.LENGTH_LONG).show();
			} else {
				addRecCustomerModel.addCustomer(userName, "成都", 1, userPhone,
						sex_id, preferPrice, priceArea, priceHouseType);
			}

			break;

		case R.id.add_client_contacts:
			Intent intent = new Intent(Intent.ACTION_PICK,
					ContactsContract.Contacts.CONTENT_URI);
			AddClientActivity.this.startActivityForResult(intent, 1);
			break;
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.ADD_REC_CUSTOMER)) {
			Toast.makeText(AddClientActivity.this, "添加成功", Toast.LENGTH_LONG)
					.show();
			finish();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				Uri contactData = data.getData();
				Cursor cursor = managedQuery(contactData, null, null, null,
						null);
				cursor.moveToFirst();
				String num = this.getContactPhone(cursor);
				if (num.contains(" ")) {
					num.replace(" ", "");
				} else if (num.contains("-")) {
					num.replace("-", "");
				}
				phone.setText(num);
			}
			break;

		default:
			break;
		}
	}

	private String getContactPhone(Cursor cursor) {
		// TODO Auto-generated method stub
		int phoneColumn = cursor
				.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
		int phoneNum = cursor.getInt(phoneColumn);
		String result = "";
		if (phoneNum > 0) {
			// 获得联系人的ID号
			int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
			String contactId = cursor.getString(idColumn);
			// 获得联系人电话的cursor
			Cursor phone = getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
							+ contactId, null, null);
			if (phone.moveToFirst()) {
				for (; !phone.isAfterLast(); phone.moveToNext()) {
					int index = phone
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
					int typeindex = phone
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
					int phone_type = phone.getInt(typeindex);
					String phoneNumber = phone.getString(index);
					result = phoneNumber;
					// switch (phone_type) {//此处请看下方注释
					// case 2:
					// result = phoneNumber;
					// break;
					//
					// default:
					// break;
					// }
				}
				if (!phone.isClosed()) {
					phone.close();
				}
			}
		}
		return result;
	}

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

}
