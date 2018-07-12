package com.cesaas.android.counselor.order.base;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class GetByKeyWordBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String ID;//商品ID
	public String NO;//商品款号
	public String TITLE;//商品title
	public String IMAGEURL;//商品图片URL
	public String URL;//商品链接URL
	public String TOTALSTOCK;//库存
	public double SELLPRICE;//商品出售价格
	public String RECOMMEND;//推荐佣金
	public String ValidateInfo;//验证信息
	public int TYPE;//商品类型
	public String POINTS;//积分
	public boolean isError;
	public String eMessage;
	public boolean isDetached;
	
	public JSONObject getStyleIdItem() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("ShopStyleId", ID);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return obj;
	}
	
}
