package com.cesaas.android.counselor.order.projecttest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ddd {
	public static void main(String[] args) throws JSONException{  
		JSONObject obj=new JSONObject();    
	    obj.put("derek","23");    
	    obj.put("dad", "49");    
	    obj.put("mom", "45");    
	    System.out.println("通过构造器的方式创建的JSONObject对象："+obj);  
	    
	    JSONArray arr=new JSONArray();    
	    arr.put(0,"derek");    
	    arr.put(1,"dad");    
	    arr.put(2,"mom");    
	    System.out.println("通过构造器的方式创建的JSONArray："+arr);
	}
}
