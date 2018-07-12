package com.cesaas.android.counselor.order.shopmange;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.popupwindow.SelectShopTopMiddlePopup;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.VipSearchActivity;
import com.cesaas.android.counselor.order.member.bean.SelectVipListBean;
import com.cesaas.android.counselor.order.shopmange.adapter.ShopMatchAdapter;
import com.cesaas.android.counselor.order.shopmange.bean.ResultBigSortAllBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultShopMatchBean;
import com.cesaas.android.counselor.order.shopmange.bean.SelectShopMatchBean;
import com.cesaas.android.counselor.order.shopmange.bean.ShopMatchBean;
import com.cesaas.android.counselor.order.shopmange.net.ShopMatchNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 商品搭配列表
 */
public class ShopMatchListActivity extends BasesActivity implements View.OnClickListener{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private LinearLayout llBaseBack;
    private TextView tvBaseTitle,tvRightTitle;
    private MClearEditText etContent;

    private int pageIndex=1;
    private int resultCode = 302;
    private int searchResultCode=1000;

    private List<ShopMatchBean> matchBeanList;
    private ShopMatchNet shopMatchNet;
    private ShopMatchAdapter matchAdapter;

    private List<SelectShopMatchBean> selectShopMatchBeen=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_match_list);

        initView();
        initData();
    }

    public void onEventMainThread(ResultShopMatchBean msg) {
        if(msg.IsSuccess==true){
            matchBeanList=new ArrayList<>();
            matchBeanList.addAll(msg.TModel);

            matchAdapter=new ShopMatchAdapter(matchBeanList,mContext);
            matchAdapter.setOnItemClickListener(onItemClickListener);
            mSwipeMenuRecyclerView.setAdapter(matchAdapter);

        }else{
            ToastFactory.getLongToast(mContext,"null!"+msg.Message);
        }
    }

    /**
     * Item 点击监听
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
//            Intent mIntent = new Intent();
//            mIntent.putExtra("id", matchBeanList.get(position).getId()+"");
//            mIntent.putExtra("title",matchBeanList.get(position).getTitle());
//            mIntent.putExtra("sell_point",matchBeanList.get(position).getEvaluate()+"");
//            mIntent.putExtra("image_url",matchBeanList.get(position).getImageUrl());
//            // 设置结果，并进行传送
//            setResult(resultCode, mIntent);
//            finish();
        }
    };

    private void initData(){
        shopMatchNet=new ShopMatchNet(mContext);
        shopMatchNet.setData(pageIndex,"","");
    }

    private void initView() {
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView= (SwipeMenuRecyclerView) findViewById(R.id.recycler_shop_match_view);

        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        etContent= (MClearEditText) findViewById(R.id.et_search_value);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvRightTitle.setText("确定");
        tvRightTitle.setVisibility(View.VISIBLE);
        tvBaseTitle.setText("商品选择");

        llBaseBack.setOnClickListener(this);
        tvRightTitle.setOnClickListener(this);
        etContent.setOnClickListener(this);
    }

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
                    initData();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:
                Intent mIntent = new Intent();
                mIntent.putExtra("selectList",(Serializable)selectShopMatchBeen);
                // 设置结果，并进行传送
                setResult(resultCode, mIntent);
                finish();
                break;
            case R.id.et_search_value:
                Skip.mActivityResult(mActivity,ShopMatchSearchActivity.class,searchResultCode);
                break;
        }
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(data!=null){
                String searchValue = data.getStringExtra("searchValue");
                String seasonSel = data.getStringExtra("seasonSel");
                etContent.setText(searchValue);
                shopMatchNet=new ShopMatchNet(mContext);

                if(seasonSel!=null){//季节搜索
                    shopMatchNet.setData(pageIndex,"",searchValue);
                }else{//关键字搜索
                    shopMatchNet.setData(pageIndex,searchValue,"");
                }
            }
        }
    }

    /**
     * Author FGB
     * Description 商品搭配Adapter
     * Created 2017/4/27 17:57
     * Version 1.0
     */
    public class ShopMatchAdapter extends SwipeMenuAdapter<DefaultViewHolder> {

        private List<ShopMatchBean> mAllShopBeanList;
        private OnItemClickListener mOnItemClickListener;
        private Context mContext;

        public ShopMatchAdapter(List<ShopMatchBean> mAllShopBeanList, Context mContext){
            this.mAllShopBeanList=mAllShopBeanList;
            this.mContext=mContext;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_match, parent, false);
        }

        @Override
        public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            return new DefaultViewHolder(realContentView);
        }

        @Override
        public void onBindViewHolder(final DefaultViewHolder holder, final int position) {

            holder.setData(mAllShopBeanList.get(position).getTitle(),mAllShopBeanList.get(position).getImageUrl()
                    ,mAllShopBeanList.get(position).getFocus(),mAllShopBeanList.get(position).getLike(),
                    mAllShopBeanList.get(position).getEvaluate(),mAllShopBeanList.get(position).getFAB());
            holder.setOnItemClickListener(mOnItemClickListener);

            //设置选框监听
            holder.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked== true) {//选中

                        SelectShopMatchBean bean=new SelectShopMatchBean();
                        bean.setId(mAllShopBeanList.get(position).getId());
                        bean.setTitle(mAllShopBeanList.get(position).getTitle());
                        bean.setImageUrl(mAllShopBeanList.get(position).getImageUrl());
                        bean.setEvaluate(mAllShopBeanList.get(position).getEvaluate());
                        bean.setPosition(position);

                        selectShopMatchBeen.add(bean);

                    }else{//未选中
                        for (Iterator it = selectShopMatchBeen.iterator(); it.hasNext();){
                            SelectShopMatchBean value= (SelectShopMatchBean) it.next();
                            if(value.getPosition()==position){
                                it.remove();
                            }
                        }
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mAllShopBeanList == null ? 0 : mAllShopBeanList.size();
        }

    }

    /**
     * DefaultViewHolder 静态类
     */
    public class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_shop_title,tv_shop_style_code,tv_shop_style_price,tv_shop_sales_price,tv_shop_sales_volume;
        TextView tv_focus,tv_like,tv_evaluate,tv_fba;
        CheckBox cbCheckBox;
        ImageView iv_shop_img;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_shop_title = (TextView) itemView.findViewById(R.id.tv_shop_title);
            tv_shop_style_code= (TextView) itemView.findViewById(R.id.tv_shop_style_code);
            iv_shop_img= (ImageView) itemView.findViewById(R.id.iv_shop_img);
            tv_shop_style_price= (TextView) itemView.findViewById(R.id.tv_shop_style_price);
            tv_shop_sales_price= (TextView) itemView.findViewById(R.id.tv_shop_sales_price);
            tv_shop_sales_volume= (TextView) itemView.findViewById(R.id.tv_shop_sales_volume);
            tv_focus= (TextView) itemView.findViewById(R.id.tv_focus);
            tv_fba= (TextView) itemView.findViewById(R.id.tv_fba);
            tv_like= (TextView) itemView.findViewById(R.id.tv_like);
            cbCheckBox= (CheckBox) itemView.findViewById(R.id.cbCheckBox);
            tv_evaluate= (TextView) itemView.findViewById(R.id.tv_evaluate);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,String ImageUrl ,int focus,int like,int evaluate,String fba) {

            this.tv_focus.setText(focus+"");
            this.tv_like.setText(like+"");
            this.tv_evaluate.setText(evaluate+"");
            this.tv_shop_title.setText(title);
            this.tv_fba.setText(fba);
            Glide.with(mContext).load(ImageUrl).placeholder(R.mipmap.loading).into(iv_shop_img);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
