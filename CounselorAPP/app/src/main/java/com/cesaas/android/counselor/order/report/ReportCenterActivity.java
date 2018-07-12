package com.cesaas.android.counselor.order.report;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.autoCompletetextview.AutoCompleteTextView;
import com.cesaas.android.counselor.order.cache.CacheManager;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.report.bean.ReportCenterBean;
import com.cesaas.android.counselor.order.report.bean.ReportCenterCacheJsonBean;
import com.cesaas.android.counselor.order.report.bean.ResultGetCategoryListBean;
import com.cesaas.android.counselor.order.report.bean.ResultReportCenterBean;
import com.cesaas.android.counselor.order.report.bean.ResultSubscibeBean;
import com.cesaas.android.counselor.order.report.net.GetCategoryListNet;
import com.cesaas.android.counselor.order.report.net.GetReportListNet;
import com.cesaas.android.counselor.order.report.net.SubscibeNet;
import com.cesaas.android.counselor.order.statistics.bean.CacheJsonBean;
import com.cesaas.android.counselor.order.statistics.bean.ResultSalesThanScreenBean;
import com.cesaas.android.counselor.order.statistics.net.GetPowerShopNet;
import com.cesaas.android.counselor.order.treeview.FileBean;
import com.cesaas.android.counselor.order.treeview.Node;
import com.cesaas.android.counselor.order.treeview.SimpleTreeAdapter;
import com.cesaas.android.counselor.order.treeview.TreeListViewAdapter;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.wx.goodview.GoodView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表中心
 */
public class ReportCenterActivity extends BasesActivity implements View.OnClickListener{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private LinearLayout llBack;
    private TextView tvBaseTitle,tvBaseRigthTitle;

    private AutoCompleteTextView complTextView;
    private CacheManager mCacheManager;
    private String reportCacheJson;
    private List<String> complTextViewList;

    public static ImageView bookmark_checked;//收藏

    private ReportCenterAdapter mMenuAdapter;
    private List<ReportCenterBean> reportCenterBeanList;
    private int size=20;

    private List<ResultGetCategoryListBean.GetCategoryListBean> categoryListBeanList;

    private int pageIndex=1;
    private boolean isSubscription=false;
    private GoodView mGoodView;

    private GetCategoryListNet getCategoryListNet;
    private GetReportListNet getReportListNet;
    private SubscibeNet subscibeNet;

    private List<FileBean> mDatas = new ArrayList<FileBean>();
    private ListView mTree;
    private TreeListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_center);

        mCacheManager=CacheManager.getInstance(this);

        getCategoryListNet=new GetCategoryListNet(mContext);
        getCategoryListNet.setData();

        getReportListNet=new GetReportListNet(mContext);
        getReportListNet.setData(pageIndex,"");

        mGoodView=new GoodView(this);
//
        initView();
        init();
        setOnPopupItemClickListener();
    }


    /**
     * 初始化
     */
    private void init(){
        //读取缓存
        mCacheManager=CacheManager.getInstance(this);
        reportCacheJson=mCacheManager.readCache("reportCacheJson");

        if(reportCacheJson!=null){//从缓存获取
            //添加店铺列表
            ResultReportCenterBean bean=gson.fromJson(reportCacheJson,ResultReportCenterBean.class);
            reportCenterBeanList = new ArrayList<>();
            reportCenterBeanList.addAll(bean.TModel);
            initData(reportCenterBeanList);

            //添加查询列表
            complTextViewList=new ArrayList<String>();
            for (int i=0; i<reportCenterBeanList.size(); i++){
                complTextViewList.add(reportCenterBeanList.get(i).getTitle());
            }
                complTextView.setDatas(complTextViewList);


        }else{//从网络获取
            getReportListNet=new GetReportListNet(mContext);
            getReportListNet.setData(pageIndex,"");
        }
    }

    /**
     * 设置setOnPopupItemClickListener
     */
    private void setOnPopupItemClickListener(){
        complTextView.setOnPopupItemClickListener(new AutoCompleteTextView.OnPopupItemClickListener() {
            @Override
            public void onPopupItemClick(CharSequence charSequence) {
                getReportListNet=new GetReportListNet(mContext);
                getReportListNet.setData(pageIndex,charSequence.toString());
            }
        });
    }


    /**
     * 初始化树形结构菜单数据
     * @param categoryListBeanList 数据列表
     */
    private void initDatas(List<ResultGetCategoryListBean.GetCategoryListBean> categoryListBeanList) {
        for (int i=0;i<categoryListBeanList.size();i++){
            // id , pid , label , 其他属性
            mDatas.add(new FileBean(categoryListBeanList.get(i).Id, categoryListBeanList.get(i).ParentId, categoryListBeanList.get(i).Title));
        }
        try {
            mAdapter = new SimpleTreeAdapter<FileBean>(mTree, this, mDatas, 0);
            mTree.setAdapter(mAdapter);
            //设置点击菜单事件监听
            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if (node.isLeaf()) {
                        if(node.getpId()==0 && node.getId()!=0){//大类，小类查询
                            getReportListNet=new GetReportListNet(mContext);
                            getReportListNet.setData(pageIndex,"",node.getpId(),node.getId());

                        } else{//大类查询
                            getReportListNet=new GetReportListNet(mContext);
                            getReportListNet.setData(pageIndex,"",node.getpId());

                        }
                    }
                }
            });

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 接收获取报表Json字符串
     * @param msg 消息实体类
     */
    public void onEventMainThread(ReportCenterCacheJsonBean msg) {
        mCacheManager.writeCache("reportCacheJson",msg.getStrJson());
    }


    /**
     * 接收报表分类消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultGetCategoryListBean msg){
        if(msg.IsSuccess==true){
            categoryListBeanList=new ArrayList<>();
            categoryListBeanList.addAll(msg.TModel);
            initDatas(categoryListBeanList);

        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败！"+msg.Message);
        }
    }

    /**
     * 接收报表列表消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultReportCenterBean msg){
        if(msg.IsSuccess==true){
            reportCenterBeanList=new ArrayList<>();
            reportCenterBeanList.addAll(msg.TModel);
            initData(reportCenterBeanList);
        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败！"+msg.Message);
        }
    }

    /**
     * 接收报表列表消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultSubscibeBean msg){
        if(msg.IsSuccess==true){
            if(isSubscription==true){
                mGoodView.setTextInfo("订阅成功", Color.parseColor("#ff941A"), 12);
                mGoodView.show(bookmark_checked);
                bookmark_checked.setImageResource(R.mipmap.bookmark_checked);
            }
           else{
                mGoodView.setTextInfo("已取消订阅", Color.parseColor("#ff941A"), 12);
                mGoodView.show(bookmark_checked);
                bookmark_checked.setImageResource(R.mipmap.bookmark);
            }

        }else{
            ToastFactory.getLongToast(mContext,"订阅失败！"+msg.Message);
        }
    }

    private void initData(List<ReportCenterBean> reportCenterBeanList ) {
        mMenuAdapter=new ReportCenterAdapter(reportCenterBeanList);
        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);

        //添加查询列表
        complTextViewList=new ArrayList<String>();
        for (int i=0; i<reportCenterBeanList.size(); i++){
            complTextViewList.add(reportCenterBeanList.get(i).getTitle());
        }
        complTextView.setDatas(complTextViewList);
    }

    private void initView() {
        mTree = (ListView) findViewById(R.id.id_tree);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_report_center_view);

        complTextView= (AutoCompleteTextView) findViewById(R.id.tvAutoCompl);
        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
        tvBaseRigthTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvBaseRigthTitle.setVisibility(View.VISIBLE);

        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        llBack.setOnClickListener(this);
        tvBaseRigthTitle.setOnClickListener(this);

        tvBaseTitle.setText("报表中心");
        tvBaseRigthTitle.setText("我的订阅");

    }

    /**
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {// 手指不能向上滑动了
                // TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。

                Toast.makeText(ReportCenterActivity.this, "滑到最底部了，加载更多数据！", Toast.LENGTH_SHORT).show();
                size += 20;
                for (int i = size - 20; i < size; i++) {
                    ReportCenterBean bean=new ReportCenterBean();
                    bean.setTitle("测试数据"+i);
                    bean.setId(i);
                    reportCenterBeanList.add(bean);
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
                    getReportListNet=new GetReportListNet(mContext);
                    getReportListNet.setData(pageIndex,"");

                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            bundle.putString("ReportId",reportCenterBeanList.get(position).getId()+"");
            //跳转到报表详情页面
            Skip.mNextFroData(mActivity,ReportDetailActivity.class,bundle);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;

            case R.id.tv_base_title_right:////我的订阅
                Skip.mNext(mActivity,MySubscriptionActivity.class);
                break;
        }
    }


    /**
     * 报表中心Adapter 内部类
     */
    public class ReportCenterAdapter extends SwipeMenuAdapter<DefaultViewHolder> {

        private List<ReportCenterBean> reportCenterBeanList;
        private OnItemClickListener mOnItemClickListener;

        public ReportCenterAdapter(List<ReportCenterBean> reportCenterBeanList) {
            this.reportCenterBeanList = reportCenterBeanList;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_center, parent, false);
        }

        @Override
        public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            return new DefaultViewHolder(realContentView);
        }

        @Override
        public void onBindViewHolder(DefaultViewHolder holder, final int position) {
            holder.setData(reportCenterBeanList.get(position).getTitle(),reportCenterBeanList.get(position).getId(),reportCenterBeanList.get(position).getSubscription());
            holder.setOnItemClickListener(mOnItemClickListener);
            bookmark_checked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subscibeNet=new SubscibeNet(mContext);
                    if(reportCenterBeanList.get(position).getSubscription()==0){//未订阅
                        subscibeNet.setData(reportCenterBeanList.get(position).getId(),1);
                        isSubscription=true;
                    }else{//取消订阅
                        subscibeNet.setData(reportCenterBeanList.get(position).getId(),0);
                        isSubscription=false;
                    }
                }

            });
        }

        @Override
        public int getItemCount() {
            return reportCenterBeanList == null ? 0 : reportCenterBeanList.size();
        }
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_subscription_name,tv_subscription_count;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_subscription_name = (TextView) itemView.findViewById(R.id.tv_report_name);
            tv_subscription_count= (TextView) itemView.findViewById(R.id.tv_subscription_count);
            bookmark_checked= (ImageView) itemView.findViewById(R.id.bookmark);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,int subscriptionCount,int subscription) {
            this.tv_subscription_name.setText(title);
            this.tv_subscription_count.setText(""+subscriptionCount);
            if(subscription==0){//未订阅
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
