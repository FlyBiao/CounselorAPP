package com.cesaas.android.counselor.order.manager.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.manager.adapter.ImageLoopAdapter;
import com.cesaas.android.counselor.order.manager.adapter.TaskDetailsAdapter;
import com.cesaas.android.counselor.order.manager.bean.ResultGetOneBean;
import com.cesaas.android.counselor.order.manager.bean.ResultTaskDetailsBean;
import com.cesaas.android.counselor.order.manager.bean.ResultTaskStatisticBean;
import com.cesaas.android.counselor.order.manager.bean.TaskDetailsBean;
import com.cesaas.android.counselor.order.manager.net.TaskGetOneNet;
import com.cesaas.android.counselor.order.manager.net.TaskWorkListNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;

import java.util.ArrayList;
import java.util.List;


/**
 * 任务详情
 */
public class TaskDetailsActivity extends BasesActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private RollPagerView mViewPager;
    private ImageView iv_task_img;
    private TextView tvTitle,tvLeftTitle;
    private TextView tv_show_details,tv_task_title,tv_task_level,tv_task_start_date,tv_task_end_date,tv_task_craete_date,tv_create_name,tv_not_date;

    private LinearLayout llBack,ll_is_show,ll_show_details;

    private String leftTitle;
    private int TaskId;
    boolean isShow=false;

    private TaskGetOneNet getOneNet;
    private TaskWorkListNet taskWorkListNet;

    private TaskDetailsAdapter detailsAdapter;
    private List<TaskDetailsBean> detailsBeanList;

    private List<String> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details_layout);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            leftTitle=bundle.getString("leftTitle");
            TaskId=bundle.getInt("TaskId");
        }

        initView();
        initData();
        initNet();
    }

    private void initNet(){
        getOneNet=new TaskGetOneNet(mActivity);
        getOneNet.setData(TaskId,1);
        taskWorkListNet=new TaskWorkListNet(mContext);
        taskWorkListNet.setData(TaskId,0,1);
    }

    /**
     * 接收任务详情数据
     * @param msg
     */
    public void onEventMainThread(ResultTaskDetailsBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                    detailsBeanList=new ArrayList<>();
                    detailsBeanList=msg.TModel;

                    detailsAdapter=new TaskDetailsAdapter(detailsBeanList);
                    detailsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                    mRecyclerView.setAdapter(detailsAdapter);
                    mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
                        @Override
                        public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                            bundle.putString("leftTitle",tvTitle.getText().toString());
                            bundle.putInt("WorkId",detailsBeanList.get(position).getWorkId());
                            bundle.putInt("FormId",detailsBeanList.get(position).getFormId());
                            bundle.putInt("FlowId",detailsBeanList.get(position).getFlowId());
                            bundle.putString("Title",detailsBeanList.get(position).getWorkName());
                            bundle.putString("CreateTime",detailsBeanList.get(position).getCreateTime());
                            bundle.putString("TaskTitle",detailsBeanList.get(position).getWorkName());
                            bundle.putString("TaskUser",detailsBeanList.get(position).getNotifyName());
                            Skip.mNextFroData(mActivity,DetailsActivity.class,bundle);
                        }
                    });
            }else{
                tv_not_date.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_date.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 接收任务详情数据
     * @param msg
     */
    public void onEventMainThread(ResultGetOneBean msg) {
        if(msg.IsSuccess!=false && msg.TModel!=null){
            tv_task_title.setText(msg.TModel.getTaskTitle());
            tv_task_start_date.setText(msg.TModel.getStartDate());
            tv_task_end_date.setText(msg.TModel.getEndDate());
            tv_task_craete_date.setText(msg.TModel.getCreateTime());
            tv_create_name.setText(msg.TModel.getCreateName());



            if(msg.TModel.getCategoryId()==0){
                tv_task_level.setText("普通");
            }else if(msg.TModel.getCategoryId()==1){//重要不紧急
                tv_task_level.setText("重要不紧急");
            }else if(msg.TModel.getCategoryId()==2){//紧急不重要
                tv_task_level.setText("紧急不重要");
            }else if(msg.TModel.getCategoryId()==3){//重要而且紧急
                tv_task_level.setText("重要而且紧急");
            }


            imageList=new ArrayList<>();
            for (int i=0;i<msg.TModel.getImages().size();i++){
                imageList.add(msg.TModel.getImages().get(i));
            }
            mViewPager.setAdapter(new ImageLoopAdapter(mViewPager,imageList,mContext));

        }

    }



    private void initData(){


    }

    private void initView() {

        mViewPager = (RollPagerView) findViewById(R.id.view_pager);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tvLeftTitle.setText(leftTitle);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("任务详情");
        tv_show_details= (TextView) findViewById(R.id.tv_show_details);
        ll_show_details= (LinearLayout) findViewById(R.id.ll_show_details);
        ll_show_details.setOnClickListener(this);

        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        ll_is_show= (LinearLayout) findViewById(R.id.ll_is_show);

        tv_task_title= (TextView) findViewById(R.id.tv_task_title);
        tv_task_level= (TextView) findViewById(R.id.tv_task_level);
        tv_task_start_date= (TextView) findViewById(R.id.tv_task_start_date);
        tv_task_end_date= (TextView) findViewById(R.id.tv_task_end_date);
        tv_task_craete_date= (TextView) findViewById(R.id.tv_task_craete_date);
        tv_create_name= (TextView) findViewById(R.id.tv_create_name);

        iv_task_img= (ImageView) findViewById(R.id.iv_task_img);
        tv_not_date= (TextView) findViewById(R.id.tv_not_date);

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

            case R.id.ll_show_details:
                if(isShow==false){//显示
                    tv_show_details.setText("隐藏");
                    ll_is_show.setVisibility(View.VISIBLE);
                    iv_task_img.setImageResource(R.mipmap.hide_task);
                    isShow=true;
                }else{//隐藏
                    tv_show_details.setText("展示");
                    ll_is_show.setVisibility(View.GONE);
                    iv_task_img.setImageResource(R.mipmap.show_task);
                    isShow=false;
                }
                break;
        }
    }
}
