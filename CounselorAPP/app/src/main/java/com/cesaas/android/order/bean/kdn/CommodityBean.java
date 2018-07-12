package com.cesaas.android.order.bean.kdn;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description 商品
 * Created at 2018/4/20 14:36
 * Version 1.0
 */

public class CommodityBean {
    private String GoodsName;

    public JSONObject getCommodity(){

        JSONObject obj=new JSONObject();
        try {
            obj.put("GoodsName",getGoodsName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

}
