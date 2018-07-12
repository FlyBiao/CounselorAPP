package com.cesaas.android.counselor.order.bean;


public class Root{
	
	public CargoInfo CargoInfo;

	public DeliverInfo DeliverInfo = new DeliverInfo();
	
	public int OrderId;
	
	public String Remark="大话黄飞鸿地方";
	
	public String UserTicket;

	public CargoInfo getCargoInfo() {
		return CargoInfo;
	}

	public void setCargoInfo(CargoInfo cargoInfo) {
		CargoInfo = cargoInfo;
	}

	public DeliverInfo getDeliverInfo() {
		return DeliverInfo;
	}

	public void setDeliverInfo(DeliverInfo deliverInfo) {
		DeliverInfo = deliverInfo;
	}

	public int getOrderId() {
		return OrderId;
	}

	public void setOrderId(int orderId) {
		OrderId = orderId;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getUserTicket() {
		return UserTicket;
	}

	public void setUserTicket(String userTicket) {
		UserTicket = userTicket;
	}
	
}
