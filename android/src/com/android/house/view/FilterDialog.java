package com.android.house.view;

import java.util.ArrayList;
import java.util.List;

import com.android.house.adapter.FilterAdapter;
import com.android.house.adapter.SelectLocationDialogAdapter;
import com.android.house.events.OnFilterItemClickedEvent;
import com.android.house.model.HouseModel;
import com.android.house.model.HouseModel.SEARCH_TYPE;
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

public class FilterDialog extends Dialog {
	private Context mContext;
	
	private ListView list;
	private FilterAdapter adapter;
	private List<String> mFilterInfo = new ArrayList<String>();
	
	private int titleHeight;
	
	private int dialogType;
	public static final int FILTER_PRICE = 0;
	public static final int FILTER_LOCATION = 1;
	public static final int FILTER_HOUSETYPE = 2;
	public static final int FILTER_DECORATION = 3;
	
	private String filterStr;

	public FilterDialog(Context context, int theme, int type) {
		super(context, theme);
		mContext = context;
		dialogType = type;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("mao","dialog created");
	}
	
	private void initDialog(){
		Window dialogWindow = this.getWindow();
		WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();

		lp.y =  (int)- (display.getHeight()*0.380 - titleHeight);
		
		switch(dialogType){
		case FILTER_PRICE:
			lp.x = -(int)(display.getWidth()*0.375);
			break;
			
		case FILTER_LOCATION:
			lp.x = - (int)(display.getWidth()*0.125);
			break;
			
		case FILTER_HOUSETYPE:
			lp.x = (int)(display.getWidth()*0.125);
			break;
			
		case FILTER_DECORATION:
			lp.x = (int)(display.getWidth()*0.375);
			break;
		}
		
		lp.dimAmount = 0f;
		lp.width = (int)(display.getWidth()*0.25);
		lp.height = (int)(display.getHeight()*0.2);
		lp.alpha=0.8f;
		dialogWindow.setAttributes(lp);
	}
	
	public void initListView(List<String> filterInfo){
		initDialog();
		
		mFilterInfo = filterInfo;
		
		list = (ListView)findViewById(R.id.filter_list);
		adapter = new FilterAdapter(mContext, mFilterInfo);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new FilterItemClickListener());
	}
	
	/**
	 * 
	 * @param layout 布局资源文件
	 * @param height 高度
	 * @param locateInfo 信息
	 */
	public void show(int layout, int height, List<String> filterInfo) {
		this.setTitleHeight(height);
		this.setCanceledOnTouchOutside(true);
		this.setContentView(layout);
		this.initListView(filterInfo);
		this.show();
	}
	
	public void setListInfo(List<String> filterInfo){
		mFilterInfo = filterInfo;
	}
	
	public void setTitleHeight(int h){
		titleHeight = h;
	}
	
	public String getFilterStr(){
		return filterStr;
	}

	
	private class FilterItemClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Log.d("mao", "item clicked");
			if(position==0)
			{
				FilterDialog.this.dismiss();
				return ;
			}
			switch (FilterDialog.this.dialogType) {
			case FILTER_PRICE:
				postEvent(SEARCH_TYPE.PRICE, position);
				break;
			case FILTER_LOCATION:
				postEvent(SEARCH_TYPE.AREA, position);
				break;
			case FILTER_HOUSETYPE:
				postEvent(SEARCH_TYPE.APARTMENT, position);
				break;
			case FILTER_DECORATION:
				postEvent(SEARCH_TYPE.DECORATE, position);
				break;
			default:
				break;
			}
				
		}
			
	}
	
	private void postEvent(SEARCH_TYPE type,int position)
	{
		EventBus.getDefault().post(new OnFilterItemClickedEvent(type, position));
		FilterDialog.this.dismiss();
	}

	@Override
	public void dismiss() {
		Log.d("mao","FilterDialog dismiss");
		super.dismiss();
	}
	
	
}
