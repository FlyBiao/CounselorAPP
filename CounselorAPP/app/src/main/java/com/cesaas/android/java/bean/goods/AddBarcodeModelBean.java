package com.cesaas.android.java.bean.goods;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2018/6/24 11:32
 * Version 1.0
 */

public class AddBarcodeModelBean {
    private String barcodeNo;
    private int num;

    public JSONObject getAddBarcodeModel(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("barcodeNo",getBarcodeNo());
            obj.put("num",getNum());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public String getBarcodeNo() {
        return barcodeNo;
    }

    public void setBarcodeNo(String barcodeNo) {
        this.barcodeNo = barcodeNo;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
