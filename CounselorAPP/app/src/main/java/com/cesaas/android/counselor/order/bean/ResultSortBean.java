package com.cesaas.android.counselor.order.bean;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/1/17 12:00
 * Version 1.0
 */

public class ResultSortBean extends BaseBean{
    private List<SortList> TModel;

    public List<SortList> getTModel() {
        return TModel;
    }

    public void setTModel(List<SortList> TModel) {
        this.TModel = TModel;
    }
}
