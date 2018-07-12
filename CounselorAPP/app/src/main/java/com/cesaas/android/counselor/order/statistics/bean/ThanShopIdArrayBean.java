package com.cesaas.android.counselor.order.statistics.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created 2017/3/16 11:55
 * Version 1.zero
 */
public class ThanShopIdArrayBean {

    public static JSONArray getArrayItem(List<String> str){
        JSONArray array=new JSONArray();
        for (int i=0;i<str.size();i++){
            array.put(str.get(i));
        }

        return array;
    }
}
