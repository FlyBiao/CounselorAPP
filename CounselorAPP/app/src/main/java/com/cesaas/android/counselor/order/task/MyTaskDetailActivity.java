package com.cesaas.android.counselor.order.task;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.WebViewUtils;

/**
 * 我的任务详情
 */
public class MyTaskDetailActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBack;
    private TextView tvBaseTitle;
    private WebView wv_my_task_detail;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task_detail);

        initView();
        loadData();
    }

    private void loadData() {
        WebViewUtils.initWebSettings(wv_my_task_detail,mDialog, Urls.TEST_TASK_DETAILS+"formid="+2+"&flowid="+3+"&taskid="+9);

        WebViewUtils.initSwipeRefreshLayout(swipeRefreshLayout,wv_my_task_detail);
    }

    private void initView() {
        wv_my_task_detail= (WebView) findViewById(R.id.wv_my_task_detail);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshShopLayout);

        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);

        tvBaseTitle.setText("任务详情");
        llBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;
        }

    }
}
