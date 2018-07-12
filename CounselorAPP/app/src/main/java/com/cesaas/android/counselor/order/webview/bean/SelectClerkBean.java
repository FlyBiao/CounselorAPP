package com.cesaas.android.counselor.order.webview.bean;

import java.util.List;

/**
 * Author FGB
 * Description 选择店员
 * Created at 2017/5/12 18:04
 * Version 1.0
 */

public class SelectClerkBean extends BaseTypeBean {

    private ClerkBean param;

    public ClerkBean getParam() {
        return param;
    }

    public void setParam(ClerkBean param) {
        this.param = param;
    }

    public class ClerkBean{
        private String multi;
        private int position_id;
        private List<Integer> role_id;//角色编号

        public String getMulti() {
            return multi;
        }

        public void setMulti(String multi) {
            this.multi = multi;
        }

        public int getPosition_id() {
            return position_id;
        }

        public void setPosition_id(int position_id) {
            this.position_id = position_id;
        }

                public List<Integer> getRole_id() {
            return role_id;
        }

        public void setRole_id(List<Integer> role_id) {
            this.role_id = role_id;
        }
    }

}
