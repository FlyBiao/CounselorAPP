package com.cesaas.android.counselor.order.webview.bean;

/**
 * Author FGB
 * Description 修改顶部标题
 * Created at 2017/5/12 17:34
 * Version 1.0
 */

public class TopTitleBean extends BaseTypeBean{

    private TitleBean param;

    public TitleBean getParam() {
        return param;
    }

    public void setParam(TitleBean param) {
        this.param = param;
    }

    public class TitleBean{
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
