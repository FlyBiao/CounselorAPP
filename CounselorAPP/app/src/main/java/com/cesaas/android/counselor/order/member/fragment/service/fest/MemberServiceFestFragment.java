package com.cesaas.android.counselor.order.member.fragment.service.fest;

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
import com.cesaas.android.counselor.order.member.fragment.service.visit.CompleteMemberServiceFragment;
import com.cesaas.android.counselor.order.member.fragment.service.visit.DealMemberServiceFragment;
import com.cesaas.android.counselor.order.member.fragment.service.visit.MoreMemberServiceFragment;
import com.cesaas.android.counselor.order.member.fragment.service.visit.TimeOutMemberServiceFragment;
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

public class MemberServiceFestFragment extends Fragment {

    private List<TabViewChild> tabViewChildList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_task_fragment_sample, container, false);
        TabView tabView = (TabView) view.findViewById(R.id.tab_view);

        try{
            tabViewChildList = new ArrayList<>();
            TabViewChild tabViewChild1 = new TabViewChild(R.mipmap.deals, R.mipmap.deal, "待处理", DealMemberServiceFestFragment.newInstance());
            TabViewChild tabViewChild2 = new TabViewChild(R.mipmap.completes, R.mipmap.complete, "已完成", CompleteMemberServiceFestFragment.newInstance());
//            TabViewChild tabViewChild3 = new TabViewChild(R.mipmap.timeouts, R.mipmap.timeout, "超时逾期", TimeOutMemberServiceFestFragment.newInstance());
            TabViewChild tabViewChild4 = new TabViewChild(R.mipmap.mores, R.mipmap.more_s, "更多", MoreMemberServiceFestFragment.newInstance());

            tabViewChildList.add(tabViewChild1);
            tabViewChildList.add(tabViewChild2);
//            tabViewChildList.add(tabViewChild3);
            tabViewChildList.add(tabViewChild4);

            tabView.setTabViewChild(tabViewChildList, getChildFragmentManager());
            tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
                @Override
                public void onTabChildClick(int position, ImageView imgView, TextView textView) {
                    BaseTabBean tabBean=new BaseTabBean();
                    if(tabViewChildList.get(position).getText().equals("更多")){
                        tabBean.setTabType(100);
                        EventBus.getDefault().post(tabBean);
                    }else if(tabViewChildList.get(position).getText().equals("待处理")){
                        tabBean.setTabType(10);
                        EventBus.getDefault().post(tabBean);
                    }else if(tabViewChildList.get(position).getText().equals("已完成")){
                        tabBean.setTabType(20);
                        EventBus.getDefault().post(tabBean);
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
}
