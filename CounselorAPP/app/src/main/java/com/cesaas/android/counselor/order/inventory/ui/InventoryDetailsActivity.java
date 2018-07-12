package com.cesaas.android.counselor.order.inventory.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.inventory.adapter.InventoryDetailsAdapter;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.scan.ZxingScanActivity;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.view.BaseRecyclerView;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.cesaas.android.java.bean.inventory.InventoryGetScopeListBean;
import com.cesaas.android.java.bean.inventory.InventoryListBeanBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryAddScopeBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryGetOneBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryGetScopeListBean;
import com.cesaas.android.java.net.inventory.InventoryAddScopeNet;
import com.cesaas.android.java.net.inventory.InventoryGetOneNet;
import com.cesaas.android.java.net.inventory.InventoryGetScopeListNet;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 盘点详情
 */
public class InventoryDetailsActivity extends BasesActivity implements View.OnClickListener{
    private TextView tvTitle,tvLeftTitle,tvRightTitle,tv_not_data;
    private TextView tv_inventory_no,tv_inventory_shop_name,tv_inventory_date,tv_inventory_user_name,tv_inventory_data,tv_submit_user,tv_submit_date,tv_sure_user,tv_sure_date,tv_inventory_type;
    private TextView tv_edit_inventory_no,tv_edit_inventory_type,tv_edit_inventory_date,tv_edit_inventory_shop_name;
//    private ImageView iv_edit_inventory;
    private LinearLayout llBack,ll_create_shelf;
    private LinearLayout ll_edit_inventory,ll_inventory_details;
    private SwipeMenuRecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private String leftTitle;
    private int type;
    private int inventoryType;
    private String id;
    private int requestCode=10;

    private BaseDialog baseDialog;

    private InventoryGetOneNet inventoryGetOneNet;
    private InventoryGetScopeListNet getScopeListNet;
    private InventoryAddScopeNet inventoryAddScopeNet;

    private List<InventoryGetScopeListBean> inventoryDetailsBeanList;
    private InventoryDetailsAdapter inventoryDetailsAdapter;
    private InventoryListBeanBean inventoryListBeanBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_details);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            id=bundle.getString("id");
            type=bundle.getInt("type");
            inventoryListBeanBean= (InventoryListBeanBean) bundle.getSerializable("InventoryListBeanBean");
        }

        initView();
        initData();

        if(inventoryListBeanBean.getStatus()==0 || inventoryListBeanBean.getStatus()==50 ){
            ll_create_shelf.setVisibility(View.VISIBLE);
        }else{
            ll_create_shelf.setVisibility(View.GONE);
        }
    }

    private void initData(){

        inventoryGetOneNet=new InventoryGetOneNet(mContext);
        inventoryGetOneNet.setData(Long.parseLong(id));

        getScopeListNet=new InventoryGetScopeListNet(mContext);
        getScopeListNet.setData(Long.parseLong(id));
    }


    /**
     * 接收盘点货架列表
     * @param msg
     */
    public void onEventMainThread(ResultInventoryGetScopeListBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if(msg.arguments.resp.records.value!=null && msg.arguments.resp.records.value.size()!=0){
                    tv_not_data.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    inventoryDetailsBeanList = new ArrayList<>();
                    inventoryDetailsBeanList.addAll(msg.arguments.resp.records.value);

                    inventoryDetailsAdapter = new InventoryDetailsAdapter(inventoryDetailsBeanList, mContext, mActivity);
                    recyclerView.setAdapter(inventoryDetailsAdapter);
                    inventoryDetailsAdapter.setOnItemClickListener(onItemClickListener);
                }else{
                    tv_not_data.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            } else {
                tv_not_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                ToastFactory.getLongToast(mContext, msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收盘点详情数据
     * @param msg
     */
    public void onEventMainThread(ResultInventoryGetOneBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                tv_inventory_no.setText(msg.arguments.resp.oneInventory.getNo());
                tv_inventory_shop_name.setText(msg.arguments.resp.oneInventory.getShopName());
                if (msg.arguments.resp.oneInventory.getPdDate() != null && !"".equals(msg.arguments.resp.oneInventory.getPdDate())) {
                    tv_inventory_date.setText(AbDateUtil.getDateYMDs(msg.arguments.resp.oneInventory.getPdDate()));
                }
                tv_inventory_user_name.setText(msg.arguments.resp.oneInventory.getCreateName());
                if (msg.arguments.resp.oneInventory.getCreateTime() != null && !"".equals(msg.arguments.resp.oneInventory.getCreateTime())) {
                    tv_inventory_data.setText(AbDateUtil.getDateYMDs(msg.arguments.resp.oneInventory.getCreateTime()));
                }
                tv_submit_user.setText(msg.arguments.resp.oneInventory.getSubmitName());
                if (msg.arguments.resp.oneInventory.getSubmitTime() != null && !"".equals(msg.arguments.resp.oneInventory.getSubmitTime())) {
                    tv_submit_date.setText(AbDateUtil.getDateYMDs(msg.arguments.resp.oneInventory.getSubmitTime()));
                }
                tv_sure_user.setText(msg.arguments.resp.oneInventory.getSureNmae());
                if (msg.arguments.resp.oneInventory.getSureTime() != null && !"".equals(msg.arguments.resp.oneInventory.getSureTime())) {
                    tv_sure_date.setText(AbDateUtil.getDateYMDs(msg.arguments.resp.oneInventory.getSureTime()));
                }
                tv_edit_inventory_no.setText(msg.arguments.resp.oneInventory.getNo());
                if (msg.arguments.resp.oneInventory.getPdDate() != null && !"".equals(msg.arguments.resp.oneInventory.getPdDate())) {
                    tv_edit_inventory_date.setText(AbDateUtil.getDateYMDs(msg.arguments.resp.oneInventory.getPdDate()));
                }
                tv_edit_inventory_shop_name.setText(msg.arguments.resp.oneInventory.getShopName());

                if (msg.arguments.resp.oneInventory.getType() == 0) {
                    tv_inventory_type.setText("全盘");
                    tv_edit_inventory_type.setText("全盘");
                } else {
                    tv_inventory_type.setText("抽盘");
                    tv_edit_inventory_type.setText("抽盘");
                }

            } else {
                ToastFactory.getLongToast(mContext, msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收新建盘点货架结果数据
     * @param msg
     */
    public void onEventMainThread(ResultInventoryAddScopeBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode != 0) {
                ToastFactory.getLongToast(mContext, "新建盘点货架成功！");
                baseDialog.dismiss();
                getScopeListNet = new InventoryGetScopeListNet(mContext);
                getScopeListNet.setData(Long.parseLong(id));
            } else {
                ToastFactory.getLongToast(mContext, msg.arguments.resp.errorMessage);
            }
        }
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
//            bundle.putString("leftTitle",inventoryDetailsBeanList.get(position).getTitle());
//            bundle.putLong("shelfId",inventoryDetailsBeanList.get(position).getId());
//            bundle.putLong("pid",Long.parseLong(id));
//            bundle.putSerializable("inventoryListBeanBean",inventoryListBeanBean);
//            Skip.mNextFroData(mActivity, ZxingScanActivity.class,bundle);


            Intent intent = new Intent(mContext, ZxingScanActivity.class);
            intent.putExtra("leftTitle",inventoryDetailsBeanList.get(position).getTitle());
            intent.putExtra("shelfId",String.valueOf(inventoryDetailsBeanList.get(position).getId()));
            intent.putExtra("pid",String.valueOf(id));
            intent.putExtra("inventoryListBeanBean",inventoryListBeanBean);
            startActivityForResult(intent, requestCode);
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
            case R.id.iv_edit_inventory:
                bundle.putString("leftTitle",tvTitle.getText().toString());
                bundle.putInt("id",Integer.parseInt(id));
                bundle.putInt("type",type);
                Skip.mNextFroData(mActivity,CreateInventoryActivity.class,bundle);
                break;
            case R.id.ll_create_shelf:
                baseDialog = new BaseDialog(mContext);
                baseDialog.mInitShow();
                baseDialog.setCancelable(false);
                break;
        }
    }

    private void initView(){
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvRightTitle.setOnClickListener(this);
        tvRightTitle.setVisibility(View.GONE);
        tvRightTitle.setText("保存");
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("盘点单明细");
        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tvLeftTitle.setText(leftTitle);
//        iv_edit_inventory= (ImageView) findViewById(R.id.iv_edit_inventory);
//        iv_edit_inventory.setOnClickListener(this);
        ll_create_shelf=(LinearLayout) findViewById(R.id.ll_create_shelf);
        ll_create_shelf.setOnClickListener(this);

        tv_inventory_no= (TextView) findViewById(R.id.tv_inventory_no);
        tv_inventory_shop_name= (TextView) findViewById(R.id.tv_inventory_shop_name);
        tv_inventory_date= (TextView) findViewById(R.id.tv_inventory_date);
        tv_inventory_user_name= (TextView) findViewById(R.id.tv_inventory_user_name);
        tv_inventory_data= (TextView) findViewById(R.id.tv_inventory_data);
        tv_submit_user= (TextView) findViewById(R.id.tv_submit_user);
        tv_submit_date= (TextView) findViewById(R.id.tv_submit_date);
        tv_sure_user= (TextView) findViewById(R.id.tv_sure_user);
        tv_sure_date= (TextView) findViewById(R.id.tv_sure_date);
        tv_inventory_type= (TextView) findViewById(R.id.tv_inventory_type);

        tv_edit_inventory_no=(TextView) findViewById(R.id.tv_edit_inventory_no);
        tv_edit_inventory_type=(TextView) findViewById(R.id.tv_edit_inventory_type);
        tv_edit_inventory_date=(TextView) findViewById(R.id.tv_edit_inventory_date);
        tv_edit_inventory_shop_name=(TextView) findViewById(R.id.tv_edit_inventory_shop_name);

        ll_edit_inventory= (LinearLayout) findViewById(R.id.ll_edit_inventory);
        ll_inventory_details= (LinearLayout) findViewById(R.id.ll_inventory_details);

        if(type==1){
            ll_inventory_details.setVisibility(View.VISIBLE);
            ll_edit_inventory.setVisibility(View.GONE);
        }else{
            ll_edit_inventory.setVisibility(View.GONE);
            tvRightTitle.setVisibility(View.GONE);
            ll_inventory_details.setVisibility(View.GONE);
        }

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
                    getScopeListNet=new InventoryGetScopeListNet(mContext);
                    getScopeListNet.setData(Long.parseLong(id));
                    swipeLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    public class BaseDialog extends Dialog implements View.OnClickListener {
        TextView tvCancel,tvSure,tv_dialog_title;
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
            tv_dialog_title= (TextView) findViewById(R.id.tv_dialog_title);
            et_style_code= (MClearEditText) findViewById(R.id.et_style_code);

            tv_dialog_title.setText("新增货架");
            et_style_code.setHint("请输入货架名称");
            et_style_code.setInputType(InputType.TYPE_CLASS_TEXT);
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
                    inventoryAddScopeNet=new InventoryAddScopeNet(mContext);
                    inventoryAddScopeNet.setData(Long.parseLong(id),et_style_code.getText().toString());
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getScopeListNet=new InventoryGetScopeListNet(mContext);
        getScopeListNet.setData(Long.parseLong(id));
    }
}
