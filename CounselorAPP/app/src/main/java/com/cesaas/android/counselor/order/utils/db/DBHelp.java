package com.cesaas.android.counselor.order.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.shopmange.bean.ClerkSearchRecordBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 店员查询记录
 * Created at 2017/5/19 10:48
 * Version 1.0
 */

public class DBHelp extends SQLiteOpenHelper {

    public static  List<ClerkSearchRecordBean> clerkSearchRecordBeen=new ArrayList<>();
    private SQLiteDatabase db=null;
    private Context ct;

    //必须要有构造函数
    public DBHelp(Context context, String name, SQLiteDatabase.CursorFactory factory,
                  int version) {
        super(context, name, factory, version);
        this.ct=context;
    }

    // 当第一次创建数据库的时候，调用该方法
    public void onCreate(SQLiteDatabase db) {
//        if(exitsTable("clerk_record_table")==true){
//        }else{
            db.execSQL(TableUtils.clerk_search_record_sql);
            //输出更新数据库的日志信息
            Log.i(DBConstant.TAG, "update Database------------->2");
//        }
    }

    //当更新数据库的时候执行该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(exitsTable("clerk_record_table")==true){
        }else{
            db.execSQL(TableUtils.clerk_search_record_sql);
            //输出更新数据库的日志信息
            Log.i(DBConstant.TAG, "update Database------------->2");
        }
    }

    //创建sqlite数据库
    public void createDB(Context ct, String dbName, int version){
        //创建DBHelp对象
        DBHelp dbHelper = new DBHelp(ct,dbName,null,version);
        //得到一个可读的SQLiteDatabase对象
        db =dbHelper.getReadableDatabase();
        Log.i(DBConstant.TAG, "dbHelper ------------->"+exitsDB(DBConstant.DB_PATH+DBConstant.DB));
//        if(exitsDB(DBConstant.DB_PATH+DBConstant.DB)==true){
//
//        }else{
//
//        }
    }


    //插入数据
    public void insterData(Context ct,String name){
        if(exitsTable("clerk_record_table")==true){
            //生成ContentValues对象 //key:列名，value:想插入的值
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            db.insert("clerk_record_table", null, cv);//将数据插入数据库
        }else{
            db.execSQL(TableUtils.clerk_search_record_sql);
        }
        //关闭数据库
        db.close();
    }

    //查询数据
    public void selectData(Context ct){
        if(exitsTable("clerk_record_table")==true){
            //参数1：表名 参数2：要想显示的列  参数3：where子句 参数4：where子句对应的条件值  参数5：分组方式  参数6：having条件  参数7：排序方式
            clerkSearchRecordBeen=new ArrayList<>();
            Cursor cursor = db.query("clerk_record_table", new String[]{"id","name"}, null, null, null, null, null);
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String id = cursor.getString(cursor.getColumnIndex("id"));

                ClerkSearchRecordBean bean=new ClerkSearchRecordBean();
                bean.setId(Integer.parseInt(id));
                bean.setName(name);
                clerkSearchRecordBeen.add(bean);
            }
            EventBus.getDefault().post(clerkSearchRecordBeen);

        }else{//创建表
            db.execSQL(TableUtils.clerk_search_record_sql);
        }
        //关闭数据库
        db.close();
    }

    //删除所有数据数据
    public  void delete(Context ct){
        if(exitsTable("clerk_record_table")==true){
            db.delete("clerk_record_table", null, null);
        }else{
            db.execSQL(TableUtils.clerk_search_record_sql);
        }

        db.close();
    }

    //删除指定数据
    public void deleteById(Context ct,int id){
        if(exitsTable("clerk_record_table")==true){
            //调用delete方法，删除数据
            String del_sql="delete from clerk_record_table where id="+id;
            db.execSQL(del_sql);
        }else{
            db.execSQL(TableUtils.clerk_search_record_sql);
        }
        db.close();
    }

    /**
     * 判断DB是否存在
     * @param
     * @param dbName
     * @return
     */
    public boolean exitsDB(String dbName){
        boolean flag=false;
        SQLiteDatabase db=null;
        try{
            db=SQLiteDatabase.openOrCreateDatabase(dbName,null);
            flag=true;
        }catch (Exception e){
            flag=false;
        }finally {
            if(db!=null) db.close();
            db=null;
        }
        return flag;
    }

    /**
     * 判断数据库表是否存在
     * @param table 表名
     * @return
     */
    public boolean exitsTable(String table){
        boolean exits = false;
        String sql = "select * from sqlite_master where name="+"'"+table+"'";
        //创建DBHelp对象
        DBHelp dbHelper = new DBHelp(ct,DBConstant.DB,null,DBConstant.VERSION);
        //得到一个可读的SQLiteDatabase对象
        db =dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.getCount()!=0){
            exits = true;
        }
        return exits;
    }
}