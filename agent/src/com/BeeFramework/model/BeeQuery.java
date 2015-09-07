package com.BeeFramework.model;

import android.content.Context;
import com.external.androidquery.AQuery;
import com.external.androidquery.callback.AjaxCallback;

import java.util.Map;


public class BeeQuery<T> extends AQuery {
	public BeeQuery(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public static final int ENVIRONMENT_PRODUCTION = 1;
	public static final int ENVIROMENT_DEVELOPMENT = 2;
	public static final int ENVIROMENT_MOCKSERVER = 3;
	
	public static int environment() 
	{
		return ENVIROMENT_DEVELOPMENT;
	}
	
	public static String serviceUrl()
	{
		if (ENVIRONMENT_PRODUCTION == BeeQuery.environment()) 
		{	
			return "http://121.41.13.145/yiwuzhijia/web/app_dev.php/";
//			return "http://121.41.109.111/yiwuzhijia/web/app_dev.php/";
		}
		else 
		{
			return "http://121.41.13.145/yiwuzhijia/web/app_dev.php/";
//			return "http://121.41.109.111/yiwuzhijia/web/app_dev.php/";
		}
	}
	public <K> AQuery ajax(AjaxCallback<K> callback){

		if (BeeQuery.environment() == BeeQuery.ENVIROMENT_MOCKSERVER)
		{
			MockServer.ajax(callback);
			return null;
		}
        else
        {
            String url = callback.getUrl();
            String absoluteUrl = getAbsoluteUrl(url);

            callback.url(absoluteUrl);

        }

        if(BeeQuery.environment() == BeeQuery.ENVIROMENT_DEVELOPMENT)
        {
            DebugMessageModel.addMessage((BeeCallback)callback);
        }

		return (BeeQuery)super.ajax(callback);
	}

    public <K> AQuery ajaxAbsolute(AjaxCallback<K> callback){

        return (BeeQuery)super.ajax(callback);
    }

	public <K> AQuery ajax(String url, Map<String, ?> params, Class<K> type, BeeCallback<K> callback){
						
		callback.type(type).url(url).params(params);
		
		if (BeeQuery.environment() == BeeQuery.ENVIROMENT_MOCKSERVER)
		{
			MockServer.ajax(callback);
			return null;
		}
        else
        {
            String absoluteUrl = getAbsoluteUrl(url);
            callback.url(absoluteUrl);
        }
		return ajax(callback);
	}


    public static String getAbsoluteUrl(String relativeUrl) {
        return  BeeQuery.serviceUrl() + relativeUrl;
    }
}