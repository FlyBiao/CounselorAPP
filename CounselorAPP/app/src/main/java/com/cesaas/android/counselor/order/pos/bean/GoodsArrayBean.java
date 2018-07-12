package com.cesaas.android.counselor.order.pos.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 商品列表Bean
 * @author FGB
 *
 */
public class GoodsArrayBean {

	private int Id;//商品id
	private double Price;//商品支付价格
	private int Count;//商品数量
	private double PriceOriginal;//商品原价
	
	
	public JSONObject getGoodsArray() {
		JSONObject json = new JSONObject();
		try {
			json.put("Id", getId());
			json.put("Price",getPrice());
			json.put("Count", getCount());
			json.put("PriceOriginal", getPriceOriginal());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public double getPriceOriginal() {
		return PriceOriginal;
	}
	public void setPriceOriginal(double priceOriginal) {
		PriceOriginal = priceOriginal;
	}
	
	
}
