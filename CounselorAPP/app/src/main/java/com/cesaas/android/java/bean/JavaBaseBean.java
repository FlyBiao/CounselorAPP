package com.cesaas.android.java.bean;

/**
 * Author FGB
 * Description
 * Created at 2018/6/6 18:41
 * Version 1.0
 */

public class JavaBaseBean {
    public int errorCode;
    public int totalCount;
    public String errorMessage;
    public String _classname;
    public int countBoxNum;   // 总箱数
    public int countNum;      // 总件数
    public int shipmentsNum;      // 发货总数
    public int num;      // 总收货件总数
    public int differenceNum;      // 差异总数
    public int sendNum;      // 收货箱总发货数
    public int nums;      // 收货箱总收货数

	public StatisticModelBean model;
}
