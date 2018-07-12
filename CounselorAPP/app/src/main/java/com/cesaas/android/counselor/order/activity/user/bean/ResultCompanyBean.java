package com.cesaas.android.counselor.order.activity.user.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 获取公司信息以及授权码
 * Created at 2018/3/15 10:52
 * Version 1.0
 */

public class ResultCompanyBean extends BaseBean {

    public CompanyBean TModel;

    public class CompanyBean implements Serializable{
        private String CompanyName;
        private String CompanyAuthCode;

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
    }
}
