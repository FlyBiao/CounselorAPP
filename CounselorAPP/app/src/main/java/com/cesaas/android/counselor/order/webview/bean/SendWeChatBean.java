package com.cesaas.android.counselor.order.webview.bean;

/**
 * Author FGB
 * Description 发送微信
 * Created at 2017/5/12 17:46
 * Version 1.0
 */

public class SendWeChatBean extends BaseTypeBean {

    private MsgBean param;

    public MsgBean getParam() {
        return param;
    }

    public void setParam(MsgBean param) {
        this.param = param;
    }

    public class MsgBean{
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
