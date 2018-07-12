package com.cesaas.android.counselor.order.bean;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/1/17 11:43
 * Version 1.0
 */

public class ResultBandBean extends BaseBean {
    private List<BandBean> TModel;

    public List<BandBean> getTModel() {
        return TModel;
    }

    public void setTModel(List<BandBean> TModel) {
        this.TModel = TModel;
    }
}
