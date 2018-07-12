package com.cesaas.android.counselor.order.boss.bean.member;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/4/26 9:03
 * Version 1.0
 */

public class CategoryDataUtils {

    private static List<MemberServiceCategory> serviceCategories=new ArrayList<>();

    public static List<MemberServiceCategory> getServiceCategory(){
        serviceCategories=new ArrayList<>();
        MemberServiceCategory category=new MemberServiceCategory();
        category.setType(0);
        category.setCategoryName("所有类别");
        serviceCategories.add(category);

        MemberServiceCategory category1=new MemberServiceCategory();
        category1.setType(1);
        category1.setCategoryName("销售回访");
        serviceCategories.add(category1);

        MemberServiceCategory category2=new MemberServiceCategory();
        category2.setType(2);
        category2.setCategoryName("生日祝福");
        serviceCategories.add(category2);

        MemberServiceCategory category3=new MemberServiceCategory();
        category3.setType(3);
        category3.setCategoryName("节日安排");
        serviceCategories.add(category3);

        MemberServiceCategory category4=new MemberServiceCategory();
        category4.setType(4);
        category4.setCategoryName("休眠激活");
        serviceCategories.add(category4);

        MemberServiceCategory category5=new MemberServiceCategory();
        category5.setType(5);
        category5.setCategoryName("退换货");
        serviceCategories.add(category5);

        MemberServiceCategory category6=new MemberServiceCategory();
        category6.setType(99);
        category6.setCategoryName("定制会员");
        serviceCategories.add(category6);

        return serviceCategories;
    }
}
