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
 * Description 邀单-审核中
 * Created 2017/5/2 11:22
 * Version 1.0
 */
public class InvitationShopAuditFragment extends Fragment {


    private View view;

    /**
     * 单例
     */
    public static InvitationShopAuditFragment newInstance() {
        InvitationShopAuditFragment fragmentCommon = new InvitationShopAuditFragment();
        return fragmentCommon;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_invitation_shop_audit_layout, container, false);
        initView();
        return view;
    }

    private void initView() {
    }

    @Override
    public void fetchData() {

    }
}
