package com.cesaas.android.counselor.order.shopmange.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;

/**
 * Author FGB
 * Description 邀单-已完成
 * Created 2017/5/2 11:18
 * Version 1.0
 */
public class InvitationShopFinishFragment extends Fragment {


    private View view;

    /**
     * 单例
     */
    public static InvitationShopFinishFragment newInstance() {
        InvitationShopFinishFragment fragmentCommon = new InvitationShopFinishFragment();
        return fragmentCommon;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_invitation_shop_finish_layout, container, false);
        initView();
        return view;
    }

    private void initView() {
    }

    @Override
    public void fetchData() {

    }
}
