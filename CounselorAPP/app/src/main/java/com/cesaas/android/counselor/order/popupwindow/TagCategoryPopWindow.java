package com.cesaas.android.counselor.order.popupwindow;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.label.bean.ResultGetCategoryListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetCategoryListBean.GetCategoryListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetTagListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetTagListBean.GetTagListBean;
import com.cesaas.android.counselor.order.label.net.GetCategoryListNet;
import com.cesaas.android.counselor.order.label.net.GetTagListNet;
import com.cesaas.android.counselor.order.popupwindow.bean.TagCategoryAdapter;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 标签分类 PopWindow
 * @author FGB
 *
 */
public class TagCategoryPopWindow extends BasesActivity implements OnClickListener{

	private LinearLayout ll_tag_category_back;
	private TextView tv_select_tag_category,tv_select_tag_list;
	
    private ListView mLv_Category; // 类型列表
    private ListView mLv_CategoryList;      // 类型标签列表
    private ListView mLv_CategoryListDetail;  // 地区列表
    private TagCategoryAdapter  categoryAdapter;
    
    public String categoryName;//标签分类名称
    public int categoryId;//标签分类编号
    public String categoryListName;//标签分类标签列表名称
    public int categoryListId;//标签分类列表编号
    
    //标签分类
    private GetCategoryListNet categoryListNet;
	private ArrayList<GetCategoryListBean> categoryListBeans; 
	//标签列表
	private GetTagListNet getTagListNet;
	private ArrayList<GetTagListBean> getTagListBeans;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.pop_tag_category_listview);
    	
    	initNetData();
    	initView();
    	initEvent();
    	initOnItemClick();
    	
    }
    
    /**
	 * 接受EventBus发送消息【标签分类】
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGetCategoryListBean msg){
		if(msg.IsSuccess==true){
			categoryListBeans= new ArrayList<GetCategoryListBean>();
			categoryListBeans.addAll(msg.TModel);
			setCategoryAdapter();
		}else{
			ToastFactory.getLongToast(mContext, msg.Message);
		}
	}
	
	/**
	 * 接受EventBus发送消息[分类下所属的标签列表]
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGetTagListBean msg){
		if(msg.IsSuccess==true){
			
			//初始化标签列表数据
			getTagListBeans= new ArrayList<GetTagListBean>();
			getTagListBeans.addAll(msg.TModel);
			
			setTagListAdapter();
			
		}else{
			ToastFactory.getLongToast(mContext, msg.Message);
		}
	}
    
	/**
	 * 设置标签类型数据适配器
	 */
    private void setCategoryAdapter() {
    	categoryAdapter=new TagCategoryAdapter(categoryListBeans, mContext);
    	mLv_Category.setAdapter(categoryAdapter);
	}
    
    /**
	 * 设置标签列表数据适配器
	 */
	public void setTagListAdapter(){
		mLv_CategoryList.setAdapter(new CommonAdapter<GetTagListBean>(mContext,R.layout.item_pcd,getTagListBeans) {

			@Override
			public void convert(ViewHolder holder, GetTagListBean bean,int postion) {
				holder.setText(R.id.tv_tag_list_name, bean.TagName);
			}

		});
		
	}


	/**
     * 初始化网络数据
     */
    private void initNetData() {
    	categoryListNet=new GetCategoryListNet(mContext);
		categoryListNet.setData();
	}

	/**
     * 初始化视图控件
     */
	private void initView() {
		ll_tag_category_back=(LinearLayout) findViewById(R.id.ll_tag_category_back);
		mLv_Category=(ListView) findViewById(R.id.lv_tag_category);
		mLv_CategoryList=(ListView) findViewById(R.id.lv_category_tag_list);
		mLv_CategoryListDetail=(ListView) findViewById(R.id.lv_category_tag_list_detail);
		
		tv_select_tag_category=(TextView) findViewById(R.id.tv_select_tag_category);
		tv_select_tag_list=(TextView) findViewById(R.id.tv_select_tag_list);
		
		ll_tag_category_back.setOnClickListener(this);
		
	}

	/**
	 * 初始化点击事件
	 */
	private void initEvent() {
		
	}

	/**
	 * 初始化ListView Item点击事件
	 */
	private void initOnItemClick() {
		//点击标签类型Item
		mLv_Category.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				// 切换选中的内容
				categoryAdapter.setSelectedItem(position);
				// 更新数据
				categoryAdapter.notifyDataSetChanged();
				
				categoryName=categoryListBeans.get(position).CategoryName;
				categoryId=categoryListBeans.get(position).CategoryId;
				
				tv_select_tag_category.setText(categoryName);
				
				getTagListNet=new GetTagListNet(mContext);
				getTagListNet.setData(categoryId);
			}
		});
		
		//标签列表
		mLv_CategoryList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				categoryListName=getTagListBeans.get(position).TagName;
			    categoryListId=getTagListBeans.get(position).TagId;
			    
			    tv_select_tag_list.setText(categoryListName);
			}
		});
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.ll_tag_category_back://返回
			Skip.mBack(mActivity);
			break;
		
		default:
			break;
		}
	}
}
