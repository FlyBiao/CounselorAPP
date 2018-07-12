package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：营销活动bean
 * 创建日期：2016/12/21 11:06
 * 版    本：1.zero
 * 修订历史：
 * ================================================
 */
public class MarketingActivityBean implements Serializable{

    public String CreateTime;//创建时间
    public int CategoryId;//分类ID
    public int ActivityType;//活动类型
    public int ActivityId;//活动ID
    public double PayPrice;//支付价格
    public double PlanId;//方案ID
    public String PlanName;//方案名称
    public String Remark;//备注
    public String Description;//描述
    public boolean IsStyle;//是否限制商品
    public boolean isShop;//是否限制店铺
    public boolean IsVip;//是否限制会员

    public ArrayList<Shops> Shops;//商品集合
    public ArrayList<Styles> Styles;//商品款号集合
    public ArrayList<VipGrads>  VipGrads;//会员ID集合
}
