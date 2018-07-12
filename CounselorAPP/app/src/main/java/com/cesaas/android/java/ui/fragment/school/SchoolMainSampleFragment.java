package com.cesaas.android.java.ui.fragment.school;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesaas.android.counselor.order.R;
import com.flybiao.basetabview.TabView;
import com.flybiao.basetabview.TabViewChild;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/6/29 14:13
 * Version 1.0
 */

public class SchoolMainSampleFragment extends Fragment {

    private List<TabViewChild> tabViewChildList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.school_fragment_sample, container, false);
        TabView tabView = (TabView) view.findViewById(R.id.tab_view);

        tabViewChildList= new ArrayList<>();
        TabViewChild tabViewChild1 = new TabViewChild(R.mipmap.tab_new_home_page_h, R.mipmap.tab_new_home_page, "首页", SchoolHomeFragment.newInstance());
        TabViewChild tabViewChild2 = new TabViewChild(R.mipmap.tab_new_workbench_wh, R.mipmap.tab_new_workbench, "商学院", SchoolFragment.newInstance());
        TabViewChild tabViewChild3 = new TabViewChild(R.mipmap.tab_new_workbench_wh, R.mipmap.tab_new_workbench, "课程表", SchoolCourseFragment.newInstance());
        TabViewChild tabViewChild4 = new TabViewChild(R.mipmap.tab_new_shop_s, R.mipmap.tab_new_shop, "最近预览", SchoolPreViewFragment.newInstance());

        tabViewChildList.add(tabViewChild1);
        tabViewChildList.add(tabViewChild2);
        tabViewChildList.add(tabViewChild3);
        tabViewChildList.add(tabViewChild4);

        tabView.setTabViewChild(tabViewChildList, getChildFragmentManager());

        return view;
    }
}
