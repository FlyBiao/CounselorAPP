package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.ResultCreateMemberService;
import com.cesaas.android.counselor.order.member.bean.service.query.ResultQueryMemberBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceCompleteListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceTimeOutListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员服务发布
 * Created at 2018/3/7 17:09
 * Version 1.0
 */

public class CreateMemberServiceNet extends BaseNet {
    public CreateMemberServiceNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/Project/Release";
    }

    public void setData(String Title, String BeginDate, String EndDate, double MoneyAreaMin, double MoneyAreaMax,
                        String BuyDateAreaMin, String BuyDateAreaMax, String BirthdayAreaMin, String BirthdayAreaMax,
                        String Remark, int NoBuyCount, JSONArray Grades,int PointsAreaMin,int PointsAreaMax,String CreateAreaMin,
                        String CreateAreaMax, int SendQuestion,JSONArray Tags,JSONArray VipIds){
        try{
            data.put("Title",Title);//服务名称
            data.put("BeginDate",BeginDate);//开始时间
            data.put("EndDate",EndDate);//结束时间
            data.put("MoneyAreaMin",MoneyAreaMin);//最低储值余额
            data.put("MoneyAreaMax",MoneyAreaMax);//最高储值余额
            data.put("BuyDateAreaMin",BuyDateAreaMin);//购买日期范围值
            data.put("BuyDateAreaMax",BuyDateAreaMax);//购买日期范围值
            data.put("BirthdayAreaMin",BirthdayAreaMin);//生日范围值
            data.put("BirthdayAreaMax",BirthdayAreaMax);//	生日范围值
            data.put("Remark",Remark);//服务说明
            data.put("NoBuyCount",NoBuyCount);//多日未购买
            data.put("Grades",Grades);//会员等级筛选条件
            data.put("PointsAreaMin",PointsAreaMin);//积分范围值
            data.put("PointsAreaMax",PointsAreaMax);//积分范围值
            data.put("CreateAreaMin",CreateAreaMin);//会员注册范围值
            data.put("CreateAreaMax",CreateAreaMax);//会员注册范围值
            data.put("SendQuestion",SendQuestion);//是否自动发送调查问卷
            data.put("Tags",Tags);//会员标签筛选条件
            data.put("VipIds",VipIds);//会员列表
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(ResultQueryMemberBean info,JSONArray Grades, JSONArray Tags, JSONArray VipIds){
        try{
            data.put("Title",info.getTitle());//服务名称
            data.put("BeginDate",info.getBeginDate());//开始时间
            data.put("EndDate",info.getEndDate());//结束时间
            data.put("MoneyAreaMin",info.getMoneyAreaMin());//最低储值余额
            data.put("MoneyAreaMax",info.getMoneyAreaMax());//最高储值余额
            data.put("BuyDateAreaMin",info.getBirthdayAreaMin());//购买日期范围值
            data.put("BuyDateAreaMax",info.getBuyDateAreaMax());//购买日期范围值
            data.put("BirthdayAreaMin",info.getBirthdayAreaMin());//生日范围值
            data.put("BirthdayAreaMax",info.getBirthdayAreaMax());//	生日范围值
            data.put("Remark",info.getRemark());//服务说明
            data.put("NoBuyCount",info.getNoBuyCount());//多日未购买
            data.put("Grades",Grades);//会员等级筛选条件
            data.put("PointsAreaMin",info.getPointsAreaMin());//积分范围值
            data.put("PointsAreaMax",info.getPointsAreaMax());//积分范围值
            data.put("CreateAreaMin",info.getCreateAreaMin());//会员注册范围值
            data.put("CreateAreaMax",info.getCreateAreaMax());//会员注册范围值
            data.put("SendQuestion",info.getSendQuestion());//是否自动发送调查问卷
            data.put("Tags",Tags);//会员标签筛选条件
            data.put("VipIds",VipIds);//会员列表
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultCreateMemberService bean= JsonUtils.fromJson(rJson,ResultCreateMemberService.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
