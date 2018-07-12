package com.cesaas.android.order.bean.retail;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.util.List;

/**
 * Author FGB
 * Description 根据商品条码查询sku以及有货店铺列表信息
 * Created at 2018/5/10 14:24
 * Version 1.0
 */

public class ResultSearchByBarcodeBean extends BaseBean {
    public List<SearchByBarcodeBean> TModel;
}
