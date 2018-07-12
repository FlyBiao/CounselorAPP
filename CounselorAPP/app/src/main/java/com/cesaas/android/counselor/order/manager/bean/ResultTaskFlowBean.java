package com.cesaas.android.counselor.order.manager.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/9/15 17:23
 * Version 1.0
 */

public class ResultTaskFlowBean extends BaseBean {

    public List<TaskFlowBean> TModel;


    public class TaskFlowBean implements Serializable{
        private int Id;
        private String Title;
        private int Status;
        private String Sales;
        private String CreateTime;
        private String Remark;

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

        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
        }

        public String getSales() {
            return Sales;
        }

        public void setSales(String sales) {
            Sales = sales;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }
    }
}
