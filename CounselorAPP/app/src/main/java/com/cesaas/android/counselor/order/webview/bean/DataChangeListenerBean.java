package com.cesaas.android.counselor.order.webview.bean;

import java.util.List;

/**
 * Author FGB
 * Description 数据改变监听
 * Created at 2017/6/9 11:38
 * Version 1.0
 */

public class DataChangeListenerBean extends BaseTypeBean{


    /**
     * param : {"types":[2]}
     */

    private ParamEntity param;

    public void setParam(ParamEntity param) {
        this.param = param;
    }

    public ParamEntity getParam() {
        return param;
    }

    public static class ParamEntity {

        private List<Integer> types;

        public void setTypes(List<Integer> types) {
            this.types = types;
        }

        public List<Integer> getTypes() {
            return types;
        }
    }
}
