package com.cesaas.android.counselor.order.inventory.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.adapter.GetStyleNoByPidAdapter;
import com.cesaas.android.java.bean.GetStyleNoByPidBean;
import com.cesaas.android.java.bean.inventory.InventoryGetDiffSubListBean;
import com.cesaas.android.java.bean.inventory.ResultGetStyleNoByPidBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryConfirmDifferenceBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryCreateDifferenceReqBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryGetDiffSubListBean;
import com.cesaas.android.java.net.GetStyleNoByPidNet;
import com.cesaas.android.java.net.inventory.InventoryConfirmDifferenceNet;
import com.cesaas.android.java.net.inventory.InventoryCreateDifferenceNet;
import com.cesaas.android.java.net.inventory.InventoryGetDiffSubListNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * 查看盘点差异
 */
public class CheckInventoryDifferenceActivity extends BasesActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    private TextView tvTitle,tv_not_data,tv_sum,tv_inventoryCreateDifference,tv_inventoryConfirmDifference;
    private LinearLayout llBack,ll;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;

    private String pid;
    private int Status;

    private InventoryGetDiffSubListNet inventoryGetDiffSubListNet;
    private GetStyleNoByPidNet getStyleNoByPidNet;

    private List<GetStyleNoByPidBean> mData=new ArrayList<>();
    private List<InventoryGetDiffSubListBean> mData2=new ArrayList<>();
    private CheckInventoryDifferenceAdapter checkInventoryDifferenceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_inventory_difference);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            pid=bundle.getString("pid");
            Status=bundle.getInt("Status");
        }

        initView();
        initData();

        if(Status==10){
            ll.setVisibility(View.VISIBLE);
        }else{
            ll.setVisibility(View.GONE);
        }
    }


    /**
     * 接收确认差异盘点单
     * @param msg
     */
    public void onEventMainThread(ResultInventoryConfirmDifferenceBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "确认差异成功");
                finish();
            } else {
                ToastFactory.getLongToast(mContext, "error:" + msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收重新生成盘点单差异
     * @param msg
     */
    public void onEventMainThread(ResultInventoryCreateDifferenceReqBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "重新生成差异成功");
                finish();
            } else {
                ToastFactory.getLongToast(mContext, "error:" + msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收查看差异列表
     * @param msg
     */
    public void onEventMainThread(ResultInventoryGetDiffSubListBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    mData2 = new ArrayList<>();
                    mData2.addAll(msg.arguments.resp.records.value);

                    checkInventoryDifferenceAdapter = new CheckInventoryDifferenceAdapter(mData, mData2, mActivity, mContext);
                    recyclerView.setAdapter(checkInventoryDifferenceAdapter);
                } else {
                    tv_not_data.setVisibility(View.VISIBLE);
                }
            } else {
                tv_not_data.setVisibility(View.VISIBLE);
                ToastFactory.getLongToast(mContext, msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收根据pid获取款号列表
     * @param
     */
    public void onEventMainThread(ResultGetStyleNoByPidBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.StyleNoList.value != null && msg.arguments.resp.StyleNoList.value.size() != 0) {
                    tv_not_data.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    tv_sum.setText(String.valueOf(msg.arguments.resp.totalCount));
                    mData = new ArrayList<>();
                    mData.addAll(msg.arguments.resp.StyleNoList.value);
                    checkInventoryDifferenceAdapter = new CheckInventoryDifferenceAdapter(mData, mActivity, mContext);
                    recyclerView.setAdapter(checkInventoryDifferenceAdapter);
                }else{
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

    private void initData(){
        getStyleNoByPidNet=new GetStyleNoByPidNet(mContext);
        getStyleNoByPidNet.setData(1,Long.parseLong(pid));
    }

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
                Skip.mNextFroData(mActivity,CreateInventoryActivity.class,bundle);
                break;
            case R.id.tv_inventoryConfirmDifference://确认差异
                InventoryConfirmDifferenceNet confirmDifferenceNet=new InventoryConfirmDifferenceNet(mContext);
                confirmDifferenceNet.setData(Long.parseLong(pid));
                break;
            case R.id.tv_inventoryCreateDifference://重新生成差异
                InventoryCreateDifferenceNet createDifferenceNet=new InventoryCreateDifferenceNet(mContext);
                createDifferenceNet.setData(Long.parseLong(pid));
                break;
        }
    }

    private void initView(){
        tv_inventoryConfirmDifference= (TextView) findViewById(R.id.tv_inventoryConfirmDifference);
        tv_inventoryConfirmDifference.setOnClickListener(this);
        tv_inventoryCreateDifference= (TextView) findViewById(R.id.tv_inventoryCreateDifference);
        tv_inventoryCreateDifference.setOnClickListener(this);
        tv_sum= (TextView) findViewById(R.id.tv_sum);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        ll= (LinearLayout) findViewById(R.id.ll);
        llBack.setOnClickListener(this);

        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("盘点单差异");

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        checkInventoryDifferenceAdapter=new CheckInventoryDifferenceAdapter(mData,mActivity,mContext);
        checkInventoryDifferenceAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                swipeLayout.setRefreshing(false);
                checkInventoryDifferenceAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }




    private GetStyleNoByPidAdapter getStyleNoByPidAdapter;
    private RecyclerView rv_list;
    private String styleNo;
    private boolean isShow=false;
    /**
     * Author FGB
     * Description 查看盘点差异
     * Created at 2017/8/28 9:51
     * Version 1.0
     */
    public class CheckInventoryDifferenceAdapter extends BaseQuickAdapter<GetStyleNoByPidBean, BaseViewHolder> {

        private List<GetStyleNoByPidBean> mData;
        private List<InventoryGetDiffSubListBean> mData2=new ArrayList<>();
        private Context mContext;
        private Activity mActivity;

        public CheckInventoryDifferenceAdapter(List<GetStyleNoByPidBean> mData, Activity activity, Context ct) {
            super( R.layout.item_check_difference,mData);
            this.mData=mData;
            this.mActivity=activity;
            this.mContext=ct;
        }

        public CheckInventoryDifferenceAdapter(List<GetStyleNoByPidBean> mData, List<InventoryGetDiffSubListBean> mData2,Activity activity, Context ct) {
            super( R.layout.item_check_difference,mData);
            this.mData=mData;
            this.mData2=mData2;
            this.mActivity=activity;
            this.mContext=ct;
        }

        @Override
        protected void convert(final BaseViewHolder helper, final GetStyleNoByPidBean item) {
            rv_list=helper.getView(R.id.rv_list);
            rv_list.setLayoutManager(new LinearLayoutManager(mContext));
            helper.setText(R.id.tv_show_info,R.string.fa_sort_desc);
            helper.setTypeface(R.id.tv_show_info, App.font);

            helper.setText(R.id.tv_stylyNo,item.getStylyNo());
            helper.setText(R.id.tv_title,item.getTitle());
            helper.setText(R.id.tv_inventoryNum,String.valueOf(item.getInventoryNum()));
            helper.setText(R.id.tv_differenceNum,String.valueOf(item.getDifferenceNum()));

            if(item.getImageUrl()!=null && !"".equals(item.getImageUrl()) && !"NULL".equals(item.getImageUrl())){
                // 加载网络图片
                Glide.with(mContext).load(item.getImageUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_img));
            }else{
                helper.setImageResource(R.id.iv_img,R.mipmap.default_image);
            }
            if(styleNo!=null && !"".equals(styleNo)){
                if(styleNo.equals(item.getStylyNo())){
                    helper.setVisible(R.id.ll_show,true);
                    getStyleNoByPidAdapter=new GetStyleNoByPidAdapter(mData2,mActivity,mContext);
                    rv_list.setAdapter(getStyleNoByPidAdapter);
                }
            }

            helper.setOnClickListener(R.id.ll_show_info, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isShow==false){
                        isShow=true;
                        helper.setVisible(R.id.ll_show,true);
                        helper.setText(R.id.tv_show_info,R.string.fa_sort_up);
                        helper.setTypeface(R.id.tv_show_info, App.font);

                        styleNo=item.getStylyNo();
                        inventoryGetDiffSubListNet=new InventoryGetDiffSubListNet(mContext);
                        inventoryGetDiffSubListNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionSelectPidAndNo(item.getStylyNo(),Long.parseLong(pid)));
                    }else{
                        isShow=false;
                        helper.setVisible(R.id.ll_show,false);
                        helper.setText(R.id.tv_show_info,R.string.fa_sort_desc);
                        helper.setTypeface(R.id.tv_show_info, App.font);
                    }
                }
            });

        }

    }
}
