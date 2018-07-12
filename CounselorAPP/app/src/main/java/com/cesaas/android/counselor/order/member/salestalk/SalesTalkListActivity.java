package com.cesaas.android.counselor.order.member.salestalk;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.salestalk.bean.GetCategoryBean;
import com.cesaas.android.counselor.order.salestalk.bean.ResultGetCategoryBean;
import com.cesaas.android.counselor.order.salestalk.bean.ResultSalesTalkCategoryListBean;
import com.cesaas.android.counselor.order.salestalk.net.GetCategoryNet;
import com.cesaas.android.counselor.order.salestalk.net.GetListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 销售话术
 * Created at 2018/6/11 14:30
 * Version 1.0
 */

public class SalesTalkListActivity extends BasesActivity implements View.OnClickListener ,BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{

    private TextView tvTitle,tvRight;
    private LinearLayout llBack;
    private ViewPager mViewPager;
    private RVPIndicator mIndicator;

    private int pageIndex=1;
    private int totalSize=0;
    private int delayMillis = 2000;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;

    private int requestCode=10;
    private int type=0;
    private int id;

    private GetCategoryNet categoryNet;
    private List<GetCategoryBean> categoryList=new ArrayList<GetCategoryBean>();
    private List<Fragment> mTabContents = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data;

    private GetListNet getListNet;
    private SalesTalkAdapter talkAdapter;
    private List<ResultSalesTalkCategoryListBean.SalesTalkCategoryListBean> mData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_talk);

        Intent intent=getIntent();
        String strType = intent.getStringExtra("Type");
        type=Integer.valueOf(strType);

        initView();
        initData();
    }

    /**
     * 接受【话术分类】
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultGetCategoryBean msg){
        if(msg.IsSuccess==true){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                categoryList=new ArrayList<>();
                categoryList.addAll(msg.TModel);

                getListNet=new GetListNet(mContext);
                getListNet.setData(categoryList.get(0).Id,pageIndex);

                for (GetCategoryBean data : categoryList) {
                    SalesSimpleFragment fragment = SalesSimpleFragment.newInstance(data.Id);
                    mTabContents.add(fragment);
                }

                mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public int getCount() {
                        return mTabContents.size();
                    }

                    @Override
                    public Fragment getItem(int position) {
                        return mTabContents.get(position);
                    }
                };

                // 设置Tab上的标题
                mIndicator.setTitleList(categoryList);
                // 设置关联的ViewPager
                mIndicator.setViewPager(mViewPager, 0);
                //设置Adapter
                mViewPager.setAdapter(mAdapter);
                // Indicator选中监听
                mIndicator.setOnIndicatorSelected(new RVPIndicator.OnIndicatorSelected() {
                    @Override
                    public void setOnIndicatorSelected(int position, String title) {
                        id=categoryList.get(position).Id;
                        getListNet=new GetListNet(mContext);
                        getListNet.setData(id,pageIndex);
                    }
                });
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取分类失败:"+msg.Message);
        }
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
                    talkAdapter=new SalesTalkAdapter(mData,mActivity,mContext);
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
                                setResult(10, mIntent);// 设置结果，并进行传送
                                finish();
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
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }




    private void initData(){
        categoryNet=new GetCategoryNet(mContext);
        categoryNet.setData();
    }

    private void initView() {
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvRight= (TextView) findViewById(R.id.tv_base_title_right);
        tvRight.setText("自定义话术");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(this);
        tvTitle.setText("选择话术");
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mIndicator = (RVPIndicator) findViewById(R.id.vp_indicator);

        mRecyclerView= (RecyclerView)findViewById(R.id.rv_list);
        mSwipeRefreshLayout= (SwipeRefreshLayout)findViewById(R.id.swipeLayout);
        tv_not_data= (TextView)findViewById(R.id.tv_not_data);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:
                Intent intent = new Intent(mContext, SalesTalkCustomListActivity.class);
                intent.putExtra("Type",String.valueOf(type));
                startActivityForResult(intent, requestCode);
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        talkAdapter = new SalesTalkAdapter(mData,mActivity,mContext);
        talkAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                getListNet=new GetListNet(mContext);
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
                    getListNet=new GetListNet(mContext);
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
    private TextView tv_copy;
    private LinearLayout ll_service_send;
    public void setSendDialog(String content){
        sendDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_sales_talk, null);
        sendDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);
        tv_copy= (TextView) sendDialog.findViewById(R.id.tv_copy);
        et_service_content= (EditText) sendDialog.findViewById(R.id.et_service_content);
        et_service_content.setText(content);
        ll_service_send= (LinearLayout) sendDialog.findViewById(R.id.ll_service_send);
        ll_service_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDialog.dismiss();
                Intent mIntent = new Intent();
                mIntent.putExtra("result",et_service_content.getText().toString());
                setResult(10, mIntent);// 设置结果，并进行传送
                finish();
            }
        });
        tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(et_service_content.getText().toString())){
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(et_service_content.getText().toString());
                    ToastFactory.getLongToast(mContext,"复制成功");
                }else{
                    ToastFactory.getLongToast(mContext,"找不到可复制的内容");
                }
            }
        });

        sendDialog.getWindow().setGravity(Gravity.BOTTOM);
        sendDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        sendDialog.setCanceledOnTouchOutside(true);
        sendDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if(data!=null){
                String result=data.getStringExtra("result");
                Intent mIntent = new Intent();
                mIntent.putExtra("result",result);
                setResult(10, mIntent);// 设置结果，并进行传送
                finish();
            }
        }
    }
}
