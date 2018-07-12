package com.cesaas.android.counselor.order.shoptask.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.shopmange.fragment.AllShopFragment;
import com.cesaas.android.counselor.order.shopmange.fragment.CollectionShopFragment;
import com.cesaas.android.counselor.order.shopmange.fragment.InterestShopFragment;
import com.cesaas.android.counselor.order.shopmange.fragment.MyAttentionShopFragment;
import com.flybiao.basetabview.TabView;
import com.flybiao.basetabview.TabViewChild;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 选择店员人员
 * Created at 2017/5/10 12:02
 * Version 1.0
 */

public class ChooseClerkSampleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample_recommend, container, false);

        TabView tabView = (TabView) view.findViewById(R.id.tab_view);

        List<TabViewChild> tabViewChildList = new ArrayList<>();
        TabViewChild tabViewChild1 = new TabViewChild(R.mipmap.tab_allshop_a, R.mipmap.tab_allshop, "全部联系人", AllContactFragment.newInstance());
        TabViewChild tabViewChild2 = new TabViewChild(R.mipmap.tab_attention_a, R.mipmap.tab_attention, "最近联系人", LatelyContactFragment.newInstance());
        TabViewChild tabViewChild3 = new TabViewChild(R.mipmap.tab_collection_c, R.mipmap.tab_collection, "组联系人", GroupContactFragment.newInstance());
        TabViewChild tabViewChild4 = new TabViewChild(R.mipmap.tab_interest_i, R.mipmap.tab_interest, "部门联系人", DepartmentContactFragment.newInstance());

        tabViewChildList.add(tabViewChild1);
        tabViewChildList.add(tabViewChild2);
        tabViewChildList.add(tabViewChild3);
        tabViewChildList.add(tabViewChild4);

        tabView.setTabViewChild(tabViewChildList, getChildFragmentManager());

        return view;
    }
}
