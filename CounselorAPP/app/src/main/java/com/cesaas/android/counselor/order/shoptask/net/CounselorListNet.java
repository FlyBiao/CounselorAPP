package com.cesaas.android.counselor.order.shoptask.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shoptask.bean.ResultCounselorListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 顾问（店员列表）
 * Created at 2017/5/10 13:52
 * Version 1.0
 */

public class CounselorListNet extends BaseNet {
    public CounselorListNet(Context context) {
        super(context, true);
//        this.uri="User/SW/Counselor/List";
        this.uri="User/SW/Counselor/GetList";
    }

    public void setData(int PageIndex,int Status,String Key){
        try{
            data.put("Key",Key);
            data.put("Status",Status);//-1：申请，0 离职 1在职
            data.put("PageIndex",PageIndex);
            data.put("PageSize",30);
            data.put("UserTicket",
                    AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int PageIndex,int Type,int Status,String Key){
        try{
            data.put("Key",Key);
            data.put("Status",Status);//-1未审核，0未启用，1已启用
            data.put("Type",Type);//查询类型： 0=全部，1=最近联系人，2=组联系人，3=部门联系人
            data.put("PageIndex",PageIndex);
            data.put("PageSize",30);
            data.put("UserTicket",
                    AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int PageIndex,int Type,String Key,int RoleIds){
            try{
                data.put("Key",Key);
                data.put("Type",Type);//查询类型： 0=全部，1=最近联系人，2=组联系人，3=部门联系人
                data.put("PageIndex",PageIndex);
                data.put("PageSize",30);
                data.put("RoleIds",RoleIds);
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
        Log.i("test","店员列表"+rJson);
        ResultCounselorListBean bean= JsonUtils.fromJson(rJson,ResultCounselorListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"店员列表ERROR："+err);
    }
}
