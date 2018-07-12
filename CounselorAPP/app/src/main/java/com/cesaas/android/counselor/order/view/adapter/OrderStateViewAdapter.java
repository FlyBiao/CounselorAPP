package com.cesaas.android.counselor.order.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.ShopDetailActivity;
import com.cesaas.android.counselor.order.adapter.MyReceiveOrderThingsAdapter;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean;
import com.cesaas.android.counselor.order.express.view.ExpressQueryActivity;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.haozhang.lib.SlantedTextView;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Author FGB
 * Description
 * Created 2017/3/23 10:41
 * Version 1.zero
 */
public class OrderStateViewAdapter extends BaseAdapter {

    private TextView tv_order_createTime,tv_order_id,tv_shop_name,tv_orderState,
            tv_express_send,tv_my_receive_user,tv_order_session1,tv_express_query,
            tv_order_session,tv_express_type;
    private LinearLayout ll_shop_assistant;
    private View view_shop_assistant;
    private ListView lv;
    private SlantedTextView slv_right_tri_express;
    private SlantedTextView slv_right_tri_self_lift;

    private Context ct;
    private Activity mActivity;
    private List<ResultGetByCounselorBean.GetByCounselorBean> orderLis;
    private List<ResultGetByCounselorBean.GetByCounselorBeanItemBean> list;

    public OrderStateViewAdapter(Context ct,Activity mActivity,List<ResultGetByCounselorBean.GetByCounselorBean> orderLis){
        this.ct=ct;
        this.mActivity=mActivity;
        this.orderLis=orderLis;
    }

    @Override
    public int getCount() {
        return orderLis.size();
    }

    @Override
    public Object getItem(int position) {
        return orderLis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(ct).inflate(R.layout.item_all_order_state, parent, false);

        initView(position,convertView);

        return convertView;
    }

    public void initView(int position,View convertView){
        ll_shop_assistant= (LinearLayout) convertView.findViewById(R.id.ll_shop_assistant);
        view_shop_assistant=convertView.findViewById(R.id.view_shop_assistant);
        lv= (ListView) convertView.findViewById(R.id.list_receive_order_things);

        tv_order_createTime = (TextView) convertView.findViewById(R.id.tv_order_createTime);
        tv_order_id = (TextView) convertView.findViewById(R.id.tv_order_id);
        tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
        tv_orderState = (TextView) convertView.findViewById(R.id.tv_orderState);
        tv_express_send = (TextView) convertView.findViewById(R.id.tv_express_send);
        tv_my_receive_user = (TextView) convertView.findViewById(R.id.tv_my_receive_user);
        tv_order_session1 = (TextView) convertView.findViewById(R.id.tv_order_session1);
        tv_express_query = (TextView) convertView.findViewById(R.id.tv_express_query);
        tv_order_session = (TextView) convertView.findViewById(R.id.tv_order_session);
        tv_express_type= (TextView) convertView.findViewById(R.id.tv_express_type);
        slv_right_tri_express= (SlantedTextView) convertView.findViewById(R.id.slv_right_tri_express);
        slv_right_tri_self_lift= (SlantedTextView) convertView.findViewById(R.id.slv_right_tri_self_lift);

        final ResultGetByCounselorBean.GetByCounselorBean bean = orderLis.get(position);

        tv_order_createTime.setText(bean.CreateDate);
        tv_order_id.setText(bean.TradeId);
        tv_shop_name.setText(bean.CounselorName);

        //判断是否显示订单所属店员
        view_shop_assistant.setVisibility(View.VISIBLE);
        ll_shop_assistant.setVisibility(View.VISIBLE);

        list = new ArrayList<ResultGetByCounselorBean.GetByCounselorBeanItemBean>();

        for (int i = 0; i < bean.OrderItem.size(); i++) {
            ResultGetByCounselorBean.GetByCounselorBeanItemBean itemBean=new ResultGetByCounselorBean().new GetByCounselorBeanItemBean();
            itemBean=bean.OrderItem.get(i);
//            Log.i("orderList:","=:"+bean.OrderItem.get(i));
            list.add(itemBean);
        }
        MyReceiveOrderThingsAdapter adapter=new MyReceiveOrderThingsAdapter(ct, list);
        lv.setAdapter(adapter);

        if (bean.OrderStatus == 40) {// 已发货
            tv_orderState.setText("已发货");
            tv_express_send.setVisibility(View.GONE);

        } else{
            tv_orderState.setText("订单状态错误");
            tv_express_send.setVisibility(View.GONE);
        }

        if (bean.OrderStatus == 40) {// 已发货
            tv_orderState.setText("已支付");

            if(bean.ExpressType==0){//物流发货
                tv_my_receive_user.setText(bean.NickName+"("+bean.Mobile+")");
                tv_order_session1.setVisibility(View.VISIBLE);
                tv_express_query.setVisibility(View.VISIBLE);
                tv_order_session.setVisibility(View.GONE);
                slv_right_tri_express.setVisibility(View.VISIBLE);

            }else if(bean.ExpressType==1){//到店自提扫描发货
                tv_my_receive_user.setText(bean.NickName);
                tv_express_query.setVisibility(View.GONE);
                tv_order_session1.setVisibility(View.GONE);
                tv_order_session.setVisibility(View.VISIBLE);
                slv_right_tri_self_lift.setVisibility(View.VISIBLE);
            }
        }


        /**
         * 查看订单详情
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("Title", list.get(position).Title);
                bundle.putString("ImageUrl", list.get(position).ImageUrl);
                bundle.putString("StyleCode", list.get(position).StyleCode);
                bundle.putString("BarcodeCode", list.get(position).BarcodeCode);
                bundle.putDouble("Price", list.get(position).Price);
                bundle.putString("Attr", list.get(position).Attr);
                Skip.mNextFroData(mActivity, ShopDetailActivity.class, bundle);
            }
        });

        //查看物流订单
        tv_express_query.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("TradeId", bean.TradeId);
                Skip.mNextFroData(mActivity, ExpressQueryActivity.class,bundle);
            }
        });

        //会话
        tv_order_session.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动单聊会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(ct, bean.VipId, bean.CounselorName);
            }
        });

        tv_order_session1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动单聊会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(ct, bean.VipId, bean.CounselorName);
            }
        });


//        if(abpUtil.getString("TypeId").equals("1")){//店长
//            holder.getView(R.id.view_shop_assistant).setVisibility(View.VISIBLE);
//            holder.getView(R.id.ll_shop_assistant).setVisibility(View.VISIBLE);
//        }else{//店员
//            holder.getView(R.id.view_shop_assistant).setVisibility(View.GONE);
//            holder.getView(R.id.ll_shop_assistant).setVisibility(View.GONE);
//        }
    }
}
