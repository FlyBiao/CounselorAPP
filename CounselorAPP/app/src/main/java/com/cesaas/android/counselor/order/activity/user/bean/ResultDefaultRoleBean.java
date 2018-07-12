package com.cesaas.android.counselor.order.activity.user.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/4/26 17:49
 * Version 1.0
 */

public class ResultDefaultRoleBean extends BaseBean {
    public List<DefaultRoleBean> TModel;




    public class DefaultRoleBean implements Serializable{
        private int RoleId;
        private String RoleName;

        public int getRoleId() {
            return RoleId;
        }

        public void setRoleId(int roleId) {
            RoleId = roleId;
        }

        public String getRoleName() {
            return RoleName;
        }

        public void setRoleName(String roleName) {
            RoleName = roleName;
        }
    }
}
