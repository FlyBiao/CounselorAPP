package com.cesaas.android.counselor.order.webview.bean;

/**
 * Author FGB
 * Description 打电话
 * Created at 2017/5/12 17:39
 * Version 1.0
 */

public class CallPhoneBean extends BaseTypeBean{

    private PhoneBean param;

    public PhoneBean getParam() {
        return param;
    }

    public void setParam(PhoneBean param) {
        this.param = param;
    }

    public class PhoneBean{

        private String vip_id;
        private String mobile;
        private String name;

        public String getVip_id() {
            return vip_id;
        }

        public void setVip_id(String vip_id) {
            this.vip_id = vip_id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
