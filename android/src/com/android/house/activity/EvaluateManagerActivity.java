package com.android.house.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.WebImageView;
import com.android.house.costants.AppConstants;
import com.android.house.model.MyAgentModel;
import com.android.house.protocol.ProtocolConst;
import com.android.house.util.JSONUtil;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.house.R;

/**
 * 评论经纪人页面
 * 
 * @author Administrator
 *
 */
public class EvaluateManagerActivity extends BaseActivity implements
		OnClickListener, OnRatingBarChangeListener, BusinessResponse {
	private Button submit;

	private TextView title;

	private TextView age;
	private TextView car;
	private TextView from;
	private TextView selling;

	private EditText evaluate;

	private WebImageView img;
	private ImageView back;

	private RatingBar dress;
	private RatingBar pickUp;
	private RatingBar houseEvaluate;

	private String evaluateStr;

	private Intent intent;

	private TextView ratingOne;
	private TextView ratingTwo;
	private TextView ratingThree;

	private MyAgentModel model;
	private int one;
	private int two;
	private int three;
	private boolean isCancel = false;
	private String imageUrl;
	private InputMethodManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluate_manager);

		initView();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	}

	private void initView() {
		submit = (Button) findViewById(R.id.evaluate_manager_submit);

		title = (TextView) findViewById(R.id.title_text);
		title.setText("评价经纪人");

		age = (TextView) findViewById(R.id.evaluate_manager_age);
		car = (TextView) findViewById(R.id.evaluate_manager_car);
		from = (TextView) findViewById(R.id.evaluate_manager_from);
		selling = (TextView) findViewById(R.id.evaluate_manager_selling);

		intent = getIntent();
		age.setText(intent.getStringExtra("sale_long"));
		from.setText(intent.getStringExtra("native_place"));
		car.setText(intent.getStringExtra("car_type"));
		selling.setText(intent.getStringExtra("sale_num"));
		isCancel = intent.getBooleanExtra("cancel", false);
		imageUrl = intent.getStringExtra("image");
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		evaluate = (EditText) findViewById(R.id.evaluate_manager_text);
		evaluate.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() != 0) {
					submit.setBackgroundResource(R.drawable.btn_shape_heavy_red);
				} else {
					submit.setBackgroundResource(R.drawable.btn_shape_light_gray);
				}
			}
		});

		back = (ImageView) findViewById(R.id.title_back);
		img = (WebImageView) findViewById(R.id.evaluate_manager_img);

		dress = (RatingBar) findViewById(R.id.evaluate_manager_dress_ratingbar);
		pickUp = (RatingBar) findViewById(R.id.evaluate_manager_pick_up_ratingbar);
		houseEvaluate = (RatingBar) findViewById(R.id.evaluate_manager_evaluate_ratingbar);
		dress.setOnRatingBarChangeListener(this);
		pickUp.setOnRatingBarChangeListener(this);
		houseEvaluate.setOnRatingBarChangeListener(this);

		ratingOne = (TextView) findViewById(R.id.evaluate_manager_pick_up_ratingbar_level);
		ratingTwo = (TextView) findViewById(R.id.evaluate_manager_evaluate_ratingbar_level);
		ratingThree = (TextView) findViewById(R.id.evaluate_manager_dress_ratingbar_level);

		if (isCancel) {
			submit.setText("解约");
		} else {
			submit.setText("提交");
		}
		if (imageUrl != null && !("".equals(imageUrl))) {
			img.setImageWithURL(EvaluateManagerActivity.this,
					AppConstants.WEBHOME + JSONUtil.getImagePath(imageUrl));
		}

		model = new MyAgentModel(this);
		model.addResponseListener(this);

		setClickListener();
	}

	private void setClickListener() {
		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			Intent intent = new Intent();
			intent.putExtra("cancel", false);
			setResult(11, intent);
			finish();
			break;

		case R.id.evaluate_manager_submit:
			if (isCancel) {
				evaluateStr = evaluate.getText().toString();
				model.cancelAgent(evaluateStr, one, two, three);
			} else {

				evaluateStr = evaluate.getText().toString();
				model.commentAgent(evaluateStr, one, two, three);
			}

			break;
		}
	}

	/**
	 * 星星评分
	 */
	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		switch (ratingBar.getId()) {
		case R.id.evaluate_manager_dress_ratingbar:
			ratingThree.setText(rating + "分");
			three = (int) rating;
			break;
		case R.id.evaluate_manager_pick_up_ratingbar:
			ratingOne.setText(rating + "分");
			one = (int) rating;
			break;
		case R.id.evaluate_manager_evaluate_ratingbar:
			ratingTwo.setText(rating + "分");
			two = (int) rating;
			break;

		default:
			break;
		}

	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.COMMENT_AGNET)) {
			Toast.makeText(EvaluateManagerActivity.this, "评论成功",
					Toast.LENGTH_LONG).show();
			Intent intent = new Intent();
			intent.putExtra("cancel", false);
			setResult(11, intent);
			finish();
		} else if (url.endsWith(ProtocolConst.CANCEL_AGENT)) {
			Toast.makeText(EvaluateManagerActivity.this, "解约成功",
					Toast.LENGTH_LONG).show();
			Intent intent = new Intent();
			intent.putExtra("cancel", true);
			setResult(11, intent);
			finish();

		}

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
