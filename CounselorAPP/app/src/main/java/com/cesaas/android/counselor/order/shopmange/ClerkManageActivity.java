package com.cesaas.android.counselor.order.shopmange;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.MemberDistributionDetailActivity;
import com.cesaas.android.counselor.order.member.MemberNewDistributionDetailActivity;
import com.cesaas.android.counselor.order.member.net.service.FaceListNet;
import com.cesaas.android.counselor.order.shopmange.adapter.ClerkMangeAdapter;
import com.cesaas.android.counselor.order.shopmange.bean.FilterClerkTypeBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultAgreeClerkBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultChangeWorkBean;
import com.cesaas.android.counselor.order.shopmange.net.AgreeClerkNet;
import com.cesaas.android.counselor.order.shopmange.net.ChangeWorkNet;
import com.cesaas.android.counselor.order.shoptask.bean.CounselorListBean;
import com.cesaas.android.counselor.order.shoptask.bean.ResultCounselorListBean;
import com.cesaas.android.counselor.order.shoptask.bean.SelectCounselorListBean;
import com.cesaas.android.counselor.order.shoptask.net.CounselorListNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.utils.db.ClerkFilterSQLiteDatabaseUtils;
import com.cesaas.android.counselor.order.utils.db.DBConstant;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.flybiao.materialdialog.MaterialDialog;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 店员管理列表
 */
public class ClerkManageActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack;
    private TextView mTextViewTitle,mTextViewRightTitme;
    private TextView tv_morning,tv_noon,tv_afternoon;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private TextView tv_not_data;
    private MClearEditText et_search_content;

    private CounselorListNet mCounselorListNet;
    private ClerkMangeAdapter mangeAdapter;
    private List<CounselorListBean> mCounselorListBeanList;
    List<SelectCounselorListBean> selectClerkList=new ArrayList<>();
    private ArrayList<TextView> tvs=new ArrayList<>();

    private Typeface font;
    private int pageIndex=1;
    private int index=0;
    private int searchResultCode=1000;
    private int searchResultFilterCode=1001;
    private MaterialDialog mMaterialDialog;
    private String searchValue=null;

    private ClerkFilterSQLiteDatabaseUtils databaseUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_manage);
        initView();
        initDB();
        mCounselorListNet=new CounselorListNet(mContext);
        mCounselorListNet.setData(pageIndex,1,"");
    }

    public void onEventMainThread(final ResultCounselorListBean msg) {
        if(msg.IsSuccess==true){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                try{
                    tv_not_data.setVisibility(View.GONE);
                    mCounselorListBeanList=new ArrayList<>();
                    mCounselorListBeanList.addAll(msg.TModel);
                    mangeAdapter=new ClerkMangeAdapter(mCounselorListBeanList,mContext,font);
                    mangeAdapter.setOnItemClickListener(onItemClickListener);
                    mSwipeMenuRecyclerView.setAdapter(mangeAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                tv_not_data.setText("暂无数据！");
                tv_not_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_data.setText("暂无数据！");
            tv_not_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(mContext,"null:"+msg.Message);
        }
    }

    public void onEventMainThread(List<SelectCounselorListBean> selectCounselorListBeen) {
        selectClerkList=new ArrayList<>();
        selectClerkList=selectCounselorListBeen;
    }

    public void onEventMainThread(FilterClerkTypeBean msg) {
        if(searchValue!=null){
            mCounselorListNet.setData(pageIndex,1,msg.getFilterAuditType(),searchValue);
        }else{
            mCounselorListNet.setData(pageIndex,1,msg.getFilterAuditType(),"");
        }

    }

    public void onEventMainThread(ResultAgreeClerkBean bean) {
        //启用
        if(bean.IsSuccess==true){
            ToastFactory.getLongToast(mContext,"该店员通过成功");
            mCounselorListNet.setData(pageIndex,1,"");
        }else{
            ToastFactory.getLongToast(mContext,"该店员通过失败"+bean.Message);
        }
    }

    public void onEventMainThread(ResultChangeWorkBean bean) {
        //停职
        if(bean.IsSuccess==true){
            ToastFactory.getLongToast(mContext,"该店员停职成功");
            mCounselorListNet.setData(pageIndex,0,"");
        }else{
            ToastFactory.getLongToast(mContext,"该店员停职失败"+bean.Message);
        }
    }

    private void initDB(){
        databaseUtils=new ClerkFilterSQLiteDatabaseUtils(mContext,DBConstant.DB,null, DBConstant.VERSION);
        databaseUtils.createDB(mContext,DBConstant.DB,DBConstant.VERSION);
        //查询数据
        databaseUtils.selectData(mContext);
    }

    private void initView() {
        font= Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");//记得加上这句
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_clerk_list_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        // 添加滚动监听。
        //mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);
        // 设置菜单创建器。
        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

        et_search_content= (MClearEditText) findViewById(R.id.et_search_content);
        tv_not_data= (TextView)findViewById(R.id.tv_not_data);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewTitle.setText("店员管理");
        mTextViewRightTitme= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewRightTitme.setVisibility(View.GONE);
        mTextViewRightTitme.setText("设置");

        tv_morning= (TextView) findViewById(R.id.tv_morning);
        tv_morning.setOnClickListener(this);
        tv_noon= (TextView) findViewById(R.id.tv_noon);
        tv_noon.setOnClickListener(this);
        tv_afternoon= (TextView) findViewById(R.id.tv_afternoon);
        tv_afternoon.setOnClickListener(this);
        tvs.add(tv_morning);
        tvs.add(tv_noon);
        tvs.add(tv_afternoon);

        llBaseBack.setOnClickListener(this);
        llBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
        mTextViewRightTitme.setOnClickListener(this);
        mTextViewRightTitme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mActivityResult(mActivity,FilterClerkActivity.class,searchResultFilterCode);
            }
        });
        et_search_content.setOnClickListener(this);
        et_search_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mActivityResult(mActivity,ClerkSearchActivity.class,searchResultCode);
            }
        });
    }

    /**
     * 菜单创建器。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_height);
            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem addItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_green)
                        .setImage(R.mipmap.play_circle)
                        .setText("通过")
                        .setTextColor(Color.WHITE)
                        .setWidth(width) // 宽度。
                        .setHeight(height); // 高度。
                swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。

                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setImage(R.mipmap.pause)
                        .setText("停职") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width) // 宽度。
                        .setHeight(height); // 高度。
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

                SwipeMenuItem closeItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_purple)
                        .setImage(R.mipmap._cancel)
                        .setText("取消")
                        .setTextColor(Color.WHITE)
                        .setWidth(width) // 宽度。
                        .setHeight(height); // 高度。
                swipeRightMenu.addMenuItem(closeItem); // 添加一个按钮到右侧菜单。
            }
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
         *                        #RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(final Closeable closeable, final int adapterPosition, int menuPosition, int direction) {
            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
           switch (menuPosition){
               case 0://启用
                   if(mCounselorListBeanList.get(adapterPosition).getCOUNSELOR_INUSE()==-1){
                       mMaterialDialog=new MaterialDialog(mContext);
                       if (mMaterialDialog != null) {
                           mMaterialDialog.setTitle("温馨提示！")
                                   .setMessage("确定启用该店员吗？")
                                   //mMaterialDialog.setBackgroundResource(R.drawable.background);
                                   .setPositiveButton("确定", new View.OnClickListener() {
                                       @Override public void onClick(View v) {
                                           //执行确定操作
                                           AgreeClerkNet agreeClerkNet=new AgreeClerkNet(mContext);
                                           agreeClerkNet.setData(mCounselorListBeanList.get(adapterPosition).getCOUNSELOR_ID());
                                           closeable.smoothCloseMenu();// 关闭被点击的菜单。
                                           mMaterialDialog.dismiss();
                                       }
                                   }).setNegativeButton("返回", new View.OnClickListener() {
                               @Override public void onClick(View v) {
                                   closeable.smoothCloseMenu();// 关闭被点击的菜单。
                                   mMaterialDialog.dismiss();
//
                               }}).setCanceledOnTouchOutside(true).show();
                       }
                   }else{
                        ToastFactory.getLongToast(mContext,"该店员已审核不能重复操作！");
                       closeable.smoothCloseMenu();// 关闭被点击的菜单
                   }

                   break;
               case 1://停职
                   if(mCounselorListBeanList.get(adapterPosition).getCOUNSELOR_INUSE()==1){
                       mMaterialDialog=new MaterialDialog(mContext);
                       if (mMaterialDialog != null) {
                           mMaterialDialog.setTitle("温馨提示！")
                                   .setMessage("确定该店员需要停职吗？")
                                   //mMaterialDialog.setBackgroundResource(R.drawable.background);
                                   .setPositiveButton("确定", new View.OnClickListener() {
                                       @Override public void onClick(View v) {
                                           //执行确定操作
                                           ChangeWorkNet changeWorkNet=new ChangeWorkNet(mContext);
                                           changeWorkNet.setData(mCounselorListBeanList.get(adapterPosition).getCOUNSELOR_ID());
                                           closeable.smoothCloseMenu();// 关闭被点击的菜单。
                                           mMaterialDialog.dismiss();
                                       }
                                   }).setNegativeButton("返回", new View.OnClickListener() {
                               @Override public void onClick(View v) {
                                   closeable.smoothCloseMenu();// 关闭被点击的菜单。
                                   mMaterialDialog.dismiss();
//
                               }}).setCanceledOnTouchOutside(true).show();
                       }
                   }else{
                       ToastFactory.getLongToast(mContext,"该店员未启用或未审核！");
                       closeable.smoothCloseMenu();// 关闭被点击的菜单
                   }
                   break;
               case 2://取消
                   closeable.smoothCloseMenu();// 关闭被点击的菜单。
                   break;
           }
        }
    };

    /**
     * OnItemClickListener
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            //跳转到店员详情页面
            bundle.putInt("CounselorID",mCounselorListBeanList.get(position).getCOUNSELOR_ID());
//            Skip.mNextFroData(mActivity,MemberDistributionDetailActivity.class,bundle);
            Skip.mNextFroData(mActivity,MemberNewDistributionDetailActivity.class,bundle);
        }
    };

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
                    switch (index){
                        case 0:
                            mCounselorListNet=new CounselorListNet(mContext);
                            mCounselorListNet.setData(pageIndex,1,"");
                            break;
                        case 1:
                            mCounselorListNet=new CounselorListNet(mContext);
                            mCounselorListNet.setData(pageIndex,-1,"");
                            break;
                        case 2:
                            mCounselorListNet=new CounselorListNet(mContext);
                            mCounselorListNet.setData(pageIndex,0,"");
                            break;
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };


    /**
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {// 手指不能向上滑动了
                // TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。
//                Toast.makeText(this, "滑到最底部了，去加载更多吧！", Toast.LENGTH_SHORT).show();
//                size += 50;
//                for (int i = size - 50; i < size; i++) {
//                    mDataList.add("我是第" + i + "个。");
////                }
//                mVipListNet.setData(pageIndex+1);
//                mVipListAdapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    public void onClick(View v) {
        index=-1;
        switch (v.getId()){
            case R.id.tv_morning:
                index=0;
                mCounselorListNet=new CounselorListNet(mContext);
                mCounselorListNet.setData(pageIndex,1,"");
                break;
            case R.id.tv_noon:
                index=1;
                mCounselorListNet=new CounselorListNet(mContext);
                mCounselorListNet.setData(pageIndex,-1,"");
                break;
            case R.id.tv_afternoon:
                index=2;
                mCounselorListNet=new CounselorListNet(mContext);
                mCounselorListNet.setData(pageIndex,0,"");
                break;
        }
        setColor(index);
    }
    private void setColor(int index) {
        for(int i=0;i<tvs.size();i++){
            tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.new_menu_text_color));
            tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.day_sign_content_text_white_30));
        }
        tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.white));
        tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_purple_bg));
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(data!=null){
                searchValue= data.getStringExtra("searchValue");
                et_search_content.setText(searchValue);
                //查询数据库筛选状态表
                databaseUtils.selectData(mContext);
            }
        }

        if(requestCode==1001){
            if(data!=null){
                String filterType = data.getStringExtra("FilterType");
                int counselorInuse=Integer.parseInt(filterType);
                //执行过滤设置的操作
                mCounselorListNet.setData(pageIndex,1,counselorInuse,"");
            }
        }
    }
}
