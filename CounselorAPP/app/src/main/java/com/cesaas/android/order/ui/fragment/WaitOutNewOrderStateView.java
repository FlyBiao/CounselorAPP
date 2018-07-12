package com.cesaas.android.order.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.CheckCargoOrderRouteActivity;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.view.WaitOutOrderStateView;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.cesaas.android.order.bean.ResultWaitOutOrderBean;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.cesaas.android.order.ui.activity.AddOrderRemarkActivity;
import com.cesaas.android.order.ui.activity.ReceiveOrderDetailActivity;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * Author FGB
 * Description
 * Created at 2017/7/24 10:15
 * Version 1.0
 */
public class WaitOutNewOrderStateView extends Fragment {
    private WaitOutOrderListNet byCounselorNet;
    private CommonAdapter<UnReceiveOrderBean> adapter;
    private ArrayList<UnReceiveOrderBean> orderLis= new ArrayList<>();

    private static int pageIndex = 1;
    private boolean isHaveMoreData = false;
    private LoadMoreListView mLoadMoreListView;
//    private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
    private boolean refresh=false;
    private int TradeId;

    private ListView lv;

    /**
     * 单例
     */
    public static WaitOutOrderStateView instance=null;
    public static WaitOutOrderStateView getInstance(){
        if(instance==null){
            instance=new WaitOutOrderStateView();
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WaitOutOrderListNet orderListNet=new WaitOutOrderListNet(getContext());
        orderListNet.setData(pageIndex,2);
    }

    public void initData() {
        loadData(1);
//        mRefreshAndLoadMoreView.setLoadMoreListView(mLoadMoreListView);
//        mLoadMoreListView.setRefreshAndLoadMoreView(mRefreshAndLoadMoreView);
//        // 设置下拉刷新监听
//        mRefreshAndLoadMoreView
//                .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        refresh=true;
//                        pageIndex = 1;
//                        loadData(pageIndex);
//                    }
//                });
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_all_order_state_views, container, false);
        mLoadMoreListView = (LoadMoreListView) view.findViewById(R.id.load_more_allOrderState_list);
//        mRefreshAndLoadMoreView = (RefreshAndLoadMoreView) view.findViewById(R.id.refresh_allOrderState_and_load_more);
        initListener();

        return view;
    }

    /**
     * 订单详情
     */
    public void initListener() {
        mLoadMoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TradeId=orderLis.get(position).getOrderId();
                Bundle bundle = new Bundle();
                bundle.putInt("TradeId", TradeId);
                bundle.putInt("OrderType", 10);
                Skip.mNextFroData(getActivity(), ReceiveOrderDetailActivity.class, bundle);
            }
        });
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected void loadData(final int page) {
        if (page == 1) {
            orderLis.clear();
        }
        byCounselorNet=new WaitOutOrderListNet(getContext());
        byCounselorNet.setData(pageIndex,2);
        pageIndex = page;

    }

    /**
     * Created by flybiao on 2016/7/4.
     */

    public class MyReceiveOrderThingsAdapter extends BaseAdapter {
        ImageView ivImg;
        TextView tvName;
        TextView tvAttr;
        TextView tvTypeCode;
        TextView tvQuantity;
        TextView tv_order_price;

        List<UnReceiveOrderBean.OrderItem> list = new ArrayList<>();
        Context context;

        public MyReceiveOrderThingsAdapter(Context ct, List<UnReceiveOrderBean.OrderItem> data) {
            this.context = ct;
            this.list = data;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_receive_order_things, parent, false);
            ivImg = (ImageView) convertView.findViewById(R.id.iv_reveice_img);
            tvName = (TextView) convertView.findViewById(R.id.tv_reveice_order_name);
            tvAttr = (TextView) convertView.findViewById(R.id.tv_reveice_order_attr);
            tvTypeCode = (TextView) convertView.findViewById(R.id.tv_reveice_type_code);
            tvQuantity = (TextView) convertView.findViewById(R.id.tv_reveice_quantity);
            tv_order_price=(TextView) convertView.findViewById(R.id.tv_order_price);

            ivImg.setScaleType(ImageView.ScaleType.FIT_XY);

            tvName.setText(list.get(position).getBarcodeNo());
            tvAttr.setText(list.get(position).getStyleName());
            tvTypeCode.setText(list.get(position).getAttr());
            tvQuantity.setText("x" + list.get(position).getQuantity());
            tv_order_price.setText("￥"+list.get(position).getPayMent());

            ivImg.setImageResource(R.drawable.no_shop_picture);
            return convertView;
        }
    }


    public class WaitOutOrderListNet extends BaseNet {

        public WaitOutOrderListNet(Context context) {
            super(context, true);
            this.uri="OrderRoute/sw/order/OrderList";
        }

        public void setData(int page,int OrderStatus){
            try {
                data.put("OrderStatus",OrderStatus);//0 : 待路由1：待接单 2：已接单 3：退回
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
            ResultWaitOutOrderBean bean= JsonUtils.fromJson(rJson,ResultWaitOutOrderBean.class);
            if(bean.IsSuccess!=false){
                if (bean.TModel.size() > 0&&bean.TModel.size()==10) {
                    mLoadMoreListView.setHaveMoreData(true);
                } else {
                    mLoadMoreListView.setHaveMoreData(false);

                }
                if(bean.TModel!=null && bean.TModel.size()!=0){
                    orderLis.addAll(bean.TModel);
                }
                //获取Adapter 设置数据
                adapter=new CommonAdapter<UnReceiveOrderBean>(getContext(),R.layout.item_all_receive_order_state,orderLis) {

                    @Override
                    public void convert(ViewHolder holder, final UnReceiveOrderBean bean, final int postion) {

                        lv=holder.getView(R.id.list_receive_order_things);
                        holder.setText(R.id.tv_order_createTime, bean.getCreateTime());
                        holder.setText(R.id.tv_order_id, bean.getOrderId()+"");
                        holder.setText(R.id.tv_shop_name, bean.getConsignee());

                        final List<UnReceiveOrderBean.OrderItem> list = new ArrayList<>();
                        for (int i = 0; i < bean.OrderItem.size(); i++) {
                            UnReceiveOrderBean.OrderItem itemBean=new UnReceiveOrderBean.OrderItem();
                            itemBean=bean.OrderItem.get(i);
                            list.add(itemBean);
                        }

                        MyReceiveOrderThingsAdapter adapters=new MyReceiveOrderThingsAdapter(mContext, list);
                        int totalHeight = 0;
                        for (int i = 0; i < adapters.getCount(); i++) {
                            View listItem = adapters.getView(i, null, lv);
                            listItem.measure(0, 0);
                            totalHeight += listItem.getMeasuredHeight();
                        }

                        ViewGroup.LayoutParams params = lv.getLayoutParams();
                        params.height = totalHeight + (lv.getDividerHeight() * (adapters.getCount() - 1));
                        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
                        lv.setLayoutParams(params);

        //                if(bean.ExpressType==0){//物流发货
                            holder.setText(R.id.tv_my_receive_user, bean.getConsignee());
                            holder.getView(R.id.tv_order_session1).setVisibility(View.VISIBLE);
                            holder.getView(R.id.tv_order_session).setVisibility(View.GONE);
                            holder.getView(R.id.slv_right_tri_express).setVisibility(View.VISIBLE);
        //
        //                    //物流订单发货点击事件监听
                            holder.getView(R.id.tv_express_send).setVisibility(View.VISIBLE);
                            holder.getView(R.id.tv_scan_send).setVisibility(View.GONE);
                            holder.setOnClickListener(R.id.tv_express_send, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("TradeId", bean.getOrderId()+"");
                                    Skip.mNextFroData(getActivity(), CheckCargoOrderRouteActivity.class, bundle);
                                }
                            });

                            //退单
                            holder.getView(R.id.tv_back_order).setVisibility(View.VISIBLE);
                            holder.setOnClickListener(R.id.tv_back_order, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getContext(), AddOrderRemarkActivity.class);
                                    intent.putExtra("OrderId",orderLis.get(postion).getOrderId()+"");
                                    intent.putExtra("OrderType","1000");
                                    startActivityForResult(intent, Constant.ORDER_BACK);
                                }
                            });

                        lv.setAdapter(adapters);
                    }
                };
                mLoadMoreListView.setAdapter(adapter);
//                mRefreshAndLoadMoreView.setRefreshing(false);
                mLoadMoreListView.onLoadComplete();
            }else{

            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);

        }
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {//退单
            if (data != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadData(1);
                    }
                });
            }
        }
    }
}
