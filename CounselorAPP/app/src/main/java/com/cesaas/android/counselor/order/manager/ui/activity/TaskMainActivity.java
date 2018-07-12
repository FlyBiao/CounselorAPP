package com.cesaas.android.counselor.order.manager.ui.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;

import com.cesaas.android.counselor.order.manager.adapter.TaskAdapter;
import com.cesaas.android.counselor.order.manager.bean.ResultTaskListBean;
import com.cesaas.android.counselor.order.manager.bean.TaskBean;

import com.cesaas.android.counselor.order.manager.bean.TaskListBean;
import com.cesaas.android.counselor.order.manager.net.CopyTaskListNet;
import com.cesaas.android.counselor.order.manager.net.TaskListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 任务主页
 */
public class TaskMainActivity extends BasesActivity implements View.OnClickListener,BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TextView tvShop,tvInstitution,tvArea;
    private ArrayList<TextView> tvs=new ArrayList<TextView>();

    private TextView tvTitle,tvLeftTitle;
    private ImageView ivShare;
    private LinearLayout llBack;

    private Typeface font;
    private String leftTitle;
    private String title;

    private List<TaskListBean> data;
    private TaskAdapter adapter;

    private TaskListNet taskListNet;
    private CopyTaskListNet copyTaskListNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_main_layout);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            leftTitle=bundle.getString("leftTitle");
            title=bundle.getString("title");
        }

        initView();
        initData();
    }

    private void initData(){
        //测试数据
        taskListNet=new TaskListNet(mContext);
        taskListNet.setData(1,1);
    }


    /**
     * 接收任务统计数据
     * @param msg
     */
    public void onEventMainThread(ResultTaskListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel.size()!=0){
                data=new ArrayList<>();
                data=msg.TModel;
                adapter=new TaskAdapter(data);
//                adapter.setOnLoadMoreListener(this, mRecyclerView);
                adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                        Toast.makeText(TaskMainActivity.this, Integer.toString(position), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private void initView() {
        font= Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tvLeftTitle.setText(leftTitle);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText(title);
        ivShare= (ImageView) findViewById(R.id.iv_add_module);
        ivShare.setVisibility(View.VISIBLE);
        ivShare.setImageResource(R.mipmap.add_task_r);

        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);

        tvShop=(TextView) findViewById(R.id.tv_shop);
        tvShop.setOnClickListener(this);
        tvInstitution=(TextView) findViewById(R.id.tv_institution);
        tvInstitution.setOnClickListener(this);
        tvArea=(TextView) findViewById(R.id.tv_area);
        tvArea.setOnClickListener(this);
        tvs.add(tvShop);
        tvs.add(tvInstitution);
        tvs.add(tvArea);
        initClickListener();
    }

    private void initClickListener(){
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle",tvTitle.getText().toString());
                Skip.mNextFroData(mActivity,AddShopTaskActivity.class,bundle);
            }
        });

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int index=-1;
        switch (v.getId()){
            case R.id.tv_shop:
                index=0;
                taskListNet=new TaskListNet(mContext);
                taskListNet.setData(0,1);
                break;
            case R.id.tv_institution:
                index=1;
                copyTaskListNet=new CopyTaskListNet(mContext);
                copyTaskListNet.setData(1,1);
                break;
            case R.id.tv_area:
                index=2;
                taskListNet=new TaskListNet(mContext);
                taskListNet.setData(1,1);
                break;
        }
        setColor(index);
    }

    private void setColor(int index) {
        for(int i=0;i<tvs.size();i++){
            tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.darkcyan));
            tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.white));
        tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_green_bg));
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, 1000);
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
