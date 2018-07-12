package com.cesaas.android.counselor.order.member.bean.manager;

import java.io.Serializable;

/**
 * Author FGB
 * Description 查询会员等级数分组
 * Created at 2018/5/8 15:10
 * Version 1.0
 */

public class VipGradeNumsListBean implements Serializable {
    private int VipGradeId;
    private String VipGradeName;
    private int Seq;
    private int Nums;
    private String FontColor;

    public int getVipGradeId() {
        return VipGradeId;
    }

    public void setVipGradeId(int vipGradeId) {
        VipGradeId = vipGradeId;
    }

    public String getVipGradeName() {
        return VipGradeName;
    }

    public void setVipGradeName(String vipGradeName) {
        VipGradeName = vipGradeName;
    }

    public int getSeq() {
        return Seq;
    }

    public void setSeq(int seq) {
        Seq = seq;
    }

    public int getNums() {
        return Nums;
    }

    public void setNums(int nums) {
        Nums = nums;
    }

    public String getFontColor() {
        return FontColor;
    }

    public void setFontColor(String fontColor) {
        FontColor = fontColor;
    }
}
