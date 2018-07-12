package com.cesaas.android.counselor.order.fans.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.Fans;
import com.cesaas.android.counselor.order.bean.ResultFans;
import com.cesaas.android.counselor.order.custom.SearchEditText;
import com.cesaas.android.counselor.order.custom.flowlayout.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.flowlayout.TagAdapter;
import com.cesaas.android.counselor.order.fans.activity.GroupSendAactivity;
import com.cesaas.android.counselor.order.fans.activity.SerachTagListActivity;
import com.cesaas.android.counselor.order.fans.activity.VipDetailActivity;
import com.cesaas.android.counselor.order.label.bean.ResultGetVipTagBean.GetVipTagBean;
import com.cesaas.android.counselor.order.label.bean.SerachTagBean;
import com.cesaas.android.counselor.order.label.net.GetVipTagNet;
import com.cesaas.android.counselor.order.net.FansNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by FGB on 2016/3/7.
 */
public class FansServiceFragment extends BaseFragment implements OnClickListener{
	
	private TagAdapter<String> mSizeTagAdapter;
	private List<String> dataSource;
	
	@ViewInject(R.id.serach_size_flow_layout)
	FlowTagLayout serach_size_flow_layout;
	@ViewInject(R.id.load_more_fans_list)
	private LoadMoreListView mLoadMoreListView;//加载更多
	@ViewInject(R.id.refresh_fans_and_load_more)
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	@ViewInject(R.id.m_search_two)
	private SearchEditText m_search_two;
	@ViewInject(R.id.ll_fans_group_send)
	private LinearLayout ll_fans_group_send;
	@ViewInject(R.id.ll_fans_labels)
	private LinearLayout ll_fans_labels;
	
	public String getMessageTargetId;
	private View view;
	private boolean refresh=false;
	private static int pageIndex = 1;//当前页
	
	private static FansServiceFragment fragment;
	
	//粉丝列表
	private FansNet fansNet;
	private ArrayList<Fans> fansLis= new ArrayList<Fans>();
	
	//粉丝标签
	private GetVipTagNet getVipTagNet;
	private ArrayList<GetVipTagBean> getVipTagBeans= new ArrayList<GetVipTagBean>();
	
	public static Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			pageIndex=1;
			fragment.loadData(pageIndex);
		};
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragment=this;
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_fans_service_layout, container,false);
		
		ViewUtils.inject(this, view);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setAdapter();
		initData();
		initItemClickListener();
		
		m_search_two.setOnSearchClickListener(new SearchEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                Toast.makeText(getContext(), "i'm going to seach", Toast.LENGTH_SHORT).show();
            }
        });
	}
	
	/**
	 * 设置Adapter数据适配器
	 */
	public void setAdapter(){
		mLoadMoreListView.setAdapter(new CommonAdapter<Fans>(getContext(),R.layout.item_user_fans,fansLis) {
			@Override
			public void convert(ViewHolder holder, Fans fans,int postion) {
				holder.setText(R.id.tv_fans_nick, fans.FANS_NICKNAME);
				holder.setText(R.id.tv_fans_mobile, fans.FANS_MOBILE);
				holder.setCircleImageViewBitmap(R.id.iv_fans_img, bitmapUtils, fans.FANS_ICON);
				
//					getVipTagNet=new GetVipTagNet(getContext());
//					getVipTagNet.setData(fans.FANS_ID);
			}

		});
	}
	
	/**
	 * 接受EventBus发送消息【查询标签】
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(SerachTagBean msg) {
		
		if(msg.getSerachTagName()!=null){
			
			//设置标签
			dataSource= new ArrayList<String>();
			//单选
			mSizeTagAdapter=new TagAdapter<String>(getContext());
			serach_size_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
			serach_size_flow_layout.setAdapter(mSizeTagAdapter);
//			for (int i = zero; i < getTagListBeans.size(); i++) {
				dataSource.add(msg.getSerachTagName());
//			}
			mSizeTagAdapter.onlyAddAll(dataSource);
			
		}else{
			ToastFactory.getLongToast(getContext(), "标签为空！");
		}
	}
	
	/**
	 * 接受EventBus发送消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultFans msg) {
		
		if (msg.IsSuccess==true) {
			if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			if(msg.TModel.size()!=0){
				fansLis.addAll(msg.TModel);
			}
				mRefreshAndLoadMoreView.setRefreshing(false);
				mLoadMoreListView.onLoadComplete();
				
		}else{
			ToastFactory.getLongToast(getContext(), msg.Message);
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
			fansLis.clear();
		}
		fansNet = new FansNet(getContext());
		fansNet.setData(page);

		pageIndex = page;
	}
	

	/**
	 * 初始化ItemClick点击事件
	 */
	private void initItemClickListener() {
	
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				Bundle bundle=new Bundle();
				bundle.putString("fansId", fansLis.get(position).FANS_ID);
				bundle.putString("fansNickName", fansLis.get(position).FANS_NICKNAME);
				//跳转粉丝详情页面
				Skip.mNextFroData(getActivity(), VipDetailActivity.class,bundle);
			}
		});
		
		//点击单选
//		serach_size_flow_layout.setOnTagSelectListener(new OnTagSelectListener() {
//            @Override
//            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
//                if (selectedList != null && selectedList.size() > zero) {
//                    StringBuilder sb = new StringBuilder();
//                    for (int i : selectedList) {
//                        sb.append(parent.getAdapter().getItem(i));
//                        sb.append(":");
//                    }
////                    ToastFactory.getLongToast(getContext(), "点击单选"+sb.toString());
//                }else{
//                	ToastFactory.getLongToast(getContext(), "没有选择标签");
//                }
//            }
//        });
	}
	
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
	}

	@OnClick({R.id.ll_fans_labels,R.id.ll_fans_group_send,R.id.tv_search_fans})
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.ll_fans_labels://粉丝标签
//			Skip.mNext(getActivity(), LabelCategoryListActivity.class);
			Skip.mNext(getActivity(), SerachTagListActivity.class);
			break;
			
		case R.id.ll_fans_group_send://群发消息
			Skip.mNext(getActivity(), GroupSendAactivity.class);
			break;
			
		case R.id.tv_search_fans://查询会员
			Skip.mNext(getActivity(), SerachTagListActivity.class);
			break;
			
//		case R.id.m_search_two://通过标签查询Fans
//			Skip.mNext(getActivity(), SerachTagListActivity.class);
//			break;

		default:
			break;
		}
		
	}
}
