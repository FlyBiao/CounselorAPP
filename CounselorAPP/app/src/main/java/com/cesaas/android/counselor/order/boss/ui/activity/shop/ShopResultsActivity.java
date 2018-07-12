package com.cesaas.android.counselor.order.boss.ui.activity.shop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.adapter.shop.GetShopMonthInfoAdapter;
import com.cesaas.android.counselor.order.boss.bean.GetShopMonthInfoBean;
import com.cesaas.android.counselor.order.boss.bean.ResultGetShopMonthInfoBean;
import com.cesaas.android.counselor.order.boss.bean.ResultGetShopMonthSumBean;
import com.cesaas.android.counselor.order.boss.bean.SelectShopListBean;
import com.cesaas.android.counselor.order.boss.net.GetShopMonthInfoNet;
import com.cesaas.android.counselor.order.boss.net.GetShopMonthSumNet;
import com.cesaas.android.counselor.order.boss.ui.activity.QueryShopActivity;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.adapter.service.returns.ResultsMonthAdapter;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.jianglei.view.AutoLocateHorizontalView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * 店铺业绩分配
 */
public class ShopResultsActivity extends BasesActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout llBaseBack;
    private TextView mTextViewTitle,mTextViewRightTitle;
    private TextView tv_not_data;
    private TextView tv_shop_num,tv_shop_average,tv_area_average,tv_counselor_average,tv_goal_sum,tv_sale_sum,tv_rate;
    private LinearLayout ll_card,tv_SaleReach,ll_goal;

    String[] ages = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
    List<String> ageList;
    private AutoLocateHorizontalView mContent;
    private ResultsMonthAdapter ageAdapter;

    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    private int month=1;
    private boolean isGoal=false;
    private boolean isSaleReach=false;
    private boolean isCard=false;

    private GetShopMonthSumNet getShopMonthSumNet;
    private GetShopMonthInfoNet getShopMonthInfoNet;
    private List<GetShopMonthInfoBean> mData=new ArrayList<>();
    private List<GetShopMonthInfoBean> mDatas=new ArrayList<>();
    private GetShopMonthInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_results);
        initView();
        initData();
    }

    public void onEventMainThread(ResultGetShopMonthSumBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && !"".equals(msg.TModel)){
                tv_shop_num.setText(String.valueOf(msg.TModel.getShopNum()));
                tv_shop_average.setText("￥"+msg.TModel.getShopAverage());
                tv_area_average.setText("￥"+msg.TModel.getAreaAverage());
                tv_counselor_average.setText("￥"+msg.TModel.getCounselorAverage());
                tv_goal_sum.setText("￥"+msg.TModel.getGoalSum());
                tv_sale_sum.setText("￥"+msg.TModel.getSaleSum());
                tv_rate.setText("￥"+msg.TModel.getRate());
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取店铺总目标失败："+msg.Message);
        }
    }

    public void onEventMainThread(ResultGetShopMonthInfoBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_data.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

                mData=new ArrayList<>();
                mDatas=new ArrayList<>();
                mData.addAll(msg.TModel);
                mDatas.addAll(msg.TModel);

                adapter=new GetShopMonthInfoAdapter(mData,mActivity,mContext);
                mRecyclerView.setAdapter(adapter);

            }else{
                tv_not_data.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        }else{
            tv_not_data.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            ToastFactory.getLongToast(mContext,"获取列表失败："+msg.Message);
        }
    }


    public void initView(){

        ll_goal= (LinearLayout) findViewById(R.id.ll_goal);
        ll_goal.setOnClickListener(this);
        tv_SaleReach= (LinearLayout) findViewById(R.id.tv_SaleReach);
        tv_SaleReach.setOnClickListener(this);
        ll_card= (LinearLayout) findViewById(R.id.ll_card);
        ll_card.setOnClickListener(this);
        tv_shop_num= (TextView) findViewById(R.id.tv_shop_num);
        tv_shop_average= (TextView) findViewById(R.id.tv_shop_average);
        tv_area_average= (TextView) findViewById(R.id.tv_area_average);
        tv_counselor_average= (TextView) findViewById(R.id.tv_counselor_average);
        tv_goal_sum= (TextView) findViewById(R.id.tv_goal_sum);
        tv_sale_sum= (TextView) findViewById(R.id.tv_sale_sum);
        tv_rate= (TextView) findViewById(R.id.tv_rate);
        mContent = (AutoLocateHorizontalView) findViewById(R.id.recyleview);

        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewRightTitle.setVisibility(View.VISIBLE);
        mTextViewRightTitle.setText(R.string.fa_glass);
        mTextViewRightTitle.setTypeface(App.font);
        mTextViewRightTitle.setOnClickListener(this);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        mTextViewTitle.setText("业绩分配报表");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        llBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    public void initData(){
        ageList = new ArrayList<>();
        for (String age : ages) {
            ageList.add(age);
        }
        ageAdapter = new ResultsMonthAdapter(this, ageList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mContent.setLayoutManager(linearLayoutManager);
        mContent.setOnSelectedPositionChangedListener(new AutoLocateHorizontalView.OnSelectedPositionChangedListener() {
            @Override
            public void selectedPositionChanged(int pos) {
                month=pos+1;
                switch (pos){
                    case 0:
                        getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                        getShopMonthSumNet.setData("2018","1",0);
                        getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                        getShopMonthInfoNet.setData("2018","1",0);
                        break;
                    case 1:
                        getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                        getShopMonthSumNet.setData("2018","2",0);
                        getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                        getShopMonthInfoNet.setData("2018","2",0);
                        break;
                    case 2:
                        getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                        getShopMonthSumNet.setData("2018","3",0);
                        getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                        getShopMonthInfoNet.setData("2018","3",0);
                        break;
                    case 3:
                        getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                        getShopMonthSumNet.setData("2018","4",0);
                        getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                        getShopMonthInfoNet.setData("2018","4",0);
                        break;
                    case 4:
                        getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                        getShopMonthSumNet.setData("2018","5",0);
                        getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                        getShopMonthInfoNet.setData("2018","5",0);
                        break;
                    case 5:
                        getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                        getShopMonthSumNet.setData("2018","6",0);
                        getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                        getShopMonthInfoNet.setData("2018","6",0);
                        break;
                    case 6:
                        getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                        getShopMonthSumNet.setData("2018","7",0);
                        getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                        getShopMonthInfoNet.setData("2018","8",0);
                        break;
                    case 7:
                        getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                        getShopMonthSumNet.setData("2018","8",0);
                        getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                        getShopMonthInfoNet.setData("2018","8",0);
                        break;
                    case 8:
                        getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                        getShopMonthSumNet.setData("2018","9",0);
                        getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                        getShopMonthInfoNet.setData("2018","9",0);
                        break;
                    case 9:
                        getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                        getShopMonthSumNet.setData("2018","10",0);
                        getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                        getShopMonthInfoNet.setData("2018","10",0);
                        break;
                    case 10:
                        getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                        getShopMonthSumNet.setData("2018","11",0);
                        getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                        getShopMonthInfoNet.setData("2018","11",0);
                        break;
                    case 11:
                        getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                        getShopMonthSumNet.setData("2018","12",0);
                        getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                        getShopMonthInfoNet.setData("2018","12",0);
                        break;
                }
            }
        });
        mContent.setInitPos(Integer.parseInt(AbDateUtil.toMarch(AbDateUtil.getCurrentDate("yyyy/MM/dd HH:mm:ss")))-1);
        mContent.setAdapter(ageAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_base_title_right:
                Intent intentShop= new Intent(mContext, QueryShopActivity.class);
                intentShop.putExtra("LeftTitle","返回");
                mActivity.startActivityForResult(intentShop,201);
                break;
            case R.id.ll_goal:
                if(isGoal==false){
                    isGoal=true;
                    Collections.sort(mData, new Comparator<GetShopMonthInfoBean>() {
                        public int compare(GetShopMonthInfoBean user1, GetShopMonthInfoBean user2) {
                            Integer completion1 =Integer.parseInt(user1.getGoal());
                            Integer completion2 =Integer.parseInt(user2.getGoal());
                            return completion2.compareTo(completion1);
                        }
                    });
                    adapter=new GetShopMonthInfoAdapter(mData,mActivity,mContext);
                    mRecyclerView.setAdapter(adapter);
                }else{
                    isGoal=false;
                    adapter=new GetShopMonthInfoAdapter(mDatas,mActivity,mContext);
                    mRecyclerView.setAdapter(adapter);
                }
                break;
            case R.id.ll_card:
                if(isCard==false){
                    isCard=true;
                    Collections.sort(mData, new Comparator<GetShopMonthInfoBean>() {
                        public int compare(GetShopMonthInfoBean user1, GetShopMonthInfoBean user2) {
                            Integer completion1 = (int)user1.getCardNum();
                            Integer completion2 = (int)user2.getCardNum();
                            return completion2.compareTo(completion1);
                        }
                    });
                    adapter=new GetShopMonthInfoAdapter(mData,mActivity,mContext);
                    mRecyclerView.setAdapter(adapter);
                }else{
                    isCard=false;
                    adapter=new GetShopMonthInfoAdapter(mDatas,mActivity,mContext);
                    mRecyclerView.setAdapter(adapter);
                }
                break;
            case R.id.tv_SaleReach:
                if(isSaleReach==false){
                    isSaleReach=true;
                    Collections.sort(mData, new Comparator<GetShopMonthInfoBean>() {
                        public int compare(GetShopMonthInfoBean user1, GetShopMonthInfoBean user2) {
                            Integer completion1 = Integer.parseInt(user1.getSaleReach());
                            Integer completion2 =Integer.parseInt(user2.getSaleReach());
                            return completion2.compareTo(completion1);
                        }
                    });
                    adapter=new GetShopMonthInfoAdapter(mData,mActivity,mContext);
                    mRecyclerView.setAdapter(adapter);
                }else{
                    isSaleReach=false;
                    adapter=new GetShopMonthInfoAdapter(mDatas,mActivity,mContext);
                    mRecyclerView.setAdapter(adapter);
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        adapter=new GetShopMonthInfoAdapter(mData,mActivity,mContext);
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                getShopMonthSumNet.setData("2018",String.valueOf(month),0);
                getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                getShopMonthInfoNet.setData("2018",String.valueOf(month),0);
                isErr = false;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==201){
            if(data!=null){
                List<SelectShopListBean> shopListBeanList=(ArrayList<SelectShopListBean>)data.getSerializableExtra("selectList");
                JSONArray shopIds=new JSONArray();
                for (int i=0;i<shopListBeanList.size();i++){
                    shopIds.put(shopListBeanList.get(i).getShopId());
                }
                getShopMonthSumNet=new GetShopMonthSumNet(mContext);
                getShopMonthSumNet.setData("2018",String.valueOf(month),0,shopIds);
                getShopMonthInfoNet=new GetShopMonthInfoNet(mContext);
                getShopMonthInfoNet.setData("2018",String.valueOf(month),0,shopIds);
            }
        }
    }


}
