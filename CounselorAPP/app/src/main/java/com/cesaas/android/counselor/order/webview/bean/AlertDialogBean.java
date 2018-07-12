package com.cesaas.android.counselor.order.webview.bean;

/**
 * Author FGB
 * Description 弹出提示框对话框
 * Created at 2017/5/12 16:54
 * Version 1.0
 */

public class AlertDialogBean extends BaseTypeBean {

    private DialogBean param;

    public DialogBean getParam() {
        return param;
    }

    public void setParam(DialogBean param) {
        this.param = param;
    }

    public class DialogBean{
        private String message;//提示文字
        private String type;//样式：success,error,warning

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
