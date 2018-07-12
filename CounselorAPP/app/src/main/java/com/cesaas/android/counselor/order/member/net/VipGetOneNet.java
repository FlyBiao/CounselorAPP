package com.cesaas.android.counselor.order.member.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.ResultBinVipBean;
import com.cesaas.android.counselor.order.member.bean.ResultVipGetOneBean;
import com.cesaas.android.counselor.order.member.bean.ResultVipGetOneLabelBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取单个会员信息
 * Created 2017/4/21 17:46
 * Version 1.zero
 */
public class VipGetOneNet extends BaseNet {
    private int type;
    public VipGetOneNet(Context context,int type) {
        super(context, true);
        this.uri="User/Sw/Vip/GetOne";
        this.type=type;
    }

    public void setData(String Id){
        try{
            data.put("Id",Id);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        if(type==1){
            ResultVipGetOneBean bean= JsonUtils.fromJson(rJson,ResultVipGetOneBean.class);
            EventBus.getDefault().post(bean);
        }else{
            ResultVipGetOneLabelBean bean= JsonUtils.fromJson(rJson,ResultVipGetOneLabelBean.class);
            EventBus.getDefault().post(bean);
        }
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
