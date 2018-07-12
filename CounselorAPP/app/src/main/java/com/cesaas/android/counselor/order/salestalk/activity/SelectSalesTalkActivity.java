package com.cesaas.android.counselor.order.salestalk.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.popupwindow.TopMiddlePopup;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.salestalk.adapter.SelectSalesTalkAdapter;
import com.cesaas.android.counselor.order.salestalk.bean.GetCategoryBean;
import com.cesaas.android.counselor.order.salestalk.bean.ResultCustomListBean;
import com.cesaas.android.counselor.order.salestalk.bean.ResultGetCategoryBean;
import com.cesaas.android.counselor.order.salestalk.bean.ResultSalesTalkCategoryListBean;
import com.cesaas.android.counselor.order.salestalk.net.CustomAddNet;
import com.cesaas.android.counselor.order.salestalk.net.GetCategoryNet;
import com.cesaas.android.counselor.order.salestalk.net.GetCustomListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择销售话术
 */
public class SelectSalesTalkActivity extends BasesActivity implements View.OnClickListener{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private LinearLayout llBaseBack,ll_top_win;
    private TextView tvBaseTitle;
    private ImageView iv_bottom_arrow_screen;

    private int resultCode = 200;
    private int pageIndex=1;
    private List<ResultCustomListBean.CustomListBean> customListBeanList;
    private SelectSalesTalkAdapter mTalkAdapter;
    private GetCustomListNet mGetCustomListNet;

    private List<GetCategoryBean> getItemsName;
    private GetCategoryNet mGetCategoryNet;

    private TopMiddlePopup middlePopup;
    public static int screenW, screenH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sales_talk);

        mGetCategoryNet=new GetCategoryNet(mContext);
        mGetCategoryNet.setData();

        mGetCustomListNet=new GetCustomListNet(mContext);
        mGetCustomListNet.setData(pageIndex);

        initView();

        getScreenPixels();

    }

    /**
     * 接受EventBus发送消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultGetCategoryBean msg){
        if(msg.IsSuccess==true){
            if(msg.TModel!=null){
                getItemsName=new ArrayList<>();
                GetCategoryBean bean=new GetCategoryBean();
                bean.Content="全部";
                getItemsName.add(bean);
                getItemsName.addAll(msg.TModel);
            }
        }else{
            ToastFactory.getLongToast(mContext,""+msg.Message);
        }
    }

    /**
     * 接受EventBus发送消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(GetCategoryBean msg){
        ToastFactory.getLongToast(mContext,msg.Id+"=="+msg.Content);
    }


    /**
     * 接受EventBus发送消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultCustomListBean msg){
        if(msg.IsSuccess==true){
            customListBeanList=new ArrayList<>();
            customListBeanList.addAll(msg.TModel);
            mTalkAdapter=new SelectSalesTalkAdapter(customListBeanList);
            mTalkAdapter.setOnItemClickListener(onItemClickListener);
            mSwipeMenuRecyclerView.setAdapter(mTalkAdapter);

        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败！"+msg.Message);
        }
    }

    private void initView() {

        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView= (SwipeMenuRecyclerView) findViewById(R.id.recycler_sales_talk_view);
        iv_bottom_arrow_screen= (ImageView) findViewById(R.id.iv_bottom_arrow_screen);

        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);


        ll_top_win= (LinearLayout) findViewById(R.id.ll_top_win);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("选择话术");

        llBaseBack.setOnClickListener(this);
        iv_bottom_arrow_screen.setOnClickListener(this);
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Intent mIntent = new Intent();
            mIntent.putExtra("talkContent", customListBeanList.get(position).Content);
            // 设置结果，并进行传送
            setResult(resultCode, mIntent);
             finish();
        }
    };

    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSwipeMenuRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //重新加载数据
                    mGetCustomListNet.setData(pageIndex);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    /**
     * 获取屏幕的宽和高
     */
    public void getScreenPixels() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenW = metrics.widthPixels;
        screenH = metrics.heightPixels;
    }


    /**
     * 设置弹窗
     *
     * @param type
     */
    private void setPopup(int type) {
        middlePopup = new TopMiddlePopup(mContext, screenW, screenH, getItemsName, type);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;
            case R.id.iv_bottom_arrow_screen://下拉筛选
                setPopup(0);
                middlePopup.show(ll_top_win);
                break;
        }
    }


}
