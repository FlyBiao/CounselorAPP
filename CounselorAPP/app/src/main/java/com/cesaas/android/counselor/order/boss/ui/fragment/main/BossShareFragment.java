package com.cesaas.android.counselor.order.boss.ui.fragment.main;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;

/**
 * Author FGB
 * Description
 * Created at 2018/4/13 16:04
 * Version 1.0
 */

public class BossShareFragment extends BaseFragment {

    private LinearLayout ll_base_title_back;
    private TextView tvTitle;

    /**
     * 单例
     */
    public static BossShareFragment newInstance() {
        BossShareFragment fragmentCommon = new BossShareFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_boss_share;
    }

    @Override
    public void initViews() {
        ll_base_title_back=findView(R.id.ll_base_title_back);
        ll_base_title_back.setVisibility(View.GONE);
        tvTitle=findView(R.id.tv_base_title);
        tvTitle.setText("分享圈");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void fetchData() {

    }
}
