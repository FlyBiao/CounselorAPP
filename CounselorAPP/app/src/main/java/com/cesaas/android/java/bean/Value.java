package com.cesaas.android.java.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2018/5/24 16:05
 * Version 1.0
 */

public class Value {


    /**
     * name : createTime
     * value : 2018-05-23 00:00:00
     * operators : 4
     * connector : 0
     */

    private String name;
    private String value;
    private int intValue;
    private int operators;
    private int connector;
    private long id;

    public JSONObject getValueInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("name",getName());
            obj.put("value",getValue());
            obj.put("operators",getOperators());
            obj.put("connector",getConnector());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj;
    }

    public JSONObject getIntValueInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("name",getName());
            obj.put("value",getIntValue());
            obj.put("operators",getOperators());
            obj.put("connector",getConnector());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj;
    }

    public JSONObject getIdValueInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("name",getName());
            obj.put("value",getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj;
    }

    public JSONObject getPidValueInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("name",getName());
            obj.put("value",getId());
            obj.put("operators",getOperators());
            obj.put("connector",getConnector());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setOperators(int operators) {
        this.operators = operators;
    }

    public void setConnector(int connector) {
        this.connector = connector;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public int getOperators() {
        return operators;
    }

    public int getConnector() {
        return connector;
    }
}
