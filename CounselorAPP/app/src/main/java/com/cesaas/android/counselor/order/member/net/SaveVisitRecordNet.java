package com.cesaas.android.counselor.order.member.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.ResultSaveVisitRecordBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 保存回访记录Net
 * Created 2017/4/24 11:09
 * Version 1.zero
 */
public class SaveVisitRecordNet extends BaseNet {
    public SaveVisitRecordNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/SW/TaskVisit/SaveVisitRecord";
    }

    public void setData(int VipId,String VipName,int VisitType,String Time){
        try{
            data.put("VipId",VipId);
            data.put("VipName",VipName);
            data.put("VisitType",VisitType);//回访类型：1电话，2短信
            data.put("Time",Time);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultSaveVisitRecordBean bean= JsonUtils.fromJson(rJson,ResultSaveVisitRecordBean.class);

        if(bean.IsSuccess==true){
            ToastFactory.getLongToast(mContext, "保存回访成功！" );
            EventBus.getDefault().post(bean);
        }else{
            ToastFactory.getLongToast(mContext, "保存回访记录失败！" );
        }
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
