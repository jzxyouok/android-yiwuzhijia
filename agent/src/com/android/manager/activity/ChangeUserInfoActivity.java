package com.android.manager.activity;

import java.io.File;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.WebImageView;
import com.android.manager.R;
import com.android.manager.costants.AppConstants;
import com.android.manager.event.OnChangeUserInfoEvent;
import com.android.manager.model.GetUserInfoModel;
import com.android.manager.model.UserModel;
import com.android.manager.protocol.CacheInfo;
import com.android.manager.protocol.ProtocolConst;
import com.android.manager.util.JSONUtil;
import com.android.manager.util.UserInfoCacheUtil;
import com.android.manager.util.headshot.CropImageActivity;
import com.android.manager.util.headshot.HeadShotUtil;
import com.android.manager.util.headshot.MyHeadShotDialog;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;

public class ChangeUserInfoActivity extends BaseActivity implements OnClickListener,BusinessResponse,IXListViewListener{
	private View header;
	
	private View img;
	private View area;
	private View male;
	private View female;
	
	private TextView title;
	private TextView confirm;

	private TextView selling;
	private TextView areaText;
	
	private EditText name;
	private EditText age;
	private EditText salenum;
	private EditText car;
	private EditText home;
	
	private ImageView back;
	private ImageView maleIcon;
	private ImageView femaleIcon;
	
	private WebImageView headImg;
	
	private XListView xlistView;
	
	private LinearLayout ageWrapper;
	private RelativeLayout sexWrapper;
	private LinearLayout sellingWrapper;
	private LinearLayout change_psd_home_wrapper;
	private LinearLayout change_psd_car_wrapper;
	
	private File file_sdcard;
	
	private File mHeadShotDir;
	
	private File imageToUpload;
	
	private static String localTempImageFileName = "";
	
	private String userHeadshotPath;
	
	private boolean sex = true;//true 为男，false为女
	
	private String newName;
	
	private String imgUrl;
	private int city_id;
	private int sex_id;
	private UserModel userModel;
	private GetUserInfoModel getUserInfoModel;
	private CacheInfo cache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_userinfo);
		
		cache = UserInfoCacheUtil.getCacheInfo(this);
		getUserInfoModel=new GetUserInfoModel(this);
		getUserInfoModel.addResponseListener(this);
		getUserInfoModel.getUserinfo(cache.getUid(), cache.getSessionId());
		initImageFileCache();
		initView();
		initData();
	}
	
	private void initView(){
		header = LayoutInflater.from(ChangeUserInfoActivity.this).inflate(
				R.layout.layout_change_user_info, null);
		xlistView = (XListView) findViewById(R.id.change_user_info_list);
		xlistView.setPullLoadEnable(false);
		xlistView.setPullRefreshEnable(false);
		xlistView.setXListViewListener(this, 1);
		xlistView.addHeaderView(header);
		xlistView.setAdapter(null);
		
		male = header.findViewById(R.id.change_psd_male);
		female = header.findViewById(R.id.change_psd_female);
		img = header.findViewById(R.id.change_psd_img_wrapper);
		area = header.findViewById(R.id.change_psd_area_wrapper);
		
		headImg=(WebImageView) header.findViewById(R.id.change_psd_img);
		confirm = (TextView)findViewById(R.id.change_info_title_confirm);
		
		title = (TextView)findViewById(R.id.change_info_title_text);
		title.setText("个人资料");
		
		name = (EditText)header.findViewById(R.id.change_psd_name);
		age = (EditText)header.findViewById(R.id.change_psd_age);
		salenum=(EditText)header.findViewById(R.id.change_psd_selling);
		car=(EditText)header.findViewById(R.id.change_pwd_car);
		home=(EditText)header.findViewById(R.id.change_psd_home);
		
		
		back = (ImageView)findViewById(R.id.change_info_title_back);
		maleIcon = (ImageView)header.findViewById(R.id.change_psd_male_img);
		femaleIcon = (ImageView)header.findViewById(R.id.change_psd_female_img);
		
		
		areaText = (TextView)header.findViewById(R.id.change_psd_area);
		
		sexWrapper = (RelativeLayout)header.findViewById(R.id.register_sex_wrapper);
		ageWrapper = (LinearLayout)header.findViewById(R.id.change_psd_age_wrapper);
		sellingWrapper = (LinearLayout)header.findViewById(R.id.change_psd_selling_wrapper);
		change_psd_home_wrapper=(LinearLayout)header.findViewById(R.id.change_psd_home_wrapper);
		change_psd_car_wrapper=(LinearLayout)header.findViewById(R.id.change_psd_car_wrapper);
		if(cache.getStype() == 2){
			sexWrapper.setVisibility(View.GONE);
			ageWrapper.setVisibility(View.GONE);
			sellingWrapper.setVisibility(View.GONE);
			change_psd_home_wrapper.setVisibility(View.GONE);
			change_psd_car_wrapper.setVisibility(View.GONE);
		}
		
		userModel=new UserModel(this);
		userModel.addResponseListener(this);
		
		setClickListener();
	}
	
	private void initImageFileCache() {
		file_sdcard=Environment.getExternalStorageDirectory();
		mHeadShotDir=new File(file_sdcard, AppConstants.APP_DIR+File.separator+AppConstants.IMAGE_DIR+File.separator+AppConstants.HEAD_SHOT);
		if(!mHeadShotDir.exists())
		{
			mHeadShotDir.mkdirs();
		}
		
	}
	private void initData()
	{
		mHeadShotDir=new File(file_sdcard, AppConstants.APP_DIR+File.separator+AppConstants.IMAGE_DIR+File.separator+AppConstants.HEAD_SHOT);
		if (!mHeadShotDir.exists()) {
			mHeadShotDir.mkdirs();
		}
		String phone=cache.getPhone();
		
		imageToUpload=new File(mHeadShotDir, "headshot_"+phone+".png");
		Log.d("mao","修改页面:用户手机="+phone+"图片地址"+imageToUpload.getAbsolutePath());
		if(imageToUpload.exists())
		{
			Bitmap headShot=HeadShotUtil.getHeadShot(phone);
			if(headShot!=null)
			{
				headImg.setImageBitmap(headShot);
			}
			else
			{
				Log.d("mao","initData  图片加载失败");
			}
		}
	}
	
	private void setClickListener(){
		img.setOnClickListener(this);
		area.setOnClickListener(this);
		back.setOnClickListener(this);
		male.setOnClickListener(this);
		female.setOnClickListener(this);
		confirm.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == AppConstants.FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {
			if (data != null) {
				Uri uri = data.getData();
				if (!TextUtils.isEmpty(uri.getAuthority())) {
					Cursor cursor = getContentResolver().query(uri,
							new String[] { MediaStore.Images.Media.DATA },
							null, null, null);
					if (null == cursor) {
						Toast.makeText(this, "图片没找到", 0).show();
						return;
					}
					cursor.moveToFirst();
					String path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					cursor.close();
					Log.i("mao","path=" + path);
					Intent intent = new Intent(this, CropImageActivity.class);
					intent.putExtra("path", path);
					startActivityForResult(intent, AppConstants.FLAG_MODIFY_FINISH);
				}else
				{
						Log.i("mao","path=" + uri.getPath());
						Intent intent = new Intent(this, CropImageActivity.class);
						intent.putExtra("path", uri.getPath());
						startActivityForResult(intent, AppConstants.FLAG_MODIFY_FINISH);
				}
			}
		}
		if(requestCode == AppConstants.FLAG_CHOOSE_PHONE && resultCode == RESULT_OK) {
			File f = new File(mHeadShotDir,localTempImageFileName);
			Intent intent = new Intent(this, CropImageActivity.class);
			intent.putExtra("path", f.getAbsolutePath());
			startActivityForResult(intent, AppConstants.FLAG_MODIFY_FINISH);
		}
		if(requestCode == 1111 && resultCode == 2222){
			areaText.setText(data.getExtras().getString("city"));
			city_id=data.getIntExtra("city_id",0);
		}
		if(requestCode==AppConstants.FLAG_MODIFY_FINISH&&resultCode == RESULT_OK)
		{
			Log.d("mao","截取头像返回");
			if (data != null) {
				userHeadshotPath=data.getStringExtra("path");
				Log.d("mao","用户截取头像缓存地址"+userHeadshotPath);
				Bitmap headShot=HeadShotUtil.getHeadShot(cache.getPhone());
				if(headShot!=null)
				{
					headImg.setImageBitmap(headShot);
				}
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			
		case R.id.change_psd_img_wrapper:
		{
			 MyHeadShotDialog selectDialog=new MyHeadShotDialog(this)
				{

					@Override
					public void doGoToImg() {
						dismiss();
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_PICK);
						intent.setType("image/*");
						startActivityForResult(intent, AppConstants.FLAG_CHOOSE_IMG);
					}

					@Override
					public void doGoToPhone() {
						this.dismiss();
						String status = Environment.getExternalStorageState();
						if (status.equals(Environment.MEDIA_MOUNTED)) {
							try {
								localTempImageFileName = "";
								localTempImageFileName = String.valueOf((new Date())
										.getTime()) + ".png";
								if (!mHeadShotDir.exists()) {
									mHeadShotDir.mkdirs();
								}
								Intent intent = new Intent(
										android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
								File f = new File(mHeadShotDir, localTempImageFileName);
								Uri u = Uri.fromFile(f);
								intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
								intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
								startActivityForResult(intent, AppConstants.FLAG_CHOOSE_PHONE);
							} catch (ActivityNotFoundException e) {
								Toast.makeText(ChangeUserInfoActivity.this, "相机打开失败", Toast.LENGTH_SHORT).show();
							}
							
						}else
						{
							Toast.makeText(ChangeUserInfoActivity.this, "手机未安装SD卡", Toast.LENGTH_SHORT).show();
						}
					}
					
				};
			 selectDialog.show();
		}
		break;
			
		case R.id.change_psd_area_wrapper:
			Intent intent = new Intent(ChangeUserInfoActivity.this, SelectLocationActivity.class);
			startActivityForResult(intent, 1111);
			break;
			
		case R.id.change_info_title_confirm:
			newName = name.getText().toString();
			if (sex) {
				sex_id=1;
			}else {
				 sex_id=0;
			}
			userModel.uploadUserInfo(name.getText().toString(), sex?1:0, city_id,areaText.getText().toString(),  car.getText().toString(),
					age.getText().toString(),salenum.getText().toString(), home.getText().toString(), imageToUpload);
			
			break;
			
		case R.id.change_info_title_back:
			finish();
			break;
	
		case R.id.change_psd_male:
			sex = true;
			maleIcon.setImageResource(R.drawable.icon_select);
			femaleIcon.setImageResource(R.drawable.icon_unselect);
			break;
			
		case R.id.change_psd_female:
			sex = false;
			maleIcon.setImageResource(R.drawable.icon_unselect);
			femaleIcon.setImageResource(R.drawable.icon_select);
			break;
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.UPDATE_USERINFO)) {
			Toast.makeText(ChangeUserInfoActivity.this, "修改成功", Toast.LENGTH_LONG).show();
			finish();
		}else if(url.endsWith(ProtocolConst.GET_USERINFO)){
			name.setText(getUserInfoModel.user.getName());
			if (getUserInfoModel.user.getCity_name().equals("")) {
				areaText.setText("成都");
			}else {
				
				areaText.setText(getUserInfoModel.user.getCity_name());
			}
			city_id=getUserInfoModel.user.getCity();
			sex_id=getUserInfoModel.user.getSex();
			age.setText(getUserInfoModel.agent.getSale_long());
			salenum.setText(getUserInfoModel.agent.getSale_num());
			car.setText(getUserInfoModel.agent.getCar_type());
			home.setText(getUserInfoModel.agent.getNative_place());
			
			if (sex_id==1) {
				maleIcon.setImageResource(R.drawable.icon_select);
				femaleIcon.setImageResource(R.drawable.icon_unselect);
			}else {
				maleIcon.setImageResource(R.drawable.icon_unselect);
				femaleIcon.setImageResource(R.drawable.icon_select);
			
			}
			String imageUrl=getUserInfoModel.user.getPic();

			if(imageUrl!=null&&!("".equals(imageUrl)))
			{
				headImg.setImageWithURL(ChangeUserInfoActivity.this, AppConstants.WEBHOME+JSONUtil.getImagePath(imageUrl));
			}
			
			
		}
		
	}

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		EventBus.getDefault().post(new OnChangeUserInfoEvent());
	}

	
}
