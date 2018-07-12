package com.cesaas.android.counselor.order.member.salestalk;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.net.service.SendMsgNet;
import com.cesaas.android.counselor.order.salestalk.bean.ResultSalesTalkCategoryListBean;
import com.cesaas.android.counselor.order.salestalk.net.GetListNet;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/6/11 14:31
 * Version 1.0
 */

public class SalesSimpleFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{

    public static final String BUNDLE_ID = "id";
    private int id=0;
    private int pageIndex=1;
    private int totalSize=0;
    private int delayMillis = 2000;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;
    private int type;

    private View view;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data;

    private GetListNet getListNet;
    private SalesTalkAdapter talkAdapter;
    private List<ResultSalesTalkCategoryListBean.SalesTalkCategoryListBean> mData=new ArrayList<>();

    public static SalesSimpleFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_ID, id);
        SalesSimpleFragment fragment = new SalesSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sales_salk, container,false);
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().register(this);

        Intent intent=getActivity().getIntent();
        String strType = intent.getStringExtra("Type");
        type=Integer.valueOf(strType);

        mRecyclerView= (RecyclerView) view.findViewById(R.id.rv_list);
        mSwipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        tv_not_data= (TextView) view.findViewById(R.id.tv_not_data);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle arguments = getArguments();
        id= arguments.getInt(BUNDLE_ID);
//        getListNet=new GetListNet(getContext());
//        getListNet.setData(id,pageIndex);

        return view;
    }

    public void onEventMainThread(ResultSalesTalkCategoryListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_data.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);
                if(isLoadMore==true){
                    talkAdapter.addData(mData);
                    talkAdapter.loadMoreComplete();
                    talkAdapter.notifyDataSetChanged();
                }else{
                    talkAdapter=new SalesTalkAdapter(mData,getActivity(),getContext());
                    mRecyclerView.setAdapter(talkAdapter);
                    talkAdapter.notifyDataSetChanged();
                    talkAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            if(type==1){
                                setSendDialog(mData.get(position).Content);
                            }else{
                                Intent mIntent = new Intent();
                                mIntent.putExtra("result",mData.get(position).Content);
                                getActivity().setResult(10, mIntent);// 设置结果，并进行传送
                                getActivity().finish();
                            }
                        }
                    });
                }
            }else{
                tv_not_data.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        }else{
            tv_not_data.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            ToastFactory.getLongToast(getContext(),"Msg:"+msg.Message);
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        talkAdapter = new SalesTalkAdapter(mData,getActivity(),getContext());
        talkAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                getListNet=new GetListNet(getContext());
                getListNet.setData(id,pageIndex);
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                talkAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        if (talkAdapter.getData().size() < Constant.PAGE_SIZE) {
            talkAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= totalSize) {
                //数据全部加载完毕
                talkAdapter.loadMoreEnd(mLoadMoreEndGone);
            } else {
                if (isErr) {
                    //成功获取更多数据
                    isLoadMore=true;
                    pageIndex+=1;
                    getListNet=new GetListNet(getContext());
                    getListNet.setData(id,pageIndex);
                } else {
                    //获取更多数据失败
                    isErr = true;
                    talkAdapter.loadMoreFail();
                }
            }
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    private Dialog sendDialog;
    private View dialogContentView;
    private EditText et_service_content;
    private LinearLayout ll_service_send;
    public void setSendDialog(String content){
        sendDialog = new Dialog(getContext(), R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sales_talk, null);
        sendDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = getContext().getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);
        et_service_content= (EditText) sendDialog.findViewById(R.id.et_service_content);
        et_service_content.setText(content);
        ll_service_send= (LinearLayout) sendDialog.findViewById(R.id.ll_service_send);
        ll_service_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.putExtra("result",et_service_content.getText().toString());
                getActivity().setResult(10, mIntent);// 设置结果，并进行传送
                getActivity().finish();
            }
        });


        sendDialog.getWindow().setGravity(Gravity.BOTTOM);
        sendDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        sendDialog.setCanceledOnTouchOutside(true);
        sendDialog.show();
    }

}
