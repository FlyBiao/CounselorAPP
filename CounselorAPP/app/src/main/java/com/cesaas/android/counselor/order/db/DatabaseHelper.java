package com.cesaas.android.counselor.order.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DatabaseHelper
 * @author FGB
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper{

	private static final int VERSION = 1;  
    private static final String SWORD="SWORD";  
    
    /**
     * 带全部参数的构造函数，此构造函数必不可少
     * @param context 上下文
     * @param name 数据库名称
     * @param factory 游标工厂
     * @param version 数据库版本
     */
    public DatabaseHelper(Context context, String name, CursorFactory factory,  
            int version) {  
        super(context, name, factory, version);  
          
    }  
    
    /**
     * 带两个参数的构造函数，调用的其实是带三个参数的构造函数  
     * @param context 上下文
     * @param name 数据库名
     */
    public DatabaseHelper(Context context,String name){  
        this(context,name,VERSION);  
    }  
    //带三个参数的构造函数，调用的是带所有参数的构造函数  
    public DatabaseHelper(Context context,String name,int version){  
        this(context, name,null,version);  
    }  
    
    /**
     * 创建数据库 表
     */
    public void onCreate(SQLiteDatabase db) {  
        Log.i(SWORD,"create a Database");  
        //创建数据库表sql语句  
        //创建挂单表
        String sql = "CREATE TABLE HANG_ORDER(id integer primary key,orderNo varchar(50),orderDate varchar(50),vipName varchar(50),Discount double,Coupon double)"; 
        //订单商品表
        String sql1 = "CREATE TABLE ORDER_SHOP(id integer primary key,Title varchar(50),ShopStyleId varchar(50),StyleCode varchar(50),BarcodeCode varchar(50),BarcodeId varchar(50),ImageUrl varchar(50),Price double,ShopCount int)";  
        //执行创建数据库操作  
        db.execSQL(sql);  
    }  
  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
        //创建成功，日志输出提示  
        Log.i(SWORD,"update a Database");  
    }  
}
