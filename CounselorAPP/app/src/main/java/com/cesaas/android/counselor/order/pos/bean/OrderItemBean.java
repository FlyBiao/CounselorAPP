package com.cesaas.android.counselor.order.pos.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderItemBean {

	private String BarcodeId;//条形码ID
	private String BarcodeNo;//条形码编号
	private int Quantity;//数量
	private double Price;//商品价格
	private String ImageUrl;//商品图片URL
	private int ShopStyleId;//商品ID
	private String Attr;//商品规格
	private double PayMent;//支付金额
	private String Title;//商品标题
	
	
	
	public JSONObject getOrderItem() {
		JSONObject json = new JSONObject();
		try {
			json.put("BarcodeId", getBarcodeId());
			json.put("BarcodeNo",getBarcodeNo());
			json.put("Quantity", getQuantity());
			json.put("Price", getPrice());
			json.put("ImageUrl", getImageUrl());
			json.put("ShopStyleId", getShopStyleId());
			json.put("Title", getTitle());
			json.put("Attr", getAttr());
			json.put("PayMent", getPayMent());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}
	


	public String getBarcodeId() {
		return BarcodeId;
	}


	public void setBarcodeId(String barcodeId) {
		BarcodeId = barcodeId;
	}


	public String getBarcodeNo() {
		return BarcodeNo;
	}


	public void setBarcodeNo(String barcodeNo) {
		BarcodeNo = barcodeNo;
	}


	public int getQuantity() {
		return Quantity;
	}


	public void setQuantity(int quantity) {
		Quantity = quantity;
	}


	public double getPrice() {
		return Price;
	}


	public void setPrice(double price) {
		Price = price;
	}


	public String getImageUrl() {
		return ImageUrl;
	}


	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}


	public int getShopStyleId() {
		return ShopStyleId;
	}


	public void setShopStyleId(int shopStyleId) {
		ShopStyleId = shopStyleId;
	}


	public String getAttr() {
		return Attr;
	}


	public void setAttr(String attr) {
		Attr = attr;
	}


	public double getPayMent() {
		return PayMent;
	}


	public void setPayMent(double payMent) {
		PayMent = payMent;
	}


	public String getTitle() {
		return Title;
	}


	public void setTitle(String title) {
		Title = title;
	}
	
	
	
}
