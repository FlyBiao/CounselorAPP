package com.cesaas.android.counselor.order.inventory.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.inventory.adapter.InventoryRecordAdapter;
import com.cesaas.android.counselor.order.inventory.bean.InventoryRecordBean;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.view.BaseRecyclerView;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 扫描盘点记录
 */
public class InventoryRecordActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle,tvLeftTitle;
    private TextView tv_input_inventory,start_inventory;
    private LinearLayout llBack;
    private SwipeMenuRecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private BaseDialog baseDialog;
    private String leftTitle;

    private List<InventoryRecordBean> inventoryRecordBeanList;
    private InventoryRecordAdapter inventoryRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_record);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            leftTitle=bundle.getString("leftTitle");
        }
        initView();
        initData();
    }

    private void initData(){
        inventoryRecordBeanList=new ArrayList<>();
        for (int i=0;i<4;i++){
            InventoryRecordBean bean=new InventoryRecordBean();
            bean.setNo("FGFB8484844"+i);
            bean.setCount(i);
            inventoryRecordBeanList.add(bean);
        }

        inventoryRecordAdapter=new InventoryRecordAdapter(inventoryRecordBeanList,mContext,mActivity);
        recyclerView.setAdapter(inventoryRecordAdapter);
        inventoryRecordAdapter.setOnItemClickListener(onItemClickListener);
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };

    private void initView(){
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("扫描记录");
        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tvLeftTitle.setText(leftTitle);
        tv_input_inventory= (TextView) findViewById(R.id.tv_input_inventory);
        tv_input_inventory.setOnClickListener(this);
        start_inventory= (TextView) findViewById(R.id.start_inventory);
        start_inventory.setOnClickListener(this);

        recyclerView= (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        BaseRecyclerView.initRecyclerView(mContext, recyclerView, swipeLayout, mOnRefreshListener, false);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_input_inventory:
                baseDialog = new BaseDialog(mContext);
                baseDialog.mInitShow();
                baseDialog.setCancelable(false);
                break;
            case R.id.start_inventory:
                finish();
                break;
        }
    }

    public class BaseDialog extends Dialog implements View.OnClickListener {
        TextView tvCancel,tvSure;
        private MClearEditText et_style_code;

        public BaseDialog(Context context) {
            this(context, R.style.dialog);
        }

        public BaseDialog(Context context, int dialog) {
            super(context, dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(R.layout.dialog_input_inventory);
            tvCancel= (TextView) findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(this);
            tvSure= (TextView) findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(this);

            et_style_code= (MClearEditText) findViewById(R.id.et_style_code);
        }

        public void mInitShow() {
            show();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_cancel:
                    dismiss();
                    break;
                case R.id.tv_sure:

                    break;
            }
        }
    }
}
