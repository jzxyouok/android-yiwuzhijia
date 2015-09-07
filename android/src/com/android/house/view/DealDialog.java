package com.android.house.view;

import com.funmi.house.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class DealDialog extends Dialog implements android.view.View.OnClickListener{
	private Context mContext;
	
	private TextView deal;
	private ImageView close;

	public DealDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.item_deal_dialog);
		
		initView();
		initDialog();
	}
	
	private void initView(){
		deal = (TextView)findViewById(R.id.deal_dialog_text);
		close = (ImageView)findViewById(R.id.deal_dialog_close);
		close.setOnClickListener(this);
	}
	
	private void initDialog(){
		Window dialogWindow = this.getWindow();
		WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		
		dialogWindow.setGravity(Gravity.CENTER);
		
		lp.width = (int)(display.getWidth()*0.8);
		lp.height = (int)(display.getHeight()*0.7);
		dialogWindow.setAttributes(lp);
	}
	
	public void setDeal(String dealText){
		deal.setText(dealText);
	}

	@Override
	public void onClick(View v) {
		this.dismiss();
	}
}
