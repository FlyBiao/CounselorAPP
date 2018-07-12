package com.cesaas.android.counselor.order.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.cesaas.android.counselor.order.activity.user.bean.EditTextChangeValueBean;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 实时监听EditText内容
 * Created at 2018/5/29 11:26
 * Version 1.0
 */

public class EditTextChangeListener implements TextWatcher {

    /**
     * 编辑框的内容发生改变之前的回调方法
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * 编辑框的内容正在发生改变时的回调方法 >>用户正在输入
     * 我们可以在这里实时地 通过搜索匹配用户的输入
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
     */
    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString().length()==8){
            EditTextChangeValueBean bean=new EditTextChangeValueBean();
            bean.setLength(s.toString().length());
            bean.setValue(s.toString());
            EventBus.getDefault().post(bean);
        }
    }
}
