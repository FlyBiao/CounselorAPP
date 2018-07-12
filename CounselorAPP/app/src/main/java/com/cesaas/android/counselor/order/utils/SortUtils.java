package com.cesaas.android.counselor.order.utils;


import com.cesaas.android.counselor.order.bean.SortBean;

import org.json.JSONArray;

/**
 * 时间排序工具类
 * @author fgb
 *
 */
public class SortUtils {

    public static JSONArray setSort(){
        JSONArray arraySort=new JSONArray();
        SortBean sortBean=new SortBean();
        sortBean.setField("CreateTime");
        sortBean.setValue("desc");
        arraySort.put(sortBean.getSortIdInfo());

        return arraySort;
    }

}
