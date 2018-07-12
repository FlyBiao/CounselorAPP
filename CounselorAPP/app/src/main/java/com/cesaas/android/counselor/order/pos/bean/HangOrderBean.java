package com.cesaas.android.counselor.order.pos.bean;

/**
 * 挂单Bean
 * @author FGB
 *
 */
public class HangOrderBean {

	private String orderNo;//单号
	private String orderDate;//订单日期
	private String vipName;//会员
	private String salesName;//销售
	private double Discount;//总折扣
	private double Coupon;//总优惠
	
	
	public double getDiscount() {
		return Discount;
	}
	public void setDiscount(double discount) {
		Discount = discount;
	}
	public double getCoupon() {
		return Coupon;
	}
	public void setCoupon(double coupon) {
		Coupon = coupon;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getVipName() {
		return vipName;
	}
	public void setVipName(String vipName) {
		this.vipName = vipName;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	
	
}
