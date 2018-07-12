package com.cesaas.android.counselor.order.base;

import com.cesaas.android.counselor.order.member.bean.MemberServiceBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/3/6 14:24
 * Version 1.0
 */

public class BaseMemberServiceBean {

    public static List<MemberServiceBean> getServiceList(){
        List<MemberServiceBean> mData=new ArrayList<>();
        MemberServiceBean serviceBean=new MemberServiceBean();
        serviceBean.setTitle("销售回访");
        serviceBean.setContext("感谢，洗涤保养，满意度等");
        serviceBean.setSum(2);
        mData.add(serviceBean);
        MemberServiceBean serviceBean2=new MemberServiceBean();
        serviceBean2.setTitle("生日祝福");
        serviceBean2.setContext("给会员提前生日祝福");
        serviceBean2.setSum(2);
        mData.add(serviceBean2);
        MemberServiceBean serviceBean3=new MemberServiceBean();
        serviceBean3.setTitle("节日安排");
        serviceBean3.setContext("会员节日祝福");
        serviceBean3.setSum(2);
        mData.add(serviceBean3);
        MemberServiceBean serviceBean4=new MemberServiceBean();
        serviceBean4.setTitle("休眠激活");
        serviceBean4.setContext("30天未消费");
        serviceBean4.setSum(2);
        mData.add(serviceBean4);
        MemberServiceBean serviceBean5=new MemberServiceBean();
        serviceBean5.setTitle("定制会员项目");
        serviceBean5.setContext("开业，积分活动等");
        serviceBean5.setSum(2);
        mData.add(serviceBean5);

        return mData;
    }
}
