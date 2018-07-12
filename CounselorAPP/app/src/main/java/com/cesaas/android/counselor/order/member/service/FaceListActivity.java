package com.cesaas.android.counselor.order.member.service;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultChangeShop;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.adapter.member.AllMemberAdapter;
import com.cesaas.android.counselor.order.member.adapter.member.FaceListAdapter;
import com.cesaas.android.counselor.order.member.bean.service.member.FaceListBean;
import com.cesaas.android.counselor.order.member.bean.service.member.ResultFaceListBean;
import com.cesaas.android.counselor.order.member.net.service.FaceListNet;
import com.cesaas.android.counselor.order.member.net.service.MemberServiceListNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 人脸识别列表
 */
public class FaceListActivity extends BasesActivity implements View.OnClickListener ,SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout llBaseBack;
    private TextView mTextViewTitle,mTextViewRightTitle;
    private TextView tv_morning,tv_noon,tv_afternoon;
    private TextView tv_not_data;

    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    private ArrayList<TextView> tvs=new ArrayList<>();
    public List<FaceListBean > mData=new ArrayList<>();
    private FaceListNet net;
    private FaceListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);
        initView();
        initData();
    }

    /**
     * 接收人脸抓拍列表结果
     * @param msg
     */
    public void onEventMainThread(ResultFaceListBean msg) {
        if (msg.IsSuccess!=false) {
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_data.setVisibility(View.GONE);
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);
                adapter=new FaceListAdapter(mData,mActivity,mContext);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                        FaceListBean bean=new FaceListBean();
                        bean.setImageUrl(mData.get(position).getImageUrl());
                        bean.setImagePath(mData.get(position).getImagePath());
                        bean.setImageTime(mData.get(position).getImageTime());
                        bean.setEquipmentName(mData.get(position).getEquipmentName());
                        Intent mIntent = new Intent();
                        mIntent.putExtra("selectList",(Serializable)bean);
                        setResult(Constant.H5_FACE_BIND, mIntent);// 设置结果，并进行传送
                        finish();
                    }
                });
            }else{
                tv_not_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(mContext,"获取人脸列表失败:"+msg.Message);
        }
    }

    public void initView(){
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        tv_morning= (TextView) findViewById(R.id.tv_morning);
        tv_morning.setOnClickListener(this);
        tv_noon= (TextView) findViewById(R.id.tv_noon);
        tv_noon.setOnClickListener(this);
        tv_afternoon= (TextView) findViewById(R.id.tv_afternoon);
        tv_afternoon.setOnClickListener(this);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewTitle.setText("人脸会员列表");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        tvs.add(tv_morning);
        tvs.add(tv_noon);
        tvs.add(tv_afternoon);

        llBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    public void initData(){
        net=new FaceListNet(mContext);
        net.setData(pageIndex, AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd 11:59:59"));
    }

    @Override
    public void onClick(View v) {
        int index=-1;
        switch (v.getId()){
            case R.id.tv_morning:
                index=0;
                net=new FaceListNet(mContext);
                net.setData(pageIndex, AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd 11:59:59"));
                break;
            case R.id.tv_noon:
                index=1;
                net=new FaceListNet(mContext);
                net.setData(pageIndex, AbDateUtil.getCurrentDate("yyyy-MM-dd 11:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd 15:59:59"));
                break;
            case R.id.tv_afternoon:
                index=2;
                net=new FaceListNet(mContext);
                net.setData(pageIndex, AbDateUtil.getCurrentDate("yyyy-MM-dd 15:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"));
                break;
        }
        setColor(index);
    }
    private void setColor(int index) {
        for(int i=0;i<tvs.size();i++){
            tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.new_menu_text_color));
            tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.day_sign_content_text_white_30));
        }
        tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.white));
        tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_purple_bg));
    }

    @Override
    public void onRefresh() {
        adapter = new FaceListAdapter(mData,mActivity,mContext);
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                net=new FaceListNet(mContext);
                net.setData(pageIndex,"","");
                isErr = false;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }
}
