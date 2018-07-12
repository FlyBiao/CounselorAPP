package com.cesaas.android.counselor.order.webview.bean;

/**
 * Author FGB
 * Description 通知app更新数据
 * Created at 2017/5/12 18:00
 * Version 1.0
 */

public class NotificationAppUpdateDataBean extends BaseTypeBean{

    private UpdateDataBean param;

    public UpdateDataBean getParam() {
        return param;
    }

    public void setParam(UpdateDataBean param) {
        this.param = param;
    }

    public class UpdateDataBean{
        private String type;//数据改变类型

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
