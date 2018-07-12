package com.cesaas.android.counselor.order.signin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description 签到记录Bean
 * Created 2017/3/17 18:05
 * Version 1.zero
 */
public class SigninRecordBean implements Serializable{

//    public int ID;//签到记录ID
//    public int USER_ID;//用户ID
//    public int SHOP_ID;//店铺ID
//    public int SIGN_TYPE;//1代表上班，2代表下班，3代表培训，4代表开会...
//    public int LONGTITUDE;//经度
//    public int LATITUDE;//维度
//    public String SIGN_TYPE_NAME;//上班，下班，开会，培训...
//    public String ADDRES_BY_NOW;//签到地址
//    public String IMAGE_PATH;//签到图片URL
//    public String CREATE_TIME;//签到时间
//    public String REMARK;//签到备注

    private String ADDRES_BY_NOW;

    private String CREATE_TIME;

    private int ID;

    private List<String> IMAGE_PATH;

    private String REMARK;

    private int LATITUDE;

    private int LONGTITUDE;

    private int SHOP_ID;

    private int SIGN_TYPE;

    private String SIGN_TYPE_NAME;

    private int USER_ID;

    private boolean isDetached;

    private boolean isError;

    public void setADDRES_BY_NOW(String ADDRES_BY_NOW){
        this.ADDRES_BY_NOW = ADDRES_BY_NOW;
    }
    public String getADDRES_BY_NOW(){
        return this.ADDRES_BY_NOW;
    }
    public void setCREATE_TIME(String CREATE_TIME){
        this.CREATE_TIME = CREATE_TIME;
    }
    public String getCREATE_TIME(){
        return this.CREATE_TIME;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public int getID(){
        return this.ID;
    }
    public void setIMAGE_PATH(List<String> IMAGE_PATH){
        this.IMAGE_PATH = IMAGE_PATH;
    }
    public List<String> getIMAGE_PATH(){
        return this.IMAGE_PATH;
    }
    public void setLATITUDE(int LATITUDE){
        this.LATITUDE = LATITUDE;
    }
    public int getLATITUDE(){
        return this.LATITUDE;
    }
    public void setLONGTITUDE(int LONGTITUDE){
        this.LONGTITUDE = LONGTITUDE;
    }
    public int getLONGTITUDE(){
        return this.LONGTITUDE;
    }
    public void setSHOP_ID(int SHOP_ID){
        this.SHOP_ID = SHOP_ID;
    }
    public int getSHOP_ID(){
        return this.SHOP_ID;
    }
    public void setSIGN_TYPE(int SIGN_TYPE){
        this.SIGN_TYPE = SIGN_TYPE;
    }
    public int getSIGN_TYPE(){
        return this.SIGN_TYPE;
    }
    public void setSIGN_TYPE_NAME(String SIGN_TYPE_NAME){
        this.SIGN_TYPE_NAME = SIGN_TYPE_NAME;
    }
    public String getSIGN_TYPE_NAME(){
        return this.SIGN_TYPE_NAME;
    }
    public void setUSER_ID(int USER_ID){
        this.USER_ID = USER_ID;
    }
    public int getUSER_ID(){
        return this.USER_ID;
    }
    public void setIsDetached(boolean isDetached){
        this.isDetached = isDetached;
    }
    public boolean getIsDetached(){
        return this.isDetached;
    }
    public void setIsError(boolean isError){
        this.isError = isError;
    }
    public boolean getIsError(){
        return this.isError;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
