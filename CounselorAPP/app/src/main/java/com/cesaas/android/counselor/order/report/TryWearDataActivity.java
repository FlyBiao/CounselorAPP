package com.cesaas.android.counselor.order.report;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetByBarcodeCodeBean;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.net.GetByBarcodeCodeNet;
import com.cesaas.android.counselor.order.report.adapter.TryWearDataAdapter;
import com.cesaas.android.counselor.order.report.bean.ResultAddTryWearDateBean;
import com.cesaas.android.counselor.order.report.bean.ResultDeleteTryWearDateBean;
import com.cesaas.android.counselor.order.report.bean.ResultTryWearListDateBean;
import com.cesaas.android.counselor.order.report.net.GetTryWearListDateNet;
import com.cesaas.android.counselor.order.report.net.AddTryWearStyleDateNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.flybiao.materialdialog.MaterialDialog;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.hugo.android.scanner.CaptureActivity;

/**
 * 试穿数据分析
 */
public class TryWearDataActivity extends BasesActivity implements View.OnClickListener{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private LinearLayout llBaseBack;
    private TextView tvBaseTitle,tv_not_data,tv_try_wear_count;
    private ImageView iv_scan_s;

    private int REQUEST_CONTACT = 20;
    final int RESULT_CODE=101;
    private String scanCode ;

    private int pageIndex=1;

    private MaterialDialog mMaterialDialog;
    private GetTryWearListDateNet mGetTryWearListDateNet;

    private List<ResultTryWearListDateBean.TryWearListDateBean> mTryWearListDateBeanList;
    private TryWearDataAdapter mTryWearDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_wear);

        mGetTryWearListDateNet=new GetTryWearListDateNet(mContext);
        mGetTryWearListDateNet.setData(pageIndex);
        
        initView();
        initData();
    }

    private void initData() {
        mMaterialDialog=new MaterialDialog(mContext);
    }


    /**
     * 接收消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultGetByBarcodeCodeBean msg){
        if(msg.IsSuccess==true){
            if (mMaterialDialog != null) {
                mMaterialDialog.setTitle(msg.TModel.getTitle())
                        .setMessage(
                                msg.TModel.BarcodeInfo.getName1()+":"+msg.TModel.BarcodeInfo.getValue1()
                                +" "+msg.TModel.BarcodeInfo.getName2()+":"+msg.TModel.BarcodeInfo.getValue2()
                                +" "+msg.TModel.BarcodeInfo.getName3()+":"+msg.TModel.BarcodeInfo.getValue3())
                        //mMaterialDialog.setBackgroundResource(R.drawable.background);
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override public void onClick(View v) {
                                mMaterialDialog.dismiss();
                                AddTryWearStyleDateNet styleCodeDateNet=new AddTryWearStyleDateNet(mContext);
                                styleCodeDateNet.setData(scanCode);
                            }
                        })
                        .setNegativeButton("取消",
                                new View.OnClickListener() {
                                    @Override public void onClick(View v) {
                                        mMaterialDialog.dismiss();
                                        ToastFactory.getLongToast(mContext,"已取消添加记录！");
                                    }
                                })
                        .setCanceledOnTouchOutside(true).show();
            }

        }else{
            ToastFactory.getLongToast(mContext,"获取商品信息失败！"+msg.Message);
        }
    }


    /**
     * 接收消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultAddTryWearDateBean msg){
        if(msg.IsSuccess==true){
            ToastFactory.getLongToast(mContext,"添加试穿记录成功！");

            mGetTryWearListDateNet.setData(pageIndex);

        }else{
            ToastFactory.getLongToast(mContext,"添加试穿记录失败！"+msg.Message);
        }
    }

    /**
     * 接收消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultDeleteTryWearDateBean msg){
        if(msg.IsSuccess==true){
            ToastFactory.getLongToast(mContext,"删除该试穿记录成功！");
            //刷新adapter数据
            mGetTryWearListDateNet.setData(pageIndex);

        }else{
            ToastFactory.getLongToast(mContext,"删除该试穿记录失败！"+msg.Message);
        }
    }

    /**
     * 接收消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultTryWearListDateBean msg){
        if(msg.IsSuccess==true){
            mTryWearListDateBeanList=new ArrayList<>();
            mTryWearListDateBeanList.addAll(msg.TModel);

            mTryWearDataAdapter=new TryWearDataAdapter(mTryWearListDateBeanList,mContext);
            mTryWearDataAdapter.setOnItemClickListener(onItemClickListener);
            mSwipeMenuRecyclerView.setAdapter(mTryWearDataAdapter);

            tv_try_wear_count.setText("共试穿 "+mTryWearListDateBeanList.size()+" 次");

        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败！"+msg.Message);
        }
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_try_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        iv_scan_s= (ImageView) findViewById(R.id.iv_scan_s);
        iv_scan_s.setImageResource(R.mipmap.scan_s);
        iv_scan_s.setVisibility(View.VISIBLE);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("试穿登记");
        tv_try_wear_count= (TextView) findViewById(R.id.tv_try_wear_count);

        llBaseBack.setOnClickListener(this);
        iv_scan_s.setOnClickListener(this);
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
                    mGetTryWearListDateNet.setData(pageIndex);
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

                Toast.makeText(TryWearDataActivity.this, "滑到最底部了，加载更多数据！", Toast.LENGTH_SHORT).show();
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

    /**
     * 处理扫描Activity返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_CODE) {
            if(data.getStringExtra("TryWearCode")!=null && data!=null){
                if(data.getStringExtra("TryWearCode").equals("112")){
                    scanCode= data.getStringExtra("resultCode");
                    GetByBarcodeCodeNet barcodeCodeNet=new GetByBarcodeCodeNet(mContext);
                    barcodeCodeNet.setData(Long.parseLong(scanCode));
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;
            case R.id.iv_scan_s:
                Intent intent = new Intent(mContext, CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("TryWearCode","112");
                startActivityForResult(intent, REQUEST_CONTACT);
//                Skip.mTryWearActivityResult(mActivity, CaptureActivity.class, REQUEST_CONTACT);
                break;
        }
    }
}
