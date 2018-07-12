package com.cesaas.android.counselor.order.net.okhttp.callback;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Author FGB
 * Description okhttp 回调基类
 * Created 2017/3/19 11:32
 * Version 1.zero
 */
public abstract class BaseCallBack<T> {

    static Type getSuperClassTypeParameter(Class<?> suClass){
        Type superClass=suClass.getGenericSuperclass();
        if(superClass instanceof Class){
            return null;
        }
        ParameterizedType parameterizedType= (ParameterizedType) superClass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    public Type mType;
    public BaseCallBack(){
        mType=getSuperClassTypeParameter(this.getClass());
    }

    public void onSuccess(T t){};
    public void onError( int code){};
    public void onFailure(Call call , IOException e){};

}
