package com.cesaas.android.counselor.order.label;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultLabelListBean;
import com.cesaas.android.counselor.order.bean.ResultLabelListBean.labelListBean;
import com.cesaas.android.counselor.order.net.LabelListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 会员标签列表
 * @author FGB
 *
 */
public class LabelListAactivity extends BasesActivity implements OnClickListener{
	
	private LoadMoreListView mLoadMoreListView;//加载更多
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private LinearLayout ll_label_list_back,ll_add_label;
	private TextView tv_create_label;
	
	private boolean refresh=false;
	private static int pageIndex = 1;//当前页
	private boolean isFalse=false;
	
	private addLabelDialog dialog;
	
	private String VipId;
	private static LabelListAactivity fragment;
	
	//标签列表接口网络
	private LabelListNet labelListNet;
	//标签列表集合
	private ArrayList<labelListBean> labelLis= new ArrayList<labelListBean>();
	
	//定义Handler
		public static Handler handler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				
				pageIndex=1;
				fragment.loadData(pageIndex);
				
			};
		};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_label_list_layout);
		
		Bundle bundle=getIntent().getExtras();
		if (bundle!=null) {
			VipId=bundle.getString("VipId");
		}
		
		fragment=this;
		
		setView();
		setLabelAdapte();
		
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
	 * 设置标签适配器
	 */
	public void setLabelAdapte(){
		mLoadMoreListView.setAdapter(new CommonAdapter<labelListBean>(mContext,R.layout.item_label_list,labelLis) {

			@Override
			public void convert(final ViewHolder holder, final labelListBean bean,int postion) {
					
				holder.setText(R.id.tv_label_name, bean.Name);
				holder.getView(R.id.tv_label_color).setBackgroundColor(Color.parseColor(bean.BColor));
			}

		});
		
		initData();
	}
	
	public void setView(){
		mRefreshAndLoadMoreView=(RefreshAndLoadMoreView) findViewById(R.id.refresh_label_list_and_load_more);
		mLoadMoreListView=(LoadMoreListView) findViewById(R.id.load_more_label_list_list);
		
		tv_create_label=(TextView) findViewById(R.id.tv_create_label);
		ll_add_label=(LinearLayout) findViewById(R.id.ll_add_label);
		ll_label_list_back=(LinearLayout) findViewById(R.id.ll_label_list_back);
		ll_label_list_back.setOnClickListener(this);
		tv_create_label.setOnClickListener(this);
		
		ll_add_label.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog=new addLabelDialog(mContext, R.style.dialog, R.layout.add_label_dialog);
				dialog.show();
				dialog.setCancelable(false);
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_label_list_back://返回
			Skip.mBack(mActivity);
			break;
			
		case R.id.tv_create_label:
			dialog=new addLabelDialog(mContext, R.style.dialog, R.layout.add_label_dialog);
			dialog.show();
			dialog.setCancelable(false);
			break;

		default:
			break;
		}
		
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
	 * 加载数据
	 * @param page 当前页
	 */
	protected void loadData(final int page) {

		if (page == 1) {
			labelLis.clear();
		}
		labelListNet = new LabelListNet(mContext);
		labelListNet.setData(VipId,page);

		pageIndex = page;
	}
	
	/**
	 * 添加标签Dialog
	 * @author FGB
	 *
	 */
	public class addLabelDialog extends Dialog implements OnClickListener{
		
		private Button btn_cancel_add_label,btn_sure_add_label;
		int layoutRes;//布局文件
	    Context context;

	    public addLabelDialog(Context context, int theme,int resLayout){
            super(context, theme);
            this.context = context;
            this.layoutRes=resLayout;
        }
	    
	    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(layoutRes);
            initView();
        }
	    
	    public void initView(){
	    	btn_cancel_add_label=(Button) findViewById(R.id.btn_cancel_add_label);
	    	btn_sure_add_label=(Button) findViewById(R.id.btn_sure_add_label);
	    	btn_sure_add_label.setOnClickListener(this);
	    	btn_cancel_add_label.setOnClickListener(this);
	    }

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_cancel_add_label:
				dialog.dismiss();
				break;
				
			case R.id.btn_sure_add_label:
				dialog.dismiss();
				break;

			default:
				break;
			}
			
		}
		
	}
	
}
