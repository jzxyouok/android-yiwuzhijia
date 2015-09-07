package com.android.house.activity;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.WebImageView;
import com.android.house.costants.AppConstants;
import com.android.house.events.OnChangeUserInfoEvent;
import com.android.house.model.GetUserInfoModel;
import com.android.house.model.UserModel;
import com.android.house.protocol.CacheInfo;
import com.android.house.protocol.ProtocolConst;
import com.android.house.util.JSONUtil;
import com.android.house.util.UserInfoCacheUtil;
import com.android.house.util.headshot.CropImageActivity;
import com.android.house.util.headshot.HeadShotUtil;
import com.android.house.util.headshot.MyHeadShotDialog;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.funmi.house.R;

public class ChangeUserInfoActivity extends BaseActivity implements OnClickListener,BusinessResponse{
	private View img;
	private View area;
	private View male;
	private View female;
	private Button confirm;
	
	private WebImageView change_psd_img;
	
	private static String localTempImageFileName = "";
	private TextView title;

	private TextView areaText;
	
	private EditText name;
	
	
	private ImageView back;
	
	private boolean sex = true;//true 为男，false为女
	
	private String newName;
	
	
	private File file_sdcard;
	
	private File mHeadShotDir;
	
	private File imageToUpload;

	
	private ImageView  changeSexFemale;
	private ImageView changeSexMale;
	
	private UserModel userModel;
	
	private String userHeadshotPath;
	private int city_id;
	private int sex_id;
	
	private CacheInfo cacheInfo;
	
	private GetUserInfoModel getUserInfo;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_userinfo);
		initImageFileCache();
		initView();
		initData();
		
	}
	
	private void initView(){
		img = findViewById(R.id.change_psd_img_wrapper);
		area = findViewById(R.id.change_psd_area_wrapper);
		change_psd_img=(WebImageView)findViewById(R.id.change_psd_img);
		confirm = (Button)findViewById(R.id.change_psd_confirm);
		
		title = (TextView)findViewById(R.id.title_text);
		title.setText("个人资料");
		
		name = (EditText)findViewById(R.id.change_psd_name);

		back = (ImageView)findViewById(R.id.title_back);
		
		male = findViewById(R.id.change_psd_male);
		female = findViewById(R.id.change_psd_female);
		areaText = (TextView)findViewById(R.id.change_psd_area);
		
		changeSexFemale = (ImageView) findViewById(R.id.change_psd_female_img);
		changeSexMale = (ImageView) findViewById(R.id.change_psd_male_img);
		
		userModel=new UserModel(this);
		userModel.addResponseListener(this);
		
		getUserInfo=new GetUserInfoModel(this);
		getUserInfo.addResponseListener(this);
		
		cacheInfo=UserInfoCacheUtil.getCacheInfo(this);
		
		getUserInfo.getUserinfo(cacheInfo.getUid(), cacheInfo.getSessionId());
		
		setClickListener();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	private void initData()
	{
		mHeadShotDir=new File(file_sdcard, AppConstants.APP_DIR+File.separator+AppConstants.IMAGE_DIR+File.separator+AppConstants.HEAD_SHOT);
		if (!mHeadShotDir.exists()) {
			mHeadShotDir.mkdirs();
		}
		cacheInfo=UserInfoCacheUtil.getCacheInfo(this);
		String phone=cacheInfo.getPhone();
		imageToUpload=new File(mHeadShotDir, "headshot_"+phone+".jpg");
		if(imageToUpload.exists())
		{
			Bitmap headShot=HeadShotUtil.getHeadShot(phone);
			if(headShot!=null)
			{
				//change_psd_img.setImageBitmap(headShot);
				//headShot.recycle();
			}
		}
	}
	
	private void initImageFileCache() {
		file_sdcard=Environment.getExternalStorageDirectory();
		mHeadShotDir=new File(file_sdcard, AppConstants.APP_DIR+File.separator+AppConstants.IMAGE_DIR+File.separator+AppConstants.HEAD_SHOT);
		if(!mHeadShotDir.exists())
		{
			mHeadShotDir.mkdirs();
		}
		//imageToUpload=new File(mHeadShotDir, "headshot_18178722503.jpg");
		
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
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.change_psd_img_wrapper:
			{
				 MyHeadShotDialog selectDialog=new MyHeadShotDialog(this,R.style.add_dialog)
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
			
		case R.id.change_psd_confirm://修改确认按钮
			if (sex) {
				sex_id=1;
			}else {
				 sex_id=2;
			}


			//this.resetUserInfoModel.uploadImage(this.imageToUpload);
			newName=name.getText().toString();
			userModel.updateUserInfo(newName, sex_id, city_id, this.imageToUpload,areaText.getText().toString());
			break;
			
		case R.id.title_back:
			finish();
			break;
			
		case R.id.change_psd_male:
			sex = true;
			changeSexMale.setImageDrawable(getResources().getDrawable(R.drawable.icon_select));
			changeSexFemale.setImageDrawable(getResources().getDrawable(R.drawable.icon_unselect));
			break;
			
		case R.id.change_psd_female:
			sex = false;
			changeSexFemale.setImageDrawable(getResources().getDrawable(R.drawable.icon_select));
			changeSexMale.setImageDrawable(getResources().getDrawable(R.drawable.icon_unselect));
			
			break;
		}
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
				Bitmap headShot=HeadShotUtil.getHeadShot(cacheInfo.getPhone());
				if(headShot!=null)
				{
					change_psd_img.setImageBitmap(headShot);
				}
				EventBus.getDefault().post(new OnChangeUserInfoEvent());
			}
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.UPDATE_USERINFO)) {
			Log.d("mao","修改个人信息返回结果:"+jo.toString());
			
			Toast.makeText(ChangeUserInfoActivity.this, "修改成功", Toast.LENGTH_LONG).show();
			finish();
			
		}else if (url.endsWith(ProtocolConst.GET_USERINFO)) {
			
			name.setText(getUserInfo.user.getUsername());
			if (getUserInfo.user.getSex()==1) {
				changeSexMale.setImageDrawable(getResources().getDrawable(R.drawable.icon_select));
				changeSexFemale.setImageDrawable(getResources().getDrawable(R.drawable.icon_unselect));
			}else {
				changeSexFemale.setImageDrawable(getResources().getDrawable(R.drawable.icon_select));
				changeSexMale.setImageDrawable(getResources().getDrawable(R.drawable.icon_unselect));
				
			}
			
			String imageUrl=getUserInfo.user.getPic();
			if (imageUrl!=null) {
				change_psd_img.setImageWithURL(ChangeUserInfoActivity.this, AppConstants.WEBHOME+JSONUtil.getImagePath(imageUrl));
			}
			
			areaText.setText(getUserInfo.user.getCity_name());
		}
	}

	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
	}

	
}
