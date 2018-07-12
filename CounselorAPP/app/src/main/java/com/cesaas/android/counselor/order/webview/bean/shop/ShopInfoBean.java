package com.cesaas.android.counselor.order.webview.bean.shop;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/8/17 10:12
 * Version 1.0
 */

public class ShopInfoBean {

    private int id;
    private String name;
    private int shop_type;

    public JSONObject getShopInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("id",getId());
            obj.put("name",getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public int getShop_type() {
        return shop_type;
    }

    public void setShop_type(int shop_type) {
        this.shop_type = shop_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
