package com.cesaas.android.counselor.order.cache;

/**
 * Author FGB
 * Description 缓存接口类
 * Created 2017/3/16 17:46
 * Version 1.zero
 */
public interface  Cache {
    //获取缓存
    String get(final String key);
    //设置缓存
    void put(final String key, final String value);
    //删除缓存
    boolean remove(final String key);
}
