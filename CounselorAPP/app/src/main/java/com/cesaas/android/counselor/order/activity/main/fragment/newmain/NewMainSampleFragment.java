package com.cesaas.android.counselor.order.activity.main.fragment.newmain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseTabBean;
import com.cesaas.android.counselor.order.bean.ResultShopPowerBean;
import com.cesaas.android.counselor.order.member.MemberFragment;
import com.cesaas.android.counselor.order.member.fragment.member.NewMemberHomeFragment;
import com.cesaas.android.counselor.order.pay.Base64;
import com.cesaas.android.counselor.order.shop.fragment.ShopFragment;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.basetabview.TabView;
import com.flybiao.basetabview.TabViewChild;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;


/**
 * Author FGB
 * Description
 * Created at 2018/1/22 17:48
 * Version 1.0
 */

public class NewMainSampleFragment extends Fragment {

    private List<TabViewChild> tabViewChildList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_fragment_sample, container, false);
        TabView tabView = (TabView) view.findViewById(R.id.tab_view);

        tabViewChildList= new ArrayList<>();
        TabViewChild tabViewChild1 = new TabViewChild(R.mipmap.tab_new_home_page_h, R.mipmap.tab_new_home_page, "首页", NewHomeFragment.newInstance());
        TabViewChild tabViewChild2 = new TabViewChild(R.mipmap.tab_new_workbench_wh, R.mipmap.tab_new_workbench, "工作台", NewWorkbenchFragment.newInstance());
        TabViewChild tabViewChild3 = new TabViewChild(0, 0, "菜单",NewHomeTestFragment.newInstance());
        TabViewChild tabViewChild4 = new TabViewChild(R.mipmap.tab_new_shop_s, R.mipmap.tab_new_shop, "商品", ShopFragment.newInstance());
        TabViewChild tabViewChild5 = new TabViewChild(R.mipmap.tab_new_vip_v, R.mipmap.tab_new_vip, "会员", NewMemberHomeFragment.newInstance());

        tabViewChildList.add(tabViewChild1);
        tabViewChildList.add(tabViewChild2);
        tabViewChildList.add(tabViewChild3);
        tabViewChildList.add(tabViewChild4);
        tabViewChildList.add(tabViewChild5);
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView imgView, TextView textView) {
                BaseTabBean tabBean=new BaseTabBean();
                if(tabViewChildList.get(position).getText().equals("首页")){
                    tabBean.setTabType(1);
                    EventBus.getDefault().post(tabBean);
                }
                if(tabViewChildList.get(position).getText().equals("工作台")){
                    tabBean.setTabType(2);
                    EventBus.getDefault().post(tabBean);
                }
                if(tabViewChildList.get(position).getText().equals("商品")){
                    tabBean.setTabType(3);
                    EventBus.getDefault().post(tabBean);
                }
                if(tabViewChildList.get(position).getText().equals("会员")){
                    tabBean.setTabType(4);
                    EventBus.getDefault().post(tabBean);
                }
                if(tabViewChildList.get(position).getText().equals("菜单")){
                    tabBean.setTabType(5);
                    EventBus.getDefault().post(tabBean);
                }
            }
        });

        tabView.setTabViewChild(tabViewChildList, getChildFragmentManager());

        return view;
    }
}
