package com.cesaas.android.counselor.order.net.okhttp.util;

import android.os.Handler;
import android.util.Log;

import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.okhttp.SimpleHttpClient;
import com.cesaas.android.counselor.order.net.okhttp.callback.BaseCallBack;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Author FGB
 * Description okhttp管理工具类
 * Created 2017/3/19 12:02
 * Version 1.zero
 */
public class OkHttpManger {

    private static OkHttpManger mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private Gson mGson;

    private OkHttpManger(){
        initOkHttp();
        mHandler=new Handler();
        mGson=new Gson();
    }

    public static synchronized  OkHttpManger getmInstance(){
        if(mInstance==null){
            mInstance=new OkHttpManger();
        }

        return mInstance;
    }

    /**
     * 初始化okhttp
     */
    public void initOkHttp(){

       mOkHttpClient= new OkHttpClient().newBuilder()
                .readTimeout(30000, TimeUnit.SECONDS)
                .connectTimeout(30000,TimeUnit.SECONDS).build();
    }

    /**
     * 网络请求
     */
    public void request(SimpleHttpClient client, final BaseCallBack callBack){

        if(callBack==null){
            throw  new NullPointerException("call back is null");
        }

        mOkHttpClient.newCall(client.buildRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("okhttp","okhttp:"+e);
                sendonFailureMessage(callBack,call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.isSuccessful()){

                    String result=response.body().string();
                    Log.i("okhttp","okhttp:"+result);
                    if(callBack.mType==null || callBack.mType==String.class){
                        sendonSuccessMessage(callBack,result);

                    }else{
                        sendonSuccessMessage(callBack,mGson.fromJson(result,callBack.mType));
                    }

                    if(response.body()!=null){
                        response.close();
                    }

                }else{
                    sendonErrorMessage(callBack,response.code());
                }
            }
        });
    }

    private void sendonFailureMessage(final BaseCallBack callBack,final Call call,final IOException e){
        Log.i("okhttp","okhttp:"+e);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(call,e);
            }
        });
    }

    private void sendonErrorMessage(final BaseCallBack callBack,final int code){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onError(code);
            }
        });
    }

    private void sendonSuccessMessage(final BaseCallBack callBack,final Object obj){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(obj);
            }
        });
    }
}
