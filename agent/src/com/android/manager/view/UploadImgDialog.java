package com.android.manager.view;

import com.android.manager.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class UploadImgDialog extends Dialog implements android.view.View.OnClickListener{
	private Context mContext;
	
	private TextView cancel;
	private TextView takePhoto;
	private TextView selectFromLocal;

	public UploadImgDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.item_uploadimg_dialog);
		
		initView();
		initDialog();
	}
	
	private void initView(){
		View view = getWindow().getDecorView();
		
		cancel = (TextView)view.findViewById(R.id.upload_img_cancel);
		takePhoto = (TextView)view.findViewById(R.id.upload_img_take_photo);
		selectFromLocal = (TextView)view.findViewById(R.id.upload_img_select_from_local);
		
		setClickListener();
	}
	
	private void setClickListener(){
		cancel.setOnClickListener(this);
		takePhoto.setOnClickListener(this);
		selectFromLocal.setOnClickListener(this);
	}
	
	private void initDialog(){
		Window dialogWindow = this.getWindow();
		WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		
		dialogWindow.setGravity(Gravity.BOTTOM);
		
		lp.height = (int)(display.getHeight()*0.25);
		dialogWindow.setAttributes(lp);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.upload_img_cancel:
			this.dismiss();
			
		case R.id.upload_img_take_photo:
			break;
			
		case R.id.upload_img_select_from_local:
			break;
		}
	}
}
