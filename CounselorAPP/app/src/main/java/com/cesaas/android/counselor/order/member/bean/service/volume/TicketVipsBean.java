package com.cesaas.android.counselor.order.member.bean.service.volume;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2018/5/15 15:12
 * Version 1.0
 */

public class TicketVipsBean {
    private int VipId;
    private String VipName;
    private int MemberId;
    private  String Mobile;
    private int GradeId;
    private String Birthday;

    public JSONObject getTicketVips(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("VipId",getVipId());
            obj.put("VipName",getVipName());
            obj.put("MemberId",getMemberId());
            obj.put("Mobile",getMobile());
            obj.put("GradeId",getGradeId());
            obj.put("Birthday",getBirthday());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public int getVipId() {
        return VipId;
    }

    public void setVipId(int vipId) {
        VipId = vipId;
    }

    public String getVipName() {
        return VipName;
    }

    public void setVipName(String vipName) {
        VipName = vipName;
    }

    public int getMemberId() {
        return MemberId;
    }

    public void setMemberId(int memberId) {
        MemberId = memberId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public int getGradeId() {
        return GradeId;
    }

    public void setGradeId(int gradeId) {
        GradeId = gradeId;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }
}
