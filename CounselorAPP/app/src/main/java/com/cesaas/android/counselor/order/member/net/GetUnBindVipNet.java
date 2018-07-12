package com.cesaas.android.counselor.order.member.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.ResultPublicMemberBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取未绑定会员（公共池会员）
 * Created 2017/4/21 15:08
 * Version 1.zero
 */
public class GetUnBindVipNet extends BaseNet{
    public GetUnBindVipNet(Context context) {
        super(context, true);
        this.uri="User/Sw/VipCounselor/GetUnBindVip";
    }

    public void setData(int PageIndex){
        try{
            data.put("PageIndex",PageIndex);
            data.put("PageSize",Constant.PAGE_SIZE);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int PageIndex,String SearchKey){
        try{
            data.put("SearchKey",SearchKey);
            data.put("PageIndex",PageIndex);
            data.put("PageSize",Constant.PAGE_SIZE);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int PageIndex,JSONObject Filters){
        try{
            data.put("Filters",Filters);
            data.put("PageIndex",PageIndex);
            data.put("PageSize",Constant.PAGE_SIZE);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultPublicMemberBean bean= JsonUtils.fromJson(rJson,ResultPublicMemberBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
