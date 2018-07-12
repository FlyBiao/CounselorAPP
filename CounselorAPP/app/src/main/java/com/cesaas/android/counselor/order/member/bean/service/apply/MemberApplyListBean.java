package com.cesaas.android.counselor.order.member.bean.service.apply;

import java.io.Serializable;

/**
 * Author FGB
 * Description 手机和生日修改申请列表
 * Created at 2018/3/15 11:24
 * Version 1.0
 */

public class MemberApplyListBean implements Serializable {
    private String UserName;//会员名称
    private String UserImage;//会员头像
    private int Status;//10.待审批20.已完成30.不同意40.超时
    private int Type;//1 :手机，2 生日
    private String ShopName;//申请店铺名称
    private String ApplyDate;//	申请日期
    private String ApplyRemark;//申请备注
    private String ApplyName;//申请人姓名
    private String InfoOld;//新的手机/生日
    private String InfoNew;//	原来的手机/生日
    private String ConfirmName;//处理人姓名
    private String ConfirmRole;//处理人职位角色
    private String ConfirmDate;//处理日期
    private String ConfirmRemark;//	处理备注
    private int Id;//申请项ID
    private int UserId;//	用户vipId

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getApplyDate() {
        return ApplyDate;
    }

    public void setApplyDate(String applyDate) {
        ApplyDate = applyDate;
    }

    public String getApplyRemark() {
        return ApplyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        ApplyRemark = applyRemark;
    }

    public String getApplyName() {
        return ApplyName;
    }

    public void setApplyName(String applyName) {
        ApplyName = applyName;
    }

    public String getInfoOld() {
        return InfoOld;
    }

    public void setInfoOld(String infoOld) {
        InfoOld = infoOld;
    }

    public String getInfoNew() {
        return InfoNew;
    }

    public void setInfoNew(String infoNew) {
        InfoNew = infoNew;
    }

    public String getConfirmName() {
        return ConfirmName;
    }

    public void setConfirmName(String confirmName) {
        ConfirmName = confirmName;
    }

    public String getConfirmRole() {
        return ConfirmRole;
    }

    public void setConfirmRole(String confirmRole) {
        ConfirmRole = confirmRole;
    }

    public String getConfirmDate() {
        return ConfirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        ConfirmDate = confirmDate;
    }

    public String getConfirmRemark() {
        return ConfirmRemark;
    }

    public void setConfirmRemark(String confirmRemark) {
        ConfirmRemark = confirmRemark;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
