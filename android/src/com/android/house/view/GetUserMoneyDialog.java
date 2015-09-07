package com.android.house.view;

import com.funmi.house.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GetUserMoneyDialog extends Dialog{
	private Context mContext;
	
	private TextView money;

	public GetUserMoneyDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.item_get_user_money_dialog);
		
		initView();
		initDialog();
	}
	
	private void initView(){
		View view = LayoutInflater.from(mContext).inflate(R.layout.item_get_user_money_dialog, null);
		
		money = (TextView)view.findViewById(R.id.get_user_money_num);
	}
	
	private void initDialog(){
		Window dialogWindow = this.getWindow();
		WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		
		dialogWindow.setGravity(Gravity.CENTER);
		
		lp.width = (int)(display.getWidth()*0.8);
		lp.height = (int)(display.getHeight()*0.35);
		dialogWindow.setAttributes(lp);
	}
	
	public void setPhone(String phoneStr){
		money.setText(phoneStr);
	}
}
