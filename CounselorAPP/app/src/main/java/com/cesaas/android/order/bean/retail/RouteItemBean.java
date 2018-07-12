package com.cesaas.android.order.bean.retail;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description 发起店铺调货Item
 * Created at 2018/5/10 14:46
 * Version 1.0
 */

public class RouteItemBean  {

    private String BarcodeNo;
    private String StyleNo;
    private int Quantity;
    private double ListPrice;
    private double SellPrice;
    private double PayMent;
    private String Remark;
    private String SkuValue1;
    private String SkuValue2;
    private String SkuValue3;
    private String StyleName;
    private int ShopStyleId;
    private String BarcodeId;
    private String Attr;
    private String ImageUrl;

    public JSONObject getRouteItem(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("BarcodeNo",getBarcodeNo());
            obj.put("StyleNo",getStyleNo());
            obj.put("Quantity",getQuantity());
            obj.put("ListPrice",getListPrice());
            obj.put("SellPrice",getSellPrice());
            obj.put("PayMent",getPayMent());
            obj.put("Remark",getRemark());
            obj.put("SkuValue1",getSkuValue1());
            obj.put("SkuValue2",getSkuValue2());
            obj.put("SkuValue3",getSkuValue3());
            obj.put("StyleName",getStyleName());
            obj.put("ShopStyleId",getShopStyleId());
            obj.put("BarcodeId",getBarcodeId());
            obj.put("Attr",getAttr());
            obj.put("ImageUrl",getImageUrl());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public double getListPrice() {
        return ListPrice;
    }

    public void setListPrice(double listPrice) {
        ListPrice = listPrice;
    }

    public double getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(double sellPrice) {
        SellPrice = sellPrice;
    }

    public double getPayMent() {
        return PayMent;
    }

    public void setPayMent(double payMent) {
        PayMent = payMent;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getSkuValue1() {
        return SkuValue1;
    }

    public void setSkuValue1(String skuValue1) {
        SkuValue1 = skuValue1;
    }

    public String getSkuValue2() {
        return SkuValue2;
    }

    public void setSkuValue2(String skuValue2) {
        SkuValue2 = skuValue2;
    }

    public String getSkuValue3() {
        return SkuValue3;
    }

    public void setSkuValue3(String skuValue3) {
        SkuValue3 = skuValue3;
    }

    public String getStyleName() {
        return StyleName;
    }

    public void setStyleName(String styleName) {
        StyleName = styleName;
    }

    public int getShopStyleId() {
        return ShopStyleId;
    }

    public void setShopStyleId(int shopStyleId) {
        ShopStyleId = shopStyleId;
    }

    public String getBarcodeId() {
        return BarcodeId;
    }

    public void setBarcodeId(String barcodeId) {
        BarcodeId = barcodeId;
    }

    public String getAttr() {
        return Attr;
    }

    public void setAttr(String attr) {
        Attr = attr;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getBarcodeNo() {
        return BarcodeNo;
    }

    public void setBarcodeNo(String barcodeNo) {
        BarcodeNo = barcodeNo;
    }

    public String getStyleNo() {
        return StyleNo;
    }

    public void setStyleNo(String styleNo) {
        StyleNo = styleNo;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
