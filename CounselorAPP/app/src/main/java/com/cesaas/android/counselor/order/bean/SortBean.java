package com.cesaas.android.counselor.order.bean;


import org.json.JSONException;
import org.json.JSONObject;

public class SortBean {

	private String Field;
	private String Value;
	private String DbField;

	public JSONObject getSortIdInfo(){
		JSONObject obj=new JSONObject();
		try {
			obj.put("Field",getField());
			obj.put("Value",getValue());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	
	public String getField() {
		return Field;
	}
	public void setField(String field) {
		Field = field;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public String getDbField() {
		return DbField;
	}
	public void setDbField(String dbField) {
		DbField = dbField;
	}
	
	
	
}
