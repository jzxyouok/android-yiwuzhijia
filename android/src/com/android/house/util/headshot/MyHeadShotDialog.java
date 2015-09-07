package com.android.house.util.headshot;

import com.funmi.house.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyHeadShotDialog extends Dialog implements android.view.View.OnClickListener {
private LayoutInflater factory;
	
	private TextView mImg;

	private TextView mPhone;

	private TextView mCancel;

	
	public MyHeadShotDialog(Context context) {
		super(context);
		factory = LayoutInflater.from(context);
	}

	public MyHeadShotDialog(Context context, int theme) {
		super(context, theme);
		factory = LayoutInflater.from(context);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(factory.inflate(R.layout.dialog_myheadshot, null));
		mImg = (TextView) this.findViewById(R.id.gl_choose_img);
		mPhone = (TextView) this.findViewById(R.id.gl_choose_phone);
		mCancel = (TextView) this.findViewById(R.id.gl_choose_cancel);
		mImg.setOnClickListener(this);
		mPhone.setOnClickListener(this);
		mCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gl_choose_img:
			Log.d("mao", "相册");
			doGoToImg();
			break;
		case R.id.gl_choose_phone:
			Log.d("mao","相机");
			doGoToPhone();
			break;
		case R.id.gl_choose_cancel:
			dismiss();
			break;
		}
	}
	
	public void doGoToImg(){
		
	}
	public void doGoToPhone(){
		
	}
}
