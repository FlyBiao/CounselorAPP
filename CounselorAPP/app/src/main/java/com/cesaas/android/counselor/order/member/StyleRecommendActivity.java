package com.cesaas.android.counselor.order.member;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.adapter.StyleRecommendAdapter;
import com.cesaas.android.counselor.order.member.bean.ResultStyleRecommendtBean;
import com.cesaas.android.counselor.order.member.bean.StyleRecommendtBean;
import com.cesaas.android.counselor.order.net.StyleRecommendNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * StyleRecommend
 */
public class StyleRecommendActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle;
    private LinearLayout llBack;
    private RecyclerView rv_style_recommend_list;

    private StyleRecommendNet recommendNet;
    private List<StyleRecommendtBean> recommendtBeanList;
    private StyleRecommendAdapter adapter;

    private String barcodeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_recommend);
        initView();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            barcodeId=bundle.getString("barcodeId");

        }

        initData();
    }

    public void onEventMainThread(final ResultStyleRecommendtBean msg){
        if(msg.IsSuccess!=false){

            if(msg.TModel!=null && msg.TModel.size()!=0){
                recommendtBeanList=new ArrayList<>();
                recommendtBeanList=msg.TModel;

                adapter=new StyleRecommendAdapter(recommendtBeanList);
                rv_style_recommend_list.setAdapter(adapter);
            }else{
                ToastFactory.getLongToast(mContext,"暂无数据!");
            }

        }else{

        }
    }

    private void initData() {
        recommendNet=new StyleRecommendNet(mContext);
        recommendNet.setData(1,barcodeId);
    }

    private void initView() {
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("商品详情");
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        rv_style_recommend_list= (RecyclerView) findViewById(R.id.rv_style_recommend_list);
        rv_style_recommend_list.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
        }
    }
}
