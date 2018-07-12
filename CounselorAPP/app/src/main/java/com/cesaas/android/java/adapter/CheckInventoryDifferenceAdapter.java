package com.cesaas.android.java.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.bean.GetStyleNoByPidBean;
import com.cesaas.android.java.bean.inventory.InventoryGetDiffSubListBean;
import com.cesaas.android.java.bean.inventory.ResultGetStyleNoByPidBean;
import com.cesaas.android.java.net.GetStyleNoByPidNet;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 查看盘点差异
 * Created at 2017/8/28 9:51
 * Version 1.0
 */
public class CheckInventoryDifferenceAdapter extends BaseQuickAdapter<InventoryGetDiffSubListBean, BaseViewHolder> {

    private List<InventoryGetDiffSubListBean> mData;
    private List<GetStyleNoByPidBean> mData2=new ArrayList<>();
    private Context mContext;
    private  Activity mActivity;
    private boolean isShow=false;

    private GetStyleNoByPidAdapter getStyleNoByPidAdapter;
    private RecyclerView rv_list;

    public CheckInventoryDifferenceAdapter(List<InventoryGetDiffSubListBean> mData, Activity activity, Context ct) {
        super( R.layout.item_check_difference,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=ct;
    }

    public CheckInventoryDifferenceAdapter(List<InventoryGetDiffSubListBean> mData, List<GetStyleNoByPidBean> mData2,Activity activity, Context ct) {
        super( R.layout.item_check_difference,mData);
        this.mData=mData;
        this.mData2=mData2;
        this.mActivity=activity;
        this.mContext=ct;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final InventoryGetDiffSubListBean item) {
        rv_list=helper.getView(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        helper.setText(R.id.tv_show_info,R.string.fa_sort_desc);
        helper.setTypeface(R.id.tv_show_info, App.font);

        helper.setText(R.id.tv_stylyNo,item.getBarcodeNo());
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_inventoryNum,String.valueOf(item.getInventoryNum()));
        helper.setText(R.id.tv_differenceNum,String.valueOf(item.getDifferenceNum()));

        if(item.getImageUrl()!=null && !"".equals(item.getImageUrl()) && !"NULL".equals(item.getImageUrl())){
            // 加载网络图片
            Glide.with(mContext).load(item.getImageUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_img));
        }else{
            helper.setImageResource(R.id.iv_img,R.mipmap.default_image);
        }

        if(mData2.size()!=0){
            helper.setVisible(R.id.ll_show,true);
//            getStyleNoByPidAdapter=new GetStyleNoByPidAdapter(mData2,mActivity,mContext);
//            rv_list.setAdapter(getStyleNoByPidAdapter);
        }

        helper.setOnClickListener(R.id.ll_show_info, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShow==false){
                    isShow=true;
                    helper.setVisible(R.id.ll_show,true);
                    helper.setText(R.id.tv_show_info,R.string.fa_sort_up);
                    helper.setTypeface(R.id.tv_show_info, App.font);
                }else{
                    isShow=false;
                    helper.setVisible(R.id.ll_show,false);
                    helper.setText(R.id.tv_show_info,R.string.fa_sort_desc);
                    helper.setTypeface(R.id.tv_show_info, App.font);
                }

                GetStyleNoByPidNet getStyleNoByPidNet=new GetStyleNoByPidNet(mContext);
                getStyleNoByPidNet.setData(1,item.getpId());
            }
        });

    }

    /**
     * 接收根据pid获取款号列表
     * @param msg
     */
    public void onEventMainThread(ResultGetStyleNoByPidBean msg) {
        Log.i("test1","===进来===="+msg.arguments.resp.StyleNoList.value.size());
        if(msg.arguments.resp.errorCode==1){
            if(msg.arguments.resp.StyleNoList.value!=null && msg.arguments.resp.StyleNoList.value.size()!=0){
//                getStyleNoByPidAdapter=new GetStyleNoByPidAdapter(msg.arguments.resp.StyleNoList.value,mActivity,mContext);
                rv_list.setAdapter(getStyleNoByPidAdapter);
            }
        }else{
            ToastFactory.getLongToast(mContext,msg.arguments.resp.errorMessage);
        }
    }
}
