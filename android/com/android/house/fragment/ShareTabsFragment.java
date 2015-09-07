package com.android.house.fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.fragment.BaseFragment;
import com.funmi.house.R;

public class ShareTabsFragment extends BaseFragment implements OnClickListener{
	private TextView shareWX;
	private TextView shareTX;
	private TextView shareSina;
	private TextView shareContacts;

	private ShareWXFragment shareWXFragment;
	private ShareTXFragment shareTXFragment;
	private ShareSinaFragment shareSinaFragment;
	private ShareContactsFragment shareContactsFragment;
	
	private static final int WX = 0;
	private static final int TX = 1;
	private static final int SINA = 2;
	private static final int CONTACTS = 3;
	
	public ShareTabsFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View tabsView = inflater.inflate(R.layout.fragment_share_tabs, container, false);
		initView(tabsView);
		
		return tabsView;
	}
	
	private void initView(View tabsView){
		shareWX = (TextView)tabsView.findViewById(R.id.share_weixin);
		shareTX = (TextView)tabsView.findViewById(R.id.share_tx_weibo);
		shareSina = (TextView)tabsView.findViewById(R.id.share_sina_weibo);
		shareContacts = (TextView)tabsView.findViewById(R.id.share_contacts);
		
		shareWX.setOnClickListener(this);
		shareTX.setOnClickListener(this);
		shareSina.setOnClickListener(this);
		shareContacts.setOnClickListener(this);
		
		selectTab(WX);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.share_weixin:
			selectTab(WX);
			break;
			
		case R.id.share_tx_weibo:
			selectTab(TX);
			break;
			
		case R.id.share_sina_weibo:
			selectTab(SINA);
			break;
			
		case R.id.share_contacts:
			selectTab(CONTACTS);
			break;
		}
	}
	
	private void selectTab(int tab){
		FragmentTransaction localFragmentTransaction;
		
		switch(tab){
		case WX:
			if(shareWXFragment == null){
				shareWXFragment = new ShareWXFragment();
			}
			
			localFragmentTransaction = getFragmentManager()
					.beginTransaction();
			localFragmentTransaction.replace(R.id.share_container,
					shareWXFragment, "tab_one");
			localFragmentTransaction.commit();
			
			break;
			
		case TX:
			if(shareTXFragment == null){
				shareTXFragment = new ShareTXFragment();
			}
			
			localFragmentTransaction = getFragmentManager()
					.beginTransaction();
			localFragmentTransaction.replace(R.id.share_container,
					shareTXFragment, "tab_two");
			localFragmentTransaction.commit();
			
			break;
			
		case SINA:
			if(shareSinaFragment == null){
				shareSinaFragment = new ShareSinaFragment();
			}
			
			localFragmentTransaction = getFragmentManager()
					.beginTransaction();
			localFragmentTransaction.replace(R.id.share_container,
					shareSinaFragment, "tab_three");
			localFragmentTransaction.commit();
			
			break;
			
		case CONTACTS:
			if(shareContactsFragment == null){
				shareContactsFragment = new ShareContactsFragment();
			}
			
			localFragmentTransaction = getFragmentManager()
					.beginTransaction();
			localFragmentTransaction.replace(R.id.share_container,
					shareContactsFragment, "tab_four");
			localFragmentTransaction.commit();
			
			break;
		}
	}
}
