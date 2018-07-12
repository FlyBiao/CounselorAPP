package com.cesaas.android.counselor.order.statistics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.statistics.bean.AffiliateTopBean;
import com.cesaas.android.counselor.order.statistics.bean.DirectSellingTopBean;
import com.cesaas.android.counselor.order.statistics.bean.ResultGetchievementReportBean;
import com.cesaas.android.counselor.order.statistics.bean.ResultJoinAndDSalesBean;
import com.cesaas.android.counselor.order.statistics.net.JoinAndDSalesNet;
import com.cesaas.android.counselor.order.store.bean.StoreDisplayBean;
import com.cesaas.android.counselor.order.task.net.PublicTaskListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.utils.Utility;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 直营加盟TOP页面
 */
public class AffiliateTopActivity extends BasesActivity implements View.OnClickListener{

    private ListView directSellingListView;//加载更多
    private ArrayList<DirectSellingTopBean> directSellingTopList= new ArrayList<DirectSellingTopBean>();

    private LinearLayout llBack;
    private TextView tvBaseTitle;
    private ScrollView scrollView;

    private JoinAndDSalesNet joinAndDSalesNet;
    private List<ResultJoinAndDSalesBean.JoinAndDSalesBean> joinAndDSalesBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiliate_top);

        joinAndDSalesNet=new JoinAndDSalesNet(mContext);
        joinAndDSalesNet.setData();

        initView();
    }

    /**
     * 接收直营加盟报表数据消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultJoinAndDSalesBean msg) {
        if(msg.IsSuccess==true){
            joinAndDSalesBeanList=new ArrayList<>();
            joinAndDSalesBeanList.addAll(msg.TModel);
            setAdapter();
        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败:"+msg.Message);
        }
    }


    public void setAdapter(){
        directSellingListView.setAdapter(new CommonAdapter<ResultJoinAndDSalesBean.JoinAndDSalesBean>(mContext,R.layout.item_direct_selling_top,joinAndDSalesBeanList) {

            @Override
            public void convert(ViewHolder holder, ResultJoinAndDSalesBean.JoinAndDSalesBean bean, int postion) {
                holder.setText(R.id.sequence,bean.TopNumber+"");
                holder.setText(R.id.tv_title,bean.TopShopName);
                holder.setText(R.id.price,bean.TopGain+"");
            }

        });
        Utility.setListViewHeightBasedOnChildren(directSellingListView);
    }

    private void initView() {
        scrollView= (ScrollView) findViewById(R.id.scrollView);
        directSellingListView=(ListView) findViewById(R.id.load_more_direct_selling_top_list);
        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("直营Top10");

        llBack.setOnClickListener(this);
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
