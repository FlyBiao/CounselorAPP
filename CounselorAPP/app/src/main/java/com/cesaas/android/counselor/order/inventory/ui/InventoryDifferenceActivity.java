package com.cesaas.android.counselor.order.inventory.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.inventory.adapter.InventoryDifferenceAdapter;
import com.cesaas.android.counselor.order.inventory.bean.InventoryDifferenceBean;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.view.BaseRecyclerView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 盘点差异页面
 */
public class InventoryDifferenceActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle,tvLeftTitle,tvRightTitle;
    private LinearLayout llBack;
    private String leftTitle;
    private SwipeMenuRecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private List<InventoryDifferenceBean> inventoryDifferenceBeanList;
    private InventoryDifferenceAdapter inventoryDifferenceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_difference);
        if(bundle!=null){
            leftTitle=bundle.getString("leftTitle");
        }

        initView();
        initData();
    }

    private void initData(){
        inventoryDifferenceBeanList=new ArrayList<>();
        for (int i=0;i<3;i++){
            InventoryDifferenceBean bean=new InventoryDifferenceBean();
            bean.setNo("FH485884393933");
            bean.setAddress("深圳市");
            bean.setReceiveAddress("广东省深圳市罗湖区2121");
            bean.setCount(30);
            bean.setType(2);
            bean.setDate("2017-08-28");
            inventoryDifferenceBeanList.add(bean);
        }
        inventoryDifferenceAdapter=new InventoryDifferenceAdapter(inventoryDifferenceBeanList,mContext,mActivity);
        recyclerView.setAdapter(inventoryDifferenceAdapter);
        inventoryDifferenceAdapter.setOnItemClickListener(onItemClickListener);
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:
                finish();
                break;
        }
    }

    private void initView(){
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvRightTitle.setOnClickListener(this);
        tvRightTitle.setVisibility(View.VISIBLE);
        tvRightTitle.setText("确定");
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("生成差异");
        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tvLeftTitle.setText(leftTitle);

        recyclerView= (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        BaseRecyclerView.initRecyclerView(mContext, recyclerView, swipeLayout, mOnRefreshListener, true);
    }

    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //重新加载数据
                    swipeLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };
}
