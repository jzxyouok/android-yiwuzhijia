package com.android.house.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.AppConst;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.WebImageView;
import com.android.house.adapter.ManagerDetailListAdapter;
import com.android.house.costants.AppConstants;
import com.android.house.model.AgentDetailModel;
import com.android.house.model.FreeAppointmentModel;
import com.android.house.model.MyAgentModel;
import com.android.house.protocol.Agent;
import com.android.house.protocol.CacheInfo;
import com.android.house.util.JSONUtil;
import com.android.house.util.UserInfoCacheUtil;
import com.android.house.util.Util;
import com.baidu.lbsapi.BMapManager;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.house.R;

public class ManagerDetailActivity extends Activity implements OnClickListener {
	private Button freeAppoint;

	private TextView title;
	private TextView sellingAge;
	private TextView managerFrom;
	private TextView managerDistance;
	private TextView managerCar;
	private TextView managerSellings;

	private WebImageView managerImage;
	private ImageView back;

	private ListView evaluateList;
	private ManagerDetailListAdapter adapter;

	private Agent agent;
	boolean ifFirstIn = true;
	CacheInfo cacheInfo;
	FreeAppointmentModel apointModel; // 预约经纪人
	MyAgentModel agentModel;
	
	private TextView noCommentText;
	
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0: {
				agentModel.getCommentList(agent.getUser_id(),
						cacheInfo.getSessionId());
				String path = agent.getPic();
				Log.d("mao", "pic=" + path);
				if (path != null)
					managerImage.setImageWithURL(ManagerDetailActivity.this,
							AppConstants.WEBHOME + JSONUtil.getImagePath(path));
			}
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_detail);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			agent = (Agent) bundle.getSerializable("agent");
			Log.d("mao", "传递进来的的AgentId为" + agent.getAgent_id());
			cacheInfo = (CacheInfo) bundle.getSerializable("cacheInfo");
		} else {
			Log.d("mao", "bundle 为空");
		}
		initView();
		initData();

		apointModel = new FreeAppointmentModel(this);
		agentModel = new MyAgentModel(this);
		apointModel.addResponseListener(new ApointAgentResponse());
		agentModel.addResponseListener(new GetCommentsResponse());

	}

	private void initView() {
		freeAppoint = (Button) findViewById(R.id.manager_detail_free_appoint);

		title = (TextView) findViewById(R.id.title_text);
		sellingAge = (TextView) findViewById(R.id.manager_detail_age);
		managerFrom = (TextView) findViewById(R.id.manager_detail_from);
		managerDistance = (TextView) findViewById(R.id.manager_detail_distance);
		managerCar = (TextView) findViewById(R.id.manager_detail_car);
		managerSellings = (TextView) findViewById(R.id.manager_detail_selling);
		managerImage = (WebImageView) findViewById(R.id.manager_detail_img);
		back = (ImageView) findViewById(R.id.title_back);
		evaluateList = (ListView) findViewById(R.id.manager_detail_list);
		noCommentText=(TextView)findViewById(R.id.no_comment_text);
		noCommentText.setVisibility(View.GONE);

		setClickListener();
	}

	private void initData() {
		this.title.setText(/*"经纪人：" + */agent.getAgent_name());
		this.sellingAge.setText(agent.getSale_long() + "年");
		this.managerSellings.setText(agent.getSale_num()+"套");
		this.managerCar.setText(agent.getCar_type());
		
		SharedPreferences sharedPreferences = getSharedPreferences("userLocation", MODE_PRIVATE);
		double lat = sharedPreferences.getFloat("lat", 100.0f);
		double lot = sharedPreferences.getFloat("lot", 90.0f);
		double distance = Util.getDistatce(lat, lot, agent.getLat(), agent.getLng());
		this.managerDistance.setText(String.format("%.0f公里", distance));
		this.managerFrom.setText(agent.getNative_place());
	}

	private void setClickListener() {
		back.setOnClickListener(this);
		freeAppoint.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;

		case R.id.manager_detail_free_appoint: {
			String msg1 = cacheInfo.isLogin() ? "已经登录" : "未登录 ";
			Log.d("mao",
					"ManagerDetailActivity: session="
							+ cacheInfo.getSessionId() + msg1 + "id="
							+ cacheInfo.getUid());
			if (this.cacheInfo.isLogin()) {
				Log.d("mao", "已经登录");
				this.apointModel.appointAgent(cacheInfo.getUid(),
						agent.getUser_id(), cacheInfo.getSessionId());
			} else {
				Toast.makeText(this, "尚未登录，请返回登录", Toast.LENGTH_SHORT).show();
			}
		}
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		if (ifFirstIn) {
			Thread thread = new Thread() {

				@Override
				public void run() {

					try {
						sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
					}
					ManagerDetailActivity.this.ifFirstIn = false;
					Message message = new Message();
					message.what = 0;
					handler.sendMessage(message);
				}
			};
			thread.start();
		}

	}

	class GetCommentsResponse implements BusinessResponse {
		@Override
		public void OnMessageResponse(String url, JSONObject jo,
				AjaxStatus status) throws JSONException {
			adapter = new ManagerDetailListAdapter(ManagerDetailActivity.this);
			adapter.bindData(agentModel.commentList);
			if (adapter.isEmpty()) {
//				Toast.makeText(ManagerDetailActivity.this, "暂无评价...",
//						Toast.LENGTH_SHORT).show();
				noCommentText.setVisibility(View.VISIBLE);
				evaluateList.setVisibility(View.GONE);
				return;
			}else {
				noCommentText.setVisibility(View.GONE);
				evaluateList.setVisibility(View.VISIBLE);
				ManagerDetailActivity.this.evaluateList.setAdapter(adapter);
			}
		}
	}

	class ApointAgentResponse implements BusinessResponse {

		@Override
		public void OnMessageResponse(String url, JSONObject jo,
				AjaxStatus status) throws JSONException {
			if (jo != null) {
				int msg = jo.optInt("status");
				if (msg == 300) {
					Toast.makeText(getApplicationContext(),
							jo.optString("msg"), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "预约成功！",
							Toast.LENGTH_SHORT).show();
				}
			}
		}

	}
	
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
    }
  
    @Override  
    protected void onPause() {  
        super.onPause();  
    }
}
