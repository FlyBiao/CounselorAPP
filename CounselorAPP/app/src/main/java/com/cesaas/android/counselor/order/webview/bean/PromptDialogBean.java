package com.cesaas.android.counselor.order.webview.bean;

import java.util.List;

/**
 * Author FGB
 * Description 弹出带输入框的Dialog
 * Created at 2017/5/12 17:20
 * Version 1.0
 */

public class PromptDialogBean extends BaseTypeBean {

    private PromptMessage param;
    private List<PromptItems> items;

    public PromptMessage getParam() {
        return param;
    }

    public void setParam(PromptMessage param) {
        this.param = param;
    }

    public List<PromptItems> getItems() {
        return items;
    }

    public void setItems(List<PromptItems> items) {
        this.items = items;
    }

    public class PromptMessage{
        private String title;
        private String text;

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

    public class PromptItems{
        private String value;//默认值
        private String text;//提示语
        private String title;
        private String color;
        private String empty;//1为必填
        private String type;//0文字,1数字

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getEmpty() {
            return empty;
        }

        public void setEmpty(String empty) {
            this.empty = empty;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }



}
