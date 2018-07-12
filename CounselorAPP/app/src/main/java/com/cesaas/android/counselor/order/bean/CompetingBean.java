package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 企业竞品Bean
 * @author fgb
 *
 */
public class CompetingBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;//企业名称
	private String saleValue;//企业销售
	
	public JSONObject getCompetingBean() {
		JSONObject json = new JSONObject();
		try {
			json.put("name", getName());
			json.put("saleValue",getSaleValue());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSaleValue() {
		return saleValue;
	}
	public void setSaleValue(String saleValue) {
		this.saleValue = saleValue;
	}
	
}
