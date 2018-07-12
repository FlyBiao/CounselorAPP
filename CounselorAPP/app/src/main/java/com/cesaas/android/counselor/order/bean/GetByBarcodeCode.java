package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * 订单商品
 * @author fgb
 *
 */
public class GetByBarcodeCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int position;
	private String Title;//商品名称
	public BarcodeInfo BarcodeInfo;//商品尺码描述信息
	private int ShopStyleId;//商品id
	private double Price;//商品价格
	private String StyleCode;//商品款号
	private String BarcodeCode;//商品sku
	private String BarcodeId;//商品sku_id
	private String ImageUrl;//商品图片
	private int ShopCount=1;//商品总数
	
	
	public String getTitle() {
		return Title;
	}


	public void setTitle(String title) {
		Title = title;
	}

	public int getShopStyleId() {
		return ShopStyleId;
	}


	public void setShopStyleId(int shopStyleId) {
		ShopStyleId = shopStyleId;
	}


	public double getPrice() {
		return Price;
	}


	public void setPrice(double price) {
		Price = price;
	}


	public String getStyleCode() {
		return StyleCode;
	}


	public void setStyleCode(String styleCode) {
		StyleCode = styleCode;
	}


	public String getBarcodeCode() {
		return BarcodeCode;
	}


	public void setBarcodeCode(String barcodeCode) {
		BarcodeCode = barcodeCode;
	}


	public String getBarcodeId() {
		return BarcodeId;
	}


	public void setBarcodeId(String barcodeId) {
		BarcodeId = barcodeId;
	}


	public String getImageUrl() {
		return ImageUrl;
	}


	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}


	public int getShopCount() {
		return ShopCount;
	}


	public void setShopCount(int shopCount) {
		ShopCount = shopCount;
	}


	public class BarcodeInfo {

		public String Name1;
		public String Value1;
		public String Name2;
		public String Value2;
		public String Name3;
		public String Value3;
		public String getName1() {
			return Name1;
		}
		public void setName1(String name1) {
			Name1 = name1;
		}
		public String getValue1() {
			return Value1;
		}
		public void setValue1(String value1) {
			Value1 = value1;
		}

		public String getName2() {
			return Name2;
		}

		public void setName2(String name2) {
			Name2 = name2;
		}

		public String getValue2() {
			return Value2;
		}

		public void setValue2(String value2) {
			Value2 = value2;
		}

		public String getName3() {
			return Name3;
		}

		public void setName3(String name3) {
			Name3 = name3;
		}

		public String getValue3() {
			return Value3;
		}

		public void setValue3(String value3) {
			Value3 = value3;
		}
	}
}
