package com.cesaas.android.counselor.order.member;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseActivity;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.bean.Fans;
import com.cesaas.android.counselor.order.bean.ResultFans;
import com.cesaas.android.counselor.order.bean.SelectFans;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.label.bean.ResultAddTagBean;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.adapter.MemberListAdapter;
import com.cesaas.android.counselor.order.member.bean.MemberDistributionBean;
import com.cesaas.android.counselor.order.member.bean.SelectVipListBean;
import com.cesaas.android.counselor.order.net.FansNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 会员列表
 */
public class MemberListActivity extends BaseTemplateActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_list;
    }

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle,tvBaseRigthTitle;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private MemberListAdapter mAdapter;
    private List<Fans> mMemberDistributionBeanList;
    private List<SelectFans> selectFans=new ArrayList<>();

    private int resultCode=201;
    private int page=1;
    private FansNet mFansNet;

    @Override
    public void initViews() {
        llBaseBack=findView(R.id.ll_base_title_back);
        tvBaseTitle=findView(R.id.tv_base_title);
        tvBaseRigthTitle=findView(R.id.tv_base_title_right);
        tvBaseRigthTitle.setVisibility(View.VISIBLE);
        tvBaseRigthTitle.setText("确定");
        tvBaseTitle.setText("粉丝列表");

        mSwipeRefreshLayout=findView(R.id.swipe_layout);
        mSwipeMenuRecyclerView=findView(R.id.recycler_member_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
//        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);// 添加滚动监听。

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
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
                    mFansNet=new FansNet(mContext);
                    mFansNet.setData(page);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    @Override
    public void initListener() {
        llBaseBack.setOnClickListener(this);
        tvBaseRigthTitle.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mFansNet=new FansNet(mContext);
        mFansNet.setData(page);
    }


    /**
     * 接受Fans List发送消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultFans msg){
        if(msg.IsSuccess==true){
            mMemberDistributionBeanList=new ArrayList<>();
            mMemberDistributionBeanList.addAll(msg.TModel);
            mAdapter=new MemberListAdapter(mMemberDistributionBeanList,mContext);
            mSwipeMenuRecyclerView.setAdapter(mAdapter);
//            mAdapter.setOnItemClickListener(onItemClickListener);
        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败！"+msg.Message);
        }
    }

//    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
//        @Override
//        public void onItemClick(int position) {
//
//        }
//    };

    @Override
    public void processClick(View v) {

        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
            break;

            case R.id.tv_base_title_right:
                Intent mIntent = new Intent();
                mIntent.putExtra("selectList",(Serializable)selectFans);
                // 设置结果，并进行传送
                setResult(resultCode, mIntent);
                finish();
                break;
        }
    }

    /**
     * Author FGB
     * Description 会员列表Adapter
     * Created 2017/4/11 18:33
     * Version 1.zero
     */
    public class MemberListAdapter extends SwipeMenuAdapter<DefaultViewHolder> {

        private List<Fans> mMemberDistributionBeanList;
        private OnItemClickListener mOnItemClickListener;
        public  Context mContext;
        public  Activity mActivity;

        public MemberListAdapter(List<Fans> mMemberDistributionBeanList, Context mContext){
            this.mContext=mContext;
            this.mMemberDistributionBeanList=mMemberDistributionBeanList;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_list, parent, false);
        }

        @Override
        public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            return new DefaultViewHolder(realContentView);
        }

        //绑定界面，设置监听
        @Override
        public void onBindViewHolder(final DefaultViewHolder holder,final int position) {

            holder.setData(mMemberDistributionBeanList.get(position).FANS_NICKNAME
                    ,mMemberDistributionBeanList.get(position).FANS_MOBILE
                    ,mMemberDistributionBeanList.get(position).FANS_POINT
                    ,mMemberDistributionBeanList.get(position).FANS_BIRTHDAY
                    ,mMemberDistributionBeanList.get(position).FANS_GRADE
                    ,mMemberDistributionBeanList.get(position).FANS_ICON);
            holder.setOnItemClickListener(mOnItemClickListener);

            //设置选框监听
            holder.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked== true) {//选中
                        SelectFans bean=new SelectFans();
                        if(mMemberDistributionBeanList.get(position).FANS_MOBILE!=null){
                            bean.FANS_MOBILE=mMemberDistributionBeanList.get(position).FANS_MOBILE;
                            bean.position=position;
                            selectFans.add(bean);
                        }else{
                            holder.cbCheckBox.setButtonDrawable(R.drawable.checkbox_checked_style);
                            holder.cbCheckBox.setFocusable(false);
                            holder.cbCheckBox.setChecked(false);
                            ToastFactory.getLongToast(mContext,"该人员没有手机号额！");
                        }

                    }else{//未选中
                        for (Iterator it = selectFans.iterator(); it.hasNext();){
                            SelectFans value= (SelectFans) it.next();
                            if(value.position==position){
                                it.remove();
                            }
                        }
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mMemberDistributionBeanList == null ? 0 : mMemberDistributionBeanList.size();
        }

    }

    /**
     * DefaultViewHolder 静态类
     */
    public class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_member_name,tv_member_grade,tv_member_mobile,tv_member_point,tv_member_birthday;
        MyImageViewWidget ivw_member_icon;
        CheckBox cbCheckBox;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_member_name = (TextView) itemView.findViewById(R.id.tv_member_name);
            tv_member_grade= (TextView) itemView.findViewById(R.id.tv_member_grade);
            tv_member_mobile= (TextView) itemView.findViewById(R.id.tv_member_mobile);
            tv_member_point= (TextView) itemView.findViewById(R.id.tv_member_point);
            tv_member_birthday= (TextView) itemView.findViewById(R.id.tv_member_birthday);
            cbCheckBox= (CheckBox) itemView.findViewById(R.id.cbCheckBox);
            ivw_member_icon= (MyImageViewWidget) itemView.findViewById(R.id.ivw_member_icon);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String name,String mobile,String point,String birthday,String grade,String imgUrl) {
            if(name!=null || name!=""){
                this.tv_member_name.setText(name);
            }else{
                this.tv_member_name.setText("找不到该会员");
            }

            if(birthday!=null){
                this.tv_member_birthday.setText("生日 "+birthday);
            }else{
                this.tv_member_birthday.setVisibility(View.GONE);
            }

            if(imgUrl!=null){
                Glide.with(mContext).load(imgUrl).into(this.ivw_member_icon);
            }else{
                Glide.with(mContext).load(imgUrl).error(R.drawable.no_picture).into(this.ivw_member_icon);
            }

            if(mobile!=null){
                this.tv_member_mobile.setText(mobile);
            }else{
                this.tv_member_mobile.setText("暂无手机号");
            }

            this.tv_member_grade.setText(grade);
            this.tv_member_point.setText("会员积分 "+point);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
