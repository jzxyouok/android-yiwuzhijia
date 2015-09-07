package com.android.manager.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.android.manager.R;
import com.android.manager.util.BitmapUtil;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImagePickerFragment extends Fragment implements OnItemClickListener{

	List<String> imagePath;//图片在手机中的地址
	List<Bitmap> imageCach;//图片缓存
	
	private int lastPostition=0;
	
	Object key=new Object();
	public static int MAX_IMAGE_SIZE=3;
	private GridView gridView;
	private MyGridAdapter adapter;
	View view =null;
	public static  Handler handler;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		handler=new Handler(getActivity().getMainLooper())
		{

			@Override
			public void handleMessage(Message msg) {
				String path=msg.getData().getString("path");
				ImagePickerFragment.this.addBitmap(path);
			}
			
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d("mao","fragment onCreateView");
			view =inflater.inflate(R.layout.activity_selectimg, null);
			initView();
			initData();
		
		return view;
	}
	
	//1.选取图片界面初始化
		private void initView()
		{
			gridView = (GridView) view.findViewById(R.id.noScrollgridview);
			gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
			gridView.setOnItemClickListener(this);
			
		}
		
		private void initData()
		{
			imagePath=new ArrayList<String>();
			imageCach=new ArrayList<Bitmap>();
			adapter=new MyGridAdapter(getActivity());
			gridView.setAdapter(adapter);
		}
		
		class MyGridAdapter extends BaseAdapter
		{
			LayoutInflater inflater;
			
			private int addPostionRes=R.drawable.icon_addpic_unfocused;
			
			public MyGridAdapter(Context context)
			{
				inflater=LayoutInflater.from(context);
			}
		
			@Override
			public int getCount() {
				// TODO 自动生成的方法存根
				return 1;
			}

			@Override
			public Object getItem(int position) {
				// TODO 自动生成的方法存根
				return null;
			}

			@Override
			public long getItemId(int position) {
				// TODO 自动生成的方法存根
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
					convertView=inflater.inflate(R.layout.item_published_grida, null);
					ImageView image=(ImageView) convertView.findViewById(R.id.item_grida_image);
				if(!imageCach.isEmpty())
				{
					image.setImageBitmap(imageCach.get(position));
				}
					
				return convertView;
			}
			
			
			public void update()
			{
				Log.d("mao", "grid view update");
				notifyDataSetChanged();
			}
		}
		
		class ViewHolder
		{
			public ImageView image;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			synchronized (key) {
				
					//向相册去图片
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_PICK);
					intent.setType("image/*");
					getActivity().startActivityForResult(intent, 200);
				key.notify();
			}
			
		}
		
		
		public void addBitmap(String path)
		{
			synchronized (key) {
				try {
					if(imageCach.isEmpty())
					{
					imageCach.add(BitmapUtil.getThumb(path));
					imagePath.add(path);
					}
					else
					{
						 Bitmap bm=imageCach.get(0);
						 bm.recycle();
						 imageCach.clear();
						 imagePath.clear();
						 imageCach.add(BitmapUtil.getThumb(path));
						 imagePath.add(path);
					}
				} catch (IOException e) {
					lastPostition--;
					Log.d("mao",e.getMessage());
				}
				adapter.update();
				key.notify();
			}
			
			
		}

		public List<String> getImagePathList()
		{
			return imagePath;
		}

		public void setMaxImage()
		{
			
		}
}
