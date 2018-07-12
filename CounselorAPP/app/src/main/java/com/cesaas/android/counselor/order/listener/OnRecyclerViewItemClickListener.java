package com.cesaas.android.counselor.order.listener;

import android.view.View;

/**
 * Author FGB
 * Description OnRecyclerViewItemClickListener
 * Created 2017/3/20 13:38
 * Version 1.zero
 */
public interface OnRecyclerViewItemClickListener {
    /**
     * Item View发生点击回调的方法
     * @param view   点击的View
     * @paramposition  具体Item View的索引
     */
    void onItemClick(View view, int position);
}
