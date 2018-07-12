package com.cesaas.android.order.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.cesaas.android.order.adapter.ReceiveNewOrderAdapter;
import com.cesaas.android.order.bean.ResultUnReceiveOrderBean;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.cesaas.android.order.ui.activity.ReceiveOrderDetailActivity;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import java.util.ArrayList;


/**
 * Author FGB
 * Description
 * Created at 2017/7/24 10:14
 * Version 1.0
 */
public class ReceiveNewOrderFragment extends Fragment {
    private Context mContext;
    private ReceiveNewOrderAdapter adapter;
    private TextView tv_order_no_data;

    private View view;
    private static int pageIndex = 1;
    private LoadMoreListView mLoadMoreListView;
    private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
    public static ReceiveNewOrderFragment fragment;

    private LayoutInflater mInflater;
    private ArrayList<UnReceiveOrderBean> orderLis = new ArrayList<>();
    private boolean refresh=false;

    private UnReceiveOrderNet unReceiveOrderNet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInflater = LayoutInflater.from(getActivity());
//		inflater();

        mContext = this.getActivity();
        fragment=this;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_order_layout, container, false);
        mLoadMoreListView = (LoadMoreListView) view.findViewById(R.id.load_more_list);
        mRefreshAndLoadMoreView = (RefreshAndLoadMoreView) view.findViewById(R.id.refresh_and_load_more);
        tv_order_no_data= (TextView) view.findViewById(R.id.tv_order_no_data);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();//记得释放出来
        initListener();
    }

    @Override
    public void fetchData() {

    }

    /**
     * 初始化ListView监听
     */
    public void initListener() {
        mLoadMoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int TradeId=orderLis.get(position).getOrderId();
                Bundle bundle = new Bundle();
                bundle.putInt("TradeId", TradeId);
                Skip.mNextFroData(getActivity(), ReceiveOrderDetailActivity.class, bundle);
            }
        });
    }

    /**
     * 初始化数据
     */
    public void initData() {

        loadData(1);
        mRefreshAndLoadMoreView.setLoadMoreListView(mLoadMoreListView);
        mLoadMoreListView.setRefreshAndLoadMoreView(mRefreshAndLoadMoreView);
        // 设置下拉刷新监听
        mRefreshAndLoadMoreView
                .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refresh=true;
                        pageIndex = 1;
                        loadData(pageIndex);
                    }
                });
        // 设置加载监听
        mLoadMoreListView
                .setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        refresh=false;
                        loadData(pageIndex + 1);
                    }
                });
    }

    public void loadData(final int page) {
        // TODO Auto-generated method stub

        if (page == 1) {
            orderLis.clear();
        }
        unReceiveOrderNet = new UnReceiveOrderNet(mContext);
        unReceiveOrderNet.setData(page);

        pageIndex = page;

    }

    public static Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            fragment.loadData(pageIndex);
        }
    };


    /**
     * Author FGB
     * Description 待接单
     * Created at 2017/7/24 17:00
     * Version 1.0
     */

    public class UnReceiveOrderNet extends BaseNet {

        public UnReceiveOrderNet(Context context) {
            super(context, true);
            this.uri="OrderRoute/sw/order/UnReceiveOrder";
        }

        public void setData(int page){
            try {
//            data.put("OrderStatus",OrderStatus);
                data.put("PageIndex", page);
                data.put("PageSize", 50);
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mPostNet(); // 开始请求网络
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            Log.i(Constant.TAG,"待接单："+rJson);
            Gson gson=new Gson();
            ResultUnReceiveOrderBean msg=gson.fromJson(rJson,ResultUnReceiveOrderBean.class);
            if (msg != null) {
                if (msg.TModel.size() > 0&&msg.TModel.size()==30) {
                    mLoadMoreListView.setHaveMoreData(true);
                } else {
                    mLoadMoreListView.setHaveMoreData(false);
                }
                if(msg.TModel.size()!=0){
                    orderLis.addAll(msg.TModel);
//                adapter.updateListView(orderLis);
                    adapter = new ReceiveNewOrderAdapter(mContext,orderLis);
                    mLoadMoreListView.setAdapter(adapter);
                    mRefreshAndLoadMoreView.setRefreshing(false);
                    mLoadMoreListView.onLoadComplete();
                    // 当加载完成之后设置此时不在刷新状态
                }else{
                    tv_order_no_data.setVisibility(View.VISIBLE);
                }

            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);

        }
    }


}
