package com.android.house.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.house.businessresponse.ForgetPwdBusinessResponse;
import com.android.house.model.ForgetPasswordModel;
import com.funmi.house.R;

public class ForgetPsdDialog extends Dialog  implements OnClickListener{
	private Context mContext;
	
	private Button cancel;
	private Button confirm;
	
	private TextView error;
	
	private EditText input;
	
	private ImageView close;
	private ImageView seePsd;
	
	private String inputStr;
	
	
	private ForgetPasswordModel forgetPasswordModel;
	private ForgetPwdBusinessResponse response;
	
	public ForgetPsdDialog(Context context, int theme,Handler handler) {
		super(context, theme);
		mContext = context;
		this.forgetPasswordModel=new ForgetPasswordModel(mContext);
		this.response=new ForgetPwdBusinessResponse(mContext, handler);
		this.forgetPasswordModel.addResponseListener(response);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.item_forget_psd_dialog);
		
		initView();
		initDialog();
	}
	
	private void initView(){
		cancel = (Button)findViewById(R.id.forget_psd_dialog_cancel);
		confirm = (Button)findViewById(R.id.forget_psd_dialog_confrim);
		
		error = (TextView)findViewById(R.id.forget_psd_dialog_error);
		
		input = (EditText)findViewById(R.id.forget_psd_dialog_new_psd);
		
		close = (ImageView)findViewById(R.id.forget_psd_dialog_close);
		seePsd = (ImageView)findViewById(R.id.forget_psd_dialog_see_psd);
		
		setClickListener();
	}
	
	public void setClickListener(){
		close.setOnClickListener(this);
		cancel.setOnClickListener(this);
		confirm.setOnClickListener(this);
		
		seePsd.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN)
					input.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				else if(event.getAction() == MotionEvent.ACTION_UP)
					input.setTransformationMethod(PasswordTransformationMethod.getInstance());
				
				return true;
			}
		});
	}
	
	private void initDialog(){
		Window dialogWindow = this.getWindow();
		WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		
		dialogWindow.setGravity(Gravity.CENTER);
		
		lp.dimAmount = 0.7f;
		lp.width = (int)(display.getWidth()*0.8);
		lp.height = (int)(display.getHeight()*0.5);
		dialogWindow.setAttributes(lp);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.forget_psd_dialog_cancel:
			this.dismiss();
			break;
		
		case R.id.forget_psd_dialog_close:
			this.dismiss();
			break;
		
		case R.id.forget_psd_dialog_confrim:
			inputStr = input.getText().toString();
			/*
			if(inputStr.matches("[0-9]+")){
				error.setVisibility(View.VISIBLE);
			}else{
				error.setVisibility(View.GONE);
			}
			*/
			//发送忘记密码命令
			this.forgetPasswordModel.forgetPwd(inputStr);
			break;
		}
	}
}
