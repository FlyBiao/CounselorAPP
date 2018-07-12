package com.cesaas.android.counselor.order.member.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created 2017/4/21 18:27
 * Version 1.zero
 */
public class BindVipIdBean implements Serializable{
    private int VipId;
    private int pos;


    public JSONObject getVips() {
        JSONObject json = new JSONObject();
        try {
            json.put("VipId", getVipId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getVipId() {
        return VipId;
    }

    public void setVipId(int vipId) {
        VipId = vipId;
    }
}
