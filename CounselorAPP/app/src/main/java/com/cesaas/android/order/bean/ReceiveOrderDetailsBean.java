package com.cesaas.android.order.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/7/25 10:30
 * Version 1.0
 */

public class ReceiveOrderDetailsBean {


    /**
     * ActualPayMent : 0.0
     * Address : 董家镇机场路济南重工南门苑西超市(菜鸟驿站:18253153047)
     * City : 济南市
     * Consignee : 王晓颖
     * CreateName : syncSystem
     * CreateTime : 2017-07-25 00:52:46
     * District : 历城区
     * Fright : 0.0
     * IsDel : 0
     * Mobile : 15053128659
     * OrderId : 17
     * Province : 山东省
     * RetailDiscount : 0.0
     * RetailEnd : 0
     * RetailFrom : 0
     * RetailPayment : 0.0
     * RetailQuantity : 1
     * RetailTotal : 0.0
     * RetailType : 0
     * ShopId : 0
     * SyncCode : 160723612604
     * TId : 24
     * UpdateTime : 0001-01-01 00:00:00
     */

    private double ActualPayMent;
    private String Address;
    private String City;
    private String Consignee;
    private String CreateName;
    private String CreateTime;
    private String District;
    private String SureDate;
    private double Fright;
    private double PayMent;
    private int IsDel;
    private String Mobile;
    private int OrderId;
    private String Province;
    private double RetailDiscount;
    private int RetailEnd;
    private int RetailFrom;
    private double RetailPayment;
    private int RetailQuantity;
    private double RetailTotal;
    private int RetailType;
    private int ShopId;
    private String SyncCode;
    private int TId;
    private String UpdateTime;
    private String WayBillNo;
    private int OrderStatus;

    public List<OrderItemDetail> OrderItem;

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getSureDate() {
        return SureDate;
    }

    public void setSureDate(String sureDate) {
        SureDate = sureDate;
    }

    public double getPayMent() {
        return PayMent;
    }

    public void setPayMent(double payMent) {
        PayMent = payMent;
    }

    public String getWayBillNo() {
        return WayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        WayBillNo = wayBillNo;
    }

    public void setActualPayMent(double ActualPayMent) {
        this.ActualPayMent = ActualPayMent;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public void setConsignee(String Consignee) {
        this.Consignee = Consignee;
    }

    public void setCreateName(String CreateName) {
        this.CreateName = CreateName;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public void setDistrict(String District) {
        this.District = District;
    }

    public void setFright(double Fright) {
        this.Fright = Fright;
    }

    public void setIsDel(int IsDel) {
        this.IsDel = IsDel;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public void setOrderId(int OrderId) {
        this.OrderId = OrderId;
    }

    public void setProvince(String Province) {
        this.Province = Province;
    }

    public void setRetailDiscount(double RetailDiscount) {
        this.RetailDiscount = RetailDiscount;
    }

    public void setRetailEnd(int RetailEnd) {
        this.RetailEnd = RetailEnd;
    }

    public void setRetailFrom(int RetailFrom) {
        this.RetailFrom = RetailFrom;
    }

    public void setRetailPayment(double RetailPayment) {
        this.RetailPayment = RetailPayment;
    }

    public void setRetailQuantity(int RetailQuantity) {
        this.RetailQuantity = RetailQuantity;
    }

    public void setRetailTotal(double RetailTotal) {
        this.RetailTotal = RetailTotal;
    }

    public void setRetailType(int RetailType) {
        this.RetailType = RetailType;
    }

    public void setShopId(int ShopId) {
        this.ShopId = ShopId;
    }

    public void setSyncCode(String SyncCode) {
        this.SyncCode = SyncCode;
    }

    public void setTId(int TId) {
        this.TId = TId;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public double getActualPayMent() {
        return ActualPayMent;
    }

    public String getAddress() {
        return Address;
    }

    public String getCity() {
        return City;
    }

    public String getConsignee() {
        return Consignee;
    }

    public String getCreateName() {
        return CreateName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getDistrict() {
        return District;
    }

    public double getFright() {
        return Fright;
    }

    public int getIsDel() {
        return IsDel;
    }

    public String getMobile() {
        return Mobile;
    }

    public int getOrderId() {
        return OrderId;
    }

    public String getProvince() {
        return Province;
    }

    public double getRetailDiscount() {
        return RetailDiscount;
    }

    public int getRetailEnd() {
        return RetailEnd;
    }

    public int getRetailFrom() {
        return RetailFrom;
    }

    public double getRetailPayment() {
        return RetailPayment;
    }

    public int getRetailQuantity() {
        return RetailQuantity;
    }

    public double getRetailTotal() {
        return RetailTotal;
    }

    public int getRetailType() {
        return RetailType;
    }

    public int getShopId() {
        return ShopId;
    }

    public String getSyncCode() {
        return SyncCode;
    }

    public int getTId() {
        return TId;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public static class OrderItemDetail implements Serializable{

        /**
         * ActualConsumption : 0.0
         * BarcodeId : 42808047950
         * BarcodeNo : 100222BY09110
         * CostPrice : 0.0
         * ListPrice : 70.0
         * OrderId : 17
         * PayMent : 698.0
         * Quantity : 1
         * RetailSort : 0
         * RetailSubId : 17
         * RetailType : 0
         * SellPrice : 698.0
         * ShopStyleId : 4280
         * SkuValue1 : 暖黄
         * SkuValue2 : 110
         * StyleNo : 100222B
         */

        private double ActualConsumption;
        private long BarcodeId;
        private String BarcodeNo;
        private double CostPrice;
        private double ListPrice;
        private int OrderId;
        private double PayMent;
        private int Quantity;
        private int RetailSort;
        private int RetailSubId;
        private int RetailType;
        private double SellPrice;
        private int ShopStyleId;
        private String SkuValue1;
        private String SkuValue2;
        private String StyleNo;
        private boolean isSuccess=false;

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public void setActualConsumption(double ActualConsumption) {
            this.ActualConsumption = ActualConsumption;
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

        public void setStyleNo(String StyleNo) {
            this.StyleNo = StyleNo;
        }

        public double getActualConsumption() {
            return ActualConsumption;
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

        public String getStyleNo() {
            return StyleNo;
        }
    }


}
