package com.cesaas.android.counselor.order.webview.resultbean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2018/1/17 14:03
 * Version 1.0
 */

public class QueryShop {

    private JSONArray year;
    private JSONArray season;
    private JSONArray band;
    private JSONArray smallSort;
    private JSONArray bigSort;
    private JSONArray category;

    public JSONObject getQueryInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("year",getYear());
            obj.put("season",getSeason());
            obj.put("band",getBand());
            obj.put("smallSort",getSmallSort());
            obj.put("bigSort",getBigSort());
            obj.put("category",getCategory());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public JSONArray getCategory() {
        return category;
    }

    public void setCategory(JSONArray category) {
        this.category = category;
    }

    public JSONArray getYear() {
        return year;
    }

    public void setYear(JSONArray year) {
        this.year = year;
    }

    public JSONArray getSeason() {
        return season;
    }

    public void setSeason(JSONArray season) {
        this.season = season;
    }

    public JSONArray getBand() {
        return band;
    }

    public void setBand(JSONArray band) {
        this.band = band;
    }

    public JSONArray getSmallSort() {
        return smallSort;
    }

    public void setSmallSort(JSONArray smallSort) {
        this.smallSort = smallSort;
    }

    public JSONArray getBigSort() {
        return bigSort;
    }

    public void setBigSort(JSONArray bigSort) {
        this.bigSort = bigSort;
    }
}
