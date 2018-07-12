package com.cesaas.android.java.net.inventory;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryEditSaveBean;
import com.cesaas.android.java.bean.inventory.ResultInventorySaveBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 盘点单备注
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class InventorySaveNet extends TestBaseNet {

    private int type;

    public InventorySaveNet(Context context,int type) {
        super(context, true);
        this.uri="drp/inventorySave";
        this.type=type;
    }

    public void setData(long id,String remark){
        try {
            data.put("id",id);
            data.put("remark",remark);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    public void setData(long id,long shopId,String pDate,int type){
        try {
            data.put("id",id);
            data.put("shopId",shopId);
            data.put("pDate",pDate);
            data.put("type",type);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        if(type==1){
            ResultInventorySaveBean bean= JsonUtils.fromJson(rJson,ResultInventorySaveBean.class);
            EventBus.getDefault().post(bean);
        }else{
            ResultInventoryEditSaveBean bean= JsonUtils.fromJson(rJson,ResultInventoryEditSaveBean.class);
            EventBus.getDefault().post(bean);
        }

    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
