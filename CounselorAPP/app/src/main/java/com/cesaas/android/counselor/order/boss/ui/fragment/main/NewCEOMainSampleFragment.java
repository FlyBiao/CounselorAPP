package com.cesaas.android.counselor.order.boss.ui.fragment.main;

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
 * Created at 2018/1/22 17:48
 * Version 1.0
 */

public class NewCEOMainSampleFragment extends Fragment {

    private List<TabViewChild> tabViewChildList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_boss_fragment_sample, container, false);
        TabView tabView = (TabView) view.findViewById(R.id.tab_view);

        tabViewChildList= new ArrayList<>();
        TabViewChild tabViewChild1 = new TabViewChild(R.mipmap.boss_home_b, R.mipmap.boss_home, "首页", BossHomeFragment.newInstance());
        TabViewChild tabViewChild2 = new TabViewChild(R.mipmap.boss_service_b, R.mipmap.boss_service, "会员服务", BossServiceFragment.newInstance());
        TabViewChild tabViewChild3 = new TabViewChild(0, 0, "菜单", BossHomeFragment.newInstance());
        TabViewChild tabViewChild4 = new TabViewChild(R.mipmap.boss_share_b, R.mipmap.boss_share, "分享圈", BossShareFragment.newInstance());
        TabViewChild tabViewChild5 = new TabViewChild(R.mipmap.boss_msg_b, R.mipmap.boss_msg, "消息", BossMessageFragment.newInstance());

        tabViewChildList.add(tabViewChild1);
        tabViewChildList.add(tabViewChild2);
        tabViewChildList.add(tabViewChild3);
        tabViewChildList.add(tabViewChild4);
        tabViewChildList.add(tabViewChild5);

        tabView.setTabViewChild(tabViewChildList, getChildFragmentManager());
//        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
//            @Override
//            public void onTabChildClick(int position, ImageView imgView, TextView textView) {
//                BaseTabBean tabBean=new BaseTabBean();
//                if(tabViewChildList.get(position).getText().equals("首页")){
//                    tabBean.setTabType(1);
//                    EventBus.getDefault().post(tabBean);
//                }else if(tabViewChildList.get(position).getText().equals("商品分析")){
//                    tabBean.setTabType(2);
//                    EventBus.getDefault().post(tabBean);
//                }else if(tabViewChildList.get(position).getText().equals("日报")){
//                    tabBean.setTabType(2);
//                    EventBus.getDefault().post(tabBean);
//                }else if(tabViewChildList.get(position).getText().equals("店员")){
//                    tabBean.setTabType(12);
//                    EventBus.getDefault().post(tabBean);
//                }else{
//                    tabBean.setTabType(2);
//                    EventBus.getDefault().post(tabBean);
//                }
//            }
//        });

        return view;
    }
}
