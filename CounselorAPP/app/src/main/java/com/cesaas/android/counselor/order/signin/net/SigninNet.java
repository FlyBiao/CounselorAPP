package com.cesaas.android.counselor.order.signin.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.signin.bean.ResultSigninBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 签到Net
 * Created 2017/3/20 9:42
 * Version 1.zero
 */
public class SigninNet extends BaseNet{
    public SigninNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/SignIn/SaveSignIn";
    }

    public void setData(int stype, int longtitude, int latitude, JSONArray Img, String remark, String address ){
        try{
            data.put("stype",stype);
            data.put("longtitude",longtitude);
            data.put("latitude",latitude);
            data.put("Img",Img);
            data.put("remark",remark);
            data.put("address",address);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Gson gson=new Gson();
        ResultSigninBean bean=gson.fromJson(rJson,ResultSigninBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
