package com.cesaas.android.counselor.order.salestalk;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.MyGallery;
import com.cesaas.android.counselor.order.custom.MyGallery.OnItemSelectListener;
import com.cesaas.android.counselor.order.custom.MyViewPagerAdapter;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.salestalk.bean.GetCategoryBean;
import com.cesaas.android.counselor.order.salestalk.bean.ResultGetCategoryBean;
import com.cesaas.android.counselor.order.salestalk.net.GetCategoryNet;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 通用销售话术ViewPagerIndicator
 * @author FGB
 *
 */
public class GeneralSalesTalkViewPagerIndicatorFragment extends BaseFragment implements OnPageChangeListener,
		OnItemSelectListener{
	
	private View view;
	
	private RelativeLayout menuLayout ;
	private ViewPager viewPager;
	private ArrayList<Fragment> fragments;
	private MyViewPagerAdapter mAdapter;
	private SaleStalkItemFragment itemFragment;
	private MyGallery myGalleryMenu;
	
	private GetCategoryNet categoryNet;
	private List<GetCategoryBean> categoryList=new ArrayList<GetCategoryBean>();
	
	/**
	 * 单例
	 */
	public static GeneralSalesTalkViewPagerIndicatorFragment instance=null;
	public static GeneralSalesTalkViewPagerIndicatorFragment getInstance(){
			GeneralSalesTalkViewPagerIndicatorFragment instance=new GeneralSalesTalkViewPagerIndicatorFragment();
		return instance;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		categoryNet=new GetCategoryNet(getContext());
		categoryNet.setData();
		
	}
	
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_general_sales_talk_indicator, container,false);
		
		initGallery();
		
		return view;
	}
	

	private void initGallery() {
		viewPager = (ViewPager) view.findViewById(R.id.pager);
		viewPager.setOnPageChangeListener(this);
		myGalleryMenu = new MyGallery(getActivity(), null, categoryList,R.drawable.tab_selected);
		myGalleryMenu.setItemSelectListener(this);
		menuLayout= (RelativeLayout) view.findViewById(R.id.relative_bottom_menu);
	}
	
	/**
	 * 接受EventBus发送消息【话术分类】
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGetCategoryBean msg){
		if(msg.IsSuccess==true){
			fragments = new ArrayList<Fragment>();
			categoryList.addAll(msg.TModel);
			GetCategoryBean bean ;
			for (int i = 0; i < categoryList.size(); i++) {
				bean =new GetCategoryBean();
				bean.Id=categoryList.get(i).Id;
				bean.Content=categoryList.get(i).Content;
				
				itemFragment = new SaleStalkItemFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("index",categoryList.get(i).Id);
				itemFragment.setArguments(bundle);
				fragments.add(itemFragment);
			}
				menuLayout.addView(myGalleryMenu.getView());
				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				mAdapter = new MyViewPagerAdapter(fragmentManager, fragments);
				viewPager.setAdapter(mAdapter);
				setCurrentPage(0);
			
		}else{
			ToastFactory.getLongToast(getContext(), msg.Message);
		}
	}

	public void setCurrentPage(int currentId) {
		viewPager.setCurrentItem(currentId);
	}

	@Override
	public void onPageSelected(int id) {
		
		myGalleryMenu.setSelected(id);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onItemSelect(int position) {
		setCurrentPage(position);
	}
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
