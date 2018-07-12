package com.cesaas.android.counselor.order.shopmange;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
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
import com.cesaas.android.counselor.order.custom.popupwindow.TopMiddlePopup;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.VipSearchActivity;
import com.cesaas.android.counselor.order.member.bean.SelectVipListBean;
import com.cesaas.android.counselor.order.shopmange.adapter.AllShopAdapter;
import com.cesaas.android.counselor.order.shopmange.adapter.SelectAllShopAdapter;
import com.cesaas.android.counselor.order.shopmange.bean.AllShopBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultBigSortAllBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultGetAllShopBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultSortAllBean;
import com.cesaas.android.counselor.order.shopmange.bean.SelectShopBean;
import com.cesaas.android.counselor.order.shopmange.bean.SortAllBean;
import com.cesaas.android.counselor.order.shopmange.fragment.AllShopFragment;
import com.cesaas.android.counselor.order.shopmange.net.GetAllShopNet;
import com.cesaas.android.counselor.order.shopmange.net.GetBigSortAllNet;
import com.cesaas.android.counselor.order.shopmange.net.GetSortAllNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.lidroid.xutils.db.annotation.Check;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 商品选择
 */
public class ShopSelectActivity extends BasesActivity implements View.OnClickListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private LinearLayout llBaseBack,ll_top_win;
    private TextView tvBaseTitle,tvRightTitle;
    private ImageView iv_bottom_arrow_screen;
    private MClearEditText et_searchTxt;

    private SelectShopTopMiddlePopup middlePopup;
    public static int screenW, screenH;

    private int pageIndex=1;
    private int shopType=0;
    private int resultCode = 301;
    private int searchResultCode=1000;

    private GetSortAllNet mGetSortAllNet;
    private GetBigSortAllNet mGetBigSortAllNet;
    private GetAllShopNet mGetAllShopNet;
    private SelectAllShopAdapter mAllShopAdapter;

    private List<String> bigSortAllBeanList;
    private List<String> sortAllBeanList;
    private List<AllShopBean> mAllShopBeanList;

    private List<SelectShopBean> selectShopBeen=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_select);

        initView();

        mGetAllShopNet=new GetAllShopNet(mContext);
        mGetAllShopNet.setData(pageIndex,shopType);

        mGetBigSortAllNet=new GetBigSortAllNet(mContext);
        mGetBigSortAllNet.setData(1);

        mGetSortAllNet=new GetSortAllNet(mContext);
        mGetSortAllNet.setData(2);


        getScreenPixels();
    }

    public void onEventMainThread(ResultBigSortAllBean msg) {
        if(msg.IsSuccess==true){
            bigSortAllBeanList=new ArrayList<>();
            bigSortAllBeanList.add("全部");
            for(int i=0;i<msg.TModel.size();i++){
                bigSortAllBeanList.add(msg.TModel.get(i).getTitle());
            }

        }else{
            ToastFactory.getLongToast(mContext,"获取商品大类失败！"+msg.Message);
        }

    }

    public void onEventMainThread(ResultSortAllBean msg) {
        if(msg.IsSuccess==true){
            sortAllBeanList=new ArrayList<>();
            sortAllBeanList.add("全部");
            for(int i=0;i<msg.TModel.size();i++){
                sortAllBeanList.add(msg.TModel.get(i).getTitle());
            }

        }else{
            ToastFactory.getLongToast(mContext,"获取商品小类失败！"+msg.Message);
        }
    }


    public void onEventMainThread(ResultGetAllShopBean msg) {
        if(msg.IsSuccess==true){
            mAllShopBeanList=new ArrayList<>();
            mAllShopBeanList.addAll(msg.TModel);

            mAllShopAdapter=new SelectAllShopAdapter(mAllShopBeanList,mContext);
            mAllShopAdapter.setOnItemClickListener(onItemClickListener);
            mSwipeMenuRecyclerView.setAdapter(mAllShopAdapter);

        }else{
            ToastFactory.getLongToast(mContext,"获取商品数据失败！"+msg.Message);
        }
    }

    /**
     * Item 点击监听
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
//            Intent mIntent = new Intent();
//            mIntent.putExtra("id", mAllShopBeanList.get(position).getId()+"");
//            mIntent.putExtra("title",mAllShopBeanList.get(position).getTitle());
//            mIntent.putExtra("price",mAllShopBeanList.get(position).getStylePrice()+"");
//            mIntent.putExtra("image_url",mAllShopBeanList.get(position).getImageUrl());
//            // 设置结果，并进行传送
//            setResult(resultCode, mIntent);
//            finish();
        }
    };


    private void initView() {
        et_searchTxt= (MClearEditText) findViewById(R.id.et_searchTxt);

        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView= (SwipeMenuRecyclerView) findViewById(R.id.recycler_sales_talk_view);
        iv_bottom_arrow_screen= (ImageView) findViewById(R.id.iv_bottom_arrow_screen);

        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        ll_top_win= (LinearLayout) findViewById(R.id.ll_top_win);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvRightTitle.setText("确定");
        tvRightTitle.setVisibility(View.VISIBLE);
        tvBaseTitle.setText("商品选择");

        llBaseBack.setOnClickListener(this);
        iv_bottom_arrow_screen.setOnClickListener(this);
        tvRightTitle.setOnClickListener(this);
        et_searchTxt.setOnClickListener(this);
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
                    mGetAllShopNet.setData(pageIndex,shopType);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    /**
     * 获取屏幕的宽和高
     */
    public void getScreenPixels() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenW = metrics.widthPixels;
        screenH = metrics.heightPixels;
    }

    /**
     * 设置弹窗
     *
     * @param type
     */
    private void setPopup(int type) {
        middlePopup = new SelectShopTopMiddlePopup(mContext, screenW, screenH, bigSortAllBeanList,getYearItems(),getSeasonItems(),sortAllBeanList, type);
    }


   //设置弹窗内容
    private List<String> getYearItems() {
        ArrayList<String> items = new ArrayList<>();
        items.add("全部");

        items.add("2017");

        items.add("2016");

        items.add("2015");

        items.add("2014");

        return items;
    }

    private List<String> getSeasonItems() {
        ArrayList<String> items = new ArrayList<>();

        items.add("全部");

        items.add("春");

        items.add("夏");

        items.add("秋");

        items.add("冬");
        return items;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                finish();
                break;
            case R.id.iv_bottom_arrow_screen://下拉筛选
                setPopup(0);
                middlePopup.show(ll_top_win);
                break;

            case R.id.tv_base_title_right://确定
                if(selectShopBeen.size()!=0){
                    Intent mIntent = new Intent();
                    mIntent.putExtra("selectList",(Serializable)selectShopBeen);
                    // 设置结果，并进行传送
                    setResult(resultCode, mIntent);
                    finish();
                }else{
                    ToastFactory.getLongToast(mContext,"请选择商品！");
                }
                break;
            case R.id.et_searchTxt://查询商品
//                Skip.mActivityResult(mActivity,ShopSearchActivity.class,searchResultCode);
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
                et_searchTxt.setText(searchValue);

                mGetAllShopNet=new GetAllShopNet(mContext);
                mGetAllShopNet.setData(pageIndex,shopType,searchValue);
            }
        }
    }



    /**
     * Author FGB
     * Description 选择所有商品Adapter
     * Created 2017/4/27 17:57
     * Version 1.0
     */
    public class SelectAllShopAdapter extends SwipeMenuAdapter<DefaultViewHolder> {

        private List<AllShopBean> mAllShopBeanList;
        private OnItemClickListener mOnItemClickListener;
        Context mContext;

        public SelectAllShopAdapter(List<AllShopBean> mAllShopBeanList, Context mContext){
            this.mAllShopBeanList=mAllShopBeanList;
            this.mContext=mContext;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sel_all_shop, parent, false);
        }

        @Override
        public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            return new DefaultViewHolder(realContentView);
        }

        @Override
        public void onBindViewHolder(final DefaultViewHolder holder, final int position) {

            holder.setData(mAllShopBeanList.get(position).getTitle(),mAllShopBeanList.get(position).getNo()
                    ,mAllShopBeanList.get(position).getImageUrl(),mAllShopBeanList.get(position).getStylePrice(),
                    mAllShopBeanList.get(position).getSalesPrice(),mAllShopBeanList.get(position).getSalesVolume());
            holder.setOnItemClickListener(mOnItemClickListener);

            //设置选框监听
            holder.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked== true) {//选中
                        SelectShopBean bean=new SelectShopBean();
                        bean.setId(mAllShopBeanList.get(position).getId());
                        bean.setTitle(mAllShopBeanList.get(position).getTitle());
                        bean.setSalesPrice(mAllShopBeanList.get(position).getSalesPrice());
                        bean.setImageUrl(mAllShopBeanList.get(position).getImageUrl());
                        bean.setPosition(position);
                        selectShopBeen.add(bean);

                    }else{//未选中
                        for (Iterator it = selectShopBeen.iterator(); it.hasNext();){
                            SelectShopBean value= (SelectShopBean) it.next();
                            if(value.getPosition()==position){
                                it.remove();
                            }
                        }
                        Log.i(Constant.TAG,"未选中:"+selectShopBeen.size());
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
        ImageView iv_shop_img;
        CheckBox cbCheckBox;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_shop_title = (TextView) itemView.findViewById(R.id.tv_shop_title);
            tv_shop_style_code= (TextView) itemView.findViewById(R.id.tv_shop_style_code);
            iv_shop_img= (ImageView) itemView.findViewById(R.id.iv_shop_img);
            tv_shop_style_price= (TextView) itemView.findViewById(R.id.tv_shop_style_price);
            tv_shop_sales_price= (TextView) itemView.findViewById(R.id.tv_shop_sales_price);
            cbCheckBox= (CheckBox) itemView.findViewById(R.id.cbCheckBox);
            tv_shop_sales_volume= (TextView) itemView.findViewById(R.id.tv_shop_sales_volume);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,String StyleCode,String ImageUrl,double StylePrice,double SalesPrice,int SalesVolume) {

            this.tv_shop_title.setText(title);
            this.tv_shop_style_code.setText(StyleCode);
            this.tv_shop_style_price.setText("￥"+StylePrice);
            this.tv_shop_sales_price.setText("￥"+SalesPrice);
            this.tv_shop_sales_volume.setText(SalesVolume+"");
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
