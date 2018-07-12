package com.cesaas.android.counselor.order.fans.activity;

import io.rong.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.label.bean.ResultGetCategoryListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetCategoryListBean.GetCategoryListBean;
import com.cesaas.android.counselor.order.label.bean.SerachTagBean;
import com.cesaas.android.counselor.order.label.net.GetCategoryListNet;
import com.cesaas.android.counselor.order.treeview.Bean;
import com.cesaas.android.counselor.order.treeview.Node;
import com.cesaas.android.counselor.order.treeview.SimpleTreeAdapter;
import com.cesaas.android.counselor.order.treeview.TreeListViewAdapter;
import com.cesaas.android.counselor.order.treeview.TreeListViewAdapter.OnTreeNodeClickListener;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 查询粉丝标签列表
 * @author fgb
 *
 */
public class SerachTagListActivity extends BasesActivity implements OnClickListener{
	
	//测试TreeView
	private List<Bean> mDatas = new ArrayList<Bean>();
	private ListView mTree;
	private TreeListViewAdapter mAdapter;
	
	private int RESULTCODE=100;
	
	private LinearLayout ll_tag_category_back;
	
	private GetCategoryListNet categoryListNet;
	private ArrayList<GetCategoryListBean> categoryListBeans= new ArrayList<GetCategoryListBean>();
	
	private ArrayList<GetCategoryListBean> arrayTemp = new ArrayList<ResultGetCategoryListBean.GetCategoryListBean>();
	private ArrayList<GetCategoryListBean> arrayBean = new ArrayList<ResultGetCategoryListBean.GetCategoryListBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serach_taglist);
		
		categoryListNet=new GetCategoryListNet(mContext);
		categoryListNet.setData();
		
		initView();
		
	}
	
	/**
	 * 初始化视图控件
	 */
	private void initView() {
		mTree = (ListView) findViewById(R.id.id_tree);
		
		ll_tag_category_back=(LinearLayout) findViewById(R.id.ll_tag_category_back);
		ll_tag_category_back.setOnClickListener(this);
		
	}

	/**
	 * 接受EventBus发送消息【标签分类】
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGetCategoryListBean msg){
		if(msg.IsSuccess==true){
			arrayTemp = msg.TModel;
			arrayBean = arrayWithSuperId(0);
			categoryListBeans.addAll(arrayBean);
			whileArray(arrayBean);
			
			//获取父节点
			for (int i = 0; i < categoryListBeans.size(); i++) {
				mDatas.add(new Bean(categoryListBeans.get(i).CategoryId,categoryListBeans.get(i).ParentId, categoryListBeans.get(i).CategoryName));
				//获取第二级标签分类列表
				for (int j = 0; j < categoryListBeans.get(i).arraySub.size(); j++) {
					mDatas.add(new Bean(categoryListBeans.get(i).arraySub.get(j).CategoryId,categoryListBeans.get(i).arraySub.get(j).ParentId,categoryListBeans.get(i).arraySub.get(j).CategoryName));
					//获取第三级标签分类列表
					for (int k = 0; k < categoryListBeans.get(i).arraySub.get(j).arraySub.size(); k++) {
						mDatas.add(new Bean(categoryListBeans.get(i).arraySub.get(j).arraySub.get(k).CategoryId,categoryListBeans.get(i).arraySub.get(j).arraySub.get(k).ParentId,categoryListBeans.get(i).arraySub.get(j).arraySub.get(k).CategoryName));
					}
				}	
			}
			try {
				//初始化标签数据
				mAdapter = new SimpleTreeAdapter<Bean>(mTree, this, mDatas, 10);
				//设置节点点击监听
				mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener()
				{
					@Override
					public void onClick(Node node, int position)
					{
						if (node.isLeaf())
						{
							//设置要查询的标签
							SerachTagBean bean=new SerachTagBean();
							bean.setSerachTagName(node.getName());
							//通过EventBus推送标签是实体消息
							EventBus.getDefault().post(bean);
							//关闭页面
							finish();
						}
					}
				});
				//设置树形Adapter数据
				mTree.setAdapter(mAdapter);
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else{
			ToastFactory.getLongToast(mContext, msg.Message);
		}
	}
	
	
	public ArrayList<GetCategoryListBean> arrayWithSuperId(int superId){
		ArrayList<GetCategoryListBean> arrayR = new ArrayList<ResultGetCategoryListBean.GetCategoryListBean>();
		ArrayList<GetCategoryListBean> arrayT = new ArrayList<ResultGetCategoryListBean.GetCategoryListBean>();
		for (int i = 0; i < arrayTemp.size(); i++) {
			GetCategoryListBean bean = arrayTemp.get(i);
			if (bean.ParentId == superId) {
				arrayR.add(bean);
				
			}else{
				arrayT.add(bean);
			}
		}
		arrayTemp = arrayT;
		return arrayR;
	}
	
	public void whileArray(ArrayList<GetCategoryListBean> array){
		for (int i = 0; i < array.size(); i++) {
			GetCategoryListBean bean = array.get(i);
			int superId = bean.CategoryId;
			bean.arraySub = arrayWithSuperId(superId);
			if (bean.arraySub.size() > 0) {
				whileArray(bean.arraySub);
			}
		}
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
