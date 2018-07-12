package com.cesaas.android.counselor.order.webview.bean;

/**
 * Author FGB
 * Description 确认对话框Bean
 * Created at 2017/5/12 17:12
 * Version 1.0
 */

public class ConfirmDialogBean extends BaseTypeBean {

    private DialogBean param;

    public DialogBean getParam() {
        return param;
    }

    public void setParam(DialogBean param) {
        this.param = param;
    }

    public class DialogBean{
        private String title;//提示标题
        private String text;//文本内容

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
