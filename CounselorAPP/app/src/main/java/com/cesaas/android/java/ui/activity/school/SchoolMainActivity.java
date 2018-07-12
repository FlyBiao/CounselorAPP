package com.cesaas.android.java.ui.activity.school;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.fragment.newmain.NewMainSampleFragment;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.java.ui.fragment.school.SchoolMainSampleFragment;

/**
 * Author FGB
 * Description 商学院主页入口
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class SchoolMainActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle;
    private LinearLayout llBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);

        initView();
        initData();
    }

    private void initView() {
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("商学院");

    }

    public void initData() {
        getSupportFragmentManager().beginTransaction().add(R.id.school_frame_layout, new SchoolMainSampleFragment()).commit();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
        }
    }

}
