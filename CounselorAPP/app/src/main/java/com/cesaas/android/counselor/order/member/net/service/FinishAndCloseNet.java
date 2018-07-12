package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.ResultServiceResultBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 完成并关闭服务
 * Created at 2018/3/7 17:45
 * Version 1.0
 */

public class FinishAndCloseNet extends BaseNet {
    public FinishAndCloseNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/Task/FinishAndClose";
    }

    public void setData(int Id,int ServiceType,int ServiceResult,int GoShop,String GoShopDate,String NextServerDate,JSONArray Mood,String Remark,String Desc,int CreateShopTask,int CreateServerTask,JSONArray CloseTaskId){
        try{
            data.put("Id",Id);
            data.put("ServiceType",ServiceType);//1.电话2.其它
            data.put("ServiceResult",ServiceResult);//1.接听或有回复2.没反馈3.拒绝沟通
            data.put("GoShop",GoShop);//1.愿意来店2.不愿意来店3.不确定
            data.put("GoShopDate",GoShopDate);//到店时间
            data.put("NextServerDate",NextServerDate);//下次回访时间
            data.put("Mood",Mood);//客人心情
            data.put("Remark",Remark);//备注
            data.put("Desc",Desc);//描述
            data.put("CreateShopTask",CreateShopTask);//是否创建到店二次服务
            data.put("CreateServerTask",CreateServerTask);//是否创建回访二次服务
            data.put("CloseTaskId",CloseTaskId);//
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int Id,int VipId,int ServiceType,int ServiceResult,int GoShop,String GoShopDate,String NextServerDate,JSONArray Mood,String Remark,String Desc,int i,int CreateShopTask,int CreateServerTask,JSONArray CloseTaskId){
        try{
            data.put("Id",Id);
            data.put("VipId",VipId);
            data.put("ServiceType",ServiceType);//1.电话2.其它
            data.put("ServiceResult",ServiceResult);//1.接听或有回复2.没反馈3.拒绝沟通
            data.put("GoShop",GoShop);//1.愿意来店2.不愿意来店3.不确定
            data.put("GoShopDate",GoShopDate);//到店时间
            data.put("NextServerDate",NextServerDate);//下次回访时间
            data.put("Mood",Mood);//客人心情
            data.put("Remark",Remark);//备注
            data.put("Desc",Desc);//描述
            data.put("CreateShopTask",CreateShopTask);//是否创建到店二次服务
            data.put("CreateServerTask",CreateServerTask);//是否创建回访二次服务
            data.put("CloseTaskId",CloseTaskId);//
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int VipId,int ServiceType,int ServiceResult,int GoShop,String GoShopDate,String NextServerDate,JSONArray Mood,String Remark,String Desc,int i,int CreateShopTask,int CreateServerTask,JSONArray CloseTaskId){
        try{
            data.put("VipId",VipId);
            data.put("ServiceType",ServiceType);//1.电话2.其它
            data.put("ServiceResult",ServiceResult);//1.接听或有回复2.没反馈3.拒绝沟通
            data.put("GoShop",GoShop);//1.愿意来店2.不愿意来店3.不确定
            data.put("GoShopDate",GoShopDate);//到店时间
            data.put("NextServerDate",NextServerDate);//下次回访时间
            data.put("Mood",Mood);//客人心情
            data.put("Remark",Remark);//备注
            data.put("Desc",Desc);//描述
            data.put("CreateShopTask",CreateShopTask);//是否创建到店二次服务
            data.put("CreateServerTask",CreateServerTask);//是否创建回访二次服务
            data.put("CloseTaskId",CloseTaskId);//
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int Id,int ServiceType,int ServiceResult,int GoShop,String GoShopDate,JSONArray Mood,String Remark,String Desc,int CreateShopTask,int CreateServerTask,JSONArray CloseTaskId){
        try{
            data.put("Id",Id);
            data.put("ServiceType",ServiceType);//1.电话2.其它
            data.put("ServiceResult",ServiceResult);//1.接听或有回复2.没反馈3.拒绝沟通
            data.put("GoShop",GoShop);//1.愿意来店2.不愿意来店3.不确定
            data.put("GoShopDate",GoShopDate);//到店时间
            data.put("Mood",Mood);//客人心情
            data.put("Remark",Remark);//备注
            data.put("Desc",Desc);//描述
            data.put("CreateShopTask",CreateShopTask);//是否创建到店二次服务
            data.put("CreateServerTask",CreateServerTask);//是否创建回访二次服务
            data.put("CloseTaskId",CloseTaskId);//
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int Id,int ServiceType,int ServiceResult,int GoShop,String NextServerDate,JSONArray Mood,String Remark,String Desc,int type,int CreateShopTask,int CreateServerTask,JSONArray CloseTaskId){
        try{
            data.put("Id",Id);
            data.put("ServiceType",ServiceType);//1.电话2.其它
            data.put("ServiceResult",ServiceResult);//1.接听或有回复2.没反馈3.拒绝沟通
            data.put("GoShop",GoShop);//1.愿意来店2.不愿意来店3.不确定
            data.put("NextServerDate",NextServerDate);//下次回访时间
            data.put("Mood",Mood);//客人心情
            data.put("Remark",Remark);//备注
            data.put("Desc",Desc);//描述
            data.put("CreateShopTask",CreateShopTask);//是否创建到店二次服务
            data.put("CreateServerTask",CreateServerTask);//是否创建回访二次服务
            data.put("CloseTaskId",CloseTaskId);//
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int Id,int VipId,int ServiceType,int ServiceResult,int GoShop,String NextServerDate,JSONArray Mood,String Remark,String Desc,int type,int i,int CreateShopTask,int CreateServerTask,JSONArray CloseTaskId){
        try{
            data.put("Id",Id);
            data.put("VipId",VipId);
            data.put("ServiceType",ServiceType);//1.电话2.其它
            data.put("ServiceResult",ServiceResult);//1.接听或有回复2.没反馈3.拒绝沟通
            data.put("GoShop",GoShop);//1.愿意来店2.不愿意来店3.不确定
            data.put("NextServerDate",NextServerDate);//下次回访时间
            data.put("Mood",Mood);//客人心情
            data.put("Remark",Remark);//备注
            data.put("Desc",Desc);//描述
            data.put("CreateShopTask",CreateShopTask);//是否创建到店二次服务
            data.put("CreateServerTask",CreateServerTask);//是否创建回访二次服务
            data.put("CloseTaskId",CloseTaskId);//
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultServiceResultBean bean= JsonUtils.fromJson(rJson,ResultServiceResultBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
