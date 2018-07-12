package com.cesaas.android.counselor.order.activity.user.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/3/17 14:57
 * Version 1.0
 */

public class CompanyBean implements Serializable {
    private String CompanyName;
    private String CompanyAuthCode;
    private int TId;

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyAuthCode() {
        return CompanyAuthCode;
    }

    public void setCompanyAuthCode(String companyAuthCode) {
        CompanyAuthCode = companyAuthCode;
    }

    public int getTId() {
        return TId;
    }

    public void setTId(int TId) {
        this.TId = TId;
    }
}
