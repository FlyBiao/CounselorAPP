package com.cesaas.android.counselor.order.manager.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 根据会员标签查询推荐商品
 * Created at 2017/11/29 16:34
 * Version 1.0
 */

public class GetLstByVipTagBean implements Serializable {


    /**
     * Id : 6076
     * ImageUrl : http://shenzhentesting.oss-cn-shenzhen.aliyuncs.com/images/24/2017/5/22/8bff5300-3eb8-11e7-8c5f-eb9eb1b76ce8.png
     * ListPrice : 1390.0
     * No : VRW14151508
     * SellPrice : 278.0
     * Title : 尖领修身版浅蓝格子衬衣
     */

    private int Id;
    private String ImageUrl;
    private double ListPrice;
    private String No;
    private double SellPrice;
    private String Title;

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public void setListPrice(double ListPrice) {
        this.ListPrice = ListPrice;
    }

    public void setNo(String No) {
        this.No = No;
    }

    public void setSellPrice(double SellPrice) {
        this.SellPrice = SellPrice;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public int getId() {
        return Id;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public double getListPrice() {
        return ListPrice;
    }

    public String getNo() {
        return No;
    }

    public double getSellPrice() {
        return SellPrice;
    }

    public String getTitle() {
        return Title;
    }
}
