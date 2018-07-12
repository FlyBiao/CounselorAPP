package com.cesaas.android.counselor.order.label;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.GetShopFansDetailActivity;
import com.cesaas.android.counselor.order.bean.ResultFansDetailBean;
import com.cesaas.android.counselor.order.bean.ResultFansDetailBean.TagBean;
import com.cesaas.android.counselor.order.bean.ResultLabelListBean;
import com.cesaas.android.counselor.order.bean.ResultLabelListBean.labelListBean;
import com.cesaas.android.counselor.order.net.DeteleTagNet;
import com.cesaas.android.counselor.order.net.FansDetailNet;
import com.cesaas.android.counselor.order.net.LabelListNet;
import com.cesaas.android.counselor.order.net.SetTagNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.FlowLayoutWidget;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 会员标签列表
 * @author FGB
 *
 */
public class VipLabelListAactivity extends BasesActivity{
	
	private LoadMoreListView mLoadMoreListView;//加载更多
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private LinearLayout ll_label_back;
	private TextView tv_label_null;
	
	private boolean refresh=false;
	private static int pageIndex = 1;//当前页
	private static VipLabelListAactivity fragment;
	
	private boolean isFalse=false;
	private String fansId;
	
	//已选标签接口
	private FansDetailNet detailNet;
	private ArrayList<TagBean> tagLis= new ArrayList<TagBean>();
	
	//标签列表接口网络
	private LabelListNet labelListNet;
	//标签列表集合
	private ArrayList<labelListBean> labelLis= new ArrayList<labelListBean>();
	
	//自定义View控件 专门用来装已选标签
	private FlowLayoutWidget mFlowLayout;
	
	//定义Handler
	public static Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			pageIndex=1;
			fragment.loadData(pageIndex);
			
			fragment.loadTagsData();
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vip_label_layout);
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			fansId=bundle.getString("fansId");
		}
		
		fragment=this;
		
		initView();
		loadTagsData();
		setLabelAdapte();
		mBack();
		
	}
	
	/**
	 * 初始化加载已选标签接口额数据
	 */
	public void loadTagsData(){
		
		detailNet=new FansDetailNet(mActivity);
		detailNet.setData(fansId);
		
	}
	
	
	/**
	 * 初始化数据
	 */
	public void initData() {
		loadData(1);
		
		mRefreshAndLoadMoreView.setLoadMoreListView(mLoadMoreListView);
		mLoadMoreListView.setRefreshAndLoadMoreView(mRefreshAndLoadMoreView);
		// 设置下拉刷新监听
		mRefreshAndLoadMoreView
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						refresh=true;
						pageIndex = 1;
						loadData(pageIndex);
					}
				});
		// 设置加载监听
		mLoadMoreListView
				.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
					@Override
					public void onLoadMore() {
						refresh=false;
						loadData(pageIndex + 1);
					}
				});
	}
	
	/**
	 * 接收已选标签信息
	 * @param msg
	 */
	public void onEventMainThread(ResultFansDetailBean msg) {
		
		if(tagLis.size()>0){
			tagLis.clear();
		}
		
		if(msg!=null){
			//添加标签到集合中
			tagLis.addAll(msg.TModel.TAGS);
			//调用初始化标签方法
			initChildViews();
		}
	}
	
	/**
	 * 接收标签列表信息
	 * @param msg
	 */
	public void onEventMainThread(ResultLabelListBean msg) {
		if (msg != null) {
			if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			if(msg.TModel.size()!=0){
				labelLis.addAll(msg.TModel);
				
			}
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
		}
	}
	
	/**
	 * 初始化视图控件
	 */
	public void initView(){
		tv_label_null=(TextView) findViewById(R.id.tv_vip_label_null);
		mFlowLayout=(FlowLayoutWidget) findViewById(R.id.tv_vip_label);
		mLoadMoreListView=(LoadMoreListView) findViewById(R.id.load_more_label_list);
		mRefreshAndLoadMoreView=(RefreshAndLoadMoreView) findViewById(R.id.refresh_label_and_load_more);
	}
	
	/**
	 * 初始化已选标签视图
	 */
	public void initChildViews(){
		if(tagLis.size()!=0){
			mFlowLayout.removeAllViews();
		}
		if(tagLis.size()==0){
			tv_label_null.setVisibility(View.VISIBLE);
        	mFlowLayout.setVisibility(View.GONE);
		}else{
			MarginLayoutParams lp = new MarginLayoutParams(
	                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	        lp.leftMargin = 17;
	        
	        //遍历已选标签
	        for(int i = 0; i < tagLis.size(); i ++){
	            TextView view = new TextView(this);
            	view.setText(tagLis.get(i).Name);
                view.setTextColor(Color.WHITE);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
                
                mFlowLayout.addView(view,lp);
		}
       }
	}
	
	/**
	 * 设置标签适配器
	 */
	public void setLabelAdapte(){
		mLoadMoreListView.setAdapter(new CommonAdapter<labelListBean>(mContext,R.layout.item_label,labelLis) {

			@Override
			public void convert(final ViewHolder holder, final labelListBean bean,int postion) {
					
				holder.setText(R.id.tv_vip_label, bean.Name);
				
					if(bean.IsSelected==0){//zero：表示没有选中标签
						//显示添加标签按钮
						holder.getView(R.id.tv_set_label).setVisibility(View.VISIBLE);
						holder.getView(R.id.tv_detele_label).setVisibility(View.GONE);
					}else{
						//显示删除标签按钮
						holder.getView(R.id.tv_detele_label).setVisibility(View.VISIBLE);
						holder.getView(R.id.tv_set_label).setVisibility(View.GONE);
					}
				
				//设置标签
				holder.setOnClickListener(R.id.tv_set_label, new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						if(tagLis.size()<3){
							SetTagNet setTagNet=new SetTagNet(mContext);
							setTagNet.setData(fansId,bean.Id);
							
							//显示删除标签按钮
							holder.getView(R.id.tv_detele_label).setVisibility(View.VISIBLE);
							holder.getView(R.id.tv_set_label).setVisibility(View.GONE);
							
							//刷新标签数据
							VipLabelListAactivity.handler.obtainMessage().sendToTarget();
							
						}else{
							ToastFactory.getLongToast(mContext, "标签最多不能超过3个!");
						}
					}
				});
				
				//删除标签
				holder.setOnClickListener(R.id.tv_detele_label, new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						DeteleTagNet deteleTagNet=new DeteleTagNet(mContext);
						deteleTagNet.setData(fansId,bean.Id);
						
						//显示添加标签按钮
						holder.getView(R.id.tv_set_label).setVisibility(View.VISIBLE);
						holder.getView(R.id.tv_detele_label).setVisibility(View.GONE);
						
						//刷新标签数据
						VipLabelListAactivity.handler.obtainMessage().sendToTarget();
					}
				});
			}

		});
		
		initData();
	}
	
	/**
	 * 加载数据
	 * @param page 当前页
	 */
	protected void loadData(final int page) {

		if (page == 1) {
			labelLis.clear();
		}
		labelListNet = new LabelListNet(mContext);
		labelListNet.setData(fansId,page);

		pageIndex = page;
	}
	
	/**
	 * 返回上一个页面
	 */
	public void mBack(){
		ll_label_back=(LinearLayout) findViewById(R.id.ll_label_back);
		ll_label_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				 new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                	//调用handler刷新页面
                    GetShopFansDetailActivity.handler.obtainMessage().sendToTarget();
                }
            }, 100);
			}
		});
	}
	
	 /**
     * 重写系统返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	//调用handler刷新页面
            	GetShopFansDetailActivity.handler.obtainMessage().sendToTarget();
            }
        }, 100);
        return super.onKeyDown(keyCode, event);
    }

}
