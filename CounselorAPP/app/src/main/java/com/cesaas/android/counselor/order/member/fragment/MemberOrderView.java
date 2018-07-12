package com.cesaas.android.counselor.order.member.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultVipOrderListBean;
import com.cesaas.android.counselor.order.bean.SortBean;
import com.cesaas.android.counselor.order.bean.SubOrder;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.counselor.order.member.StyleRecommendActivity;
import com.cesaas.android.counselor.order.member.adapter.VipOrderLstAdapter;
import com.cesaas.android.counselor.order.member.bean.VipOrderListSection;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailActivity;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailsActivity;
import com.cesaas.android.counselor.order.net.VipOrderListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2017/7/24 10:16
 * Version 1.0
 */

public class MemberOrderView extends Fragment{
    RecyclerView rv_shopping_record_list;
    TextView tv_not_data;
    private View view;

    private VipOrderLstAdapter orderLstAdapter;
    private List<VipOrderListSection> mData;
    private VipOrderListNet net;
    private SortBean sortBean;
    private JSONArray arraySort;
    private int pageIndex=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        view = inflater.inflate(R.layout.fragment_member_order, container, false);
        tv_not_data= (TextView) view.findViewById(R.id.tv_not_data);
        rv_shopping_record_list= (RecyclerView) view.findViewById(R.id.rv_shopping_record_list);
        rv_shopping_record_list.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器。
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        arraySort=new JSONArray();
        sortBean=new SortBean();
        sortBean.setField("CreateTime");
        sortBean.setValue("desc");
        arraySort.put(sortBean.getSortIdInfo());
        net=new VipOrderListNet(getContext());
        if(MemberReturnVisitDetailsActivity.getVipId()!=0){
            net.setData(pageIndex, MemberReturnVisitDetailsActivity.getVipId()+"",arraySort);
        }else{
            net.setData(pageIndex,MemberReturnVisitDetailActivity.getVipId()+"",arraySort);
        }
    }

    public void onEventMainThread(final ResultVipOrderListBean msg){
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                try{
                    tv_not_data.setVisibility(View.GONE);
                    mData=new ArrayList<>();
                    for(int i=0;i<msg.TModel.size();i++){
                        for (int j=0;j<msg.TModel.get(i).getOrderItem().size();j++){
                            mData.add(new VipOrderListSection(true, "Section", false,msg.TModel.get(i).getCreateTime()));
                            mData.add(new VipOrderListSection(new SubOrder(msg.TModel.get(i).getOrderItem().get(j).getImageUrl(),msg.TModel.get(i).getOrderItem().get(j).getStyleName(),msg.TModel.get(i).getOrderItem().get(j).getBarcodeNo(),msg.TModel.get(i).getOrderItem().get(j).getPayMent(),msg.TModel.get(i).getOrderItem().get(j).getSkuValue1(),msg.TModel.get(i).getOrderItem().get(j).getSkuValue2(),msg.TModel.get(i).getOrderItem().get(j).getSkuValue3(),msg.TModel.get(i).getOrderItem().get(j).getBarcodeId())));
                        }
                    }
                    orderLstAdapter=new VipOrderLstAdapter(R.layout.item_vip_order_content, R.layout.item_vip_order_header, mData);
                    orderLstAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            VipOrderListSection mySection = mData.get(position);
                            if (mySection.isHeader){

                            } else{
                                Bundle bundle=new Bundle();
                                bundle.putString("barcodeId",mySection.t.getBarcodeId()+"");
                                Skip.mNextFroData(getActivity(), StyleRecommendActivity.class,bundle);
                            }
                        }
                    });

                    rv_shopping_record_list.setAdapter(orderLstAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                tv_not_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(getContext(),"Msg:"+msg.Message);
        }
    }

    @Override
    public void fetchData() {

    }
}

