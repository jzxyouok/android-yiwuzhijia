package com.android.house.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.BeeFramework.fragment.BaseFragment;
import com.funmi.house.R;

public class ShareContactsFragment extends BaseFragment{
	private View view;
	
	private Button share;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_share_contacts, null);
		share = (Button)view.findViewById(R.id.share_contacts_btn);
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				sendSMS();
			}
		});
		
		return view;
	}
	/**
	 * 发送分享短信
	 * */
	public void sendSMS(){
		Uri smsToUri = Uri.parse("smsto:请选择联系人");
		Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
		intent.putExtra("sms_body", "易屋之家测试发送短信");
		startActivity(intent);
	}
}
