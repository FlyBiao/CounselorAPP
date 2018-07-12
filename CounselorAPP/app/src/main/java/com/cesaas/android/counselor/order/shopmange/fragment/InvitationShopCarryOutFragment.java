package com.cesaas.android.counselor.order.shopmange.fragment;

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
 * Description 邀单-进行中
 * Created 2017/5/2 11:19
 * Version 1.0
 */
public class InvitationShopCarryOutFragment extends com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment {


    private View view;

    /**
     * 单例
     */
    public static InvitationShopCarryOutFragment newInstance() {
        InvitationShopCarryOutFragment fragmentCommon = new InvitationShopCarryOutFragment();
        return fragmentCommon;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_invitation_shop_carry_out_layout, container, false);
        initView();
        return view;
    }

    private void initView() {
    }

    @Override
    public void fetchData() {

    }
}
