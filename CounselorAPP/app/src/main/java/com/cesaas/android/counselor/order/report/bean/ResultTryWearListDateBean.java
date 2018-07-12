package com.cesaas.android.counselor.order.report.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description 试穿数据分析结果Bean
 * Created 2017/4/20 19:55
 * Version 1.zero
 */
public class ResultTryWearListDateBean extends BaseBean{

    public List<TryWearListDateBean> TModel;


    public class TryWearListDateBean implements Serializable{
        private String Color;//颜色

        private String CreateName;//导购

        private String CreateTime;//添加时间

        private int Id;//记录id

        private String No;//商品编号

        private String Size;//商品款号大小

        private String Title;//试穿商品名称

        private String Cup;////罩杯

        public String getCup() {
            return Cup;
        }

        public void setCup(String cup) {
            Cup = cup;
        }

        public String getColor() {
            return Color;
        }

        public void setColor(String color) {
            Color = color;
        }

        public String getCreateName() {
            return CreateName;
        }

        public void setCreateName(String createName) {
            CreateName = createName;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getNo() {
            return No;
        }

        public void setNo(String no) {
            No = no;
        }

        public String getSize() {
            return Size;
        }

        public void setSize(String size) {
            Size = size;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }
    }
}
