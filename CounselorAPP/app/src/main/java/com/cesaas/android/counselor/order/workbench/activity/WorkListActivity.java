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
 * 工作列表
 */
public class WorkListActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_work_list;
    }

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle;

    @Override
    public void initViews() {
        llBaseBack=findView(R.id.ll_base_title_back);
        tvBaseTitle=findView(R.id.tv_base_title);
        tvBaseTitle.setText("工作列表");

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
