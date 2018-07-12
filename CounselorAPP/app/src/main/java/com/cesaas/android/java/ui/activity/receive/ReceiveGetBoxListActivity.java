package com.cesaas.android.java.ui.activity.receive;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.adapter.receive.ReceiveBoxListAdapter;
import com.cesaas.android.java.bean.move.MoveDeliveryBoxListBean;
import com.cesaas.android.java.bean.move.MoveDeliveryListBeanBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryAddBoxBean;
import com.cesaas.android.java.bean.receive.ResultReceiveBoxListBean;
import com.cesaas.android.java.bean.receive.ResultReceivingSyncNumBean;
import com.cesaas.android.java.net.receive.MoveReceiveBoxListNet;
import com.cesaas.android.java.net.receive.ReceiveAddBoxNet;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 获取箱列表 /收货
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class ReceiveGetBoxListActivity extends BasesActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    private TextView tvTitle;
    private TextView tv_date;
    private LinearLayout llBack;
    private ImageView ivScan;
    private TextView tv_send_shop,tv_receive_shop,tv_no,tv_pack,tv_num,tv_send_remark,tv_not_data;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;
    private int requestCode=10;

    private long id;
    private MoveDeliveryListBeanBean moveDeliveryListBeanBean=new MoveDeliveryListBeanBean();

    private MoveReceiveBoxListNet moveReceiveBoxListNet;
    private ReceiveBoxListAdapter packingAdapter;
    private List<MoveDeliveryBoxListBean> mData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_box_list);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getLong("id");
        moveDeliveryListBeanBean= (MoveDeliveryListBeanBean) bundle.getSerializable("moveDeliveryListBeanBean");

        initView();
        initData();
    }


    /**
     * 接收确认收货
     * @param msg
     */
    public void onEventMainThread(ResultReceivingSyncNumBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "按箱收货成功");
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收新增箱
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryAddBoxBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "新增箱成功");
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }


    /**
     * 接收装箱列表
     * @param msg
     */
    public void onEventMainThread(ResultReceiveBoxListBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    tv_not_data.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    tv_pack.setText(String.valueOf(msg.arguments.resp.totalCount));
                    tv_num.setText(String.valueOf(msg.arguments.resp.countBoxNum));
                    mData = new ArrayList<>();
                    mData.addAll(msg.arguments.resp.records.value);
                    packingAdapter = new ReceiveBoxListAdapter(mData, id, moveDeliveryListBeanBean.getNo(), mActivity, mContext,moveDeliveryListBeanBean.getStatus());
                    recyclerView.setAdapter(packingAdapter);
                    packingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(mContext, ReceiveBoxDetailsActivity.class);
                            intent.putExtra("id", id + "");
                            intent.putExtra("boxId", mData.get(position).getBoxId() + "");
                            intent.putExtra("boxNo", mData.get(position).getBoxNo() + "");
                            intent.putExtra("num", String.valueOf(mData.get(position).getNum()));
                            intent.putExtra("status", String.valueOf(moveDeliveryListBeanBean.getStatus()));
                            startActivityForResult(intent, requestCode);
                        }
                    });
                } else {
                    tv_not_data.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            } else {
                tv_not_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    private void initView() {
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("收货装箱单列表");
        tv_date= (TextView) findViewById(R.id.tv_date);
        tv_send_shop= (TextView) findViewById(R.id.tv_send_shop);
        tv_receive_shop= (TextView) findViewById(R.id.tv_receive_shop);
        tv_no= (TextView) findViewById(R.id.tv_no);
        tv_pack= (TextView) findViewById(R.id.tv_pack);
        tv_num= (TextView) findViewById(R.id.tv_num);
        tv_send_remark= (TextView) findViewById(R.id.tv_send_remark);

        ivScan= (ImageView) findViewById(R.id.iv_add_module);
        ivScan.setVisibility(View.GONE);
        ivScan.setImageResource(R.mipmap.add_module);
        ivScan.setOnClickListener(this);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void initData() {
        tv_send_shop.setText(moveDeliveryListBeanBean.getOriginOrganizationTitle()+" - "+moveDeliveryListBeanBean.getOriginShopName());
        tv_receive_shop.setText(moveDeliveryListBeanBean.getReceiveOrganizationTitle()+" - "+moveDeliveryListBeanBean.getReceiveShopName());
        tv_send_remark.setText(moveDeliveryListBeanBean.getRemark());
        tv_no.setText(moveDeliveryListBeanBean.getNo());

        moveReceiveBoxListNet=new MoveReceiveBoxListNet(mContext,0);
        moveReceiveBoxListNet.setData(id);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.iv_add_module:
                AddDialog addDialog=new AddDialog(mContext);
                addDialog.setCancelable(false);
                addDialog.mInitShow();
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        packingAdapter=new ReceiveBoxListAdapter(mData,id,moveDeliveryListBeanBean.getNo(),mActivity,mContext,moveDeliveryListBeanBean.getStatus());
        packingAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                swipeLayout.setRefreshing(false);
                packingAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    public class AddDialog extends Dialog implements View.OnClickListener {
        TextView tvCancel,tv_sure;
        EditText et_box_no;


        public AddDialog(Context context) {
            this(context, R.style.dialog);
        }

        public AddDialog(Context context, int dialog) {
            super(context, dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(R.layout.dialog_add_pack);
            et_box_no= (EditText) findViewById(R.id.et_box_no);
            tv_sure= (TextView) findViewById(R.id.tv_sure);
            tv_sure.setOnClickListener(this);
            tvCancel= (TextView) findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(this);
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
                    if (!TextUtils.isEmpty(et_box_no.getText().toString())){
                        ReceiveAddBoxNet addBoxNet=new ReceiveAddBoxNet(mContext);
                        addBoxNet.setData(id,et_box_no.getText().toString());
                        dismiss();
                    }else{
                        ToastFactory.getLongToast(mContext,"请输入BoxNo");
                    }
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if(data!=null){
                initData();
            }
        }
    }
}
