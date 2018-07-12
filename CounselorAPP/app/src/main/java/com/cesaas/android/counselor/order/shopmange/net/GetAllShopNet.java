package com.cesaas.android.counselor.order.shopmange.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shopmange.bean.ResultGetAllShopBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取所有商品Net
 * Created 2017/4/27 20:28
 * Version 1.0
 */
public class GetAllShopNet extends BaseNet {
    public GetAllShopNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/Style/GetAppList";
    }

    public void setData(int PageIndex,int Type){
        try{

            data.put("Type",Type);//0所有，1最新，2预售，3关注
            data.put("PageIndex",PageIndex);
            data.put("PageSize",30);
            data.put("UserTicket",
                    AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int PageIndex,int Type,String Year,String Season,String SmallSortId,String BigSortId){
        try{
            data.put("Type",Type);//0所有，1最新，2预售，3关注
            data.put("Year",Year);
            data.put("Season",Season);
            data.put("SmallSortId",SmallSortId);
            data.put("BigSortId",BigSortId);
            data.put("PageIndex",PageIndex);
            data.put("PageSize",30);
            data.put("UserTicket",
                    AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int PageIndex,int Type,String SearchTxt){
        try{
            data.put("Type",Type);//0所有，1最新，2预售，3关注
            data.put("SearchTxt",SearchTxt);
            data.put("PageIndex",PageIndex);
            data.put("PageSize",30);
            data.put("UserTicket",
                    AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultGetAllShopBean bean= JsonUtils.fromJson(rJson,ResultGetAllShopBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
