package com.cesaas.android.counselor.order.member.bean.manager;

import java.io.Serializable;

/**
 * Author FGB
 * Description 查询会员粉丝数分组
 * Created at 2018/5/8 15:30
 * Version 1.0
 */

public class VipFansNumsListByShopIdBean implements Serializable {
    private int CounselorId;
    private String CounselorName;
    private int VipNums;
    private int FansNums;
    private String Image;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getCounselorId() {
        return CounselorId;
    }

    public void setCounselorId(int counselorId) {
        CounselorId = counselorId;
    }

    public String getCounselorName() {
        return CounselorName;
    }

    public void setCounselorName(String counselorName) {
        CounselorName = counselorName;
    }

    public int getVipNums() {
        return VipNums;
    }

    public void setVipNums(int vipNums) {
        VipNums = vipNums;
    }

    public int getFansNums() {
        return FansNums;
    }

    public void setFansNums(int fansNums) {
        FansNums = fansNums;
    }
}
