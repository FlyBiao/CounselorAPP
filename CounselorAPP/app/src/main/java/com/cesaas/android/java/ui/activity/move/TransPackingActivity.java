package com.cesaas.android.java.ui.activity.move;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.adapter.TransPackingAdapter;
import com.cesaas.android.java.bean.move.MoveDeliveryBoxListBean;
import com.cesaas.android.java.bean.move.MoveDeliveryListBeanBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryAddBoxBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryBoxListBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryConfirmBean;
import com.cesaas.android.java.net.move.MoveDeliveryAddBoxNet;
import com.cesaas.android.java.net.move.MoveDeliveryBoxListNet;
import com.cesaas.android.java.net.move.MoveDeliveryConfirmNet;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 调拨装箱单列表
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class TransPackingActivity extends BasesActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    private TextView tvTitle,tv_trans_no;
    private TextView tv_date,tv_submit_packing,tv_back,tv_not_data;
    private LinearLayout llBack;
    private TextView tv_send_shop,tv_receive_shop,tv_no,tv_pack,tv_num;
    private ImageView ivScan;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;
    private int type=0;
    private int sumCount=0;

    private long id;
    private MoveDeliveryListBeanBean moveDeliveryListBeanBean=new MoveDeliveryListBeanBean();

    private TransPackingAdapter packingAdapter;
    private List<MoveDeliveryBoxListBean> mData=new ArrayList<>();

    private MoveDeliveryBoxListNet moveDeliveryBoxListNet;
    private MoveDeliveryAddBoxNet moveDeliveryAddBoxNet;
    private MoveDeliveryConfirmNet moveDeliveryConfirmNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_packing);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getLong("id");
        type=bundle.getInt("netType");
        moveDeliveryListBeanBean= (MoveDeliveryListBeanBean) bundle.getSerializable("moveDeliveryListBeanBean");

        initView();
        initData();
    }


    /**
     * 接收确认发货
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryConfirmBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "发货成功");
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
                moveDeliveryBoxListNet = new MoveDeliveryBoxListNet(mContext, type, 0);
                moveDeliveryBoxListNet.setData(id);
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收装箱列表
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryBoxListBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    tv_not_data.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    mData = new ArrayList<>();
                    mData.addAll(msg.arguments.resp.records.value);
                    packingAdapter = new TransPackingAdapter(mData, moveDeliveryListBeanBean.getNo(), mActivity, mContext, type);
                    View footerView = getFooterView(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddDialog addDialog = new AddDialog(mContext);
                            addDialog.setCancelable(false);
                            addDialog.mInitShow();
                        }
                    });
                    if(moveDeliveryListBeanBean.getStatus()==0 || moveDeliveryListBeanBean.getStatus()==50){
                        packingAdapter.addFooterView(footerView, 0);
                    }

                    recyclerView.setAdapter(packingAdapter);
                    packingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            bundle.putLong("id", id);
                            bundle.putLong("boxId", mData.get(position).getBoxId());
                            bundle.putInt("boxNo", mData.get(position).getBoxNo());
                            bundle.putInt("num", mData.get(position).getNum());
                            bundle.putInt("netType", type);
                            bundle.putSerializable("moveDeliveryListBeanBean",moveDeliveryListBeanBean);
                            Skip.mNextFroData(mActivity, MoveDeliveryBoxDetailsActivity.class, bundle);
                        }
                    });

                    for (int i = 0; i < mData.size(); i++) {
                        sumCount += mData.get(i).getNum();
                    }
                    tv_pack.setText(String.valueOf(mData.size()));
                    tv_num.setText(String.valueOf(sumCount));
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
        tv_trans_no= (TextView) findViewById(R.id.tv_trans_no);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        tv_back= (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
        tv_submit_packing= (TextView) findViewById(R.id.tv_submit_packing);
        tv_submit_packing.setOnClickListener(this);
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        if(type==1){
            tvTitle.setText("退货装箱单列表");
            tv_trans_no.setText("退货号：");
        }else{
            tvTitle.setText("调拨装箱单列表");
            tv_trans_no.setText("调拨号：");
        }

        tv_date= (TextView) findViewById(R.id.tv_date);
        tv_send_shop= (TextView) findViewById(R.id.tv_send_shop);
        tv_receive_shop= (TextView) findViewById(R.id.tv_receive_shop);
        tv_no= (TextView) findViewById(R.id.tv_no);
        tv_pack= (TextView) findViewById(R.id.tv_pack);
        tv_num= (TextView) findViewById(R.id.tv_num);
        ivScan= (ImageView) findViewById(R.id.iv_add_module);
        ivScan.setVisibility(View.VISIBLE);
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
        tv_no.setText(moveDeliveryListBeanBean.getNo());

        moveDeliveryBoxListNet=new MoveDeliveryBoxListNet(mContext,type,0);
        moveDeliveryBoxListNet.setData(id);
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
            case R.id.tv_submit_packing:
                moveDeliveryConfirmNet=new MoveDeliveryConfirmNet(mContext,type);
                moveDeliveryConfirmNet.setData(id);
                break;
            case R.id.tv_back:
                Skip.mBack(mActivity);
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        packingAdapter=new TransPackingAdapter(mData,moveDeliveryListBeanBean.getNo(),mActivity,mContext,type);
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

    private View getFooterView(View.OnClickListener listener) {
        TextView tv_add_trans_pack;
        View view = getLayoutInflater().inflate(R.layout.footer_add_pack_view, (ViewGroup) recyclerView.getParent(), false);
        tv_add_trans_pack= (TextView)view. findViewById(R.id.tv_add_trans_pack);
        tv_add_trans_pack.setText(R.string.fa_plus_circle);
        tv_add_trans_pack.setTypeface(App.font);
        view.setOnClickListener(listener);
        return view;
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
                        moveDeliveryAddBoxNet=new MoveDeliveryAddBoxNet(mContext,type);
                        moveDeliveryAddBoxNet.setData(id,et_box_no.getText().toString());
                        dismiss();
                    }else{
                        ToastFactory.getLongToast(mContext,"请输入BoxNo");
                    }
                    break;
            }
        }
    }

}
