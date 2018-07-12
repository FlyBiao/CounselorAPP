package com.cesaas.android.counselor.order.manager.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.manager.bean.ResultCreateTaskBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 新建任务草稿
 * Created at 2017/9/14 11:22
 * Version 1.0
 */

public class CreateTaskNet extends BaseNet{
    public CreateTaskNet(Context context) {
        super(context, true);
        this.uri="ShopTask/Sw/ShopTask/Create";
    }

    public void setData(String TaskTitle, String StartDate, String EndDate,JSONArray ImageUrl, int CategoryId,int TemplateId, int TaskLeve, String CreateName, JSONArray Contracts,JSONArray ContractBak) {
        try {
            data.put("TaskTitle",TaskTitle);
            data.put("StartDate",StartDate);
            data.put("EndDate",EndDate);
            data.put("Images",ImageUrl);
//            data.put("Status",Status);
            data.put("CategoryId",CategoryId);
            data.put("TemplateId",TemplateId);
            data.put("TaskLeve",TaskLeve);
            data.put("CreateName",CreateName);
            data.put("Contracts",Contracts);
            data.put("ContractBak",ContractBak);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i(Constant.TAG, "新建任务草稿"+rJson);
        ResultCreateTaskBean bean=new ResultCreateTaskBean();
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG, "新建任务草稿"+err);
    }
}
