package com.cesaas.android.counselor.order.member.bean.service;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;
import java.util.List;


/**
 * Author FGB
 * Description 查询店铺导购
 * Created at 2018/3/10 9:51
 * Version 1.0
 */

public class ResultCounselorListBean extends BaseBean{

    public List<CounselorListBean> TModel;

    public class CounselorListBean implements Serializable{
        private int Id;
        private String Name;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }
}
