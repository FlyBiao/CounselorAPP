package com.cesaas.android.counselor.order.boss.bean.tag;

import com.cesaas.android.counselor.order.label.bean.GetTagListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/1/15 14:02
 * Version 1.0
 */

public class TagDataUtils {

    public static List<GetTagListBean> yearTagList=new ArrayList<>();
    public static List<GetTagListBean> seasonTagList=new ArrayList<>();

    public static List<GetTagListBean>  test(){
        seasonTagList=new ArrayList<>();
        GetTagListBean bean1=new GetTagListBean();
        bean1.setTagId(1);
        bean1.setTagName("波段1");
        seasonTagList.add(bean1);
        GetTagListBean bean2=new GetTagListBean();
        bean2.setTagId(1);
        bean2.setTagName("波段2");
        seasonTagList.add(bean2);
        GetTagListBean bean3=new GetTagListBean();
        bean3.setTagId(1);
        bean3.setTagName("波段3");
        seasonTagList.add(bean1);
        return seasonTagList;
    }

    public static List<GetTagListBean>  getSeasonTagList(){
        seasonTagList=new ArrayList<>();
        GetTagListBean bean1=new GetTagListBean();
        bean1.setTagId(1);
        bean1.setTagName("春");
        seasonTagList.add(bean1);
        GetTagListBean bean2=new GetTagListBean();
        bean2.setTagId(2);
        bean2.setTagName("夏");
        seasonTagList.add(bean2);
        GetTagListBean bean3=new GetTagListBean();
        bean3.setTagId(3);
        bean3.setTagName("秋");
        seasonTagList.add(bean3);
        GetTagListBean bean4=new GetTagListBean();
        bean4.setTagId(4);
        bean4.setTagName("冬");
        seasonTagList.add(bean4);
        GetTagListBean bean5=new GetTagListBean();
        bean5.setTagId(5);
        bean5.setTagName("春夏");
        seasonTagList.add(bean5);
        GetTagListBean bean6=new GetTagListBean();
        bean6.setTagId(6);
        bean6.setTagName("秋冬");
        seasonTagList.add(bean6);

        return seasonTagList;
    }

    public static List<GetTagListBean> getYearTagList(){
        yearTagList=new ArrayList<>();

        GetTagListBean bean1=new GetTagListBean();
        bean1.setTagId(1);
        bean1.setTagName("2011");
        yearTagList.add(bean1);
        GetTagListBean bean2=new GetTagListBean();
        bean2.setTagId(2);
        bean2.setTagName("2012");
        yearTagList.add(bean2);
        GetTagListBean bean3=new GetTagListBean();
        bean3.setTagId(3);
        bean3.setTagName("2013");
        yearTagList.add(bean3);
        GetTagListBean bean4=new GetTagListBean();
        bean4.setTagId(4);
        bean4.setTagName("2014");
        yearTagList.add(bean4);
        GetTagListBean bean5=new GetTagListBean();
        bean5.setTagId(5);
        bean5.setTagName("2015");
        yearTagList.add(bean5);
        GetTagListBean bean6=new GetTagListBean();
        bean6.setTagId(6);
        bean6.setTagName("2016");
        yearTagList.add(bean6);
        GetTagListBean bean7=new GetTagListBean();
        bean7.setTagId(7);
        bean7.setTagName("2017");
        yearTagList.add(bean7);
        GetTagListBean bean8=new GetTagListBean();
        bean8.setTagId(8);
        bean8.setTagName("2018");
        yearTagList.add(bean8);
        GetTagListBean bean9=new GetTagListBean();
        bean9.setTagId(9);
        bean9.setTagName("2019");
        yearTagList.add(bean9);
        GetTagListBean bean20=new GetTagListBean();
        bean20.setTagId(10);
        bean20.setTagName("2020");
        yearTagList.add(bean20);
        GetTagListBean bean21=new GetTagListBean();
        bean21.setTagId(11);
        bean21.setTagName("2021");
        yearTagList.add(bean21);
        GetTagListBean bean22=new GetTagListBean();
        bean22.setTagId(12);
        bean22.setTagName("2022");
        yearTagList.add(bean22);

        return yearTagList;
    }

    public static List<GetTagListBean> getMoodList(){
        yearTagList=new ArrayList<>();

        GetTagListBean bean1=new GetTagListBean();
        bean1.setTagId(1);
        bean1.setTagName("愉快");
        yearTagList.add(bean1);
        GetTagListBean bean2=new GetTagListBean();
        bean2.setTagId(2);
        bean2.setTagName("不耐烦");
        yearTagList.add(bean2);
        GetTagListBean bean3=new GetTagListBean();
        bean3.setTagId(3);
        bean3.setTagName("耐心");
        yearTagList.add(bean3);
        return yearTagList;
    }
}
