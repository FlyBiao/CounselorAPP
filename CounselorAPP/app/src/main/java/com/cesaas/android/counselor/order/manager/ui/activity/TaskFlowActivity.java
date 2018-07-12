package com.cesaas.android.counselor.order.manager.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.manager.adapter.TaskFlowAdapter;
import com.cesaas.android.counselor.order.manager.bean.ResultTaskFlowBean;
import com.cesaas.android.counselor.order.manager.bean.ResultTaskStatisticBean;
import com.cesaas.android.counselor.order.manager.bean.TaskDetailsBean;
import com.cesaas.android.counselor.order.manager.net.TaskFlowNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 任务流程
 */
public class TaskFlowActivity extends BasesActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private TextView tvTitle,tvLeftTitle,tv_task_details,tv_task_title,tv_task_create_date,tv_task_create_name;
    private LinearLayout llBack;

    private String Title;
    private String CreateTime;
    private String TaskUser;
    private String leftTitle;
    private int WorkId;

    private TaskFlowNet taskFlowNet;

    private TaskFlowAdapter taskFlowAdapter;
    private List<ResultTaskFlowBean.TaskFlowBean> detailsBeanList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_flow_layout);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            leftTitle=bundle.getString("leftTitle");
            WorkId=bundle.getInt("WorkId");
            CreateTime=bundle.getString("CreateTime");
            TaskUser=bundle.getString("TaskUser");
            Title=bundle.getString("Title");
        }

        initView();
        initData();
        initNet();
    }

    private void initNet(){
        taskFlowNet=new TaskFlowNet(mContext);
        taskFlowNet.setData(WorkId,1);
    }

    /**
     * 接收任务流程数据
     * @param msg
     */
    public void onEventMainThread(ResultTaskFlowBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                detailsBeanList=new ArrayList<>();
                detailsBeanList=msg.TModel;

                taskFlowAdapter=new TaskFlowAdapter(detailsBeanList);
                taskFlowAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                mRecyclerView.setAdapter(taskFlowAdapter);
                mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                        bundle.putString("leftTitle",tvTitle.getText().toString());
                        Skip.mNextFroData(mActivity,DetailsActivity.class,bundle);
                    }
                });
            }
        }
    }

    private void initData(){
        tv_task_title.setText(Title);
        tv_task_create_date.setText(CreateTime);
        tv_task_create_name.setText(TaskUser);
    }

    private void initView() {
        tv_task_title= (TextView) findViewById(R.id.tv_task_title);
        tv_task_create_date= (TextView) findViewById(R.id.tv_task_create_date);
        tv_task_create_name= (TextView) findViewById(R.id.tv_task_create_name);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tv_task_details= (TextView) findViewById(R.id.tv_task_details);
        tv_task_details.setOnClickListener(this);
        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tvLeftTitle.setText(leftTitle);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("任务流程");
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        initClickListener();
    }

    private void initClickListener(){

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_task_details:
                Skip.mNext(mActivity,TaskDetailsActivity.class);
                break;
        }
    }
}
