package com.cesaas.android.counselor.order.manager.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/9/14 17:38
 * Version 1.0
 */

public class ContractsBean {

    private int CounselorId;
    private int ShopId;
    private String Description;
    private String CounselorName;
    private String ImageUrl;


    public JSONObject getInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("CounselorId",getCounselorId());
            obj.put("ShopId",getShopId());
            obj.put("Description",getDescription());
            obj.put("ImageUrl",getImageUrl());
            obj.put("CounselorName",getCounselorName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getCounselorId() {
        return CounselorId;
    }

    public void setCounselorId(int counselorId) {
        CounselorId = counselorId;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCounselorName() {
        return CounselorName;
    }

    public void setCounselorName(String counselorName) {
        CounselorName = counselorName;
    }
}
