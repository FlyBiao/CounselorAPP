package com.cesaas.android.counselor.order.member.service;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.adapter.member.FaceListAdapter;
import com.cesaas.android.counselor.order.member.adapter.member.MemberResultsAdapter;
import com.cesaas.android.counselor.order.member.adapter.service.returns.ResultsMonthAdapter;
import com.cesaas.android.counselor.order.member.bean.service.member.ResultFaceListBean;
import com.cesaas.android.counselor.order.member.bean.service.results.GetListCounselorMonthGoalBean;
import com.cesaas.android.counselor.order.member.bean.service.results.ResultAddCounselorMonthGoalBean;
import com.cesaas.android.counselor.order.member.bean.service.results.ResultGetListCounselorMonthGoalBean;
import com.cesaas.android.counselor.order.member.bean.service.results.ResultGetShopGoalBean;
import com.cesaas.android.counselor.order.member.net.results.AddCounselorMonthGoalNet;
import com.cesaas.android.counselor.order.member.net.results.GetListCounselorMonthGoalNet;
import com.cesaas.android.counselor.order.member.net.results.GetShopGoalNet;
import com.cesaas.android.counselor.order.member.net.service.FaceListNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.ui.activity.ReceiveOrderDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jianglei.view.AutoLocateHorizontalView;

import java.util.ArrayList;
import java.util.List;


/**
 * 会员业绩分配
 */
public class MemberResultsActivity extends BasesActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout llBaseBack;
    private TextView mTextViewTitle,mTextViewRightTitle;
    private TextView tv_not_data,tv_shop_name,tv_year,tv_month;
    private TextView tv_month_sales_target,tv_month_complete_sales_target,tv_month_surpass_target,tv_month_complete_surpass_target,tv_month_card_target,tv_month_complete_card_target;

    String[] ages = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
    List<String> ageList;
    private AutoLocateHorizontalView mContent;
    private ResultsMonthAdapter ageAdapter;

    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    private double salesTargetSum;
    private double suroassTargetSum;
    private int cardSum;
    private BaseDialog baseDialog;
    private int CounselorId;
    private int selectPos;

    private GetShopGoalNet shopGoalNet;
    private AddCounselorMonthGoalNet addCounselorMonthGoalNet;

    private MemberResultsAdapter resultsAdapter;
    private GetListCounselorMonthGoalNet getListCounselorMonthGoalNet;
    private List<GetListCounselorMonthGoalBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_results);
        initView();
        initData(AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"));
    }

    /**
     * 接收店员月度目标分配
     * @param msg
     */
    public void onEventMainThread(ResultAddCounselorMonthGoalBean msg) {
        if (msg.IsSuccess!=false) {
            ToastFactory.getLongToast(mContext,"店员月度目标分配成功！");
            initData(AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"));
        }else{
            ToastFactory.getLongToast(mContext,"店员月度目标分配失败:"+msg.Message);
        }
    }

    /**
     * 接收查询店铺月度目标
     * @param msg
     */
    public void onEventMainThread(ResultGetShopGoalBean msg) {
        if (msg.IsSuccess!=false) {
            if(msg.TModel!=null){
                tv_month_sales_target.setText("￥"+msg.TModel.getSalesTarget());
                tv_month_surpass_target.setText("￥"+msg.TModel.getSalesSurpass());
                tv_month_card_target.setText(msg.TModel.getCardTarget()+"");
                if(salesTargetSum!=0){
                    if(msg.TModel.getSalesTarget()>salesTargetSum){
                        tv_month_complete_sales_target.setText("-"+(msg.TModel.getSalesTarget()-salesTargetSum));
                    }else{
                        tv_month_complete_sales_target.setText("+"+(salesTargetSum-msg.TModel.getSalesTarget()));
                    }
                }else{
                    tv_month_complete_sales_target.setText("- ￥"+msg.TModel.getSalesTarget());
                }
                if(suroassTargetSum!=0){
                    if(msg.TModel.getSalesSurpass()>suroassTargetSum){
                        tv_month_complete_surpass_target.setText("- ￥"+(msg.TModel.getSalesSurpass()-suroassTargetSum));
                    }else{
                        tv_month_complete_surpass_target.setText("+ ￥"+(suroassTargetSum-msg.TModel.getSalesSurpass()));
                    }
                }else{
                    tv_month_complete_surpass_target.setText("- ￥"+msg.TModel.getSalesSurpass());
                }
                if(cardSum!=0){
                    if(msg.TModel.getCardTarget()>cardSum){
                        tv_month_complete_card_target.setText("- ￥"+(msg.TModel.getCardTarget()-cardSum));
                    }else{
                        tv_month_complete_card_target.setText("+ ￥"+(cardSum-msg.TModel.getCardTarget()));
                    }
                }else{
                    tv_month_complete_card_target.setText("- ￥"+msg.TModel.getCardTarget());
                }
            }
        }else{
            ToastFactory.getLongToast(mContext,"接收查询店铺月度目标失败:"+msg.Message);
        }
    }

    /**
     * 接收查询店员月度目标分配情况
     * @param msg
     */
    public void onEventMainThread(ResultGetListCounselorMonthGoalBean msg) {
        if (msg.IsSuccess!=false) {
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_data.setVisibility(View.GONE);
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);
                resultsAdapter=new MemberResultsAdapter(mData,mActivity,mContext);
                mRecyclerView.setAdapter(resultsAdapter);
                for (int i=0;i<msg.TModel.size();i++){
                    salesTargetSum+=msg.TModel.get(i).getSalesTarget();
                    suroassTargetSum+=msg.TModel.get(i).getSalesSurpass();
                    cardSum+=msg.TModel.get(i).getCardTarget();
                }
                baseDialog = new BaseDialog(mContext);
                baseDialog.setCancelable(false);
                mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                        baseDialog.mInitShow(mData.get(position));
                    }
                });
                shopGoalNet=new GetShopGoalNet(mContext);
                switch (selectPos){
                    case 0:
                        shopGoalNet.setData("2018-01-1 00:00:00");
                        break;
                    case 1:
                        shopGoalNet.setData("2018-02-1 00:00:00");
                        break;
                    case 2:
                        shopGoalNet.setData("2018-03-1 00:00:00");
                        break;
                    case 3:
                        shopGoalNet.setData("2018-04-1 00:00:00");
                        break;
                    case 4:
                        shopGoalNet.setData("2018-05-1 00:00:00");
                        break;
                    case 5:
                        shopGoalNet.setData("2018-06-1 00:00:00");
                        break;
                    case 6:
                        shopGoalNet.setData("2018-07-1 00:00:00");
                        break;
                    case 7:
                        shopGoalNet.setData("2018-08-1 00:00:00");
                        break;
                    case 8:
                        shopGoalNet.setData("2018-09-1 00:00:00");
                        break;
                    case 9:
                        shopGoalNet.setData("2018-10-1 00:00:00");
                        break;
                    case 10:
                        shopGoalNet.setData("2018-11-1 00:00:00");
                        break;
                    case 11:
                        shopGoalNet.setData("2018-12-1 00:00:00");
                        break;
                }
            }else{
                tv_not_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(mContext,"查询店员月度目标分配情况失败:"+msg.Message);
        }
    }

    public void initView(){
        tv_month= (TextView) findViewById(R.id.tv_month);
        tv_month.setText(Integer.parseInt(AbDateUtil.toMarch(AbDateUtil.getCurrentDate("yyyy/MM/dd HH:mm:ss")))+"月");
        tv_year= (TextView) findViewById(R.id.tv_year);
        tv_year.setText(AbDateUtil.toyear(AbDateUtil.getCurrentDate("yyyy/MM/dd HH:mm:ss"))+"年");
        tv_shop_name= (TextView) findViewById(R.id.tv_shop_name);
        tv_shop_name.setText(prefs.getString("shopName"));
        tv_month_sales_target= (TextView) findViewById(R.id.tv_month_sales_target);
        tv_month_complete_sales_target= (TextView) findViewById(R.id.tv_month_complete_sales_target);
        tv_month_surpass_target= (TextView) findViewById(R.id.tv_month_surpass_target);
        tv_month_complete_surpass_target= (TextView) findViewById(R.id.tv_month_complete_surpass_target);
        tv_month_card_target= (TextView) findViewById(R.id.tv_month_card_target);
        tv_month_complete_card_target= (TextView) findViewById(R.id.tv_month_complete_card_target);

        mContent = (AutoLocateHorizontalView) findViewById(R.id.recyleview);
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
                selectPos=pos;
                suroassTargetSum=0;
                salesTargetSum=0;
                cardSum=0;
                switch (selectPos){
                    case 0:
                        tv_month.setText("1月");
                        initData("2018-01-1 00:00:00");
                        break;
                    case 1:
                        tv_month.setText("2月");
                        initData("2018-02-1 00:00:00");
                        break;
                    case 2:
                        tv_month.setText("3月");
                        initData("2018-03-1 00:00:00");
                        break;
                    case 3:
                        tv_month.setText("4月");
                        initData("2018-04-1 00:00:00");
                        break;
                    case 4:
                        tv_month.setText("5月");
                        initData("2018-05-1 00:00:00");
                        break;
                    case 5:
                        tv_month.setText("6月");
                        initData("2018-06-1 00:00:00");
                        break;
                    case 6:
                        tv_month.setText("7月");
                        initData("2018-07-1 00:00:00");
                        break;
                    case 7:
                        tv_month.setText("8月");
                        initData("2018-08-1 00:00:00");
                        break;
                    case 8:
                        tv_month.setText("9月");
                        initData("2018-09-1 00:00:00");
                        break;
                    case 9:
                        tv_month.setText("10月");
                        initData("2018-10-1 00:00:00");
                        break;
                    case 10:
                        tv_month.setText("11月");
                        initData("2018-11-1 00:00:00");
                        break;
                    case 11:
                        tv_month.setText("12月");
                        initData("2018-12-1 00:00:00");
                        break;
                }
            }
        });

        mContent.setInitPos(Integer.parseInt(AbDateUtil.toMarch(AbDateUtil.getCurrentDate("yyyy/MM/dd HH:mm:ss")))-1);
        mContent.setAdapter(ageAdapter);

        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        mTextViewTitle.setText("分配"+AbDateUtil.toyear(AbDateUtil.getCurrentDate("yyyy/MM/dd HH:mm:ss"))+"月度目标");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        llBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    public void initData(String date){
        getListCounselorMonthGoalNet=new GetListCounselorMonthGoalNet(mContext);
        getListCounselorMonthGoalNet.setData(date);
    }

    @Override
    public void onClick(View v) {
        int index=-1;
        switch (v.getId()){

        }
    }

//    @Override
//    public void onRefresh() {
//        resultsAdapter=new MemberResultsAdapter(mData,mActivity,mContext);
//        resultsAdapter.setEnableLoadMore(false);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getListCounselorMonthGoalNet=new GetListCounselorMonthGoalNet(mContext);
//                getListCounselorMonthGoalNet.setData(AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"));
//                isErr = false;
//                mSwipeRefreshLayout.setRefreshing(false);
//                resultsAdapter.setEnableLoadMore(true);
//            }
//        }, delayMillis);
//    }

    private CircleImageView ivw_user_icon;
    private TextView tv_name;
    TextView tvCancel;
    TextView tvSure;
    TextView card_icon,tv_circle,tv_surpass_icon;
    EditText et_sales,et_surpass,et_card;
    public class BaseDialog extends Dialog implements View.OnClickListener {

        public BaseDialog(Context context) {
            this(context, R.style.dialog);
        }

        public BaseDialog(Context context, int dialog) {
            super(context, dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(R.layout.dialog_results);
            et_sales= (EditText) findViewById(R.id.et_sales);
            et_surpass= (EditText) findViewById(R.id.et_surpass);
            et_card= (EditText) findViewById(R.id.et_card);
            ivw_user_icon= (CircleImageView) findViewById(R.id.ivw_user_icon);
            tv_name= (TextView) findViewById(R.id.tv_name);
            tv_surpass_icon= (TextView) findViewById(R.id.tv_surpass_icon);
            tv_surpass_icon.setText(R.string.fa_copyright);
            tv_surpass_icon.setTypeface(App.font);
            tv_circle= (TextView) findViewById(R.id.tv_circle);
            tv_circle.setText(R.string.fa_dot_circle);
            tv_circle.setTypeface(App.font);
            card_icon= (TextView) findViewById(R.id.card_icon);
            card_icon.setText(R.string.user);
            card_icon.setTypeface(App.font);
            tvSure = (TextView) findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(this);
            tvCancel = (TextView) findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(this);

        }

        public void mInitShow(GetListCounselorMonthGoalBean bean) {
            et_surpass.setText(bean.getSalesSurpass()+"");
            et_sales.setText(bean.getSalesTarget()+"");
            et_card.setText(bean.getCardTarget()+"");
            CounselorId=bean.getCounselorId();
            tv_name.setText(bean.getName());
            if(bean.getImage()!=null && !"".equals(bean.getImage()) && !"NULL".equals(bean.getImage())){
                // 加载网络图片
                Glide.with(mContext).load(bean.getImage()).crossFade().into(ivw_user_icon);
            }else{
                ivw_user_icon.setImageResource(R.mipmap.ic_launcher);
            }
            show();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_cancel:
                    dismiss();
                    break;
                case R.id.tv_sure:
                    dismiss();
                    addCounselorMonthGoalNet=new AddCounselorMonthGoalNet(mContext);
                    addCounselorMonthGoalNet.setData(AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"),CounselorId,Double.parseDouble(et_sales.getText().toString()),Double.parseDouble(et_surpass.getText().toString()),Integer.parseInt(et_card.getText().toString()));
                    break;
            }
        }
    }

}
