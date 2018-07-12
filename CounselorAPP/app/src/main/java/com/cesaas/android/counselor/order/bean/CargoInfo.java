package com.cesaas.android.counselor.order.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 货物信息
 * @author FGB
 *
 */

public class CargoInfo {

	private String Cargo="测试Title";

	public JSONObject getCargoInfo(){
		
		JSONObject json = new JSONObject();
		
		try {
			json.put("Cargo", getCargo());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getCargo() {
		return Cargo;
	}

	public void setCargo(String cargo) {
		Cargo = cargo;
	}
	
	
	
}
