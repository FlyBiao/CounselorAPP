package com.cesaas.android.counselor.order.member.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.bean.BackBean;
import com.cesaas.android.counselor.order.member.bean.BindVipIdBean;
import com.cesaas.android.counselor.order.member.bean.PublicMemberBean;
import com.cesaas.android.counselor.order.member.bean.ResultPublicMemberBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.lidroid.xutils.exception.HttpException;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 公共池会员
 * Created 2017/4/12 14:44
 * Version 1.zero
 */
public class PublicMemberFragment extends BaseFragment{
    @Override
    public int getLayoutId() {
        return R.layout.activity_public_member;
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private int pageIndex=1;

    private GetUnBindVipNet mGetUnBindVipNet;
    private PublicMemberAdapter mAdapter;
    private List<PublicMemberBean> mMemberDistributionBeanList;
    private int resultCode=200;

    private JSONArray vipArray=new JSONArray();

    private List<BindVipIdBean> selectBindVipIdBeen=new ArrayList<>();

    @Override
    public void initViews() {

        //通过EventBus订阅事件
        EventBus.getDefault().register(this);

        mGetUnBindVipNet=new GetUnBindVipNet(getContext());
        mGetUnBindVipNet.setData(pageIndex);

        mSwipeRefreshLayout=findView(R.id.swipe_layout);
        mSwipeMenuRecyclerView=findView(R.id.recycler_member_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
//        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

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
                    mGetUnBindVipNet.setData(pageIndex);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    /**
     * OnItemClickListener
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

//            vipArray.put(bean.getVips());
//            BindVipNet mBindVipNet=new BindVipNet(getContext());
//            mBindVipNet.setData(177,vipArray);
        }
    };

    @Override
    public void processClick(View v) {

    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);//取消EventBus订阅
        super.onDestroy();
    }


    public void onEventMainThread(BackBean bean){
       if(bean.isBack()==true){
           mGetUnBindVipNet.setData(pageIndex);
       }
    }

    /**
     * Author FGB
     * Description 会员分配Adapter
     * Created 2017/4/11 18:33
     * Version 1.zero
     */
    public class PublicMemberAdapter extends SwipeMenuAdapter<DefaultViewHolder> {

        private List<PublicMemberBean> mMemberDistributionBeanList;
        private OnItemClickListener mOnItemClickListener;
        public  Context mContext;
        public  Activity mActivity;

        public PublicMemberAdapter(List<PublicMemberBean> mMemberDistributionBeanList, Context mContext){
            this.mContext=mContext;
            this.mMemberDistributionBeanList=mMemberDistributionBeanList;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_public_member, parent, false);
        }

        @Override
        public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            return new DefaultViewHolder(realContentView);
        }

        //绑定界面，设置监听
        @Override
        public void onBindViewHolder(DefaultViewHolder holder, final int position) {

            holder.setData(
                    mMemberDistributionBeanList.get(position).getVipName(),
                    mMemberDistributionBeanList.get(position).getNickName()
                    ,mMemberDistributionBeanList.get(position).getMobile()
                    ,mMemberDistributionBeanList.get(position).getRegisterDate()
                    ,mMemberDistributionBeanList.get(position).getBirthday()
                    ,mMemberDistributionBeanList.get(position).getCardName()
                    ,mMemberDistributionBeanList.get(position).getImage());
            holder.setOnItemClickListener(mOnItemClickListener);


            holder.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    buttonView.toString();
                    // 调整选定条目
                    if (isChecked== true) {
                        BindVipIdBean bindVipIdBean=new BindVipIdBean();
                        bindVipIdBean.setVipId(mMemberDistributionBeanList.get(position).getVipId());
                        bindVipIdBean.setPos(position);
                        selectBindVipIdBeen.add(bindVipIdBean);
                        if(selectBindVipIdBeen.size()!=0){
                            EventBus.getDefault().post(selectBindVipIdBeen);
                        }
                    } else {
                        for (Iterator it = selectBindVipIdBeen.iterator(); it.hasNext();){
                            BindVipIdBean value= (BindVipIdBean) it.next();
                            if(value.getPos()==position){
                                it.remove();
                            }
                        }
                        if(selectBindVipIdBeen.size()!=0){
                            EventBus.getDefault().post(selectBindVipIdBeen);
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
        CheckBox cbCheckBox;
        MyImageViewWidget ivw_member_icon;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_member_name = (TextView) itemView.findViewById(R.id.tv_member_name);
            tv_member_grade= (TextView) itemView.findViewById(R.id.tv_member_grade);
            tv_member_mobile= (TextView) itemView.findViewById(R.id.tv_member_mobile);
            tv_member_point= (TextView) itemView.findViewById(R.id.tv_member_point);
            tv_member_birthday= (TextView) itemView.findViewById(R.id.tv_member_birthday);
            ivw_member_icon= (MyImageViewWidget) itemView.findViewById(R.id.ivw_member_icon);
            cbCheckBox= (CheckBox) itemView.findViewById(R.id.cbCheckBox);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String name,String nickName,String mobile,String regDate,String birthday,String grade,String imgUrl) {
            if(birthday!=null && !"".equals(birthday)){
                this.tv_member_birthday.setText("生日 "+ AbDateUtil.toDateYMD(birthday));
            }else{
                this.tv_member_birthday.setText("暂无填写生日");
            }
            if(mobile!=null && !"".equals(mobile)){
                this.tv_member_mobile.setText(mobile);
            }else{
                this.tv_member_mobile.setText("暂无手机号");
            }
            if(name!=null && !"".equals(name)){
                this.tv_member_name.setText(name);
            }else{
                this.tv_member_name.setText(nickName);
            }
            if(regDate!=null && !"".equals(regDate)){
                this.tv_member_point.setText(AbDateUtil.getDateYMDs(regDate)+" "+AbDateUtil.formats(regDate));
            }else{
                this.tv_member_point.setText("暂无注册时间");
            }
            if(imgUrl!=null && !"".equals(imgUrl)){
                Glide.with(getContext()).load(imgUrl).into(this.ivw_member_icon);
            }else{
                this.ivw_member_icon.setImageResource(R.mipmap.not_user_icon);
            }

            this.tv_member_grade.setText(grade);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }




    /**
     * Author FGB
     * Description 获取未绑定会员（公共池会员）
     * Created 2017/4/21 15:08
     * Version 1.zero
     */
    public class GetUnBindVipNet extends BaseNet {
        public GetUnBindVipNet(Context context) {
            super(context, true);
            this.uri="User/Sw/VipCounselor/GetUnBindVip";
        }

        public void setData(int PageIndex){
            try{
                data.put("PageIndex",PageIndex);
                data.put("PageSize",Constant.PAGE_SIZE);
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultPublicMemberBean bean= JsonUtils.fromJson(rJson,ResultPublicMemberBean.class);
            if(bean.IsSuccess==true){
                mMemberDistributionBeanList=new ArrayList<>();
                mMemberDistributionBeanList.addAll(bean.TModel);

                mAdapter=new PublicMemberAdapter(mMemberDistributionBeanList,getContext());
                mAdapter.setOnItemClickListener(onItemClickListener);
                mSwipeMenuRecyclerView.setAdapter(mAdapter);
            }else{
                ToastFactory.getLongToast(getContext(),"获取数据失败！"+bean.Message);
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }
}
