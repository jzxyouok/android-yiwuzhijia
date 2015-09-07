package com.android.manager.fragment;


import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.fragment.BaseFragment;
import com.android.manager.R;
import com.android.manager.util.UserInfoCacheUtil;

public class ManageClientTabsFragment extends BaseFragment implements OnClickListener{
	private TextView invalid;
	private TextView success;
	private TextView trending;
	private TextView important;
	
	private InvalidFragment invalidFragment;
	private SuccessFragment successFragment;
	private TrendingFragment trendingFragment;
	private ImportantFragment importantFragment;
	
	private Context myContext;
	
	
	public static final int INVALID = 0;
	public static final int SUCCESS = 1;
	public static final int TRENDING = 2;
	public static final int IMPORTANT = 3;
	
	
	public void setNetworkModel(Context context)
	{
		Log.d("mao","fragment 绑定model");
		this.myContext=context;
		initFragment(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("mao","fragment onCreateView");
		View tabsView = inflater.inflate(R.layout.fragment_tabs, container, false);
		int stype=UserInfoCacheUtil.getCacheInfo(getActivity()).getStype();
		initView(tabsView,stype==3?true:false);
		//initFragment(ManagerConst.isManager);
		return tabsView;
	}
	
	private void initView(View tabsView,boolean isManager){
		invalid = (TextView)tabsView.findViewById(R.id.tabs_invalid_client);
		success = (TextView)tabsView.findViewById(R.id.tabs_success_client);
		trending = (TextView)tabsView.findViewById(R.id.tabs_trending_client);
		important = (TextView)tabsView.findViewById(R.id.tabs_important_client);
		
		invalid.setOnClickListener(this);
		success.setOnClickListener(this);
		trending.setOnClickListener(this);
		important.setOnClickListener(this);
		
		if(isManager){
			important.setVisibility(View.VISIBLE);
		}else{
			important.setVisibility(View.GONE);
		}
		
		//selectTab(TRENDING);
	}
	
	private void initFragment(boolean isManager){
		invalidFragment = new InvalidFragment();
		successFragment = new SuccessFragment();
		trendingFragment = new TrendingFragment();
		importantFragment = new ImportantFragment();
		
		invalidFragment.initFragment(myContext);
		successFragment.initFragment(myContext);
		trendingFragment.initFragment(myContext);
		importantFragment.initFragment(myContext);
		selectTab(TRENDING);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tabs_invalid_client:
			selectTab(INVALID);
			break;
			
		case R.id.tabs_success_client:
			selectTab(SUCCESS);
			break;
			
		case R.id.tabs_trending_client:
			selectTab(TRENDING);
			break;
			
		case R.id.tabs_important_client:
			selectTab(IMPORTANT);
			break;
		}
	}
	
	private void selectTab(int tab){
		
		switch (tab) {
		case INVALID:
			showFragment(INVALID);
			break;
		case TRENDING:
			showFragment(TRENDING);
			break;
		case SUCCESS:
			showFragment(SUCCESS);
			break;
		case IMPORTANT:
			showFragment(IMPORTANT);
			break;
		default:
			break;
		}
	}

	public  final void showFragment(int type)
	{
		FragmentTransaction localFragmentTransaction;
		
		switch(type){
		case TRENDING:
			
			if(trendingFragment == null){
				trendingFragment = new TrendingFragment();
			}
			localFragmentTransaction = getFragmentManager()
					.beginTransaction();
			localFragmentTransaction.replace(R.id.manage_client_container,
					trendingFragment, "tab_one");
			
			trending.setBackgroundResource(R.drawable.tabs_shape_select);
			success.setBackgroundResource(R.drawable.tabs_shape_unselect);
			invalid.setBackgroundResource(R.drawable.tabs_shape_unselect);
			important.setBackgroundResource(R.drawable.tabs_shape_unselect);
			
			trending.setTextColor(Color.WHITE);
			success.setTextColor(getResources().getColor(R.color.text_heavy_orange));
			invalid.setTextColor(getResources().getColor(R.color.text_heavy_orange));
			important.setTextColor(getResources().getColor(R.color.text_heavy_orange));
			
			localFragmentTransaction.commit();
			
			break;
			
		case IMPORTANT:
			if(importantFragment == null){
				importantFragment = new ImportantFragment();
			}
			
			localFragmentTransaction = getFragmentManager()
					.beginTransaction();
			localFragmentTransaction.replace(R.id.manage_client_container,
					importantFragment, "tab_two");
			
			trending.setBackgroundResource(R.drawable.tabs_shape_unselect);
			success.setBackgroundResource(R.drawable.tabs_shape_unselect);
			invalid.setBackgroundResource(R.drawable.tabs_shape_unselect);
			important.setBackgroundResource(R.drawable.tabs_shape_select);
			
			important.setTextColor(Color.WHITE);
			invalid.setTextColor(getResources().getColor(R.color.text_heavy_orange));
			success.setTextColor(getResources().getColor(R.color.text_heavy_orange));
			trending.setTextColor(getResources().getColor(R.color.text_heavy_orange));
			
			localFragmentTransaction.commit();
			
			break;
			
		case SUCCESS:
			if(successFragment == null){
				successFragment = new SuccessFragment();
			}
			
			localFragmentTransaction = getFragmentManager()
					.beginTransaction();
			localFragmentTransaction.replace(R.id.manage_client_container,
					successFragment, "tab_three");
			
			trending.setBackgroundResource(R.drawable.tabs_shape_unselect);
			success.setBackgroundResource(R.drawable.tabs_shape_select);
			invalid.setBackgroundResource(R.drawable.tabs_shape_unselect);
			important.setBackgroundResource(R.drawable.tabs_shape_unselect);
			
			success.setTextColor(Color.WHITE);
			invalid.setTextColor(getResources().getColor(R.color.text_heavy_orange));
			trending.setTextColor(getResources().getColor(R.color.text_heavy_orange));
			important.setTextColor(getResources().getColor(R.color.text_heavy_orange));
			
			localFragmentTransaction.commit();
			
			break;
			
		case INVALID:
				if(invalidFragment == null){
					invalidFragment = new InvalidFragment();
				}
				
			localFragmentTransaction = getFragmentManager()
					.beginTransaction();
			localFragmentTransaction.replace(R.id.manage_client_container,
					invalidFragment, "tab_four");
			
			trending.setBackgroundResource(R.drawable.tabs_shape_unselect);
			success.setBackgroundResource(R.drawable.tabs_shape_unselect);
			invalid.setBackgroundResource(R.drawable.tabs_shape_select);
			important.setBackgroundResource(R.drawable.tabs_shape_unselect);
			
			invalid.setTextColor(Color.WHITE);
			success.setTextColor(getResources().getColor(R.color.text_heavy_orange));
			trending.setTextColor(getResources().getColor(R.color.text_heavy_orange));
			important.setTextColor(getResources().getColor(R.color.text_heavy_orange));
			
			localFragmentTransaction.commit();
			
			break;
		}
	}
}
