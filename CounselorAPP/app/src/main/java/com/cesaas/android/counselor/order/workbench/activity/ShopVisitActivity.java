package com.cesaas.android.counselor.order.workbench.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseActivity;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 巡店任务
 */
public class ShopVisitActivity extends BaseActivity {



    @Override
    public int getLayoutId() {
        return R.layout.activity_shop_visit;
    }

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle;

    @Override
    public void initViews() {
        llBaseBack=findView(R.id.ll_base_title_back);
        tvBaseTitle=findView(R.id.tv_base_title);
        tvBaseTitle.setText("巡店任务");

        llBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
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


}
