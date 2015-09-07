package com.android.manager.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.e;

import com.BeeFramework.AppConst;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.WebImageView;
import com.android.manager.R;
import com.android.manager.component.SpinnerHolder;
import com.android.manager.component.SpinnerHolder.SpinnerHolderListener;
import com.android.manager.costants.AppConstants;
import com.android.manager.fragment.ImagePickerFragment;
import com.android.manager.model.AddRecordModel;
import com.android.manager.model.ChangeClientTypeModel;
import com.android.manager.protocol.Customer;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.protocol.Record;
import com.android.manager.util.JSONUtil;
import com.android.manager.view.SeeDialog;
import com.android.manager.view.TimePickerDialog;
import com.android.manager.view.TimePickerDialog.PickerDialogButtonListener;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;

public class MyClientActivity extends BaseActivity implements OnClickListener,
		IXListViewListener {

	public static final int TYPE_CHANGE_RELATION = 0;
	public static final int TYPE_CHANGE_HOUSE = 1;
	public static final int TYPE_CHANGE_AREA = 2;
	public static final int TYPE_CHANGE_STATE = 3;

	private View header;

	private Button confirm;
	private Button successConfirm;

	private TextView title;
	private TextView clientSex;
	private TextView clientName;
	private TextView clientPhone;

	private EditText amount;
	private EditText buyers;
	private EditText content;
	private EditText houseNo;
	private EditText payment;
	private EditText posOrder;
	private EditText contrastNo;
	private EditText seeManager;
	private EditText relationship;
	private TextView selectedHousename;
	private TextView signTime;

	private String imagePath = null;

	private ImageView back;
	private WebImageView clientImage;

	private PopupWindow window;

	private XListView xlistView;

	private RelativeLayout clientKind;
	private RelativeLayout selectArea;
	private RelativeLayout selectHouse;
	private RelativeLayout selectHouseName;
	private RelativeLayout selectHouseState;
	private RelativeLayout signTimeWrapper;

	private SpinnerHolder holder;

	private Customer customer;

	ImagePickerFragment pickFragment;
	Handler fragmentHandler;

	ChangeClientTypeModel changeTypeModel;
	AddRecordModel addRecordModel;
	List<String> relationList = new ArrayList<String>();
	List<String> areaList = new ArrayList<String>();
	List<String> houseList = new ArrayList<String>();
	List<String> stateList = new ArrayList<String>();

	List<Integer> areaIdList = new ArrayList<Integer>();
	List<Integer> houseIdList = new ArrayList<Integer>();

	TextView areaName;
	TextView houseName;
	TextView stateName;
	TextView changeTypeText;

	TimePickerDialog dialog;

	private int houseId;
	private int filterTYPE = 0;
	private int clientStatus = 0;

	private String selectedStr;

	private LinearLayout successPage;

	private boolean isChooseArea = false;
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// EventBus.getDefault().register(this);
		setContentView(R.layout.activity_my_client);
		Bundle bundle = getIntent().getExtras();
		customer = (Customer) bundle.getSerializable("customer");
		if (customer == null) {
			Log.d("mao", "传来的customer为空");
		} else {
			Log.d("mao", "传来的customer不为空");
		}
		initView();
		setView();
		initData();
		initNetwork();

	}

	private void initNetwork() {
		changeTypeModel = new ChangeClientTypeModel(this);
		addRecordModel = new AddRecordModel(this);
		ChangeTypeBusiness listener = new ChangeTypeBusiness();
		changeTypeModel.addResponseListener(listener);
		addRecordModel.addResponseListener(listener);
		if (shared.getInt("areaId", 0) != 0) {
			addRecordModel.getAreaHouseList(shared.getInt("areaId", 0), 1);
		}

	}

	private void initData() {
		String[] values = getResources().getStringArray(
				R.array.customer_relationType);
		for (int i = 0; i < values.length; i++) {
			this.relationList.add(values[i]);
		}

		String[] stateValues = getResources().getStringArray(
				R.array.client_state);
		for (int i = 0; i < stateValues.length; i++) {
			this.stateList.add(stateValues[i]);
		}
	}

	private void initView() {

		title = (TextView) findViewById(R.id.title_text);
		title.setText("我的客户：" + customer.getUser_name());
		header = LayoutInflater.from(MyClientActivity.this).inflate(
				R.layout.layout_my_client, null);
		xlistView = (XListView) findViewById(R.id.my_client_list);
		xlistView.addHeaderView(header);
		xlistView.setPullLoadEnable(false);
		xlistView.setRefreshTime();
		xlistView.setXListViewListener(this, 1);
		xlistView.setAdapter(null);

		confirm = (Button) header.findViewById(R.id.my_client_confirm);
		successConfirm = (Button) header
				.findViewById(R.id.my_client_success_confirm);

		clientSex = (TextView) header.findViewById(R.id.my_client_sex);
		clientName = (TextView) header.findViewById(R.id.my_client_name);
		clientPhone = (TextView) header.findViewById(R.id.my_client_phone);
		changeTypeText = (TextView) header
				.findViewById(R.id.my_client_kind_spinner_text);
		areaName = (TextView) header
				.findViewById(R.id.my_client_select_area_spinner_text);
		houseName = (TextView) header
				.findViewById(R.id.my_client_select_house_spinner_text);
		stateName = (TextView) header
				.findViewById(R.id.my_client_select_state_spinner_text);
		signTime = (TextView) header.findViewById(R.id.my_client_sign_time);

		amount = (EditText) header.findViewById(R.id.my_client_amount);
		buyers = (EditText) header.findViewById(R.id.my_client_house_buyer);
		content = (EditText) header.findViewById(R.id.my_client_fill_record);
		houseNo = (EditText) header.findViewById(R.id.my_client_house_num);
		posOrder = (EditText) header.findViewById(R.id.my_client_pos);
		payment = (EditText) header
				.findViewById(R.id.my_client_pay_spinner_text);
		contrastNo = (EditText) header
				.findViewById(R.id.my_client_contrast_num);
		seeManager = (EditText) header.findViewById(R.id.my_client_manager);
		relationship = (EditText) header.findViewById(R.id.my_client_relation);
		selectedHousename = (TextView) header
				.findViewById(R.id.my_client_housename_spinner_text);

		back = (ImageView) findViewById(R.id.title_back);
		clientImage = (WebImageView) header.findViewById(R.id.my_client_img);

		clientKind = (RelativeLayout) header.findViewById(R.id.my_client_kind);
		selectArea = (RelativeLayout) header
				.findViewById(R.id.my_client_select_area);
		selectHouse = (RelativeLayout) header
				.findViewById(R.id.my_client_select_house);
		selectHouseName = (RelativeLayout) header
				.findViewById(R.id.my_client_housename);
		selectHouseState = (RelativeLayout) header
				.findViewById(R.id.my_client_select_state);
		signTimeWrapper = (RelativeLayout) header
				.findViewById(R.id.my_client_sign_time_wrapper);

		successPage = (LinearLayout) findViewById(R.id.success_trade_data_wrapper);

		shared = getSharedPreferences("record", 0);
		editor = shared.edit();

		areaName.setText(shared.getString("area", "区域"));
		houseName.setText(shared.getString("house", "楼盘名"));
		stateName.setText(shared.getString("status", "用户状态"));
		

		houseId = shared.getInt("houseId", 0);
		clientStatus = shared.getInt("statusId", 0);
		if (clientStatus == 3) {
			successPage.setVisibility(View.VISIBLE);
			confirm.setVisibility(View.GONE);
		}
		selectedHousename.setText(shared.getString("house", ""));

		setClickListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	private void setView() {
		clientName.setText(customer.getUser_name());
		clientPhone.setText(customer.getUser_phone());
		if (customer.getRelation_status() == 2) {
			changeTypeText.setText("意向");
		}
		if (customer.getRelation_status() == 3) {
			changeTypeText.setText("重要");
		}
		if (customer.getRelation_status() == 5) {
			changeTypeText.setText("失效");
		}

	}

	private void setClickListener() {
		back.setOnClickListener(this);
		confirm.setOnClickListener(this);
		successConfirm.setOnClickListener(this);

		clientKind.setOnClickListener(this);
		selectArea.setOnClickListener(this);
		selectHouse.setOnClickListener(this);
		selectHouseName.setOnClickListener(this);
		selectHouseState.setOnClickListener(this);
		signTimeWrapper.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;

		case R.id.my_client_confirm: {
			Record record = new Record();
			record.setBusinessStatus(clientStatus);
			record.setContent(content.getText().toString());
			record.setHouseId(houseId);
			record.setHouseName(houseName.getText().toString());
			addRecordModel.addRecord(record, customer, imagePath);
		}
			break;
		case R.id.my_client_success_confirm:

			Record record = new Record();

			record.setBusinessStatus(3);
			record.setContent(content.getText().toString());
			record.setHouseId(houseId);
			record.setHouseName(houseName.getText().toString());
			record.setBuyer(buyers.getText().toString());
			record.setMoney(amount.getText().toString());
			record.setRelation(relationship.getText().toString());
			record.setAgreementNum(contrastNo.getText().toString());
			record.setSuccessTime(signTime.getText().toString());
			record.setPayWay(payment.getText().toString());
			record.setPOS(posOrder.getText().toString());
			record.setHouseSourceNum(houseNo.getText().toString());
			record.setSaleMan(seeManager.getText().toString());

			addRecordModel.addBusiness(record, customer, imagePath);
			break;

		case R.id.my_client_kind:

			showSpinner(clientKind, this.relationList, TYPE_CHANGE_RELATION);
			break;

		case R.id.my_client_select_area:

			addRecordModel.getCityAreaList(1);

			break;

		case R.id.my_client_select_house:

			if (areaName.getText().toString().equals("区域")) {
				Toast.makeText(MyClientActivity.this, "请先选择区域",
						Toast.LENGTH_LONG).show();
			} else {

				if (houseList.size() == 0) {

					Toast.makeText(MyClientActivity.this, "该区域没有相应的楼盘哦",
							Toast.LENGTH_LONG).show();
				}
			}
			showSpinner(selectHouse, this.houseList, TYPE_CHANGE_HOUSE);
			break;

		case R.id.my_client_select_state:
			showSpinner(selectHouseState, this.stateList, TYPE_CHANGE_STATE);
			break;

		case R.id.my_client_sign_time_wrapper:
			dialog = new TimePickerDialog(MyClientActivity.this,
					R.style.SampleTheme_Light,
					new PickerDialogButtonListener() {

						@Override
						public void onClick(View v) {
							signTime.setText(dialog.getTime());
						}
					});
			dialog.show();
			break;
		}
	}

	private void showSpinner(View spinner, List<String> data, int type) {
		filterTYPE = type;

		holder = new SpinnerHolder(MyClientActivity.this, data,
				new RelaytionTypeChangedListener());
		holder.setType(type);
		window = new PopupWindow(holder.getView());
		window.setWidth(spinner.getWidth());
		window.setHeight(LayoutParams.WRAP_CONTENT);
		window.setBackgroundDrawable(new BitmapDrawable());
		window.setOutsideTouchable(true);
		window.setFocusable(true);
		window.setContentView(holder.getView());
		window.showAsDropDown(spinner, 0, 0);

		holder.getList().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectedStr = holder.getData().get(position);

				switch (filterTYPE) {
				case TYPE_CHANGE_RELATION: {
					SeeDialog dialog = new SeeDialog(MyClientActivity.this,
							R.style.dialog);
					dialog.setBtnText("确认提交", "放弃提交");
					dialog.setClickListener(new ConfirmChangeTypeListener());
					dialog.setMsg(position);
					dialog.show();
				}
					break;
				case TYPE_CHANGE_AREA:
					areaName.setText(selectedStr);
					Log.d("mao",
							"选择的区域名:" + selectedStr + " id:"
									+ areaIdList.get(position));
					houseIdList.clear();
					houseName.setText("楼盘名");
					isChooseArea = true;
					editor.putInt("areaId", areaIdList.get(position));
					editor.commit();
					addRecordModel.getAreaHouseList(areaIdList.get(position), 1);

					break;
				case TYPE_CHANGE_HOUSE:

					houseName.setText(selectedStr);
					houseId = houseIdList.get(position);
					selectedHousename.setText(selectedStr);
					break;
				case TYPE_CHANGE_STATE:
					stateName.setText(selectedStr);
					clientStatus = position + 1;
					if (clientStatus == 3) {
						successPage.setVisibility(View.VISIBLE);
						confirm.setVisibility(View.GONE);
					} else {
						successPage.setVisibility(View.GONE);
						confirm.setVisibility(View.VISIBLE);
					}
				default:
					break;
				}

				window.dismiss();
			}
		});
	}

	@Override
	public void onRefresh(int id) {

	}

	@Override
	public void onLoadMore(int id) {

	}

	@Override
	protected void onResume() {
		super.onResume();
		pickFragment = (ImagePickerFragment) getFragmentManager()
				.findFragmentById(R.id.image_picker_fragment);
		fragmentHandler = pickFragment.handler;

		clientImage.setImageWithURL(
				this,
				AppConstants.WEBHOME
						+ JSONUtil.getImagePath(customer.getUser_head_pic()));
	}

	class RelaytionTypeChangedListener implements SpinnerHolderListener {

		@Override
		public void onItemClickListener(int position, String name, int type) {
			switch (type) {
			case TYPE_CHANGE_RELATION: {
				SeeDialog dialog = new SeeDialog(MyClientActivity.this,
						R.style.dialog);
				dialog.setBtnText("确认提交", "放弃提交");
				dialog.setClickListener(new ConfirmChangeTypeListener());
				dialog.setMsg(position);
				dialog.show();
			}
				break;
			case TYPE_CHANGE_AREA:
				areaName.setText(areaList.get(position));
				editor.putInt("areaId", areaIdList.get(position));
				editor.commit();
				addRecordModel.getAreaHouseList(areaIdList.get(position), 1);
				break;
			case TYPE_CHANGE_HOUSE:
				houseName.setText(houseList.get(position));

				houseId = houseIdList.get(position);
				break;
			case TYPE_CHANGE_STATE:
				stateName.setText(stateList.get(position));
				clientStatus = position + 1;
			default:
				break;
			}
		}

		@Override
		public void onItemClickListener() {

		}

	}

	class ConfirmChangeTypeListener implements SeeDialog.OnDialogClickListener {

		@Override
		public void onPositiveButtonClick(int msg) {
			changeTypeText.setText(relationList.get(msg));
			Log.d("mao", "customer :" + customer.getId());
			if (msg < (relationList.size() - 1)) {
				changeTypeModel.ajaxChangeClientType(customer, msg + 2);
			} else {
				changeTypeModel.ajaxChangeClientType(customer, msg + 3);
			}
		}

		@Override
		public void onGiveUpButtonClick() {

		}

	}

	class ChangeTypeBusiness implements BusinessResponse {

		@Override
		public void OnMessageResponse(String url, JSONObject jo,
				AjaxStatus status) throws JSONException {
			Log.d("mao", jo.toString());

			if (url.endsWith(ProtocolConst.CHANGE_CUSTOMER_RELATION)) {// 修改用户状态
				handleRepley(jo);
			} else if (url.endsWith(ProtocolConst.ADD_BUSINESS_RECORD)) {// 新增陪同记录
				if (jo.optString("status").equals("200")) {
					editor.putString("area", areaName.getText().toString());
					editor.putString("house", houseName.getText().toString());
					editor.putString("status", stateName.getText().toString());
					editor.putInt("houseId", houseId);
					editor.putInt("statusId", clientStatus);
					editor.commit();

				}
				handleRepley(jo);
			} else if (url.endsWith(ProtocolConst.GET_AREA_LIST)) {
				Log.d("mao", "区域列表长度:" + addRecordModel.areaList.size());
				areaList.clear();
				areaIdList.clear();
				for (int i = 0; i < addRecordModel.areaList.size(); i++) {
					areaList.add(addRecordModel.areaList.get(i).getName());
					areaIdList.add(addRecordModel.areaList.get(i).getId());
				}
				showSpinner(selectArea, areaList, TYPE_CHANGE_AREA);
			} else if (url.endsWith(ProtocolConst.GET_HOUSE_LIST_BYAREA)) {
				houseList.clear();
				houseIdList.clear();
				for (int i = 0; i < addRecordModel.houseList.size(); i++) {
					houseList.add(addRecordModel.houseList.get(i).getName());
					houseIdList.add(addRecordModel.houseList.get(i)
							.getHouse_id());
				}
			}
		}

		private void handleRepley(JSONObject jo) {
			if (jo != null) {
				int code = jo.optInt("status");
				if (code == 200) {
					Toast.makeText(MyClientActivity.this, "提交成功!",
							Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(MyClientActivity.this,
							"提交失败:" + jo.optString("msg"), Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				Toast.makeText(MyClientActivity.this, "网络出错",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 200 && resultCode == RESULT_OK) {
			if (data != null) {
				Uri uri = data.getData();
				if (!TextUtils.isEmpty(uri.getAuthority())) {
					Cursor cursor = getContentResolver().query(uri,
							new String[] { MediaStore.Images.Media.DATA },
							null, null, null);
					if (null == cursor) {
						Toast.makeText(this, "图片没找到", 0).show();
						return;
					}
					cursor.moveToFirst();
					String path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					cursor.close();
					Log.i("mao", "path=" + path);
					if (fragmentHandler != null) {
						File pic = new File(path);
						if (pic.exists()) {
							this.imagePath = path;
							Log.d("mao", "handler不为空");
							Message msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putString("path", path);
							msg.setData(bundle);
							fragmentHandler.sendMessage(msg);
						} else {
							Toast.makeText(this, "图像不存在", Toast.LENGTH_SHORT)
									.show();
						}
					} else {
						Log.d("mao", "handler为空");
					}
				}
			}
		}
	}
}
