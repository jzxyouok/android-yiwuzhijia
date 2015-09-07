package com.android.house.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.android.house.adapter.PicAdapter;
import com.android.house.adapter.TitleAdapter;
import com.android.house.view.PicViewPager;
import com.funmi.house.R;

public class HousePicDetailActivity extends BaseActivity implements OnClickListener{

	private TextView topTitle;
	private ImageView topBack;
	private ArrayList<String> picList;
	private PicViewPager picViewpager;
	private PicAdapter titlePager;
	private Intent intent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pic_detail);
		initView();
	}


	private void initView() {
		topTitle=(TextView) findViewById(R.id.title_text);
		topBack=(ImageView) findViewById(R.id.title_back);
		picViewpager=(PicViewPager) findViewById(R.id.pic_detail_viewpager);
		
		intent=getIntent();
		picList=intent.getStringArrayListExtra("picUrl");
		titlePager=new PicAdapter(this);
		titlePager.bindData(picList);
		picViewpager.setAdapter(titlePager);
		topTitle.setText("1"+" of "+picList.size());
		topBack.setOnClickListener(this);
		picViewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
				topTitle.setText((arg0+1)+" of "+picList.size());
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
				
			}
		});
	
		
	}
	


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;

		default:
			break;
		}
	}
}
