package com.cesaas.android.counselor.order.autoCompletetextview.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description PopupTextBean
 * Created 2017/3/17 9:45
 * Version 1.zero
 */
public class PopupTextBean implements Serializable {

    public String mTarget;//索引目标
    public int mStartIndex = -1; //开始索引
    public int mEndIndex = -1;//结束索引

    public PopupTextBean(String target) {
        this.mTarget = target;
    }

    public PopupTextBean(String target, int startIndex) {
        this.mTarget = target;
        this.mStartIndex = startIndex;
        if (-1 != startIndex) {
            this.mEndIndex = startIndex + target.length();
        }
    }

    public PopupTextBean(String target, int startIndex, int endIndex) {
        this.mTarget = target;
        this.mStartIndex = startIndex;
        this.mEndIndex = endIndex;
    }
}
