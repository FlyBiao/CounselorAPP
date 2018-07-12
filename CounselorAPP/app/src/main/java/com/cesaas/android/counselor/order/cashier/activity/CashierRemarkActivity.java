package com.cesaas.android.counselor.order.cashier.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：收银备注
 * 创建日期：2016/8/10
 * 版    本：1.zero
 * 修订历史：
 * ================================================
 */
public class CashierRemarkActivity extends BasesActivity implements View.OnClickListener{

    private TextView tv_title;
    private TextView tv_title_left;
    private TextView tv_title_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_remark);

        initView();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title.setText("备注");
        tv_title_left.setText("返回");
        tv_title_left.setOnClickListener(this);
        tv_title_right.setText("保存");
        tv_title_right.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title_left:
                Skip.mBack(mActivity);
                break;

            case R.id.tv_title_right:

                break;
        }

    }
}
