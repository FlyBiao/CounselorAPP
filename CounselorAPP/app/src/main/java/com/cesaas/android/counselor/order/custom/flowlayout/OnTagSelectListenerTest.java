package com.cesaas.android.counselor.order.custom.flowlayout;

import com.cesaas.android.counselor.order.label.bean.ResultGetTagListBean;

import java.util.List;

/**
 * Created by FlyBiao on 15/10/20.
 */
public interface OnTagSelectListenerTest {
    void onItemSelect(FlowTagLayout parent, List<ResultGetTagListBean.GetTagListBean> selectedList);
}
