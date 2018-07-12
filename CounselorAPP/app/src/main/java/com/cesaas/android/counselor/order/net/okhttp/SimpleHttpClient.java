package com.cesaas.android.counselor.order.net.okhttp;

import android.net.Uri;
import android.util.Log;

import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.okhttp.callback.BaseCallBack;
import com.cesaas.android.counselor.order.net.okhttp.util.OkHttpManger;
import com.google.gson.JsonIOException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Author FGB
 * Description SimpleHttpClient
 * Created 2017/3/19 11:05
 * Version 1.zero
 */
public class SimpleHttpClient {

    private Builder mBuilder;

    private SimpleHttpClient(Builder mBuilder){
        this.mBuilder=mBuilder;
    }

    /**
     * 构建请求方法
     * @return
     */
    public Request buildRequest(){

        Request.Builder builder=new Request.Builder();
        if(mBuilder.method=="GET"){
            builder.url(buildGetRequstParam());
            builder.get();

        }else if(mBuilder.method=="POST"){
            try {
                builder.post(buildRequestBody());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            builder.url(mBuilder.url);

        }
        return builder.build();
    }

    /**
     * g构建get请求参数
     * @return
     */
    private String buildGetRequstParam(){
        if(mBuilder.mParams.size()==0)
            return this.mBuilder.url;
            Uri.Builder builder=Uri.parse(mBuilder.url).buildUpon();
            for(RequestParam p: mBuilder.mParams){
                builder.appendQueryParameter(p.getKey(),p.getObj()==null?"":p.getObj().toString());
        }
        String url=builder.build().toString();
        return url;
    }

    private RequestBody buildRequestBody() throws JSONException{

        if(mBuilder.isJsonParam){
            JSONObject jsonObject=new JSONObject();
            for(RequestParam p:mBuilder.mParams){
                jsonObject.put(p.getKey(),p.getObj());
            }
            String json=jsonObject.toString();
            Log.i(Constant.TAG,"okhttpJson:"+json);
            return RequestBody.create(MediaType.parse("application/json:charset=utf-8"),json);
        }
        FormBody.Builder builder=new FormBody.Builder();
        for(RequestParam p:mBuilder.mParams){
            builder.add(p.getKey(),p.getObj()==null?"":p.getObj().toString());
        }
        return builder.build();
    }


    /**
     * 请求网络回调
     * @param callBack
     */
    public void enqueue(BaseCallBack callBack){
        OkHttpManger.getmInstance().request(this,callBack);
    }

    /**
     * 创建Build构建函数
     * @return
     */
    public static Builder newBuilder(){
        return new Builder();
    }

    /**
     * okhttp Builder内部类
     */
    public static class Builder{

        private String url;
        private String method;
        private boolean isJsonParam;
        private List<RequestParam> mParams;

        public Builder(){
            method="GET";
        }

        public SimpleHttpClient build(){
            return new SimpleHttpClient(this);
        }

        public Builder url(String url){
            this.url=url;
            return this;
        }

        public Builder get(){
            method="GET";
            return this;
        }

        /**
         * Form 表单
         * @return
         */
        public Builder post(){
            method="POST";
            return this;
        }

        /**
         * JSON 参数
         * @return json
         */
        public Builder json(){
            isJsonParam=true;
            return post();
        }

        /**
         * 构建添加post请求参数
         * @param key
         * @param value
         * @return
         */
        public Builder addParam(String key,Object value){
            if(mParams==null){
                mParams=new ArrayList<>();
            }
           mParams.add(new RequestParam(key,value));
            return this;
        }
    }
}
