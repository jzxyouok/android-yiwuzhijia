package com.android.manager.util.headshot;

import java.io.File;

import com.android.manager.costants.AppConstants;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class HeadShotUtil {
	
	public static Bitmap getHeadShot(String phone)
	{
		Bitmap bitmap=null;
		String seperator=File.separator;
		String path=Environment.getExternalStorageDirectory()+seperator+AppConstants.APP_DIR+
				seperator+AppConstants.IMAGE_DIR+seperator+AppConstants.HEAD_SHOT
				+seperator+AppConstants.HEAD_IMAGE_PRE+phone+".png";
		File headImage=new File(path);
		if(!headImage.exists())
		{
			Log.d("mao",headImage.getAbsolutePath());
			Log.d("mao", "文件不存在");
		}
		else
		{
			try
			{
			bitmap=BitmapFactory.decodeFile(headImage.getAbsolutePath());
			}catch(Exception e)
			{
				Log.d("mao", "BitmapFactory error");
				Log.d("mao",e.getMessage());
				e.printStackTrace();
			}
		}
		return bitmap;
	}
	
}
