package com.cesaas.android.counselor.order.cache;

/**
 * Author FGB
 * Description
 * Created 2017/3/16 17:52
 * Version 1.zero
 */
public interface EvictedListener {
    void handleEvictEntry(String evictKey, String evictValue);
}
