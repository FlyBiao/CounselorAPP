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
 * 描    述：收款页面
 * 创建日期：2016/8/10
 * 版    本：1.zero
 * 修订历史：
 * ================================================
 */
public class CashierActivity extends BasesActivity implements View.OnClickListener{

    private TextView tv_title;
    private TextView tv_title_left;
    private TextView tv_received_cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);
        initView();

        Bundle bundle = getIntent().getExtras();
        tv_received_cash.setText(bundle.getString("money"));
    }
    public void initView(){
        tv_received_cash= (TextView) findViewById(R.id.tv_received_cash);
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("收款");
        tv_title_left= (TextView) findViewById(R.id.tv_title_left);
        tv_title_left.setOnClickListener(this);
        tv_title_left.setText("返回");
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_title_left:
                Skip.mBack(mActivity);
                break;
        }
    }
}
