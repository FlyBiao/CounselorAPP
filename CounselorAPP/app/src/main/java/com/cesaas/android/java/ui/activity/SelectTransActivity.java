package com.cesaas.android.java.ui.activity;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.returns.ReturnServiceBean;
import com.cesaas.android.counselor.order.member.net.service.ServiceRemarkNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.cesaas.android.java.adapter.move.MoveDeliveryNoticeListAdapter;
import com.cesaas.android.java.bean.notice.NoticeListListBean;
import com.cesaas.android.java.bean.notice.ResultMoveDeliveryAddByNoticeBean;
import com.cesaas.android.java.bean.notice.ResultNoticeListListBean;
import com.cesaas.android.java.net.move.MoveDeliveryNoticeListNet;
import com.cesaas.android.java.net.notice.MoveDeliveryAddByNoticeNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 选择调拨单
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class SelectTransActivity extends BasesActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    private TextView tvTitle,tv_not_data,tv_sure,tv_cancel;
    private LinearLayout llBack;
    private LinearLayout ll_search_vip;
    private MClearEditText et_search;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;
    private int type;

    private MoveDeliveryNoticeListAdapter adapter;
    public List<NoticeListListBean> mData=new ArrayList<>();
    private NoticeListListBean noticeListListBean;
    private MoveDeliveryNoticeListNet moveDeliveryNoticeListNet;
    private MoveDeliveryAddByNoticeNet moveDeliveryAddByNoticeNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_trans_fers);

        Intent intent=getIntent();
        String strType = intent.getStringExtra("Type");
        type=Integer.valueOf(strType);

        initView();
        initData();
    }

    /**
     * 接收
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryAddByNoticeBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
        if(msg.arguments!=null && msg.arguments.resp.errorCode==1){
                Intent mIntent = new Intent();
                setResult(20, mIntent);// 设置结果，并进行传送
                finish();
        }else{
            ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
        }
        }
    }

    /**
     * 接收通知单
     * @param msg
     */
    public void onEventMainThread(ResultNoticeListListBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    tv_not_data.setVisibility(View.GONE);
                    mData = new ArrayList<>();
                    mData.addAll(msg.arguments.resp.records.value);

                    adapter = new MoveDeliveryNoticeListAdapter(mData);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new MoveDeliveryNoticeListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            for (int i = 0; i < mData.size(); i++) {
                                mData.get(i).setChecked(false);
                            }
                            noticeListListBean = new NoticeListListBean();
                            noticeListListBean = mData.get(position);
                            mData.get(position).setChecked(true);
                            adapter.notifyDataSetChanged();
                        }
                    });

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

    private void initView() {
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tv_cancel= (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        tv_sure= (TextView) findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("选择调拨单通知");
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ll_search_vip= (LinearLayout) findViewById(R.id.ll_search);
        ll_search_vip.setOnClickListener(this);
        et_search= (MClearEditText) findViewById(R.id.et_search_content);
        et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    moveDeliveryNoticeListNet=new MoveDeliveryNoticeListNet(mContext,type);
                    moveDeliveryNoticeListNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionSelect(et_search.getText().toString()));
                }
                return false;
            }
        });
    }

    public void initData() {
        moveDeliveryNoticeListNet=new MoveDeliveryNoticeListNet(mContext,type);
        moveDeliveryNoticeListNet.setData(pageIndex);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.ll_search_vip:
                moveDeliveryNoticeListNet=new MoveDeliveryNoticeListNet(mContext,type);
                moveDeliveryNoticeListNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionSelect(et_search.getText().toString()));
                break;
            case R.id.tv_sure:
                if(noticeListListBean!=null){
                    setRemarkDialog(noticeListListBean);
                }else{
                    ToastFactory.getLongToast(mContext,"请选择通知单！");
                }
                break;
            case R.id.tv_cancel:
                Skip.mBack(mActivity);
                break;
        }
    }


    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        adapter=new MoveDeliveryNoticeListAdapter(mData);
//        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                swipeLayout.setRefreshing(false);
//                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    private Dialog remarkDialog;
    private View dialogContentView;
    private EditText et_service_remark;
    private TextView tv_remark;
    private LinearLayout ll_service_remark;
    public void setRemarkDialog(final NoticeListListBean noticeListListBean){
        remarkDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_remark, null);
        remarkDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        tv_remark= (TextView) remarkDialog.findViewById(R.id.tv_remark);
        tv_remark.setText("备注");
        et_service_remark= (EditText) remarkDialog.findViewById(R.id.et_service_remark);
        ll_service_remark= (LinearLayout) remarkDialog.findViewById(R.id.ll_service_remark);
        ll_service_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remarkDialog.dismiss();
                moveDeliveryAddByNoticeNet=new MoveDeliveryAddByNoticeNet(mContext,type);
                moveDeliveryAddByNoticeNet.setData(noticeListListBean.getId(),noticeListListBean.getShipmentsDate(),noticeListListBean.getType(),et_service_remark.getText().toString());
            }
        });

        remarkDialog.getWindow().setGravity(Gravity.BOTTOM);
        remarkDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        remarkDialog.setCanceledOnTouchOutside(true);
        remarkDialog.show();
    }

}
