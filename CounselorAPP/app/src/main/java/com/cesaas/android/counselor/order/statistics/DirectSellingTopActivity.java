package com.cesaas.android.counselor.order.statistics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.progress.CustomArcCircleProgress;
import com.cesaas.android.counselor.order.statistics.bean.AffiliateTopBean;
import com.cesaas.android.counselor.order.statistics.bean.DirectSellingTopBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

import java.util.ArrayList;

/**
 * 直营TOP页面
 */
public class DirectSellingTopActivity extends BasesActivity {

    private ListView mLoadMoreListView;//加载更多

    private ArrayList<DirectSellingTopBean> directSellingTopList= new ArrayList<DirectSellingTopBean>();

    private LinearLayout llBack;
    private TextView tvBaseTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_selling);

        initView();
        initData();
        setAdapter();
    }

    public void initData(){
        tvBaseTitle.setText("直营TOP10");
        for (int i = 0; i < 10; i++) {
            DirectSellingTopBean bean=new DirectSellingTopBean();
            bean.setSequence(i+1);
            bean.setTitle("直销TOP10"+i);
            bean.setPrice(4755.89);
            directSellingTopList.add(bean);
        }
    }

    public void setAdapter(){
        mLoadMoreListView.setAdapter(new CommonAdapter<DirectSellingTopBean>(mContext,R.layout.item_direct_selling_top,directSellingTopList) {

            @Override
            public void convert(ViewHolder holder, DirectSellingTopBean bean, int postion) {
                holder.setText(R.id.sequence,bean.getSequence()+"");
                holder.setText(R.id.tv_title,bean.getTitle());
                holder.setText(R.id.price,bean.getPrice()+"");
            }

        });
    }

    /**
     * 设置返回上一个页面
     */
    public void setBack(){
        llBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    private void initView() {
        mLoadMoreListView=(ListView) findViewById(R.id.load_more_direct_selling_top_list);
        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
        setBack();
    }
}
