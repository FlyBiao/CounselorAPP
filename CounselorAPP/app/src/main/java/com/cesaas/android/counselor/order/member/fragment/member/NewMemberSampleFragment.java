package com.cesaas.android.counselor.order.member.fragment.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.fragment.newmain.NewHomeFragment;
import com.cesaas.android.counselor.order.activity.main.fragment.newmain.NewMenuFragment;
import com.cesaas.android.counselor.order.activity.main.fragment.newmain.NewWorkbenchFragment;
import com.cesaas.android.counselor.order.base.BaseTabBean;
import com.cesaas.android.counselor.order.member.MemberFragment;
import com.cesaas.android.counselor.order.shop.fragment.ShopFragment;
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

public class NewMemberSampleFragment extends Fragment {

    private List<TabViewChild> tabViewChildList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_member_fragment_sample, container, false);
        TabView tabView = (TabView) view.findViewById(R.id.tab_view);
        try{
            tabViewChildList= new ArrayList<>();
            TabViewChild tabViewChild1 = new TabViewChild(R.mipmap.member_all_b, R.mipmap.member_all, "所有会员", AllMemberFragment.newInstance());
            TabViewChild tabViewChild2 = new TabViewChild(R.mipmap.member_birth_b, R.mipmap.member_birth, "本月生日", BirthdayMemberFragment.newInstance());
            TabViewChild tabViewChild4 = new TabViewChild(R.mipmap.member_new_b, R.mipmap.member_new, "新会员", NewMemberFragment.newInstance());
            TabViewChild tabViewChild5 = new TabViewChild(R.mipmap.member_focus_b, R.mipmap.member_focus, "关注的", FocusMemberFragment.newInstance());

            tabViewChildList.add(tabViewChild1);
            tabViewChildList.add(tabViewChild2);
            tabViewChildList.add(tabViewChild4);
            tabViewChildList.add(tabViewChild5);

            tabView.setTabViewChild(tabViewChildList, getChildFragmentManager());

            tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
                @Override
                public void onTabChildClick(int position, ImageView imgView, TextView textView) {
                    BaseTabBean tabBean=new BaseTabBean();
                    if(tabViewChildList.get(position).getText().equals("所有会员")){
                        tabBean.setTabType(10);
                        EventBus.getDefault().post(tabBean);
                    }
                    if(tabViewChildList.get(position).getText().equals("今天生日")){
                        tabBean.setTabType(20);
                        EventBus.getDefault().post(tabBean);
                    }
                    if(tabViewChildList.get(position).getText().equals("新会员")){
                        tabBean.setTabType(30);
                        EventBus.getDefault().post(tabBean);
                    }
                    if(tabViewChildList.get(position).getText().equals("关注的")){
                        tabBean.setTabType(40);
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
