package com.cesaas.android.counselor.order.manager.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/9/19 8:38
 * Version 1.0
 */

public class ResultTemplateListBean extends BaseBean {


    public List<TemplateListBean> TModel;


    public class TemplateListBean implements Serializable{

        /**
         * TemplateId : 44
         * CategoryId : 1
         * TemplateName : 模板名称
         * Status : 1
         */

        private int TemplateId;
        private int CategoryId;
        private String TemplateName;
        private int Status;

        public void setTemplateId(int TemplateId) {
            this.TemplateId = TemplateId;
        }

        public void setCategoryId(int CategoryId) {
            this.CategoryId = CategoryId;
        }

        public void setTemplateName(String TemplateName) {
            this.TemplateName = TemplateName;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public int getTemplateId() {
            return TemplateId;
        }

        public int getCategoryId() {
            return CategoryId;
        }

        public String getTemplateName() {
            return TemplateName;
        }

        public int getStatus() {
            return Status;
        }
    }
}
