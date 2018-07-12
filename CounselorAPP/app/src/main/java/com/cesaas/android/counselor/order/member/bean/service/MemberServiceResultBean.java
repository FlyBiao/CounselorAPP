package com.cesaas.android.counselor.order.member.bean.service;

import java.io.Serializable;

/**
 * Author FGB
 * Description 会员服务记录查询
 * Created at 2018/3/9 9:49
 * Version 1.0
 */

public class MemberServiceResultBean implements Serializable {
    /**
     * Content : 很好的建立友好沟通渠道
     * Date : 2018-03-09 16:21:17
     * Desc : 很好的建立友好沟通渠道
     * GoShop : 1
     * GoShopDate : 2018-03-12 16:20:22
     * Name : 谢伟伟
     * NextServerDate : 2018-03-20 16:20:26
     * Remark : 客户满意度调查
     * ServiceResult : 1
     * ServiceType : 0
     * Type : 0
     */

    private String Content;
    private String Date;
    private String Desc;
    private int GoShop;
    private String GoShopDate;
    private String Name;
    private String NextServerDate;
    private String Remark;
    private int ServiceResult;
    private int ServiceType;
    private int Type;

    public void setContent(String Content) {
        this.Content = Content;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    public void setGoShop(int GoShop) {
        this.GoShop = GoShop;
    }

    public void setGoShopDate(String GoShopDate) {
        this.GoShopDate = GoShopDate;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setNextServerDate(String NextServerDate) {
        this.NextServerDate = NextServerDate;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public void setServiceResult(int ServiceResult) {
        this.ServiceResult = ServiceResult;
    }

    public void setServiceType(int ServiceType) {
        this.ServiceType = ServiceType;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getContent() {
        return Content;
    }

    public String getDate() {
        return Date;
    }

    public String getDesc() {
        return Desc;
    }

    public int getGoShop() {
        return GoShop;
    }

    public String getGoShopDate() {
        return GoShopDate;
    }

    public String getName() {
        return Name;
    }

    public String getNextServerDate() {
        return NextServerDate;
    }

    public String getRemark() {
        return Remark;
    }

    public int getServiceResult() {
        return ServiceResult;
    }

    public int getServiceType() {
        return ServiceType;
    }

    public int getType() {
        return Type;
    }

//
//    private String Date;//时间
//    private String Name;//服务人员
//    private int ServiceType;//1.电话2.其它
//    private int ServiceResult;//1.接听或有回复2.没反馈3.拒绝沟通
//    private int GoShop;//1.愿意来店2.不愿意来店3.不确定
//    private String Remark;//备注
//    private int Id;
//    private int Type;//服务类型：1：销售回访；2生日祝福；3节日安排；4休眠激活;99定制会员
//    private String Desc;//沟通内容
//    private String GoShopDate;//到店时间
//    private String NextServerDate;//下次回访时间
//
//    public String getDate() {
//        return Date;
//    }
//
//    public void setDate(String date) {
//        Date = date;
//    }
//
//    public String getName() {
//        return Name;
//    }
//
//    public void setName(String name) {
//        Name = name;
//    }
//
//    public int getServiceType() {
//        return ServiceType;
//    }
//
//    public void setServiceType(int serviceType) {
//        ServiceType = serviceType;
//    }
//
//    public int getServiceResult() {
//        return ServiceResult;
//    }
//
//    public void setServiceResult(int serviceResult) {
//        ServiceResult = serviceResult;
//    }
//
//    public int getGoShop() {
//        return GoShop;
//    }
//
//    public void setGoShop(int goShop) {
//        GoShop = goShop;
//    }
//
//    public String getRemark() {
//        return Remark;
//    }
//
//    public void setRemark(String remark) {
//        Remark = remark;
//    }
//
//    public int getId() {
//        return Id;
//    }
//
//    public void setId(int id) {
//        Id = id;
//    }
//
//    public int getType() {
//        return Type;
//    }
//
//    public void setType(int type) {
//        Type = type;
//    }
//
//    public String getDesc() {
//        return Desc;
//    }
//
//    public void setDesc(String desc) {
//        Desc = desc;
//    }
//
//    public String getGoShopDate() {
//        return GoShopDate;
//    }
//
//    public void setGoShopDate(String goShopDate) {
//        GoShopDate = goShopDate;
//    }
//
//    public String getNextServerDate() {
//        return NextServerDate;
//    }
//
//    public void setNextServerDate(String nextServerDate) {
//        NextServerDate = nextServerDate;
//    }
}
