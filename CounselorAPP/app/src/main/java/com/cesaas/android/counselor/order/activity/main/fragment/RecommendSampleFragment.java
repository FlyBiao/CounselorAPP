package com.cesaas.android.counselor.order.activity.main.fragment;

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
import com.cesaas.android.counselor.order.shopmange.fragment.AllShopFragment;
import com.cesaas.android.counselor.order.shopmange.fragment.CollectionShopFragment;
import com.cesaas.android.counselor.order.shopmange.fragment.InterestShopFragment;
import com.cesaas.android.counselor.order.shopmange.fragment.MyAttentionShopFragment;
import com.cesaas.android.counselor.order.workbench.fragment.WorkbenchFragment;
import com.flybiao.basetabview.TabView;
import com.flybiao.basetabview.TabViewChild;

import java.util.ArrayList;
import java.util.List;

/**
 * HomeSampleFragment
 * Created by JieChen on 2017/4/4.
 */

public class RecommendSampleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample_recommend, container, false);

        TabView tabView = (TabView) view.findViewById(R.id.tab_view);

        List<TabViewChild> tabViewChildList = new ArrayList<>();
        TabViewChild tabViewChild1 = new TabViewChild(R.mipmap.tab_allshop_a, R.mipmap.tab_allshop, "所有商品", AllShopFragment.newInstance());
        TabViewChild tabViewChild2 = new TabViewChild(R.mipmap.tab_attention_a, R.mipmap.tab_attention, "我的关注", MyAttentionShopFragment.newInstance());
        TabViewChild tabViewChild3 = new TabViewChild(R.mipmap.tab_collection_c, R.mipmap.tab_collection, "TA的收藏", CollectionShopFragment.newInstance());
        TabViewChild tabViewChild4 = new TabViewChild(R.mipmap.tab_interest_i, R.mipmap.tab_interest, "TA感兴趣", InterestShopFragment.newInstance());

        tabViewChildList.add(tabViewChild1);
        tabViewChildList.add(tabViewChild2);
        tabViewChildList.add(tabViewChild3);
        tabViewChildList.add(tabViewChild4);

        tabView.setTabViewChild(tabViewChildList, getChildFragmentManager());

        return view;
    }
}
