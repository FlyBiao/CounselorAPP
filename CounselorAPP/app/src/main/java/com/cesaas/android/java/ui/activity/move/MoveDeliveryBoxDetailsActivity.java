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
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesSpecialActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.scan.ZxingScanBoxActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.cesaas.android.java.adapter.move.MoveDeliveryBoxGoodsDetailAdapter;
import com.cesaas.android.java.bean.inventory.ResultInventoryScanBean;
import com.cesaas.android.java.bean.move.MoveDeliveryGoodsDetailBean;
import com.cesaas.android.java.bean.move.MoveDeliveryGoodsListBean;
import com.cesaas.android.java.bean.move.MoveDeliveryListBeanBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryBoxRemarkBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryDetailBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryGoodsDetailBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryGoodsListBean;
import com.cesaas.android.java.net.move.MoveDeliveryBoxRemarkNet;
import com.cesaas.android.java.net.move.MoveDeliveryDetailNet;
import com.cesaas.android.java.net.move.MoveDeliveryGoodsDetailNet;
import com.cesaas.android.java.net.move.MoveDeliveryGoodsListNet;
import com.cesaas.android.java.net.move.MoveDeliveryScanNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 调拨单 发货详情
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class MoveDeliveryBoxDetailsActivity extends BasesSpecialActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private TextView tvTitle,tv_box_no,tv_box_num,tv_complete_box,tv_back;
    private TextView tv_shop_icon,tv_org_icon,tv_remark_icon,tv_angle_down,tv_angle_up,tv_not_data;
    private TextView tv_no,tv_originOrganizationTitle,tv_originShopName,tv_receiveOrganizationTitle,tv_receiveShopName,tv_remark,tv_num;
    private LinearLayout llBack,ll_remark,ll_scan_add_box,ll_search,ll_barcodeNo,lll;
    private MClearEditText et_search_content;
    private EditText et_barcodeNo,et_goods_num;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;

    private long id;
    private long boxId;
    private int boxNo;
    private int num;
    private int goodsNum=1;
    private int type=0;

    private MoveDeliveryGoodsListNet moveDeliveryGoodsListNet;
    private MoveDeliveryGoodsDetailNet moveDeliveryGoodsDetailNet;
    private MoveDeliveryDetailNet deliveryDetailNet;
    private MoveDeliveryBoxRemarkNet moveDeliveryBoxRemarkNet;
    private MoveDeliveryScanNet moveDeliveryScanNet;

    private MoveDeliveryBoxGoodsDetailAdapter moveDeliveryBoxGoodsDetailAdapter;
    private List<MoveDeliveryGoodsDetailBean> mData;
    private List<MoveDeliveryGoodsListBean> goodsListBeen;

    private MoveDeliveryListBeanBean moveDeliveryListBeanBean=new MoveDeliveryListBeanBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_deliver_box_details);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getLong("id");
        boxId=bundle.getLong("boxId");
        boxNo=bundle.getInt("boxNo");
        num=bundle.getInt("num");
        type=bundle.getInt("netType");
        moveDeliveryListBeanBean= (MoveDeliveryListBeanBean) bundle.getSerializable("moveDeliveryListBeanBean");

        initView();
        initData();

        if(moveDeliveryListBeanBean.getStatus()==30 || moveDeliveryListBeanBean.getStatus()==40){
            lll.setVisibility(View.GONE);
        }else{
            lll.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 接收调货条码列表
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryGoodsDetailBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    tv_not_data.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    mData = new ArrayList<>();
                    mData.addAll(msg.arguments.resp.records.value);

                    moveDeliveryBoxGoodsDetailAdapter = new MoveDeliveryBoxGoodsDetailAdapter(mData, mActivity, mContext);
                    recyclerView.setAdapter(moveDeliveryBoxGoodsDetailAdapter);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tv_not_data.setVisibility(View.VISIBLE);
                }
            } else {
                recyclerView.setVisibility(View.GONE);
                tv_not_data.setVisibility(View.VISIBLE);
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收调货商品列表[=====]
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryGoodsListBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                goodsListBeen = new ArrayList<>();
                goodsListBeen.addAll(msg.arguments.resp.records.value);
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收调货发货详情
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryDetailBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.model != null && !"".equals(msg.arguments.resp.model)) {
                    tv_no.setText(msg.arguments.resp.model.getNo());
                    tv_originOrganizationTitle.setText(msg.arguments.resp.model.getOriginOrganizationTitle());
                    tv_originShopName.setText(msg.arguments.resp.model.getOriginShopName());
                    tv_receiveOrganizationTitle.setText(msg.arguments.resp.model.getReceiveOrganizationTitle());
                    tv_receiveShopName.setText(msg.arguments.resp.model.getReceiveShopName());
                    tv_num.setText(String.valueOf(msg.arguments.resp.model.getNum()));
                    tv_remark.setText(msg.arguments.resp.model.getRemark());
                }
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }


    /**
     * 接收备注
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryBoxRemarkBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "添加箱备注成功");
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    public void initData() {
        tv_box_no.setText(String.valueOf(boxNo));
        tv_box_num.setText(String.valueOf(num));

        deliveryDetailNet=new MoveDeliveryDetailNet(mContext,type);
        deliveryDetailNet.setData(id);

        moveDeliveryGoodsListNet=new MoveDeliveryGoodsListNet(mContext,type);
        moveDeliveryGoodsListNet.setData(id,boxId);

        moveDeliveryGoodsDetailNet=new MoveDeliveryGoodsDetailNet(mContext,type);
        moveDeliveryGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPIdAndBoxId(id,boxId));
    }

    /**
     * 接收扫描录入
     * @param msg
     */
    public void onEventMainThread(ResultInventoryScanBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "录入成功！");
                goodsNum = 1;
                et_goods_num.setText(String.valueOf(goodsNum));
                et_barcodeNo.setText("");
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    private void initView() {
        lll= (LinearLayout) findViewById(R.id.lll);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        tv_complete_box= (TextView) findViewById(R.id.tv_complete_box);
        tv_complete_box.setOnClickListener(this);
        tv_back= (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
        ll_scan_add_box= (LinearLayout) findViewById(R.id.ll_scan_add_box);
        ll_scan_add_box.setOnClickListener(this);
        tv_num= (TextView) findViewById(R.id.tv_num);
        tv_remark= (TextView) findViewById(R.id.tv_remark);
        tv_receiveShopName= (TextView) findViewById(R.id.tv_receiveShopName);
        tv_receiveOrganizationTitle= (TextView) findViewById(R.id.tv_receiveOrganizationTitle);
        tv_originShopName= (TextView) findViewById(R.id.tv_originShopName);
        tv_originOrganizationTitle= (TextView) findViewById(R.id.tv_originOrganizationTitle);
        tv_no= (TextView) findViewById(R.id.tv_no);
        tv_angle_down= (TextView) findViewById(R.id.tv_angle_down);
        tv_angle_down.setText(R.string.fa_caret_down);
        tv_angle_down.setTypeface(App.font);
        tv_angle_down.setOnClickListener(this);
        tv_angle_up= (TextView) findViewById(R.id.tv_angle_up);
        tv_angle_up.setText(R.string.fa_caret_up);
        tv_angle_up.setTypeface(App.font);
        tv_angle_up.setOnClickListener(this);
        tv_remark_icon= (TextView) findViewById(R.id.tv_remark_icon);
        tv_remark_icon.setText(R.string.fa_pencil);
        tv_remark_icon.setTypeface(App.font);
        tv_shop_icon= (TextView) findViewById(R.id.tv_shop_icon);
        tv_shop_icon.setText(R.string.business_school);
        tv_shop_icon.setTypeface(App.font);
        tv_org_icon= (TextView) findViewById(R.id.tv_org_icon);
        tv_org_icon.setText(R.string.fa_long_arrow_right);
        tv_org_icon.setTypeface(App.font);
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        if(type==1){
            tvTitle.setText("退货装箱发货");
        }else{
            tvTitle.setText("调拨装箱发货");
        }

        ll_remark= (LinearLayout) findViewById(R.id.ll_remark);
        ll_remark.setOnClickListener(this);
        tv_box_no= (TextView) findViewById(R.id.tv_box_no);
        tv_box_num= (TextView) findViewById(R.id.tv_box_num);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ll_barcodeNo= (LinearLayout) findViewById(R.id.ll_barcodeNo);
        ll_barcodeNo.setOnClickListener(this);
        ll_search= (LinearLayout) findViewById(R.id.ll_search);
        ll_search.setOnClickListener(this);
        et_goods_num= (EditText) findViewById(R.id.et_goods_num);
        et_barcodeNo= (EditText) findViewById(R.id.et_barcodeNo);
        et_search_content= (MClearEditText) findViewById(R.id.et_search_content);
        et_search_content.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    moveDeliveryGoodsDetailNet=new MoveDeliveryGoodsDetailNet(mContext,type);
                    moveDeliveryGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPIdAndBoxIdAndNo(id,boxId,et_search_content.getText().toString()));
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_search:
                moveDeliveryGoodsDetailNet=new MoveDeliveryGoodsDetailNet(mContext,type);
                moveDeliveryGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPIdAndBoxIdAndNo(id,boxId,et_search_content.getText().toString()));
                break;
            case R.id.tv_angle_down:
                if(goodsNum==1){
                }else{
                    goodsNum=goodsNum-1;
                }
                et_goods_num.setText(String.valueOf(goodsNum));
                break;
            case R.id.tv_angle_up:
                goodsNum=goodsNum+1;
                et_goods_num.setText(String.valueOf(goodsNum));
                break;
            case R.id.ll_barcodeNo:
                if(!TextUtils.isEmpty(et_barcodeNo.getText().toString())){
                    moveDeliveryScanNet=new MoveDeliveryScanNet(mContext,type);
                    moveDeliveryScanNet.setData(id,boxId,et_barcodeNo.getText().toString(),goodsNum);
                }else{
                    ToastFactory.getLongToast(mContext,"请输入商品条码");
                }
                break;
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_complete_box:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_back:
                Skip.mBack(mActivity);
                break;
            case R.id.ll_remark:
                AddRemarkDialog remarkDialog=new AddRemarkDialog(mContext);
                remarkDialog.setCancelable(false);
                remarkDialog.mInitShow();
                break;
            case R.id.ll_scan_add_box:
                bundle.putLong("id",id);
                bundle.putLong("boxId",boxId);
                bundle.putInt("netType",type);
                Skip.mNextFroData(mActivity, ZxingScanBoxActivity.class,bundle);
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        moveDeliveryBoxGoodsDetailAdapter=new MoveDeliveryBoxGoodsDetailAdapter(mData,mActivity,mContext);
        moveDeliveryBoxGoodsDetailAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                swipeLayout.setRefreshing(false);
                moveDeliveryBoxGoodsDetailAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }


    public class AddRemarkDialog extends Dialog implements View.OnClickListener {
        TextView tvCancel,tv_sure;
        EditText et_pack_remark;

        public AddRemarkDialog(Context context) {
            this(context, R.style.dialog);
        }

        public AddRemarkDialog(Context context, int dialog) {
            super(context, dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(R.layout.dialog_add_pack_remark);
            et_pack_remark= (EditText) findViewById(R.id.et_pack_remark);
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
                    if (!TextUtils.isEmpty(et_pack_remark.getText().toString())){
                        moveDeliveryBoxRemarkNet=new MoveDeliveryBoxRemarkNet(mContext,type);
                        moveDeliveryBoxRemarkNet.setData(boxId,et_pack_remark.getText().toString());
                        dismiss();
                    }else{
                        ToastFactory.getLongToast(mContext,"请输入备注");
                    }
                    break;
            }
        }
    }
}
