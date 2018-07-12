package com.cesaas.android.counselor.order.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class StyleCodeArrayBean {

	private String StyleCode;//商品款号
	
	public JSONObject getStyleCodeArray() {
		JSONObject json = new JSONObject();
		try {
			json.put("StyleCode", getStyleCode());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}

	public String getStyleCode() {
		return StyleCode;
	}

	public void setStyleCode(String styleCode) {
		StyleCode = styleCode;
	}
	
}
