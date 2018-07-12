package com.cesaas.android.counselor.order.shopmange;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.bean.SelectShopListBean;
import com.cesaas.android.counselor.order.boss.ui.activity.QueryShopActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.member.DateScreenActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.member.DateScreeningActivity;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shopmange.adapter.ClerkRankingAdapter;
import com.cesaas.android.counselor.order.shopmange.bean.ClerkRankingBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultClerkRankingBean;
import com.cesaas.android.counselor.order.shopmange.net.CounselorSaleListNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 店员排名
 */
public class ClerkRankingActivity extends BasesActivity implements View.OnClickListener ,BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tvTitle,mTextViewRightTitle;
    private TextView tv_screening,tv_not_data;
    private TextView tv_all,tv_bill,tv_clerk_count;
    private TextView tv_start_sel_date,tv_end_sel_date;
    private LinearLayout back,ll_screening;

    private int switchType=0;

    private ArrayList<TextView> tvs=new ArrayList<TextView>();
    private List<ClerkRankingBean> mData=new ArrayList<>();
    private JSONArray shopIds=new JSONArray();
    private ClerkRankingAdapter adapter;
    private CounselorSaleListNet net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_ranking);

        initView();
        initData();
    }

    public void onEventMainThread(ResultClerkRankingBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_clerk_count.setText(String.valueOf(msg.TModel.size()));
                tv_not_data.setVisibility(View.GONE);
                mData=new ArrayList<>();
                for (int i=0;i<msg.TModel.size();i++){
                    ClerkRankingBean cr=new ClerkRankingBean();
                    cr.setShopId(msg.TModel.get(i).getShopId());
                    cr.setActualSalePayment(msg.TModel.get(i).getActualSalePayment());
                    cr.setCity(msg.TModel.get(i).getCity());
                    cr.setCounnselorId(msg.TModel.get(i).getCounnselorId());
                    cr.setCounselorName(msg.TModel.get(i).getCounselorName());
                    cr.setPayMent(msg.TModel.get(i).getPayMent());
                    cr.setDiscount(msg.TModel.get(i).getDiscount());
                    cr.setQuantity(msg.TModel.get(i).getQuantity());
                    cr.setSaleGoal(msg.TModel.get(i).getSaleGoal());
                    cr.setShopName(msg.TModel.get(i).getShopName());
                    if (msg.TModel.get(i).getActualSalePayment()!=0){
                        double relative=msg.TModel.get(i).getActualSalePayment()/msg.TModel.get(i).getPayMent()*100;
                        cr.setCompletion(Double.parseDouble(DecimalFormatUtils.decimalToFormats(relative)));
                    }else{
                        cr.setCompletion(0);
                    }
                    mData.add(cr);
                }
                showSwitch(switchType);
            }else{
                tv_not_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_data.setVisibility(View.VISIBLE);
        }
    }

    public void initData() {
        net=new CounselorSaleListNet(mContext);
        net.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds);
    }

    /**
     * 初始化视图控件
     */
    public void initView(){
        tv_clerk_count= (TextView) findViewById(R.id.tv_clerk_count);
        tv_start_sel_date= (TextView) findViewById(R.id.tv_start_sel_date);
        tv_start_sel_date.setText(AbDateUtil.getDateYMDs(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00")));
        tv_end_sel_date= (TextView) findViewById(R.id.tv_end_sel_date);
        tv_end_sel_date.setText(AbDateUtil.getDateYMDs(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59")));
        ll_screening= (LinearLayout) findViewById(R.id.ll_screening);
        tv_screening= (TextView) findViewById(R.id.tv_screening);
        tv_screening.setText(R.string.fa_glass);
        tv_screening.setTypeface(App.font);
        back= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("店员排名");
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewRightTitle.setVisibility(View.VISIBLE);
        mTextViewRightTitle.setText(R.string.fa_th_large);
        mTextViewRightTitle.setTypeface(App.font);
        mTextViewRightTitle.setTextSize(16);
        tv_all= (TextView) findViewById(R.id.tv_all);
        tv_all.setOnClickListener(this);
        tv_bill= (TextView) findViewById(R.id.tv_bill);
        tv_bill.setOnClickListener(this);
        tvs.add(tv_all);
        tvs.add(tv_bill);
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_retail_order_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mTextViewRightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentShop= new Intent(mContext, QueryShopActivity.class);
                intentShop.putExtra("LeftTitle","返回");
                mActivity.startActivityForResult(intentShop,201);
            }
        });
        ll_screening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tagIntent = new Intent(mContext, DateScreenActivity.class);
                startActivityForResult(tagIntent, Constant.H5_TAG_SELECT);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int index=-1;
        switch (v.getId()){
            case R.id.tv_all://按金额
                index=0;
                switchType=index;
                showSwitch(switchType);
                break;
            case R.id.tv_bill://按完成率
                index=1;
                switchType=index;
                showSwitch(switchType);
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                net=new CounselorSaleListNet(mContext);
                net.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==901){//标签筛选
            if(data!=null){
                String startDate=data.getStringExtra("StartDate");
                String endDate=data.getStringExtra("EndDate");
                tv_start_sel_date.setText(startDate);
                tv_end_sel_date.setText(endDate);
                net=new CounselorSaleListNet(mContext);
                net.setData(startDate+" 00:00:00",endDate+" 23:59:59",shopIds);
            }
        }
        if(requestCode==201){
            if(data!=null){
                List<SelectShopListBean> shopListBeanList=(ArrayList<SelectShopListBean>)data.getSerializableExtra("selectList");
                shopIds=new JSONArray();
                for (int i=0;i<shopListBeanList.size();i++){
                    shopIds.put(shopListBeanList.get(i).getShopId());
                }
                net=new CounselorSaleListNet(mContext);
                net.setData(tv_start_sel_date.getText().toString()+" 00:00:00",tv_end_sel_date.getText()+" 23:59:59",shopIds);
            }
        }
    }


    public void showSwitch(int switchType){
        if(switchType==0){
            //按金额从大到小排序
            Collections.sort(mData, new Comparator<ClerkRankingBean>() {
                public int compare(ClerkRankingBean user1, ClerkRankingBean user2) {
                    Integer payment1 = (int)user1.getPayMent();
                    Integer payment2 = (int)user2.getPayMent();
                    return payment2.compareTo(payment1);
                }
            });
            adapter=new ClerkRankingAdapter(mData,mActivity,mContext);
            mRecyclerView.setAdapter(adapter);
        }else{
            //按完成率从大到小排序
            Collections.sort(mData, new Comparator<ClerkRankingBean>() {
                public int compare(ClerkRankingBean user1, ClerkRankingBean user2) {
                    Integer completion1 = (int)user1.getCompletion();
                    Integer completion2 = (int)user2.getCompletion();
                    return completion2.compareTo(completion1);
                }
            });
            adapter=new ClerkRankingAdapter(mData,mActivity,mContext);
            mRecyclerView.setAdapter(adapter);
        }
    }
}
