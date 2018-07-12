package com.cesaas.android.order.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.CheckCargoOrderRouteActivity;
import com.cesaas.android.counselor.order.bean.ResultExpressBean;
import com.cesaas.android.counselor.order.bean.ResultOrderBackBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean;
import com.cesaas.android.counselor.order.bean.RsultRejectOrderBean;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.express.view.ExpressQueryRouteActivity;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.ExpressNet;
import com.cesaas.android.counselor.order.net.ReceivingOrderNet;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.adapter.OrderExpressAdapter;
import com.cesaas.android.order.adapter.ReceiveOrderDetailAdapter;
import com.cesaas.android.order.bean.ReceiveOrderDetailsBean;
import com.cesaas.android.order.bean.ResultReceiveOrderDetailsBean;
import com.cesaas.android.order.bean.RsultReceivingOrderDetailBean;
import com.cesaas.android.order.net.OrderBackNetTest;
import com.cesaas.android.order.net.RejectOrderNet;
import com.cesaas.android.order.net.SendOfflineOrderNet;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 接单详情
 */
@ContentView(R.layout.activity_receive_order_detail)
public class ReceiveOrderDetailActivity extends BasesActivity {

    @ViewInject(R.id.ll_waybill_no)
    LinearLayout ll_waybill_no;
    @ViewInject(R.id.ll_sure_date)
    LinearLayout ll_sure_date;
    @ViewInject(R.id.lv_order_detail)
    private ListView lv_order_detail;
    @ViewInject(R.id.iv_orderdetail_back)
    private LinearLayout iv_backDetail;
    @ViewInject(R.id.ll_send_order)
    private LinearLayout ll_send_order;
    @ViewInject(R.id.ll_receive_order)
    private LinearLayout ll_receive_order;
    @ViewInject(R.id.tv_crate_time)
    TextView tv_crate_time;
    @ViewInject(R.id.tv_crate_name)
    TextView tv_crate_name;
    @ViewInject(R.id.tv_total_amount)
    TextView tv_total_amount;
    @ViewInject(R.id.tv_count)
    TextView tv_count;
    @ViewInject(R.id.tv_order_no)
    TextView tv_order_no;
    @ViewInject(R.id.tv_send_order)
    TextView tv_send_order;
    @ViewInject(R.id.tv_back_order)
    TextView tv_back_order;
    @ViewInject(R.id.tv_receive_order)
    TextView tv_receive_order;
    @ViewInject(R.id.tv_back_receive_order)
    TextView tv_back_receive_order;
    @ViewInject(R.id.ll_query_express)
    private LinearLayout ll_query_express;
    @ViewInject(R.id.tv_mobile)
    private TextView tv_mobile;
    @ViewInject(R.id.tv_WayBillNo)
    TextView tv_WayBillNo;
    @ViewInject(R.id.tv_SureDate)
    TextView tv_SureDate;
    @ViewInject(R.id.tv_order_status)
    TextView tv_order_status;
    @ViewInject(R.id.iv_retail_from)
    ImageView iv_retail_from;

    private EditText et_express_name,et_express_no;

    private BaseDialog baseDialog;
    private ListView lv_express;

    private int orderId;
    private String wayBillNo;
    private String etExpressName;
    private int etExpressNo;

    private OrderExpressAdapter orderExpressAdapter;

    private ReceiveOrderDetailsNetTest detailsNetTest;
    private SendOfflineOrderNet sendOfflineOrderNet;
    private OrderBackNetTest orderBackNetTest;
    private ExpressNet expressNet;

    private int TradeId;
    private int OrderType;

    private ResultOrderDetailBean.OrderDetailBean bean;

    private ReceiveOrderDetailAdapter detailAdapter;
    private List<ReceiveOrderDetailsBean.OrderItemDetail> orderDetailList= new ArrayList<>();

    private List<ResultExpressBean.ExpressBean> expressList= new ArrayList<ResultExpressBean.ExpressBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            OrderType=bundle.getInt("OrderType");
            TradeId=bundle.getInt("TradeId");

            if(OrderType==100){//接单
                ll_receive_order.setVisibility(View.VISIBLE);
                ll_waybill_no.setVisibility(View.GONE);
                ll_sure_date.setVisibility(View.GONE);
            }else if(OrderType==10){//待发货
                ll_send_order.setVisibility(View.VISIBLE);
            }else if(OrderType==30){
                ll_waybill_no.setVisibility(View.VISIBLE);
                ll_sure_date.setVisibility(View.VISIBLE);
                ll_query_express.setVisibility(View.VISIBLE);
                ll_receive_order.setVisibility(View.GONE);
                ll_send_order.setVisibility(View.GONE);
            }else{
                ll_query_express.setVisibility(View.GONE);
                ll_receive_order.setVisibility(View.GONE);
                ll_send_order.setVisibility(View.GONE);
                ll_waybill_no.setVisibility(View.GONE);
                ll_sure_date.setVisibility(View.GONE);
            }
            detailsNetTest=new ReceiveOrderDetailsNetTest(mContext);
            detailsNetTest.setData(TradeId);
        }
        scanSendOrder();
        initBack();
    }

    public void onEventMainThread(RsultReceivingOrderDetailBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"接单成功！");
            ll_send_order.setVisibility(View.VISIBLE);
            ll_receive_order.setVisibility(View.GONE);
            ll_query_express.setVisibility(View.GONE);
        }else{
            ToastFactory.getLongToast(mContext,"接单失败！"+msg.Message);
        }
    }public void onEventMainThread(RsultRejectOrderBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"订单拒绝成功！");
            Skip.mNext(mActivity, NewOrderActivity.class,true);
        }else{
            ToastFactory.getLongToast(mContext,"订单拒绝失败！"+msg.Message);
        }
    }

    public void onEventMainThread(ResultOrderBackBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"退单成功！");
            Skip.mNext(mActivity, NewOrderActivity.class,true);
        }else{
            ToastFactory.getLongToast(mContext,"退单失败："+msg.Message);
        }
    }

    public void onEventMainThread(ResultExpressBean msg) {
        if(msg.IsSuccess!=false){
            expressList=msg.TModel;
            orderExpressAdapter=new OrderExpressAdapter(expressList,mContext);
            lv_express.setAdapter(orderExpressAdapter);

        }else{
            ToastFactory.getLongToast(mContext,"获取物流失败！"+msg.Message);
        }
    }

    /**
     * 发货
     */
    public void scanSendOrder(){

        tv_send_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("TradeId", TradeId+"");
                Skip.mNextFroData(mActivity, CheckCargoOrderRouteActivity.class, bundle);
            }
        });

        tv_back_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAlertDialog(mContext).mInitShow("温馨提示", "是否确定退单",
                    "退单", "点错了", new MyAlertDialog.ConfirmListener() {
                        @Override
                        public void onClick(Dialog dialog) {

                            orderBackNetTest=new OrderBackNetTest(mContext);
                            orderBackNetTest.setData(orderId,"退单");

                        }
                    }, null);
            }
        });
        tv_back_receive_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RejectOrderNet net=new RejectOrderNet(mContext);
                net.setData(TradeId);
            }
        });
        tv_receive_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceivingOrderNet net=new ReceivingOrderNet(mContext,2);
                net.setData(TradeId);
            }
        });

        ll_query_express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("TradeId", wayBillNo);
                Skip.mNextFroData(mActivity, ExpressQueryRouteActivity.class,bundle);
            }
        });
    }

    /**
     * 返回上一个页面
     */
    public void initBack(){
        //
        iv_backDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }


    public class ReceiveOrderDetailsNetTest extends BaseNet {

        public ReceiveOrderDetailsNetTest(Context context) {
            super(context, true);
            this.uri="OrderRoute/sw/order/OrderDetail";
        }

        public void setData(int OrderId){
            try {
                data.put("OrderId",OrderId);
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mPostNet(); // 开始请求网络
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            Gson gson=new Gson();
            ResultReceiveOrderDetailsBean msg=gson.fromJson(rJson,ResultReceiveOrderDetailsBean.class);
            if(msg.IsSuccess!=false){
                if(msg.TModel!=null && !"".equals(msg.TModel)){
                    orderId=msg.TModel.getOrderId();
                    TradeId=msg.TModel.getOrderId();
                    wayBillNo=msg.TModel.getWayBillNo();
                    tv_order_no.setText(msg.TModel.getOrderId()+"");
                    tv_crate_time.setText(msg.TModel.getCreateTime());
                    tv_crate_name.setText(msg.TModel.getConsignee());
                    if(msg.TModel.getMobile()!=null && !"".equals(msg.TModel.getMobile())){
                        tv_mobile.setText(msg.TModel.getMobile());
                    }else{
                        tv_mobile.setText("暂无电话");
                    }
                    tv_total_amount.setText("￥"+msg.TModel.getPayMent());
                    if(msg.TModel.getWayBillNo()!=null && !"".equals(msg.TModel.getWayBillNo())){
                        tv_WayBillNo.setText(msg.TModel.getWayBillNo());
                    }
                    if(msg.TModel.getSureDate()!=null && !"".equals(msg.TModel.getSureDate())){
                        tv_SureDate.setText(msg.TModel.getSureDate());
                    }
                    tv_count.setText(msg.TModel.getProvince()+msg.TModel.getCity()+msg.TModel.getDistrict()+msg.TModel.getAddress());

                    orderDetailList=new ArrayList<>();
                    orderDetailList=msg.TModel.OrderItem;
                    detailAdapter=new ReceiveOrderDetailAdapter(orderDetailList,mContext);
                    lv_order_detail.setAdapter(detailAdapter);

                    if(msg.TModel.getRetailFrom()==1){//只能接单发货
                        tv_back_order.setVisibility(View.GONE);
                        tv_back_receive_order.setVisibility(View.GONE);
                        iv_retail_from.setImageResource(R.mipmap.order_offline);
                    }else{
                        tv_back_order.setVisibility(View.VISIBLE);
                        tv_back_receive_order.setVisibility(View.VISIBLE);
                        iv_retail_from.setImageResource(R.mipmap.order_online);
                    }

                    switch (msg.TModel.getOrderStatus()){
                        case 1://待接单
                            tv_order_status.setText("待接单");
                            tv_order_status.setTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                            break;
                        case 2://待发货
                            tv_order_status.setText("待发货");
                            tv_order_status.setTextColor(mContext.getResources().getColor(R.color.lightgreen));
                            break;
                        case 4://已发货
                            tv_order_status.setText("已发货");
                            tv_order_status.setTextColor(mContext.getResources().getColor(R.color.green_pressed));
                            break;
                        default:
                            tv_order_status.setText("");
                            break;
                    }

                }else{
                    ToastFactory.getLongToast(mContext,"获取订单详情数据为空！");
                }

            }else{
                ToastFactory.getLongToast(mContext,"msg:"+msg.Message);
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);

        }
    }


    public class BaseDialog extends Dialog implements View.OnClickListener {

        TextView tvCancel;
        TextView tvSure;

        private String inviteCode;

        public BaseDialog(Context context) {
            this(context, R.style.dialog);

        }

        public BaseDialog(Context context, int dialog) {
            super(context, dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(R.layout.dialog_random_anount);

            et_express_name= (EditText) findViewById(R.id.et_express_name);
            et_express_name.setOnClickListener(this);
            et_express_no= (EditText) findViewById(R.id.et_express_no);
            tvSure = (TextView) findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(this);
            tvCancel = (TextView) findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(this);

        }

        public void mInitShow() {
            show();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.et_express_name:
                    show1();
                    expressNet=new ExpressNet(mContext);
                    expressNet.setData();
                    break;
                case R.id.tv_cancel:
                    dismiss();
                    break;
                case R.id.tv_sure:
                    sendOfflineOrderNet=new SendOfflineOrderNet(mContext);
                    sendOfflineOrderNet.setData(etExpressNo,etExpressName,wayBillNo,orderId);
                    break;
            }
        }
    }

    private void show1() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);

        TextView tv_cancel_dialog= (TextView) contentView.findViewById(R.id.tv_cancel_dialog);
        lv_express= (ListView) contentView.findViewById(R.id.lv_express);

        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();


        tv_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });

        lv_express.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etExpressName=expressList.get(position).Name;
                etExpressNo= Integer.parseInt(expressList.get(position).Id);
                et_express_name.setText(etExpressName);
                bottomDialog.dismiss();
            }
        });
    }

}