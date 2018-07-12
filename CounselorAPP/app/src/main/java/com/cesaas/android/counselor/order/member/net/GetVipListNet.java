package com.cesaas.android.counselor.order.member.net;

import android.content.Context;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.ResultVipListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;

import io.rong.eventbus.EventBus;


/**
 * Author FGB
 * Description 获取会员列表-发布会员服务时，根据条件查询相关会员
 * Created at 2017/5/16 14:18
 * Version 1.0
 */

public class GetVipListNet extends BaseNet{
    public GetVipListNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/Project/GetVipList";
    }

    public void setData(){
        try{
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(double MoneyAreaMin, double MoneyAreaMax){
        try{
            data.put("MoneyAreaMin",MoneyAreaMin);//最低储值余额
            data.put("MoneyAreaMax",MoneyAreaMax);//最高储值余额
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(double MoneyAreaMin, double MoneyAreaMax,
                        String BuyDateAreaMin, String BuyDateAreaMax, String BirthdayAreaMin, String BirthdayAreaMax,
                        int NoBuyCount, JSONArray Grades, int PointsAreaMin, int PointsAreaMax, String CreateAreaMin,
                        String CreateAreaMax,JSONArray Tags){
        try{
            data.put("MoneyAreaMin",MoneyAreaMin);//最低储值余额
            data.put("MoneyAreaMax",MoneyAreaMax);//最高储值余额
            data.put("BuyDateAreaMin",BuyDateAreaMin);//购买日期范围值
            data.put("BuyDateAreaMax",BuyDateAreaMax);//购买日期范围值
            data.put("BirthdayAreaMin",BirthdayAreaMin);//生日范围值
            data.put("BirthdayAreaMax",BirthdayAreaMax);//	生日范围值
            data.put("NoBuyCount",NoBuyCount);//多日未购买
            data.put("Grades",Grades);//会员等级筛选条件
            data.put("PointsAreaMin",PointsAreaMin);//积分范围值
            data.put("PointsAreaMax",PointsAreaMax);//积分范围值
            data.put("CreateAreaMin",CreateAreaMin);//会员注册范围值
            data.put("CreateAreaMax",CreateAreaMax);//会员注册范围值
            data.put("Tags",Tags);//会员标签筛选条件
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultVipListBean bean = JsonUtils.fromJson(rJson,ResultVipListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
