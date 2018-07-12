package com.cesaas.android.counselor.order.custom.tablayout.bean;

/**
 * Author FGB
 * Description
 * Created 2017/4/6 10:41
 * Version 1.zero
 */
public class TabItem {

    /**
     * icon
     */
    public int imageResId;
    /**
     * 文本
     */
    public int lableResId;


    public Class<? extends Fragment>tagFragmentClz;

    public TabItem(int imageResId, int lableResId) {
        this.imageResId = imageResId;
        this.lableResId = lableResId;
    }


    public TabItem(int imageResId, int lableResId, Class<? extends Fragment> tagFragmentClz) {
        this.imageResId = imageResId;
        this.lableResId = lableResId;
        this.tagFragmentClz = tagFragmentClz;
    }
}
