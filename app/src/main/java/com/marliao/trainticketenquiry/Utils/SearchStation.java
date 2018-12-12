package com.marliao.trainticketenquiry.Utils;

import android.content.Context;
import android.hardware.camera2.params.StreamConfigurationMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ConcurrentModificationException;

public class SearchStation {
    private static SearchStation searchStation=null;
    private JSONObject jsonObject;

    private SearchStation(Context context){
        try {
            InputStream inputStream = context.getAssets().open("tuniu.json");
            String jsonString  = StreamUtil.StreamToString(inputStream);
            jsonObject = new JSONObject(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static SearchStation getInstence(Context context){
        if (searchStation==null){
            searchStation=new SearchStation(context);
        }
        return searchStation;
    }
    public String getStationcoe(String stationName){
        String code=null;
        try {
            if (jsonObject != null) {
                if (jsonObject.has(stationName)) {
                    code=jsonObject.getString(stationName);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return code;
        }
    }
}
