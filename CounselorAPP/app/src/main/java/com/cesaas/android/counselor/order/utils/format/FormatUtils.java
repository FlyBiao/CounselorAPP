package com.cesaas.android.counselor.order.utils.format;

import android.util.Log;

import com.cesaas.android.counselor.order.bean.GroupBean;
import com.cesaas.android.counselor.order.bean.GroupTagBean;
import com.cesaas.android.counselor.order.boss.bean.ShopListBean;
import com.cesaas.android.counselor.order.boss.bean.ShopTagListBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/1/10 17:45
 * Version 1.0
 */

public class FormatUtils {

    //对format方法的二次封装，输出一个分组后的二维数组【机构】
    public static HashMap<String,GroupBean> organizationGroup(List<ShopListBean> list){
        HashMap<String,GroupBean> hashMap = new HashMap<>();
        for(ShopListBean bean:list){
            if(hashMap.get(bean.getOrganizationName()) != null){
                GroupBean beans = hashMap.get(bean.getOrganizationName());
                beans.add(bean);
            }else{
                GroupBean beans = new GroupBean();
                beans.groupInfo = bean.getOrganizationName();
                beans.add(bean);
                hashMap.put(bean.getOrganizationName(),beans);
            }
        }
        return hashMap;
    }

    //对format方法的二次封装，输出一个分组后的二维数组【区域】
    public static HashMap<String,GroupBean> areaGroup(List<ShopListBean> list){
        HashMap<String,GroupBean> hashMap = new HashMap<>();
        for(ShopListBean bean:list){
            if(hashMap.get(bean.getAreaName()) != null){
                GroupBean beans = hashMap.get(bean.getAreaName());
                beans.add(bean);
            }else{
                GroupBean beans = new GroupBean();
                beans.groupInfo = bean.getAreaName();
                beans.add(bean);
                hashMap.put(bean.getAreaName(),beans);
            }
        }
        return hashMap;
    }


    //对format方法的二次封装，输出一个分组后的二维数组【标签】
    public static HashMap<String,GroupTagBean> shopTagGroup(List<ShopTagListBean> list){
        HashMap<String,GroupTagBean> hashMap = new HashMap<>();
        for(ShopTagListBean bean:list){
            if(hashMap.get(bean.getTagName())!=null){
                GroupTagBean beans = hashMap.get(bean.getTagName());
                beans.add(bean);
            }else{
                GroupTagBean beans = new GroupTagBean();
                beans.groupInfo = bean.getTagName();
                beans.add(bean);
                hashMap.put(bean.getTagName(),beans);
            }
        }
        return hashMap;
    }







    //对format方法的二次封装，输出一个分组后的二维数组【暂时用不到】
    public HashMap<String,ArrayList<ShopListBean>> format(List<ShopListBean> list){
        HashMap<String,ArrayList<ShopListBean>> hashMap = new HashMap<>();
        for(ShopListBean bean:list){
            if(hashMap.get(bean.getOrganizationName()) != null){
                ArrayList<ShopListBean> beans = hashMap.get(bean.getOrganizationName());
                beans.add(bean);
            }else{
                ArrayList<ShopListBean> beans = new ArrayList<>();
                beans.add(bean);
                hashMap.put(bean.getOrganizationName(),beans);
            }
        }
        return hashMap;
    }
}
