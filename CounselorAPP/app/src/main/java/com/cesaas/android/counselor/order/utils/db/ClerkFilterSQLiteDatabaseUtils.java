package com.cesaas.android.counselor.order.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cesaas.android.counselor.order.member.bean.SearchRecordBean;
import com.cesaas.android.counselor.order.shopmange.bean.FilterClerkTypeBean;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 店员过滤
 * Created at 2017/5/19 10:48
 * Version 1.0
 */

public class ClerkFilterSQLiteDatabaseUtils extends SQLiteOpenHelper {

    private static FilterClerkTypeBean typeBean=new FilterClerkTypeBean();
    private Context ct;
    private SQLiteDatabase db;

    //必须要有构造函数
    public ClerkFilterSQLiteDatabaseUtils(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                          int version) {
        super(context, name, factory, version);
        this.ct=context;
    }

    // 当第一次创建数据库的时候，调用该方法
    public void onCreate(SQLiteDatabase db) {
//        if(exitsTable("clerk_filter_type_table",ct)==true){
//
//        }else{
            db.execSQL(TableUtils.clerk_filter_sql);
            //输出更新数据库的日志信息
            Log.i(DBConstant.TAG, "当第一次创建数据库的时候------------->");
//        }
    }

    //当更新数据库的时候执行该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(exitsTable("clerk_filter_type_table",ct)==true){

        }else{
            db.execSQL(TableUtils.clerk_filter_sql);
            //输出更新数据库的日志信息
            Log.i(DBConstant.TAG, "当更新数据库的时候执行该方法------------->");
        }
    }

    //创建sqlite数据库
    public void createDB(Context ct, String dbName, int version){
        //创建StuDBHelper对象
        ClerkFilterSQLiteDatabaseUtils dbHelper = new ClerkFilterSQLiteDatabaseUtils(ct,dbName,null,version);
        //得到一个可读的SQLiteDatabase对象 创建数据库
        db =dbHelper.getReadableDatabase();

    }

    //插入数据
    public void insterData(Context ct,int filterAuditType,int filterDimissionType,int filterJobType){
        if(exitsTable("clerk_filter_type_table",ct)==true){
            //生成ContentValues对象 //key:列名，value:想插入的值
            ContentValues cv = new ContentValues();
            //往ContentValues对象存放数据，键-值对模式
            cv.put("filterAuditType", filterAuditType);
            cv.put("filterDimissionType", filterDimissionType);
            cv.put("filterJobType", filterJobType);

            //调用insert方法，将数据插入数据库
            db.insert("clerk_filter_type_table", null, cv);
            Log.i(DBConstant.TAG,"insert------->" );
        }else{
            db.execSQL(TableUtils.clerk_filter_sql);
        }
        //关闭数据库
        db.close();

    }

    //更新数据
    public  void updateData(Context ct,int filterAuditType,int filterDimissionType,int filterJobType,int id){
        if(exitsTable("clerk_filter_type_table",ct)==true){
//            String set_sql="update clerk_filter_type_table set filterDimissionType='1',filterJobType='1' where id=1 ";
            String set_sql="update clerk_filter_type_table set filterAuditType="+"'"+filterAuditType+"'"
                    +","
                    +"filterDimissionType="+"'"+filterDimissionType+"'"
                    +","
                    +"filterJobType="+"'"+filterJobType+"'"
                    +" where id="+"'"+id+"'";
            db.execSQL(set_sql);
            Log.i(DBConstant.TAG,"updateData更新数据------->"+filterAuditType+"   "+ filterDimissionType+"  "+filterJobType+"  "+id);
        }else{
            db.execSQL(TableUtils.clerk_filter_sql);
        }
        db.close();
    }

    //查询数据
    public void selectData(Context ct){
        if(exitsTable("clerk_filter_type_table",ct)==true){
            //参数1：表名
            //参数2：要想显示的列
            //参数3：where子句
            //参数4：where子句对应的条件值
            //参数5：分组方式
            //参数6：having条件
            //参数7：排序方式
            typeBean=new FilterClerkTypeBean();
            Cursor cursor = db.query("clerk_filter_type_table", new String[]{"id","filterAuditType","filterDimissionType","filterJobType"},null, null, null, null, null);
            if(cursor.getCount()==0){
                insterData(ct,0,0,0);
            }else{
                while(cursor.moveToNext()){
                    String filterAuditType = cursor.getString(cursor.getColumnIndex("filterAuditType"));
                    String filterDimissionType = cursor.getString(cursor.getColumnIndex("filterDimissionType"));
                    String filterJobType = cursor.getString(cursor.getColumnIndex("filterJobType"));
                    String id = cursor.getString(cursor.getColumnIndex("id"));

                    Log.i(DBConstant.TAG,"query------->" + "filterAuditType"+filterAuditType+" "+" filterDimissionType："+filterDimissionType+"filterJobType:"+filterJobType+"===id:"+id);

                    typeBean.setId(Integer.parseInt(id));
                    typeBean.setFilterAuditType(Integer.parseInt(filterAuditType));
                    typeBean.setFilterDimissionType(Integer.parseInt(filterDimissionType));
                    typeBean.setFilterJobType(Integer.parseInt(filterJobType));
                }
                EventBus.getDefault().post(typeBean);
            }

        }else{
            db.execSQL(TableUtils.clerk_filter_sql);
        }
        //关闭数据库
        db.close();
    }

    //删除所有数据数据
    public  void delete(Context ct){
        if(exitsTable("clerk_filter_type_table",ct)==true){
            db.delete("clerk_filter_type_table", null, null);
            Log.i(DBConstant.TAG,"删除数据------->" );
        }else{
            db.execSQL(TableUtils.clerk_filter_sql);
        }
        db.close();
    }

    //删除指定数据
    public void deleteById(Context ct,int id){
        if(exitsTable("clerk_filter_type_table",ct)==true){
            //调用delete方法，删除数据
            String del_sql="delete from clerk_filter_type_table where id="+id;
            db.execSQL(del_sql);
            Log.i(DBConstant.TAG,"删除数据------->" );
        }else{
            db.execSQL(TableUtils.clerk_filter_sql);
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
        ClerkFilterSQLiteDatabaseUtils dbHelper = new ClerkFilterSQLiteDatabaseUtils(ct,DBConstant.DB,null,DBConstant.VERSION);
        //得到一个可读的SQLiteDatabase对象 创建数据库
        db =dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.getCount()!=0){
            exits = true;
        }
        return exits;
    }
}
