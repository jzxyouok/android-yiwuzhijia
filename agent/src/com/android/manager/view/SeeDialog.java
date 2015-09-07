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
import android.widget.Button;

public class SeeDialog extends Dialog{
	
	private int msg;
	private Context mContext;
	
	private Button seen;
	private Button giveUp;
	
	private String pButtonStr;
	private String nButtonStr;
	public void setBtnText(String str1,String str2)
	{
		pButtonStr=str1;
		nButtonStr=str2;
	}
	
	public void setMsg(int msg)
	{
		this.msg=msg;
	}
	
	
	public interface OnDialogClickListener{
		public void onPositiveButtonClick(int position);
		public void onGiveUpButtonClick();
		
	};
	
	private OnDialogClickListener dialogClickListener;
	
	
	public SeeDialog(Context context,int theme) {
		super(context,theme);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.item_see_dialog);
		
		seen = (Button)findViewById(R.id.see_dialog_seen_btn);
		if(pButtonStr!=null)
		{
			seen.setText(pButtonStr);
		}
		giveUp = (Button)findViewById(R.id.see_dialog_give_up_btn);
		if(nButtonStr!=null)
		{
			giveUp.setText(nButtonStr);
		}
		initDialog();
		seen.setOnClickListener(new clickListener());
		giveUp.setOnClickListener(new clickListener());
	}
	public void setClickListener(OnDialogClickListener dialogClickListener){
		this.dialogClickListener=dialogClickListener;
	}

	private void initDialog(){
		this.setCanceledOnTouchOutside(true);
		
		Window dialogWindow = this.getWindow();
		WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		
		dialogWindow.setGravity(Gravity.CENTER);
		
		lp.width = (int)(display.getWidth()*0.8);
		lp.height = (int)(display.getHeight()*0.3);
		dialogWindow.setAttributes(lp);
		
	}
	
	private class clickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.see_dialog_seen_btn){
				dialogClickListener.onPositiveButtonClick(SeeDialog.this.msg);
				SeeDialog.this.dismiss();
				
			}else{
				
				dialogClickListener.onGiveUpButtonClick();
				SeeDialog.this.dismiss();
			}
			
		}
		
	}
}
