package com.android.house.view;

import java.util.ArrayList;
import java.util.List;

import com.android.house.adapter.SelectLocationDialogAdapter;
import com.android.house.events.OnChangeCityEvent;
import com.android.house.events.OnRefreshEvent;
import com.external.eventbus.EventBus;
import com.funmi.house.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectLocationDialog extends Dialog{
	
	private View view;
	
	private Context mContext;
	
	private TextView current;
	private TextView locating;
	
	private ListView list;
	
	private TextView refresh;
	private SelectLocationDialogAdapter adapter;
	
	private int titleHeight;
	String mCurrentCityName;
	private List<String> mLocateInfo = new ArrayList<String>();

	public SelectLocationDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.item_select_location_dialog);
		initView();
		initDialog();
	}
	
	@Override
	public void onAttachedToWindow() {
		
		super.onAttachedToWindow();
	}

	private void initView(){
		current = (TextView)findViewById(R.id.select_location_dialog_current);
		locating = (TextView)findViewById(R.id.select_location_dialog_locate);		
		list = (ListView)findViewById(R.id.select_location_dialog_list);
		refresh=(TextView)findViewById(R.id.select_location_refresh);
		current.setText(mCurrentCityName);
		adapter = new SelectLocationDialogAdapter(mContext, mLocateInfo);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new ChangeCityListener());
		locating.setOnClickListener(new RefreshClickListener());
		locating.setText("成都");
	}
	
	private void initDialog(){
		Window dialogWindow = this.getWindow();
		WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();

		lp.dimAmount = 0f;
		lp.x = (int)(display.getWidth()*0.25);
		lp.y = (int)- (display.getHeight()*0.20 - titleHeight);
		lp.width = (int)(display.getWidth()*0.5);
		lp.height = (int)(display.getHeight()*0.60);
		lp.alpha=0.90f;
		dialogWindow.setAttributes(lp);
	}
	
	public void initListView(List<String> locateInfo,String currentCityName)
	{
		mCurrentCityName=currentCityName;
		mLocateInfo = locateInfo;
		
	}

	public void setTitleHeight(int h){
		titleHeight = h;
	}
	
	public void setCurrent(String currentLocation){
		current.setText(currentLocation);
	}
	
	public void setLocating(String locatingLocation){
		locating.setText(locatingLocation);
	}
	
	public void setListInfo(List<String> locateInfo){
		mLocateInfo = locateInfo;
	}

	@Override
	public void onDetachedFromWindow() {
		// TODO 自动生成的方法存根
		super.onDetachedFromWindow();
		Log.d("mao","dialog detached from window");
	}
	
	class RefreshClickListener implements android.view.View.OnClickListener
	{

		@Override
		public void onClick(View arg0) {
			
			locating.setText("定位中");
			EventBus.getDefault().post(new OnRefreshEvent(1));
		}
		
	}
	
	class ChangeCityListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
				current.setText(mLocateInfo.get(position));
				EventBus.getDefault().post(new OnChangeCityEvent(mLocateInfo.get(position)));
				SelectLocationDialog.this.dismiss();
		}
	}
}
