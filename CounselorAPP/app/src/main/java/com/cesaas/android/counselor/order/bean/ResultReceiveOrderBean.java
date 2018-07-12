package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 待接单
 * @author fgb
 *
 */
public class ResultReceiveOrderBean extends BaseBean{

	public ArrayList<GetUnReceiveOrderBean> TModel;
	
	public class GetUnReceiveOrderBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String CreateDate;//下单时间
		public int OrderStatus;//分销订单状态
		public String TradeId;//订单id
		public String VipId;//下单用户id
		public String NickName;//下单用户昵称
		public String Mobile;//下单用户手机号
		public int ExpressType;//取货方式：0快递，1到店自提
		
		public ArrayList<OrderItemBean> OrderItem;
	}
	
	public class OrderItemBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String Attr;//商品规格
		private String ImageUrl;//图片url
		private String OId;//
		private String OrderId;//订单id
		private int OrderStatus;//订单状态
		private double Price;//商品价格
		private int Quantity;//数量
		private String ShopStyleId;//商品类型ID
		private String Title;//商品名称
		private String BarcodeCode;//条形码
		private String StyleCode;//款号
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
		public String getOId() {
			return OId;
		}
		public void setOId(String oId) {
			OId = oId;
		}
		public String getOrderId() {
			return OrderId;
		}
		public void setOrderId(String orderId) {
			OrderId = orderId;
		}
		public int getOrderStatus() {
			return OrderStatus;
		}
		public void setOrderStatus(int orderStatus) {
			OrderStatus = orderStatus;
		}
		public double getPrice() {
			return Price;
		}
		public void setPrice(double price) {
			Price = price;
		}
		public int getQuantity() {
			return Quantity;
		}
		public void setQuantity(int quantity) {
			Quantity = quantity;
		}
		public String getShopStyleId() {
			return ShopStyleId;
		}
		public void setShopStyleId(String shopStyleId) {
			ShopStyleId = shopStyleId;
		}
		public String getTitle() {
			return Title;
		}
		public void setTitle(String title) {
			Title = title;
		}
		public String getBarcodeCode() {
			return BarcodeCode;
		}
		public void setBarcodeCode(String barcodeCode) {
			BarcodeCode = barcodeCode;
		}
		public String getStyleCode() {
			return StyleCode;
		}
		public void setStyleCode(String styleCode) {
			StyleCode = styleCode;
		}
		
		
		
		
	}
}
