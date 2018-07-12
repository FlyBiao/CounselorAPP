package com.cesaas.android.order.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/7/24 18:05
 * Version 1.0
 */

public class UnReceiveOrderBean{

    public List<OrderItem> OrderItem;
    /**
     * TId : 24
     * SyncCode : 160723612608
     * ShopId : 16176
     * ShopNo : null
     * VipMobiel : null
     * RetailType : 0
     * CreateName : syncSystem
     * CreateTime : 0001-01-01 00:00:00
     * RetailDiscount : 0.0
     * RetailTotal : 0.0
     * RetailPayment : 0.0
     * RetailRemark : null
     * RetailEnd : 0
     * UpdateTime : 0001-01-01 00:00:00
     * IsDel : 0
     * RetailFrom : 0
     * RetailQuantity : 12
     * ActualPayMent : 0.0
     * Mobile : 18266900358
     * Consignee : 谢跃
     * Fright : 0.0
     * ExpressId : null
     * ExpressName : null
     * WayBillNo : null
     * City : 金华市
     * Province : 浙江省
     * District : 义乌市
     * Address : 稠城街道兴中小区19栋6号301室，门铃0301
     * OrderId : 15
     * UserTicket : null
     */

    private int OrderStatus;
    private int TId;
    private String SyncCode;
    private double PayMent;
    private int ShopId;
    private String ShopNo;
    private String VipMobiel;
    private int RetailType;
    private String CreateName;
    private String CreateTime;
    private double RetailDiscount;
    private double RetailTotal;
    private double RetailPayment;
    private double OrderTotal;
    private String RetailRemark;
    private int RetailEnd;
    private String UpdateTime;
    private int IsDel;
    private int RetailQuantity;
    private double ActualPayMent;
    private String Mobile;
    private String Consignee;
    private double Fright;
    private int ExpressId;
    private String ExpressName;
    private String WayBillNo;
    private String City;
    private String Province;
    private String District;
    private String Address;
    private int OrderId;
    private String UserTicket;
    private int RetailFrom;//1 只能接单发货


    public double getPayMent() {
        return PayMent;
    }

    public void setPayMent(double payMent) {
        PayMent = payMent;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public double getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        OrderTotal = orderTotal;
    }

    public List<UnReceiveOrderBean.OrderItem> getOrderItem() {
        return OrderItem;
    }

    public void setOrderItem(List<UnReceiveOrderBean.OrderItem> orderItem) {
        OrderItem = orderItem;
    }

    public int getTId() {
        return TId;
    }

    public void setTId(int TId) {
        this.TId = TId;
    }

    public String getSyncCode() {
        return SyncCode;
    }

    public void setSyncCode(String syncCode) {
        SyncCode = syncCode;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getShopNo() {
        return ShopNo;
    }

    public void setShopNo(String shopNo) {
        ShopNo = shopNo;
    }

    public String getVipMobiel() {
        return VipMobiel;
    }

    public void setVipMobiel(String vipMobiel) {
        VipMobiel = vipMobiel;
    }

    public int getRetailType() {
        return RetailType;
    }

    public void setRetailType(int retailType) {
        RetailType = retailType;
    }

    public String getCreateName() {
        return CreateName;
    }

    public void setCreateName(String createName) {
        CreateName = createName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public double getRetailDiscount() {
        return RetailDiscount;
    }

    public void setRetailDiscount(double retailDiscount) {
        RetailDiscount = retailDiscount;
    }

    public double getRetailTotal() {
        return RetailTotal;
    }

    public void setRetailTotal(double retailTotal) {
        RetailTotal = retailTotal;
    }

    public double getRetailPayment() {
        return RetailPayment;
    }

    public void setRetailPayment(double retailPayment) {
        RetailPayment = retailPayment;
    }

    public String getRetailRemark() {
        return RetailRemark;
    }

    public void setRetailRemark(String retailRemark) {
        RetailRemark = retailRemark;
    }

    public int getRetailEnd() {
        return RetailEnd;
    }

    public void setRetailEnd(int retailEnd) {
        RetailEnd = retailEnd;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int isDel) {
        IsDel = isDel;
    }

    public int getRetailFrom() {
        return RetailFrom;
    }

    public void setRetailFrom(int retailFrom) {
        RetailFrom = retailFrom;
    }

    public int getRetailQuantity() {
        return RetailQuantity;
    }

    public void setRetailQuantity(int retailQuantity) {
        RetailQuantity = retailQuantity;
    }

    public double getActualPayMent() {
        return ActualPayMent;
    }

    public void setActualPayMent(double actualPayMent) {
        ActualPayMent = actualPayMent;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String consignee) {
        Consignee = consignee;
    }

    public double getFright() {
        return Fright;
    }

    public void setFright(double fright) {
        Fright = fright;
    }

    public int getExpressId() {
        return ExpressId;
    }

    public void setExpressId(int expressId) {
        ExpressId = expressId;
    }

    public String getExpressName() {
        return ExpressName;
    }

    public void setExpressName(String expressName) {
        ExpressName = expressName;
    }

    public String getWayBillNo() {
        return WayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        WayBillNo = wayBillNo;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getUserTicket() {
        return UserTicket;
    }

    public void setUserTicket(String userTicket) {
        UserTicket = userTicket;
    }

    public static class OrderItem implements Serializable{


        /**
         * ActualConsumption : 0.0
         * Attr : 暖黄:110
         * BarcodeId : 42808047950
         * BarcodeNo : 100222BY09110
         * CostPrice : 0.0
         * ListPrice : 70.0
         * OrderId : 26
         * PayMent : 376.0
         * Quantity : 1
         * RetailSort : 0
         * RetailSubId : 26
         * RetailType : 0
         * SellPrice : 376.0
         * ShopStyleId : 4280
         * SkuValue1 : 暖黄
         * SkuValue2 : 110
         * StyleName : StyleNo
         * StyleNo : 100222B
         */

        private String ImageUrl;
        private double ActualConsumption;
        private String Attr;
        private long BarcodeId;
        private String BarcodeNo;
        private double CostPrice;
        private double ListPrice;
        private int OrderId;
        private int Quantity;
        private int RetailSort;
        private int RetailSubId;
        private int RetailType;
        private double SellPrice;
        private double PayMent;
        private int ShopStyleId;
        private String SkuValue1;
        private String SkuValue2;
        private String StyleName;
        private String StyleNo;

        public OrderItem(String SkuValue1,String SkuValue2,String BarcodeNo,double PayMent,int Quantity,int OrderId){
            this.SkuValue1=SkuValue1;
            this.SkuValue2=SkuValue2;
            this.BarcodeNo=BarcodeNo;
            this.PayMent=PayMent;
            this.Quantity=Quantity;
            this.OrderId=OrderId;
        }
        public OrderItem(){

        }



        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String imageUrl) {
            ImageUrl = imageUrl;
        }

        public void setActualConsumption(double ActualConsumption) {
            this.ActualConsumption = ActualConsumption;
        }

        public void setAttr(String Attr) {
            this.Attr = Attr;
        }

        public void setBarcodeId(long BarcodeId) {
            this.BarcodeId = BarcodeId;
        }

        public void setBarcodeNo(String BarcodeNo) {
            this.BarcodeNo = BarcodeNo;
        }

        public void setCostPrice(double CostPrice) {
            this.CostPrice = CostPrice;
        }

        public void setListPrice(double ListPrice) {
            this.ListPrice = ListPrice;
        }

        public void setOrderId(int OrderId) {
            this.OrderId = OrderId;
        }

        public void setPayMent(double PayMent) {
            this.PayMent = PayMent;
        }

        public void setQuantity(int Quantity) {
            this.Quantity = Quantity;
        }

        public void setRetailSort(int RetailSort) {
            this.RetailSort = RetailSort;
        }

        public void setRetailSubId(int RetailSubId) {
            this.RetailSubId = RetailSubId;
        }

        public void setRetailType(int RetailType) {
            this.RetailType = RetailType;
        }

        public void setSellPrice(double SellPrice) {
            this.SellPrice = SellPrice;
        }

        public void setShopStyleId(int ShopStyleId) {
            this.ShopStyleId = ShopStyleId;
        }

        public void setSkuValue1(String SkuValue1) {
            this.SkuValue1 = SkuValue1;
        }

        public void setSkuValue2(String SkuValue2) {
            this.SkuValue2 = SkuValue2;
        }

        public void setStyleName(String StyleName) {
            this.StyleName = StyleName;
        }

        public void setStyleNo(String StyleNo) {
            this.StyleNo = StyleNo;
        }

        public double getActualConsumption() {
            return ActualConsumption;
        }

        public String getAttr() {
            return Attr;
        }

        public long getBarcodeId() {
            return BarcodeId;
        }

        public String getBarcodeNo() {
            return BarcodeNo;
        }

        public double getCostPrice() {
            return CostPrice;
        }

        public double getListPrice() {
            return ListPrice;
        }

        public int getOrderId() {
            return OrderId;
        }

        public double getPayMent() {
            return PayMent;
        }

        public int getQuantity() {
            return Quantity;
        }

        public int getRetailSort() {
            return RetailSort;
        }

        public int getRetailSubId() {
            return RetailSubId;
        }

        public int getRetailType() {
            return RetailType;
        }

        public double getSellPrice() {
            return SellPrice;
        }

        public int getShopStyleId() {
            return ShopStyleId;
        }

        public String getSkuValue1() {
            return SkuValue1;
        }

        public String getSkuValue2() {
            return SkuValue2;
        }

        public String getStyleName() {
            return StyleName;
        }

        public String getStyleNo() {
            return StyleNo;
        }
    }




}
