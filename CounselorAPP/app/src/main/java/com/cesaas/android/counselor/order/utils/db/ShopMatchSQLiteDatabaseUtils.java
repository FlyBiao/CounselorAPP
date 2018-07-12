package com.cesaas.android.counselor.order.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cesaas.android.counselor.order.shopmange.bean.ShopSearchRecordBean;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 商品
 * Created at 2017/5/19 10:48
 * Version 1.0
 */

public class ShopMatchSQLiteDatabaseUtils extends SQLiteOpenHelper {

    private static  List<ShopSearchRecordBean> searchRecordBeanList=new ArrayList<>();
    private Context ct;
    private SQLiteDatabase db;

    //必须要有构造函数
    public ShopMatchSQLiteDatabaseUtils(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                        int version) {
        super(context, name, factory, version);
        this.ct=context;
    }

    // 当第一次创建数据库的时候，调用该方法
    public void onCreate(SQLiteDatabase db) {
//        if(exitsTable("shop_search_table",ct)==true){
//
//        }else{
            db.execSQL(TableUtils.shop_sql);
//        }
    }

    //当更新数据库的时候执行该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(exitsTable("shop_search_table",ct)==true){

        }else{
            db.execSQL(TableUtils.shop_sql);
        }
    }


    //创建sqlite数据库
    public void createDB(Context ct,String dbName,int version){
        //创建StuDBHelper对象
        ShopMatchSQLiteDatabaseUtils dbHelper = new ShopMatchSQLiteDatabaseUtils(ct,dbName,null,version);
        //得到一个可读的SQLiteDatabase对象
        db =dbHelper.getReadableDatabase();
    }

    //插入数据
    public void insterData(Context ct,int id,String name){
        if(exitsTable("shop_search_table",ct)==true){
            //生成ContentValues对象 //key:列名，value:想插入的值
            ContentValues cv = new ContentValues();
            //往ContentValues对象存放数据，键-值对模式
            cv.put("id", id);
            cv.put("name", name);
            //调用insert方法，将数据插入数据库
            db.insert("shop_search_table", null, cv);
            Log.i(DBConstant.TAG,"insterData-------成功!>" + "姓名："+name+" "+"id："+id);
        }else{
            db.execSQL(TableUtils.shop_sql);
        }

        db.close();
    }

    //查询数据
    public void selectData(Context ct){
        if(exitsTable("shop_search_table",ct)==true){
            //参数1：表名
            //参数2：要想显示的列
            //参数3：where子句
            //参数4：where子句对应的条件值
            //参数5：分组方式
            //参数6：having条件
            //参数7：排序方式
            searchRecordBeanList=new ArrayList<>();
            Cursor cursor = db.query("shop_search_table", new String[]{"id","name"}, null, null, null, null, null);
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                Log.i(DBConstant.TAG,"query------->" + "姓名："+name+" "+"id："+id);

                ShopSearchRecordBean bean=new ShopSearchRecordBean();
                bean.setId(id);
                bean.setName(name);
                searchRecordBeanList.add(bean);
            }
            EventBus.getDefault().post(searchRecordBeanList);
        }else{
            db.execSQL(TableUtils.shop_sql);
        }
        //关闭数据库
        db.close();
    }

    //删除所有数据数据
    public  void delete(Context ct){
        if(exitsTable("shop_search_table",ct)==true){
            db.delete("shop_search_table", null, null);
            Log.i(DBConstant.TAG,"删除数据------->" );
        }else{
            db.execSQL(TableUtils.shop_sql);
        }
        db.close();
    }

    //删除指定数据
    public  void deleteById(Context ct,int id){
        if(exitsTable("shop_search_table",ct)==true){
            String [] whereArgs = {String.valueOf(id)};
            //调用delete方法，删除数据
            db.execSQL("delete from shop_search_table where id="+whereArgs);
            Log.i(DBConstant.TAG,"删除数据------->" );
        }else{
            db.execSQL(TableUtils.shop_sql);
        }
        db.close();
    }

    /**
     * 判断数据库表是否存在
     * @param table 表名
     * @return
     */
    public boolean exitsTable(String table,Context ct){
        boolean exits = false;
        String sql = "select * from sqlite_master where name="+"'"+table+"'";
        //创建StuDBHelper对象
        ShopMatchSQLiteDatabaseUtils dbHelper = new ShopMatchSQLiteDatabaseUtils(ct,DBConstant.DB,null,DBConstant.VERSION);
        //得到一个可读的SQLiteDatabase对象
        db =dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.getCount()!=0){
            exits = true;
        }
        return exits;
    }
}
