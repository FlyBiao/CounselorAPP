package com.cesaas.android.order.bean.kdn;

/**
 * Author FGB
 * Description 收件人公司 信息
 * Created at 2018/4/20 11:31
 * Version 1.0
 */

public class ReceiverCompany {
//    private String Company;
    private String Name;
    private String Mobile;
    private String ProvinceName;
    private String CityName;
    private String ExpAreaName;
    private String Address;
//
//    public String getCompany() {
//        return Company;
//    }
//
//    public void setCompany(String company) {
//        Company = company;
//    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getExpAreaName() {
        return ExpAreaName;
    }

    public void setExpAreaName(String expAreaName) {
        ExpAreaName = expAreaName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
