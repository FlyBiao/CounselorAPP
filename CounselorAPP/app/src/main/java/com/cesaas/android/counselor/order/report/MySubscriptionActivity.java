package com.cesaas.android.counselor.order.report;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.autoCompletetextview.AutoCompleteTextView;
import com.cesaas.android.counselor.order.cache.CacheManager;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.report.adapter.MySubscriptionAdapter;
import com.cesaas.android.counselor.order.report.bean.MySubscriptionBean;
import com.cesaas.android.counselor.order.report.bean.ResultCancelSubscibeBean;
import com.cesaas.android.counselor.order.report.bean.ResultMySubscriptionBean;
import com.cesaas.android.counselor.order.report.bean.ResultReportCenterBean;
import com.cesaas.android.counselor.order.report.bean.ResultSubscibeBean;
import com.cesaas.android.counselor.order.report.bean.SubcriptionCacheJsonBean;
import com.cesaas.android.counselor.order.report.net.CancelSubscibeNet;
import com.cesaas.android.counselor.order.report.net.GetMyReportListNet;
import com.cesaas.android.counselor.order.report.net.GetReportListNet;
import com.cesaas.android.counselor.order.report.net.SubscibeNet;
import com.cesaas.android.counselor.order.statistics.bean.CacheJsonBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.wx.goodview.GoodView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * 我的订阅【报表】
 */
public class MySubscriptionActivity extends BasesActivity implements View.OnClickListener{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private LinearLayout llBack;
    private TextView tvBaseTitle;
    private static ImageView bookmark_checked;//订阅

    private AutoCompleteTextView complTextView;
    private CacheManager mCacheManager;
    private String subcriptionCacheJson;
    private List<String> complTextViewList;

    private MySubscriptionAdapter mMenuAdapter;
    private List<MySubscriptionBean> subscriptionBeanList;
    private int size=20;
    private int PageIndex=1;
    private boolean isSubscription=false;

    private GoodView mGoodView;

    private GetMyReportListNet getMyReportListNet;
    private CancelSubscibeNet subscibeNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscription);

        getMyReportListNet=new GetMyReportListNet(mContext);
        getMyReportListNet.setData(PageIndex,"");

        mGoodView=new GoodView(this);

        initView();
//        init();
        setOnPopupItemClickListener();
    }

    /**
     * 初始化
     */
    private void init(){
        //读取缓存
        mCacheManager=CacheManager.getInstance(this);
        subcriptionCacheJson=mCacheManager.readCache("subcriptionCacheJson");
        if(subcriptionCacheJson!=null){//从缓存获取
            //添加店铺列表
            ResultMySubscriptionBean bean=gson.fromJson(subcriptionCacheJson,ResultMySubscriptionBean.class);
            subscriptionBeanList = new ArrayList<>();
            subscriptionBeanList.addAll(bean.TModel);
            initData(subscriptionBeanList);

            //添加查询列表
            complTextViewList=new ArrayList<String>();
            for (int i=0; i<subscriptionBeanList.size(); i++){
                complTextViewList.add(subscriptionBeanList.get(i).getTitle());
            }
            complTextView.setDatas(complTextViewList);

        }else{//从网络获取
            getMyReportListNet=new GetMyReportListNet(mContext);
            getMyReportListNet.setData(PageIndex,"");
        }
    }


    /**
     * 设置setOnPopupItemClickListener
     */
    private void setOnPopupItemClickListener(){
        complTextView.setOnPopupItemClickListener(new AutoCompleteTextView.OnPopupItemClickListener() {
            @Override
            public void onPopupItemClick(CharSequence charSequence) {
                getMyReportListNet=new GetMyReportListNet(mContext);
                getMyReportListNet.setData(PageIndex,charSequence.toString());
            }
        });
    }


    /**
     * 接收获取报表Json字符串
     * @param msg 消息实体类
     */
    public void onEventMainThread(SubcriptionCacheJsonBean msg) {
        mCacheManager.writeCache("subcriptionCacheJson",msg.getStrJson());
    }

    /**
     * 接收报表列表消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultMySubscriptionBean msg){
        if(msg.IsSuccess==true){
            subscriptionBeanList=new ArrayList<>();
            subscriptionBeanList.addAll(msg.TModel);
            initData(subscriptionBeanList);

        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败！"+msg.Message);
        }
    }


    /**
     *
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultCancelSubscibeBean msg){
        if(msg.IsSuccess==true){
            if(isSubscription==true){
                bookmark_checked.setImageResource(R.mipmap.bookmark_checked);
                mGoodView.setTextInfo("订阅成功", Color.parseColor("#ff941A"), 12);
                mGoodView.show(bookmark_checked);
            }
            else{
                bookmark_checked.setImageResource(R.mipmap.bookmark);
                mGoodView.setTextInfo("已取消订阅", Color.parseColor("#ff941A"), 12);
                mGoodView.show(bookmark_checked);

                //重新加载数据
                getMyReportListNet=new GetMyReportListNet(mContext);
                getMyReportListNet.setData(PageIndex,"");
            }

        }else{
            ToastFactory.getLongToast(mContext,"订阅失败！"+msg.Message);
        }
    }


    private void initData(List<MySubscriptionBean> subscriptionBeanList) {
        mMenuAdapter=new MySubscriptionAdapter(subscriptionBeanList);
        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);

        //添加查询列表
        complTextViewList=new ArrayList<String>();
        for (int i=0; i<subscriptionBeanList.size(); i++){
            complTextViewList.add(subscriptionBeanList.get(i).getTitle());
        }
        complTextView.setDatas(complTextViewList);

    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_subscription_view);

        complTextView= (AutoCompleteTextView) findViewById(R.id.tvAutoCompl);
        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);

        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        llBack.setOnClickListener(this);

        tvBaseTitle.setText("我的订阅");
    }

    /**
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {// 手指不能向上滑动了
                // TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。

                Toast.makeText(MySubscriptionActivity.this, "滑到最底部了，加载更多数据！", Toast.LENGTH_SHORT).show();
                size += 20;
                for (int i = size - 20; i < size; i++) {
                    MySubscriptionBean bean=new MySubscriptionBean();
                    bean.setTitle("测试数据"+i);
                    bean.setId(i);
                    subscriptionBeanList.add(bean);
                }
                mMenuAdapter.notifyDataSetChanged();
            }
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
                    getMyReportListNet=new GetMyReportListNet(mContext);
                    getMyReportListNet.setData(PageIndex,"");

                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            bundle.putString("ReportId",subscriptionBeanList.get(position).getId()+"");
            Skip.mNextFroData(mActivity,ReportDetailActivity.class,bundle);

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;

            case R.id.tv_base_title_right://
                ToastFactory.getLongToast(mContext,"点击了我啊！");
                break;
        }
    }


    /**
     * 我的订阅内部数据适配器
     */
    public class MySubscriptionAdapter extends SwipeMenuAdapter<DefaultViewHolder> {

        private List<MySubscriptionBean> subscriptionBeanList;
        private OnItemClickListener mOnItemClickListener;

        public MySubscriptionAdapter(List<MySubscriptionBean> subscriptionBeanList) {
            this.subscriptionBeanList = subscriptionBeanList;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }


        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscription, parent, false);
        }

        @Override
        public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            return new DefaultViewHolder(realContentView);
        }

        @Override
        public void onBindViewHolder(DefaultViewHolder holder,final int position) {
            holder.setData(subscriptionBeanList.get(position).getTitle(),subscriptionBeanList.get(position).getId(),subscriptionBeanList.get(position).getSubscription());
            holder.setOnItemClickListener(mOnItemClickListener);
            bookmark_checked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subscibeNet=new CancelSubscibeNet(mContext);
                    if(subscriptionBeanList.get(position).getSubscription()==0){//未订阅
                        subscibeNet.setData(subscriptionBeanList.get(position).getId(),1);
                        isSubscription=true;
                    }else{//已订阅
                        subscibeNet.setData(subscriptionBeanList.get(position).getId(),0);
                        isSubscription=false;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return subscriptionBeanList == null ? 0 : subscriptionBeanList.size();
        }
    }


    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_subscription_name,tv_subscription_count;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_subscription_name = (TextView) itemView.findViewById(R.id.tv_subscription_name);
            tv_subscription_count= (TextView) itemView.findViewById(R.id.tv_subscription_count);
            bookmark_checked= (ImageView) itemView.findViewById(R.id.bookmark);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,int subscriptionCount,int isSubscription) {
            this.tv_subscription_name.setText(title);
            this.tv_subscription_count.setText(""+subscriptionCount);
            if(isSubscription==0){//未订阅
                bookmark_checked.setImageResource(R.mipmap.bookmark);
            }else{//已订阅
                bookmark_checked.setImageResource(R.mipmap.bookmark_checked);
            }
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
