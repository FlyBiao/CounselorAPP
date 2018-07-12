package com.cesaas.android.java.ui.activity.move;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.cesaas.android.java.adapter.move.BaseBoxAdapter;
import com.cesaas.android.java.adapter.move.MoveDeliveryGoodsDetailAdapter;
import com.cesaas.android.java.bean.move.MoveDeliveryBoxListBean;
import com.cesaas.android.java.bean.move.MoveDeliveryGoodsDetailBean;
import com.cesaas.android.java.bean.move.MoveDeliveryListBeanBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryBoxListsBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryDetailBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryGoodsDetailBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryGoodsListBean;
import com.cesaas.android.java.net.move.MoveDeliveryBoxListNet;
import com.cesaas.android.java.net.move.MoveDeliveryDetailNet;
import com.cesaas.android.java.net.move.MoveDeliveryGoodsDetailNet;
import com.cesaas.android.java.net.move.MoveDeliveryGoodsListNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;
import com.cesaas.android.java.utils.MoveDeliveryStatusUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 调拨单 发货详情
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class MoveDeliveryDetailsActivity extends BasesActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private TextView tvTitle;
    private TextView tv_shop_icon,tv_org_icon,tv_remark_icon,tv_angle_down,tv_not_data,tv_box;
    private TextView tv_status_icon,tv_mover_status,tv_category;
    private TextView tv_no,tv_originOrganizationTitle,tv_originShopName,tv_receiveOrganizationTitle,tv_receiveShopName,tv_remark,tv_num,tv_hasNum;
    private TextView tv_createTime,tv_sureTime,tv_createName,tv_sureName,tv_createTime_icon,tv_sureTime_icon,tv_create_name_icon,tv_sure_name_icon;
    private LinearLayout llBack;
    private LinearLayout tv_box_list,ll_search,ll_box;
    private MClearEditText et_search_content;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private long id;
    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;
    private int type=0;

    private MoveDeliveryDetailNet deliveryDetailNet;
    private MoveDeliveryGoodsListNet moveDeliveryGoodsListNet;
    private MoveDeliveryGoodsDetailNet moveDeliveryGoodsDetailNet;
    private MoveDeliveryBoxListNet moveDeliveryBoxListNet;
    private MoveDeliveryGoodsDetailAdapter moveDeliveryGoodsDetailAdapter;
    private List<MoveDeliveryGoodsDetailBean> mData=new ArrayList<>();

    private MoveDeliveryListBeanBean moveDeliveryListBeanBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_deliver_details);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getLong("id");
        type=bundle.getInt("netType");

        initView();
        initData();
    }

    /**
     * 接收调货管理-调货发货商品条码列表
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
                    moveDeliveryGoodsDetailAdapter = new MoveDeliveryGoodsDetailAdapter(mData, mActivity, mContext);
                    recyclerView.setAdapter(moveDeliveryGoodsDetailAdapter);
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
                    moveDeliveryListBeanBean = new MoveDeliveryListBeanBean();
                    moveDeliveryListBeanBean = msg.arguments.resp.model;

                    tv_no.setText(msg.arguments.resp.model.getNo());
                    tv_originOrganizationTitle.setText(msg.arguments.resp.model.getOriginOrganizationTitle());
                    tv_originShopName.setText(msg.arguments.resp.model.getOriginShopName());
                    tv_receiveOrganizationTitle.setText(msg.arguments.resp.model.getReceiveOrganizationTitle());
                    tv_receiveShopName.setText(msg.arguments.resp.model.getReceiveShopName());
                    tv_num.setText(String.valueOf(msg.arguments.resp.model.getNum()));
                    tv_hasNum.setText(String.valueOf(msg.arguments.resp.model.getReceivingNum()));
                    tv_createTime.setText(AbDateUtil.getDateYMDs(msg.arguments.resp.model.getCreateTime()));
                    tv_sureTime.setText(AbDateUtil.getDateYMDs(msg.arguments.resp.model.getSureTime()));
                    tv_createName.setText(msg.arguments.resp.model.getCreateName());
                    tv_sureName.setText(msg.arguments.resp.model.getSureName());
                    tv_remark.setText(msg.arguments.resp.model.getRemark());

                    MoveDeliveryStatusUtils.getDetailStatus(tv_status_icon,tv_mover_status,moveDeliveryListBeanBean.getStatus(),mContext);
                }
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }


    /**
     * 接收 商品列表
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryGoodsListBean msg) {

    }

    /**
     * 接收装箱列表
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryBoxListsBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    showBoxDialog(msg.arguments.resp.records.value);
                }else{
                    ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
                }
            }
        }
    }

    public void initData() {
        deliveryDetailNet=new MoveDeliveryDetailNet(mContext,type);
        deliveryDetailNet.setData(id);

//        moveDeliveryGoodsListNet=new MoveDeliveryGoodsListNet(mContext,type);
//        moveDeliveryGoodsListNet.setData(id);

        moveDeliveryGoodsDetailNet=new MoveDeliveryGoodsDetailNet(mContext,type);
        moveDeliveryGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPId(id));
    }

    private void initView() {
        tv_category= (TextView) findViewById(R.id.tv_category);
        tv_mover_status= (TextView) findViewById(R.id.tv_mover_status);
        tv_status_icon= (TextView) findViewById(R.id.tv_status_icon);
        tv_box= (TextView) findViewById(R.id.tv_box);
        ll_box= (LinearLayout) findViewById(R.id.ll_box);
        ll_box.setOnClickListener(this);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        tv_box_list= (LinearLayout) findViewById(R.id.tv_box_list);
        tv_box_list.setOnClickListener(this);
        tv_sure_name_icon= (TextView) findViewById(R.id.tv_sure_name_icon);
        tv_sure_name_icon.setText(R.string.user);
        tv_sure_name_icon.setTypeface(App.font);
        tv_create_name_icon= (TextView) findViewById(R.id.tv_create_name_icon);
        tv_create_name_icon.setText(R.string.user);
        tv_create_name_icon.setTypeface(App.font);
        tv_sureTime_icon= (TextView) findViewById(R.id.tv_sureTime_icon);
        tv_sureTime_icon.setText(R.string.fa_clock);
        tv_sureTime_icon.setTypeface(App.font);
        tv_createTime_icon= (TextView) findViewById(R.id.tv_createTime_icon);
        tv_createTime_icon.setText(R.string.fa_clock);
        tv_createTime_icon.setTypeface(App.font);
        tv_sureName= (TextView) findViewById(R.id.tv_sureName);
        tv_createName= (TextView) findViewById(R.id.tv_createName);
        tv_sureTime= (TextView) findViewById(R.id.tv_sureTime);
        tv_createTime= (TextView) findViewById(R.id.tv_createTime);
        tv_hasNum= (TextView) findViewById(R.id.tv_hasNum);
        tv_num= (TextView) findViewById(R.id.tv_num);
        tv_remark= (TextView) findViewById(R.id.tv_remark);
        tv_receiveShopName= (TextView) findViewById(R.id.tv_receiveShopName);
        tv_receiveOrganizationTitle= (TextView) findViewById(R.id.tv_receiveOrganizationTitle);
        tv_originShopName= (TextView) findViewById(R.id.tv_originShopName);
        tv_originOrganizationTitle= (TextView) findViewById(R.id.tv_originOrganizationTitle);
        tv_no= (TextView) findViewById(R.id.tv_no);
        tv_angle_down= (TextView) findViewById(R.id.tv_angle_down);
        tv_angle_down.setText(R.string.fa_angle_down);
        tv_angle_down.setTypeface(App.font);
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

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ll_search= (LinearLayout) findViewById(R.id.ll_search);
        ll_search.setOnClickListener(this);
        et_search_content= (MClearEditText) findViewById(R.id.et_search_content);
        et_search_content.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    moveDeliveryGoodsDetailNet=new MoveDeliveryGoodsDetailNet(mContext,type);
                    moveDeliveryGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPIdAndNo(id,et_search_content.getText().toString()));
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_box_list:
                bundle.putLong("id",id);
                bundle.putInt("netType",type);
                bundle.putSerializable("moveDeliveryListBeanBean",moveDeliveryListBeanBean);
                Skip.mNextFroData(mActivity,TransPackingActivity.class,bundle);
                break;
            case R.id.ll_search:
                moveDeliveryGoodsDetailNet=new MoveDeliveryGoodsDetailNet(mContext,type);
                moveDeliveryGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPIdAndNo(id,et_search_content.getText().toString()));
                break;
            case R.id.ll_box:
                moveDeliveryBoxListNet=new MoveDeliveryBoxListNet(mContext,type,1);
                moveDeliveryBoxListNet.setData(id);
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        moveDeliveryGoodsDetailAdapter=new MoveDeliveryGoodsDetailAdapter(mData,mActivity,mContext);
        moveDeliveryGoodsDetailAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                swipeLayout.setRefreshing(false);
                moveDeliveryGoodsDetailAdapter.setEnableLoadMore(true);
                tv_box.setText("所有箱");
            }
        }, delayMillis);
    }


    private RecyclerView recyclerViewBox;
    public static View dialogContentView;
    private TextView tv_all_box;
    private static Dialog bottomDialog;
    private BaseBoxAdapter boxAdapter;
    public void showBoxDialog(final List<MoveDeliveryBoxListBean> boxListBeen){
        bottomDialog= new Dialog(mContext, R.style.BottomDialog);
        dialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_box_list, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        tv_all_box= (TextView) bottomDialog.findViewById(R.id.tv_all_box);
        recyclerViewBox= (RecyclerView) bottomDialog.findViewById(R.id.rv_box_view);
        recyclerViewBox.setLayoutManager(new LinearLayoutManager(this));

        boxAdapter=new BaseBoxAdapter(boxListBeen,mContext,mActivity);
        recyclerViewBox.setAdapter(boxAdapter);
        boxAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tv_box.setText("第"+boxListBeen.get(position).getBoxNo()+"箱");
                moveDeliveryGoodsDetailNet=new MoveDeliveryGoodsDetailNet(mContext,type);
                moveDeliveryGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPIdAndBoxId(id,boxListBeen.get(position).getBoxId()));
                bottomDialog.dismiss();
            }
        });
        tv_all_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_box.setText("所有箱");
                initData();
                bottomDialog.dismiss();
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }
}
