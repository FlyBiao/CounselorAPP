package com.cesaas.android.counselor.order.member.bean.service.custom;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/3/9 17:33
 * Version 1.0
 */

public class ResultVipGradeBean extends BaseBean {
    public List<VipGradeBean> TModel;


    public class VipGradeBean implements Serializable{
        private int Id;
        private String Title;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }
    }
}
