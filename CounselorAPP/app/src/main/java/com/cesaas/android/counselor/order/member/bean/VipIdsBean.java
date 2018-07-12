package com.cesaas.android.counselor.order.member.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2018/3/10 14:11
 * Version 1.0
 */

public class VipIdsBean {
    private int Id;
    private String Name;
    private int CounselorId;

    public JSONObject getVipIds(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("Id",getId());
            obj.put("Name",getName());
            obj.put("CounselorId",getCounselorId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCounselorId() {
        return CounselorId;
    }

    public void setCounselorId(int counselorId) {
        CounselorId = counselorId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
