package com.cesaas.android.counselor.order.activity.main.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.fragment.HomeFragment;
import com.cesaas.android.counselor.order.member.MemberFragment;
import com.cesaas.android.counselor.order.shop.fragment.ShopFragment;
import com.cesaas.android.counselor.order.workbench.fragment.WorkbenchFragment;
import com.flybiao.basetabview.TabView;
import com.flybiao.basetabview.TabViewChild;

import java.util.ArrayList;
import java.util.List;

/**
 * HomeSampleFragment
 * Created by JieChen on 2017/4/4.
 */

public class HomeSampleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);
        Typeface font= Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");//记得加上这句
        TabView tabView = (TabView) view.findViewById(R.id.tab_view);

        List<TabViewChild> tabViewChildList = new ArrayList<>();
        TabViewChild tabViewChild1 = new TabViewChild(R.mipmap.tab_home_page_h, R.mipmap.tab_home_page, "首页", HomeFragment.newInstance());
        TabViewChild tabViewChild2 = new TabViewChild(R.mipmap.tab_workbench_wh, R.mipmap.tab_workbench, "工作台", WorkbenchFragment.newInstance());
        TabViewChild tabViewChild3 = new TabViewChild(R.mipmap.tab_shop_s, R.mipmap.tab_shop, "商品", ShopFragment.newInstance());
        TabViewChild tabViewChild4 = new TabViewChild(R.mipmap.tab_vip_v, R.mipmap.tab_vip, "会员", MemberFragment.newInstance());

        tabViewChildList.add(tabViewChild1);
        tabViewChildList.add(tabViewChild2);
        tabViewChildList.add(tabViewChild3);
        tabViewChildList.add(tabViewChild4);

        tabView.setTabViewChild(tabViewChildList, getChildFragmentManager());

        return view;
    }
}
