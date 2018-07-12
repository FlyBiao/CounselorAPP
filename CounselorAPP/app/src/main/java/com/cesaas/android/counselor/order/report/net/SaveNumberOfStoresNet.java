package com.cesaas.android.counselor.order.report.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.report.bean.ResultSaveNumberOfStoresBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.exception.HttpException;

/**
 * Author FGB
 * Description
 * Created 2017/4/24 18:05
 * Version 1.zero
 */
public class SaveNumberOfStoresNet extends BaseNet {
    public SaveNumberOfStoresNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/Sw/NumberOfStores/SaveNumberOfStores";
    }
    public void setData(int CustomerCount){
        try{

            data.put("CustomerCount",CustomerCount);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultSaveNumberOfStoresBean bean= JsonUtils.fromJson(rJson,ResultSaveNumberOfStoresBean.class);
        if(bean.IsSuccess==true){
//            ToastFactory.getLongToast(mContext,"添加进店记录成功！");
            GetCustomersNet mGetCustomersNet=new GetCustomersNet(mContext);
            mGetCustomersNet.setData(1);

            GetCustomersCountNet  mGetCustomersCountNet=new GetCustomersCountNet(mContext);
            mGetCustomersCountNet.setData();
        }else{
            ToastFactory.getLongToast(mContext,"添加进店记录失败！"+bean.Message);
        }
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
