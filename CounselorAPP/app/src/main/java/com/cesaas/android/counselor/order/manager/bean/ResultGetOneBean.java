package com.cesaas.android.counselor.order.manager.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/9/15 16:01
 * Version 1.0
 */

public class ResultGetOneBean extends BaseBean {

    public GetOne TModel;


    public class GetOne implements Serializable{

        /**
         * TId : 24
         * TaskId : 137
         * TaskTitle : 啊啊啊
         * Descriptoin : null
         * StartDate : null
         * EndDate : null
         * Status : 0
         * CategoryId : 0
         * TaskLeve : 0
         * Inuse : 0
         * CreateTime : 0001-01-01 00:00:00
         * SyncCode : null
         * CreateName : null
         * TemplateId : 0
         * ImageUrl : null
         * WorkQuantity : 0
         */

        private int TId;
        private int TaskId;
        private String TaskTitle;
        private String Descriptoin;
        private String StartDate;
        private String EndDate;
        private int Status;
        private int CategoryId;
        private int TaskLeve;
        private int Inuse;
        private String CreateTime;
        private String SyncCode;
        private String CreateName;
        private int TemplateId;
        private String ImageUrl;
        private int WorkQuantity;
        private List<String > Images;


        public List<String> getImages() {
            return Images;
        }

        public void setImages(List<String> images) {
            Images = images;
        }

        public int getTId() {
            return TId;
        }

        public void setTId(int TId) {
            this.TId = TId;
        }

        public int getTaskId() {
            return TaskId;
        }

        public void setTaskId(int taskId) {
            TaskId = taskId;
        }

        public String getTaskTitle() {
            return TaskTitle;
        }

        public void setTaskTitle(String taskTitle) {
            TaskTitle = taskTitle;
        }

        public String getDescriptoin() {
            return Descriptoin;
        }

        public void setDescriptoin(String descriptoin) {
            Descriptoin = descriptoin;
        }

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String startDate) {
            StartDate = startDate;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(String endDate) {
            EndDate = endDate;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
        }

        public int getCategoryId() {
            return CategoryId;
        }

        public void setCategoryId(int categoryId) {
            CategoryId = categoryId;
        }

        public int getTaskLeve() {
            return TaskLeve;
        }

        public void setTaskLeve(int taskLeve) {
            TaskLeve = taskLeve;
        }

        public int getInuse() {
            return Inuse;
        }

        public void setInuse(int inuse) {
            Inuse = inuse;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public String getSyncCode() {
            return SyncCode;
        }

        public void setSyncCode(String syncCode) {
            SyncCode = syncCode;
        }

        public String getCreateName() {
            return CreateName;
        }

        public void setCreateName(String createName) {
            CreateName = createName;
        }

        public int getTemplateId() {
            return TemplateId;
        }

        public void setTemplateId(int templateId) {
            TemplateId = templateId;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String imageUrl) {
            ImageUrl = imageUrl;
        }

        public int getWorkQuantity() {
            return WorkQuantity;
        }

        public void setWorkQuantity(int workQuantity) {
            WorkQuantity = workQuantity;
        }
    }
}
