
package com.android.house.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

public class STATUS  extends Model
{

     public int succeed;

     public int error_code;

     public String error_desc;

 public static STATUS fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     STATUS   localItem = new STATUS();

     JSONArray subItemArray;

     localItem.succeed = jsonObject.optInt("succeed");

     localItem.error_code = jsonObject.optInt("error_code");

     localItem.error_desc = jsonObject.optString("error_desc");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("succeed", succeed);
     localItemObject.put("error_code", error_code);
     localItemObject.put("error_desc", error_desc);
     return localItemObject;
 }

}
