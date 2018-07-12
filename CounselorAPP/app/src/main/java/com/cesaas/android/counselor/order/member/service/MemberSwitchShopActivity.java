package com.cesaas.android.counselor.order.member.service;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 会员转店
 */
public class MemberSwitchShopActivity extends BaseTemplateActivity {

    private TextView tvTitle;
    private LinearLayout llBack;

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_switch_shop;
    }

    public void onEventMainThread(ResultUserBean lbean) {

    }


    @Override
    public void initViews() {
        tvTitle=findView(R.id.tv_base_title);
        tvTitle.setText("转店申请");
        llBack=findView(R.id.ll_base_title_back);
    }



    @Override
    public void initListener() {
        llBack.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void processClick(View v) {

        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
            break;
            default:
                break;
        }
    }

}
