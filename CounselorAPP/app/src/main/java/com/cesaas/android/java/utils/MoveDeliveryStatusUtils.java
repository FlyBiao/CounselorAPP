package com.cesaas.android.java.utils;

import android.content.Context;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Author FGB
 * Description 调货状态Utils
 * Created at 2018/6/2 15:48
 * Version 1.0
 */

public class MoveDeliveryStatusUtils {

    //0:制单(未生差异)10:生成差异、通知捡货15 开始捡货20:捡货完成、确认差异30:提交40:确认41:通过申请42:拒绝申请45:正在发货46:已部分发货47:完成通知发货50:驳回60停用
    public static void getStatus(BaseViewHolder helper, int status, Context mContext){
        switch (status){
            case 0://草稿
                helper.setText(R.id.tv_status_icon,R.string.fa_pencil);
                helper.setTypeface(R.id.tv_status_icon, App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"草稿");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 10://生成差异
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent4));
                helper.setText(R.id.tv_mover_status,"生成差异");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent4));
                break;
            case 15://开始捡货
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.green_pressed));
                helper.setText(R.id.tv_mover_status,"开始捡货");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.green_pressed));
                break;
            case 20://确认差异
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.green_pressed));
                helper.setText(R.id.tv_mover_status,"确认差异");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.green_pressed));
                break;
            case 30://已提交
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"已提交");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 40://已确认
                helper.setText(R.id.tv_status_icon,R.string.fa_check);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.darkslategray));
                helper.setText(R.id.tv_mover_status,"已确认");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.darkslategray));
                break;
            case 41://通过申请
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"通过申请");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 42://拒绝申请
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"拒绝申请");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 45://正在发货
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"正在发货");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 46://已部分发货
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"已部分发货");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 47://完成通知发货
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"完成通知发货");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 50://已驳回
                helper.setText(R.id.tv_status_icon,R.string._cancel);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.red));
                helper.setText(R.id.tv_mover_status,"已驳回");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.red));
                break;
            case 60://已停用
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colordefault));
                helper.setText(R.id.tv_mover_status,"已停用");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colordefault));
                break;
        }
    }

    public static void getDetailStatus(TextView tv_status_icon,TextView tv_mover_status, int status, Context mContext){
        switch (status){
            case 0://草稿
                tv_status_icon.setText(R.string.fa_pencil);
                tv_status_icon.setTypeface(App.font);
                tv_status_icon.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                tv_mover_status.setText("草稿");
                tv_mover_status.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 10://生成差异
                tv_status_icon.setText(R.string.fa_circle);
                tv_status_icon.setTypeface(App.font);
                tv_status_icon.setTextColor(mContext.getResources().getColor(R.color.colorAccent4));
                tv_mover_status.setText("生成差异");
                tv_mover_status.setTextColor(mContext.getResources().getColor(R.color.colorAccent4));
                break;
            case 15://开始捡货
                tv_status_icon.setText(R.string.fa_circle);
                tv_status_icon.setTypeface(App.font);
                tv_status_icon.setTextColor(mContext.getResources().getColor(R.color.green_pressed));
                tv_mover_status.setText("开始捡货");
                tv_mover_status.setTextColor(mContext.getResources().getColor(R.color.green_pressed));
                break;
            case 20://确认差异
                tv_status_icon.setText(R.string.fa_circle);
                tv_status_icon.setTypeface(App.font);
                tv_status_icon.setTextColor(mContext.getResources().getColor(R.color.green_pressed));
                tv_mover_status.setText("确认差异");
                tv_mover_status.setTextColor(mContext.getResources().getColor(R.color.green_pressed));
                break;
            case 30://已提交
                tv_status_icon.setText(R.string.fa_circle);
                tv_status_icon.setTypeface(App.font);
                tv_status_icon.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                tv_mover_status.setText("已提交");
                tv_mover_status.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 40://已确认
                tv_status_icon.setText(R.string.fa_check);
                tv_status_icon.setTypeface(App.font);
                tv_status_icon.setTextColor(mContext.getResources().getColor(R.color.darkslategray));
                tv_mover_status.setText("已确认");
                tv_mover_status.setTextColor(mContext.getResources().getColor(R.color.darkslategray));
                break;
            case 41://通过申请
                tv_status_icon.setText(R.string.fa_circle);
                tv_status_icon.setTypeface(App.font);
                tv_status_icon.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                tv_mover_status.setText("通过申请");
                tv_mover_status.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 42://拒绝申请
                tv_status_icon.setText(R.string.fa_circle);
                tv_status_icon.setTypeface(App.font);
                tv_status_icon.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                tv_mover_status.setText("拒绝申请");
                tv_mover_status.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 45://正在发货
                tv_status_icon.setText(R.string.fa_circle);
                tv_status_icon.setTypeface(App.font);
                tv_status_icon.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                tv_mover_status.setText("正在发货");
                tv_mover_status.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 46://已部分发货
                tv_status_icon.setText(R.string.fa_circle);
                tv_status_icon.setTypeface(App.font);
                tv_status_icon.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                tv_mover_status.setText("已部分发货");
                tv_mover_status.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 47://完成通知发货
                tv_status_icon.setText(R.string.fa_circle);
                tv_status_icon.setTypeface(App.font);
                tv_status_icon.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                tv_mover_status.setText("完成通知发货");
                tv_mover_status.setTextColor(mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 50://已驳回
                tv_status_icon.setText(R.string._cancel);
                tv_status_icon.setTypeface(App.font);
                tv_status_icon.setTextColor(mContext.getResources().getColor(R.color.red));
                tv_mover_status.setText("已驳回");
                tv_mover_status.setTextColor(mContext.getResources().getColor(R.color.red));
                break;
            case 60://已停用
                tv_status_icon.setText(R.string.fa_circle);
                tv_status_icon.setTypeface(App.font);
                tv_status_icon.setTextColor(mContext.getResources().getColor(R.color.colordefault));
                tv_mover_status.setText("已停用");
                tv_mover_status.setTextColor(mContext.getResources().getColor(R.color.colordefault));
                break;
        }
    }

    public static void getIneentoryStatus(BaseViewHolder helper, int status, Context mContext){
        switch (status){
            case 0://草稿
                helper.setText(R.id.tv_status_icon,R.string.fa_pencil);
                helper.setTypeface(R.id.tv_status_icon, App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"草稿");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 10://生成差异
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent4));
                helper.setText(R.id.tv_mover_status,"已生成差异");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent4));
                break;
            case 15://开始捡货
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.green_pressed));
                helper.setText(R.id.tv_mover_status,"开始捡货");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.green_pressed));
                break;
            case 20://确认差异
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.green_pressed));
                helper.setText(R.id.tv_mover_status,"已确认差异");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.green_pressed));
                break;
            case 30://已提交
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"已提交");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 40://已确认
                helper.setText(R.id.tv_status_icon,R.string.fa_check);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.darkslategray));
                helper.setText(R.id.tv_mover_status,"已确认");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.darkslategray));
                break;
            case 41://通过申请
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"通过申请");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 42://拒绝申请
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"拒绝申请");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 45://正在发货
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"正在发货");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 46://已部分发货
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"已部分发货");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 47://完成通知发货
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colorAccent2));
                helper.setText(R.id.tv_mover_status,"完成通知发货");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colorAccent2));
                break;
            case 50://已驳回
                helper.setText(R.id.tv_status_icon,R.string._cancel);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.red));
                helper.setText(R.id.tv_mover_status,"已驳回");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.red));
                break;
            case 60://已停用
                helper.setText(R.id.tv_status_icon,R.string.fa_circle);
                helper.setTypeface(R.id.tv_status_icon,App.font);
                helper.setTextColor(R.id.tv_status_icon,mContext.getResources().getColor(R.color.colordefault));
                helper.setText(R.id.tv_mover_status,"已停用");
                helper.setTextColor(R.id.tv_mover_status,mContext.getResources().getColor(R.color.colordefault));
                break;
        }
    }

    //1：入库单2：次品退厂3：需求单4：配货单5：发货单6:收货单7:申请单8:审批单9:通知单10:调拨11:盘点单
    public static void getType(BaseViewHolder helper,int type,Context mContext){
        switch (type){
            case 1://
                helper.setVisible(R.id.tv_type,true);
                helper.setText(R.id.tv_type,"入库单");
                break;
            case 2://
                helper.setVisible(R.id.tv_type,true);
                helper.setText(R.id.tv_type,"次品退厂单");
                break;
            case 3://
                helper.setVisible(R.id.tv_type,true);
                helper.setText(R.id.tv_type,"需求单");
                break;
            case 4://
                helper.setVisible(R.id.tv_type,true);
                helper.setText(R.id.tv_type,"配货单");
                break;
            case 5://
                helper.setVisible(R.id.tv_type,true);
                helper.setText(R.id.tv_type,"发货单");
                break;
            case 6://
                helper.setVisible(R.id.tv_type,true);
                helper.setText(R.id.tv_type,"收货单");
                break;
            case 7://
                helper.setVisible(R.id.tv_type,true);
                helper.setText(R.id.tv_type,"申请单");
                break;
            case 8://
                helper.setVisible(R.id.tv_type,true);
                helper.setText(R.id.tv_type,"审批单");
                break;
            case 9://
                helper.setVisible(R.id.tv_type,true);
                helper.setText(R.id.tv_type,"通知单");
                break;
            case 10://
                helper.setVisible(R.id.tv_type,true);
                helper.setText(R.id.tv_type,"调拨单");
                break;
            case 11://
                helper.setVisible(R.id.tv_type,true);
                helper.setText(R.id.tv_type,"盘点单");
                break;
            default:
                helper.setVisible(R.id.tv_type,false);
                break;
        }
    }

    ////0:无1：发货2：调拨3：退货4：跨区调拨
    public static void getCategory(BaseViewHolder helper,int category,Context mContext){
        switch (category){
            case 0://
                helper.setVisible(R.id.tv_category,false);
                break;
            case 1://
                helper.setVisible(R.id.tv_category,true);
                helper.setText(R.id.tv_category,"发货");
                break;
            case 2://
                helper.setVisible(R.id.tv_category,true);
                helper.setText(R.id.tv_category,"调拨");
                break;
            case 3://
                helper.setVisible(R.id.tv_category,true);
                helper.setText(R.id.tv_category,"退货");
                break;
            case 4://
                helper.setVisible(R.id.tv_category,true);
                helper.setText(R.id.tv_category,"跨区");
                break;
            default:
                helper.setVisible(R.id.tv_category,false);
                break;
        }
    }
}
