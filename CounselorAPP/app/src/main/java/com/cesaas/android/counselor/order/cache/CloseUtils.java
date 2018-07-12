package com.cesaas.android.counselor.order.cache;

import java.io.Closeable;
import java.io.IOException;

/**
 * Author FGB
 * Description 关闭Closeable对象工具方法
 * Created 2017/3/16 18:57
 * Version 1.zero
 */
public class CloseUtils {
    private CloseUtils(){

    }
    /**
     * 关闭Closeable对象
     */
    public static void closeCloseable(Closeable closeable){
        if(null != closeable){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
