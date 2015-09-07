package com.android.manager.util;


public class JSONUtil {
	public static String getImagePath(String path)
	{
		path.replace("\\", "/");
		return path;
	}
}
