
package com.android.house.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.service.textservice.SpellCheckerService.Session;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "SESSION")
public class SESSION  extends Model
{

     @Column(name = "uid")
     public int uid;

     @Column(name = "sid")
     public String sid;
     
     //单例，整个应用一个SESSION
     private static SESSION instance;
     
     private SESSION()
     {
    	 
     }
     public static void initSession(int id)
     {
    	 if(instance==null)
    	 {
    		 instance=new SESSION();
    		 instance.uid=id;
    	 }
     }
     public static SESSION getInstance()
     {
         if(instance!=null)
         {
        	 return instance;
         }
         else
         {
        	 instance=new SESSION();
        	 instance.uid=-1;
         }
         return instance;
     }
     

 public static SESSION fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }
     
    SESSION localItem=instance;
     JSONArray subItemArray;
     localItem.uid = jsonObject.optInt("uid");

     localItem.sid = jsonObject.optString("sid");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("uid", uid);
     localItemObject.put("sid", sid);
     return localItemObject;
 }

}
