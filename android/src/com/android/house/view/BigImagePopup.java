package com.android.house.view;

import com.BeeFramework.view.WebImageView;
import com.funmi.house.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class BigImagePopup {
	
	View container;
	PopupWindow window;
	WebImageView image;
	LayoutInflater layoutInflater;
	Context mContext;
	private int windowWidth;
	
	public void initView(Context context)
	{
		mContext=context;
		layoutInflater=LayoutInflater.from(context);
		container=layoutInflater.inflate(R.layout.popup_big_image, null);
		image=(WebImageView) container.findViewById(R.id.pupup_bigimage);
		WindowManager manager=(WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		
		int wid = (int) (manager.getDefaultDisplay().getWidth()*0.9);
		int height = (int) (manager.getDefaultDisplay().getHeight()*0.5);
		
		window = new PopupWindow(container,wid,height);
	}
	
	
	public void showBigImage(String url,View view,int width,int height)
	{
		this.image.setImageWithURL(mContext, url);
		this.image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				window.dismiss();
			}
		});
		window.setBackgroundDrawable(new BitmapDrawable());
		window.setOutsideTouchable(true);
		window.setFocusable(true);
		window.setContentView(container);
		window.showAtLocation(container, Gravity.BOTTOM, 0, 0);
	}
}
