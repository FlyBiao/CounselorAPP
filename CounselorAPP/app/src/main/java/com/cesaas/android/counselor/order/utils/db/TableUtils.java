package com.cesaas.android.counselor.order.utils.db;

/**
 * Author FGB
 * Description
 * Created at 2017/5/31 16:21
 * Version 1.0
 */

public class TableUtils {
    //商品
    public static final String shop_sql = "create table shop_search_table(id INTEGER PRIMARY KEY AUTOINCREMENT,name varchar(100))";
    //会员
    public static String vip_sql = "create table vip_search_table(id int,name varchar(20))";
    //店员过滤 filterType 0:显示 1：隐藏（默认0）
    public static String clerk_filter_sql = "create table clerk_filter_type_table(id INTEGER PRIMARY KEY AUTOINCREMENT,filterAuditType int default 0 ,filterDimissionType int default 0 ,filterJobType int default 0 )";
    //店员查询记录
    public static String clerk_search_record_sql = "create table clerk_record_table(id INTEGER PRIMARY KEY AUTOINCREMENT,name varchar(100))";


    //删除表数据
    public static final String del_order_sql = "DROP TABLE clerk_filter_type_table";
}
