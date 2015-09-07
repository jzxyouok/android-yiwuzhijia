package com.android.manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.android.manager.ManagerConst;
import com.android.manager.R;
import com.android.manager.fragment.ManageClientTabsFragment;
import com.android.manager.protocol.CacheInfo;

public class ManageClientActivity extends BaseActivity implements OnClickListener{
	private TextView title;
	private TextView search;
	private ImageView back;
	
	private boolean isLogin=false;
	
	private CacheInfo cacheInfo;
	
	private ManageClientTabsFragment tabFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_client);
		
		initLoginInfo();
		
		initView();
		
		tabFragment=(ManageClientTabsFragment) getFragmentManager().findFragmentById(R.id.tabs_fragment);
		tabFragment.setNetworkModel(this);
		setClickListener();
	}

	private void initView() {
		title = (TextView)findViewById(R.id.title_text);
		title.setText("我的客户");
		
		search = (TextView)findViewById(R.id.manage_client_search);
		
		back = (ImageView)findViewById(R.id.title_back);
	}

	private void initLoginInfo() {
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		if(bundle==null)
		{
			Log.d("mao","尚未登录");
			this.isLogin=false;
			this.cacheInfo=new CacheInfo();
			cacheInfo.clearSelf();
		}
		else {
			this.isLogin=true;
			this.cacheInfo=(CacheInfo) bundle.getSerializable("cacheInfo");
			Log.d("mao","nick_name="+this.cacheInfo.getNick_name());
			Log.d("mao","stype="+this.cacheInfo.getStype());
			if(cacheInfo.getStype()==3)
			{
				ManagerConst.isManager=true;
			}
			else
			{
				ManagerConst.isManager=false;
			}
		}
	}

	private void setClickListener(){
		back.setOnClickListener(this);
		search.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.title_back:
			finish();
			break;
			
		case R.id.manage_client_search:
			Intent intent = new Intent(ManageClientActivity.this, SearchActivity.class);
			startActivity(intent);
			break;
		}
	}
}
