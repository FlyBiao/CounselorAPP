package com.cesaas.android.counselor.order.report;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.report.adapter.IntoShopAdapter;
import com.cesaas.android.counselor.order.report.bean.IntoShopBean;
import com.cesaas.android.counselor.order.report.bean.ResultDeleteCustomersBean;
import com.cesaas.android.counselor.order.report.bean.ResultIntoShopBean;
import com.cesaas.android.counselor.order.report.bean.ResultIntoShopCountBean;
import com.cesaas.android.counselor.order.report.net.GetCustomersCountNet;
import com.cesaas.android.counselor.order.report.net.GetCustomersNet;
import com.cesaas.android.counselor.order.report.net.SaveNumberOfStoresNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.materialdialog.MaterialDialog;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 进店管理
 */
public class IntoManagerActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private TextView tvOne,tvTwo,tvThree,tvFour,tvFive,tvSix,tvSeven,tvEigh,tvNine;
    private TextView tv_today_date,tv_week,tv_into_shop_number;

    private List<IntoShopBean> mIntoShopBeen;
    private IntoShopAdapter mIntoShopAdapter;

    private SaveNumberOfStoresNet mSaveNumberOfStoresNet;
    private GetCustomersNet mGetCustomersNet;
    private  MaterialDialog mMaterialDialog;

    private int pageIndex=1;
    private int addNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into_manager);
        mMaterialDialog=new MaterialDialog(mContext);
        mSaveNumberOfStoresNet=new SaveNumberOfStoresNet(mContext);

        mGetCustomersNet=new GetCustomersNet(mContext);
        mGetCustomersNet.setData(pageIndex);

        initView();
    }

    private void initView(){
        tvOne= (TextView) findViewById(R.id.tv_into_one);
        tvThree= (TextView) findViewById(R.id.tv_into_three);
        tvTwo= (TextView) findViewById(R.id.tv_into_two);
        tvFour= (TextView) findViewById(R.id.tv_into_four);
        tvFive= (TextView) findViewById(R.id.tv_into_five);
        tvSix= (TextView) findViewById(R.id.tv_into_six);
        tvSeven= (TextView) findViewById(R.id.tv_into_seven);
        tvEigh= (TextView) findViewById(R.id.tv_into_eigh);
        tvNine= (TextView) findViewById(R.id.tv_into_nine);
        tv_into_shop_number= (TextView) findViewById(R.id.tv_into_shop_number);

        tv_today_date= (TextView) findViewById(R.id.tv_today_date);
        tv_week= (TextView) findViewById(R.id.tv_week);
        tv_today_date.setText(AbDateUtil.getCurrentDay());
        tv_week.setText(AbDateUtil.getWeek());

        tvTwo.setOnClickListener(this);
        tvOne.setOnClickListener(this);
        tvThree.setOnClickListener(this);
        tvFour.setOnClickListener(this);
        tvFive.setOnClickListener(this);
        tvSix.setOnClickListener(this);
        tvSeven.setOnClickListener(this);
        tvEigh.setOnClickListener(this);
        tvNine.setOnClickListener(this);

        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("进店管理");

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_into_shop_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
//        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    /**
     * 接收消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultIntoShopBean msg){
        if(msg.IsSuccess==true){
            mIntoShopBeen=new ArrayList<>();
            mIntoShopBeen.addAll(msg.TModel);
            tv_into_shop_number.setText(msg.Total+"");

            mIntoShopAdapter=new IntoShopAdapter(mIntoShopBeen,mContext);
            mIntoShopAdapter.setOnItemClickListener(onItemClickListener);
            mSwipeMenuRecyclerView.setAdapter(mIntoShopAdapter);
        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败！"+msg.Message);
        }
    }

    public void onEventMainThread(ResultDeleteCustomersBean msg){
        if(msg.IsSuccess==true){
            mGetCustomersNet=new GetCustomersNet(mContext);
            mGetCustomersNet.setData(pageIndex);
        }else{
            ToastFactory.getLongToast(mContext,"删除失败！"+msg.Message);
        }
    }

    /**
     * OnItemClickListener
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

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
                    mGetCustomersNet=new GetCustomersNet(mContext);
                    mGetCustomersNet.setData(pageIndex);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    /**
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {// 手指不能向上滑动了
                // TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。

                Toast.makeText(IntoManagerActivity.this, "滑到最底部了，加载更多数据！", Toast.LENGTH_SHORT).show();
//                size += 20;
//                for (int i = size - 20; i < size; i++) {
//                    ReportCenterBean bean=new ReportCenterBean();
//                    bean.setTitle("测试数据"+i);
//                    bean.setId(i);
//                    reportCenterBeanList.add(bean);
//                }
//                mMenuAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_into_one:
                addNumber=0;
                addNumber=Integer.parseInt(tvOne.getText().toString());
                showDialogMsg();
                break;
            case R.id.tv_into_two:
                addNumber=0;
                addNumber=Integer.parseInt(tvTwo.getText().toString());
                showDialogMsg();
                break;
            case R.id.tv_into_three:
                addNumber=0;
                addNumber=Integer.parseInt(tvThree.getText().toString());
                showDialogMsg();
                break;
            case R.id.tv_into_four:
                addNumber=0;
                addNumber=Integer.parseInt(tvFour.getText().toString());
                showDialogMsg();
                break;
            case R.id.tv_into_five:
                addNumber=0;
                addNumber=Integer.parseInt(tvFive.getText().toString());
                showDialogMsg();
                break;
            case R.id.tv_into_six:
                addNumber=0;
                addNumber=Integer.parseInt(tvSix.getText().toString());
                showDialogMsg();
                break;
            case R.id.tv_into_seven:
                addNumber=0;
                addNumber=Integer.parseInt(tvSeven.getText().toString());
                showDialogMsg();
                break;
            case R.id.tv_into_eigh:
                addNumber=0;
                addNumber=Integer.parseInt(tvEigh.getText().toString());
                showDialogMsg();
                break;
            case R.id.tv_into_nine:
                addNumber=0;
                addNumber=Integer.parseInt(tvNine.getText().toString());
                showDialogMsg();
                break;
        }
    }

    private void showDialogMsg(){
        if (mMaterialDialog != null) {
            mMaterialDialog.setTitle("温馨提示！")
                    .setMessage(
                            "确定新增进店人数 "+addNumber+" 人？")
                    //mMaterialDialog.setBackgroundResource(R.drawable.background);
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            mMaterialDialog.dismiss();
                            mSaveNumberOfStoresNet.setData(addNumber);
                        }
                    })
                    .setNegativeButton("取消",
                            new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    mMaterialDialog.dismiss();
                                    ToastFactory.getLongToast(mContext,"已取消进店登记！");
                                }
                            })
                    .setCanceledOnTouchOutside(true).show();
        }
    }
}
