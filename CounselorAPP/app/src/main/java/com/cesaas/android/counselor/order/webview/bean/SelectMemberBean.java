package com.cesaas.android.counselor.order.webview.bean;

/**
 * Author FGB
 * Description 选择会员Bean
 * Created at 2017/5/12 16:41
 * Version 1.0
 */

public class SelectMemberBean extends BaseTypeBean{

    private MemberBean param;

    public MemberBean getParam() {
        return param;
    }

    public void setParam(MemberBean param) {
        this.param = param;
    }

    public class MemberBean{

        private int mutil;//是否多选 0：单选，1：多选

        public int getMutil() {
            return mutil;
        }

        public void setMutil(int mutil) {
            this.mutil = mutil;
        }
    }
}
