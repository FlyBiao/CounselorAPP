package com.cesaas.android.order.bean.retail;

import java.io.Serializable;

/**
 * Author FGB
 * Description 根据商品条码查询sku以及有货店铺列表信息
 * Created at 2018/5/10 16:36
 * Version 1.0
 */

public class SearchByBarcodeBean implements Serializable {

    public boolean isSelect;
    /**
     * BarcodeNo : 100122BW01100
     * Quantity : 599
     * ShopNo : 0081
     * ShopName : 福田南园店
     * AreaName : 总代
     * StyleNo : 100122B
     * UserTicket : null
     * AuthKey : null
     */

    private String BarcodeNo;
    private int Quantity;
    private String ShopNo;
    private String ShopName;
    private String AreaName;
    private String StyleNo;
    private Object UserTicket;
    private Object AuthKey;
    private int ShopId;

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public void setBarcodeNo(String BarcodeNo) {
        this.BarcodeNo = BarcodeNo;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public void setShopNo(String ShopNo) {
        this.ShopNo = ShopNo;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public void setAreaName(String AreaName) {
        this.AreaName = AreaName;
    }

    public void setStyleNo(String StyleNo) {
        this.StyleNo = StyleNo;
    }

    public void setUserTicket(Object UserTicket) {
        this.UserTicket = UserTicket;
    }

    public void setAuthKey(Object AuthKey) {
        this.AuthKey = AuthKey;
    }

    public String getBarcodeNo() {
        return BarcodeNo;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getShopNo() {
        return ShopNo;
    }

    public String getShopName() {
        return ShopName;
    }

    public String getAreaName() {
        return AreaName;
    }

    public String getStyleNo() {
        return StyleNo;
    }

    public Object getUserTicket() {
        return UserTicket;
    }

    public Object getAuthKey() {
        return AuthKey;
    }
}
