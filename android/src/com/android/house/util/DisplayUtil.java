package com.android.house.util;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class DisplayUtil {

	
	
	public  static Point getDisplayCenter(Context context,int header,int footer)
	{
		if(header<0)
		{
			header=0;
		}
		if(footer<0)
		{
			footer=0;
		}
		Point point=new Point();
		
		DisplayMetrics displayMetric=context.getResources().getDisplayMetrics();
		int width=displayMetric.widthPixels;
		int height=displayMetric.heightPixels;
		
		point.set(width/2, (height-header-footer)/2);
		
		return point;
	}
	
	
	public static  int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
	
	
	public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
}
