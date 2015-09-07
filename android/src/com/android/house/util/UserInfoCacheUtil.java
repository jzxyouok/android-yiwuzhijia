package com.android.house.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.android.house.protocol.CacheInfo;



public class UserInfoCacheUtil {

	
	public static int  getUid(Context context)
	{
		SharedPreferences shared=context.getSharedPreferences("userinfo_cache", 0);
		int uid=shared.getInt("uid", -1);
		return uid;
	}

	public static void refreshSessionid(Context context,String sessionId)
	{
		SharedPreferences shared=context.getSharedPreferences("userinfo_cache", 0);
		Editor editor=shared.edit();
		editor.putString("sessionid", sessionId);
		editor.putBoolean("isuserlogin", true);
		editor.commit();
	}
	
	public static CacheInfo getCacheInfo(Context context)
	{
		CacheInfo cacheInfo=new CacheInfo();
		SharedPreferences sharedPreferenced=context.getSharedPreferences("userinfo_cache", 0);
		
		cacheInfo.setUid(sharedPreferenced.getInt("user_id", -1));
		cacheInfo.setSessionId(sharedPreferenced.getString("sessionid", null));
		cacheInfo.setPhone(sharedPreferenced.getString("phone", null));
		cacheInfo.setLogin(sharedPreferenced.getBoolean("isuserlogin", false));
		cacheInfo.setNick_name(sharedPreferenced.getString("nickname", null));
		cacheInfo.setCity_id(sharedPreferenced.getInt("cityid", -1));
		
		return cacheInfo;
	}

	public static void saveCacheInfo(Context context,CacheInfo cacheInfo)
	{
		SharedPreferences sharedPreferences=context.getSharedPreferences("userinfo_cache", 0);
		Editor editor=sharedPreferences.edit();
		
		editor.putString("sessionid", cacheInfo.getSessionId());
		editor.putString("phone", cacheInfo.getPhone());
		editor.putBoolean("isuserlogin", cacheInfo.isLogin());
		editor.putInt("user_id", cacheInfo.getUid());
		editor.putString("nickname", cacheInfo.getNick_name());
		editor.putInt("cityid", cacheInfo.getCity_id());
		editor.commit();
	}

	public static void clearCahce(Context context)
	{
		SharedPreferences sharedPreferences=context.getSharedPreferences("userinfo_cache", 0);
		Editor editor=sharedPreferences.edit();
		editor.putString("sessionid", null);
		editor.putString("phone", null);
		editor.putBoolean("isuserlogin", false);
		editor.putInt("user_id",-1);
		editor.putString("nickname",null);
		editor.putInt("cityid", -1);
		editor.commit();
		sharedPreferences=null;
	}

	public static String getUserPhone(Context context)
	{
		SharedPreferences sharedPreferences=context.getSharedPreferences("userinfo_cache", 0);
		String phone=sharedPreferences.getString("phone", null);
		sharedPreferences=null;
		return phone;
	}
	
	public static boolean isUserLogin(Context context)
	{
		SharedPreferences shared=context.getSharedPreferences("userinfo_cache", 0);
		
		return shared.getBoolean("isuserlogin", false);
				
	}


}
