package com.cesaas.android.counselor.order.shopmange.bean;

/**
 * Author FGB
 * Description 过滤店员类型Bean
 * Created at 2017/6/14 15:28
 * Version 1.0
 */

public class FilterClerkTypeBean {

    private int id;
    private int filterAuditType;
    private int filterDimissionType;
    private int filterJobType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilterAuditType() {
        return filterAuditType;
    }

    public void setFilterAuditType(int filterAuditType) {
        this.filterAuditType = filterAuditType;
    }

    public int getFilterDimissionType() {
        return filterDimissionType;
    }

    public void setFilterDimissionType(int filterDimissionType) {
        this.filterDimissionType = filterDimissionType;
    }

    public int getFilterJobType() {
        return filterJobType;
    }

    public void setFilterJobType(int filterJobType) {
        this.filterJobType = filterJobType;
    }
}
