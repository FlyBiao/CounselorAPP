package com.cesaas.android.counselor.order.webview.bean;

import java.util.List;

/**
 * Author FGB
 * Description 筛选标签
 * Created at 2017/5/14 10:03
 * Version 1.0
 */

public class LabelScreenBean extends BaseTypeBean{
    private LabelBean param;

    public LabelBean getParam() {
        return param;
    }

    public void setParam(LabelBean param) {
        this.param = param;
    }

    public class LabelBean{
        private List<Integer> id;

        public List<Integer> getId() {
            return id;
        }

        public void setId(List<Integer> id) {
            this.id = id;
        }
    }

}
