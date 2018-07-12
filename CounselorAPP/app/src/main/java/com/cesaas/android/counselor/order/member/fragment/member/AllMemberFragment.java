package com.cesaas.android.counselor.order.member.fragment.member;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.bean.ResultSetRemarkBean;
import com.cesaas.android.counselor.order.bean.ResultSetTagBean;
import com.cesaas.android.counselor.order.custom.menu.ContextMenuItem;
import com.cesaas.android.counselor.order.custom.toprightmenu.TopRightMenu;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.label.net.SetTagNet;
import com.cesaas.android.counselor.order.member.GroupSendMessageActivity;
import com.cesaas.android.counselor.order.member.adapter.member.AllMemberNewAdapter;
import com.cesaas.android.counselor.order.member.bean.service.ResultFaceBindBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultFocusBean;
import com.cesaas.android.counselor.order.member.bean.service.SearchVipEventBean;
import com.cesaas.android.counselor.order.member.bean.service.check.ResultQueryVipBean;
import com.cesaas.android.counselor.order.member.bean.service.member.FaceEventBusBean;
import com.cesaas.android.counselor.order.member.bean.service.member.FaceListBean;
import com.cesaas.android.counselor.order.member.bean.service.member.FocusEventBusBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.member.RemarkEventBusBean;
import com.cesaas.android.counselor.order.member.bean.service.member.ResultMemberServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.member.TagEventBusBean;
import com.cesaas.android.counselor.order.member.bean.service.query.Grades;
import com.cesaas.android.counselor.order.member.net.service.FaceBindNet;
import com.cesaas.android.counselor.order.member.net.service.FocusNet;
import com.cesaas.android.counselor.order.member.net.service.MemberServiceListNet;
import com.cesaas.android.counselor.order.member.net.service.SetMemberRemarkNet;
import com.cesaas.android.counselor.order.member.service.FaceListActivity;
import com.cesaas.android.counselor.order.member.service.MemberNewTagListActivity;
import com.cesaas.android.counselor.order.member.volume.SendVolumeActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/1/23 14:56
 * Version 1.0
 */

public class AllMemberFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,AllMemberNewAdapter.MyOnItemClickListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data,tv_all_member_sum,tv_select_o;
    private ImageView iv_all;
    private LinearLayout ll_show_switch,ll_cancel_check;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;

    private int VipId;
    private int MemberType;
    private int FocusType;
    private String Mobile;
    private boolean isSelectAll = false;
    private int index = 0;

    private SetMemberRemarkNet remarkNet;
    private MemberServiceListNet net;
    private List<MemberServiceListBean> mData=new ArrayList<>();
    private List<MemberServiceListBean> selectMember=new ArrayList<>();
    private AllMemberNewAdapter adapter;

    private ResultQueryVipBean queryVipBean=new ResultQueryVipBean();
    private JSONArray gradeArray;
    private JSONArray tagArray;

    /**
     * 单例
     */
    public static AllMemberFragment newInstance() {
        AllMemberFragment fragmentCommon = new AllMemberFragment();
        return fragmentCommon;
    }

    public void onEventMainThread(SearchVipEventBean msg) {
        if(msg.getType()==10){
            net=new MemberServiceListNet(getContext(),1);
            net.setData(pageIndex,0,msg.getContent());
        }
    }

    public void onEventMainThread(ResultQueryVipBean msg) {
       if(msg!=null && msg.getMemberType()==10){
           queryVipBean=new ResultQueryVipBean();
           queryVipBean=msg;
           if(msg.getGrade()!=null && msg.getGrade().size()!=0){
               gradeArray=new JSONArray();
               for (int i=0;i<msg.getGrade().size();i++){
                   Grades g=new Grades();
                   g.setId(msg.getGrade().get(i).getId());
                   g.setTitle(msg.getGrade().get(i).getTitle());
                   gradeArray.put(g.getGrades());
               }
               queryVipBean.setGrades(gradeArray);
           }
           if(msg.getTag()!=null && msg.getTag().size()!=0){
               tagArray=new JSONArray();
               for (int i=0;i<msg.getTag().size();i++){
                   GetTagListBean tagListBean=new GetTagListBean();
                   tagListBean.setTagId(msg.getTag().get(i).getTagId());
                   tagListBean.setTagName(msg.getTag().get(i).getTagName());
                   tagArray.put(tagListBean.getTagInfo());
               }
               queryVipBean.setTags(tagArray);
           }
           net=new MemberServiceListNet(getContext(),1);
           net.setData(pageIndex,0,queryVipBean.getVipInfo());
       }
    }

    public void onEventMainThread(RemarkEventBusBean msg) {
        MemberType=msg.getMemberType();
        if(msg.getMemberType()==1){
            remarkNet=new SetMemberRemarkNet(getContext());
            remarkNet.setData(msg.getRemark(),msg.getVipId());
        }
    }

    public void onEventMainThread(ResultSetRemarkBean msg) {
        if(MemberType==1){
            if(msg.IsSuccess!=false){
                ToastFactory.getLongToast(getContext(),"添加备注成功!");
                net=new MemberServiceListNet(getContext(),1);
                net.setData(pageIndex,0);
            }else{
                ToastFactory.getLongToast(getContext(),"添加备注失败"+msg.Message);
            }
        }
    }

    public void onEventMainThread(FocusEventBusBean msg) {
        FocusType=msg.getFocusType();
        if(msg.getMemberType()==1){
            if(msg.getFocusType()==1){
                //取消关注
                FocusNet net=new FocusNet(getContext());
                net.setData(msg.getVipId(),0);
            }else{
                //进行关注
                FocusNet net=new FocusNet(getContext());
                net.setData(msg.getVipId(),1);
            }
        }
    }

    public void onEventMainThread(ResultFocusBean msg) {
        if(msg.IsSuccess!=false){
            if(FocusType==1){
                ToastFactory.getLongToast(getContext(),"已取消关注！");
                net=new MemberServiceListNet(getContext(),1);
                net.setData(pageIndex,0);
            }else{
                ToastFactory.getLongToast(getContext(),"关注成功！");
                net=new MemberServiceListNet(getContext(),1);
                net.setData(pageIndex,0);
            }

        }else{
            if(FocusType==1){
                ToastFactory.getLongToast(getContext(),"取消关注失败："+msg.Message);
            }else{
                ToastFactory.getLongToast(getContext(),"关注成功失败："+msg.Message);
            }
        }
    }

    public void onEventMainThread(FaceEventBusBean msg) {
        if(msg!=null){
            VipId=msg.getVipId();
            MemberType=msg.getMemberType();
            Mobile=msg.getMobile();
            if(msg.getMemberType()==1){
                Intent tagIntent = new Intent(getContext(), FaceListActivity.class);
                startActivityForResult(tagIntent, Constant.H5_FACE_BIND);
            }
        }
    }

    public void onEventMainThread(ResultFaceBindBean msg) {
        if(MemberType==1){
            if(msg.IsSuccess!=false){
                ToastFactory.getLongToast(getContext(),"绑定人脸成功！");
                net=new MemberServiceListNet(getContext(),1);
                net.setData(pageIndex,0);
            }else{
                ToastFactory.getLongToast(getContext(),"绑定人脸失败："+msg.Message);
            }
        }
    }

    public void onEventMainThread(TagEventBusBean msg) {
        if(msg!=null){
            VipId=msg.getVipId();
            MemberType=msg.getMemberType();
            if(msg.getMemberType()==1){
                Intent tagIntent = new Intent(getContext(), MemberNewTagListActivity.class);
                tagIntent.putExtra("vipId",msg.getVipId()+"");
                startActivityForResult(tagIntent, Constant.H5_TAG_SELECT);
            }
        }
    }
    public void onEventMainThread(ResultSetTagBean msg) {
        if(MemberType==1){
            if(msg.IsSuccess!=false){
                ToastFactory.getLongToast(getContext(),"设置标签成功！");
            }else{
                ToastFactory.getLongToast(getContext(),"设置标签失败："+msg.Message);
            }
        }
    }

    public void onEventMainThread(ResultMemberServiceListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                totalSize=msg.RecordCount;
                tv_all_member_sum.setText(msg.RecordCount+"");
                tv_not_data.setVisibility(View.GONE);

                mData=new ArrayList<>();
                mData.addAll(msg.TModel);

                if(isLoadMore==true){
                    adapter.addData(mData);
                    adapter.loadMoreComplete();
                    adapter.notifyDataSetChanged();
                }else{
                    adapter=new AllMemberNewAdapter(mData,getActivity(),getContext());
                    adapter.setMyOnItemClickListener(this);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                adapter.setOnLoadMoreListener(this, mRecyclerView);
                mCurrentCounter = adapter.getData().size();

            }else{
                tv_not_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(getContext(),"Msg:"+msg.Message);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_all_member;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        iv_all=findView(R.id.iv_all);
        ll_cancel_check=findView(R.id.ll_cancel_check);
        ll_show_switch=findView(R.id.ll_show_switch);
        tv_select_o=findView(R.id.tv_select_o);
        tv_select_o.setText(R.string.fa_caret_down);
        tv_select_o.setTypeface(App.font);
        tv_all_member_sum=findView(R.id.tv_all_member_sum);
        mRecyclerView=findView(R.id.rv_list);
        mSwipeRefreshLayout=findView(R.id.swipeLayout);
        tv_not_data=findView(R.id.tv_not_data);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initListener() {
        ll_show_switch.setOnClickListener(this);
        ll_cancel_check.setOnClickListener(this);
    }

    @Override
    public void initData() {
        adapter=new AllMemberNewAdapter(mData,getActivity(),getContext());

        net=new MemberServiceListNet(getContext(),1);
        net.setData(pageIndex,0);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.ll_show_switch:
                if(adapter.getMyLiveList().size()!=0){
                    selectMember=new ArrayList<>();
                    for (int i=0;i<adapter.getMyLiveList().size();i++){
                        if(adapter.getMyLiveList().get(i).isSelect()==true){
                            selectMember.add(adapter.getMyLiveList().get(i));
                        }
                    }
                    if(selectMember.size()!=0){
                        showBottonDialog(getContext());
                    }else{
                        ToastFactory.getLongToast(getContext(),"请选择会员！");
                    }
                }else{
                    ToastFactory.getLongToast(getContext(),"请选择会员！");
                }
            break;
            case R.id.ll_cancel_check:
                if(index!=0){
                    for (int i=0;i<adapter.getMyLiveList().size();i++){
                        adapter.getMyLiveList().get(i).setSelect(false);
                    }
                    iv_all.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        adapter = new AllMemberNewAdapter(mData,getActivity(),getContext());
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                net=new MemberServiceListNet(getContext(),1);
                net.setData(pageIndex,0);
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        if (adapter.getData().size() < Constant.PAGE_SIZE) {
            adapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= totalSize) {
                //数据全部加载完毕
                adapter.loadMoreEnd(mLoadMoreEndGone);
            } else {
                if (isErr) {
                    //成功获取更多数据
                    isLoadMore=true;
                    pageIndex+=1;
                    net=new MemberServiceListNet(getContext(),1);
                    net.setData(pageIndex,0);
                } else {
                    //获取更多数据失败
                    isErr = true;
                    adapter.loadMoreFail();
                }
            }
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==901){//标签筛选
            if(data!=null){
                List<GetTagListBean> selectList=(ArrayList<GetTagListBean>)data.getSerializableExtra("selectList");
                JSONArray tagArray=new JSONArray();
                for (int i=0;i<selectList.size();i++){
                    tagArray.put(selectList.get(i).getTagId());
                }
                //设置变标签
                SetTagNet net=new SetTagNet(getContext());
                net.setData(VipId,tagArray);
            }
        }else if(requestCode==403){//人脸
            if(data!=null){
                FaceListBean bean= (FaceListBean) data.getSerializableExtra("selectList");
                FaceBindNet net=new FaceBindNet(getContext());
                net.setData(VipId,Mobile,bean.getEquipmentName(),bean.getImagePath(),bean.getImageUrl());
            }
        }
    }

    @Override
    public void onItemClickListener(int pos, List<MemberServiceListBean> myLiveList) {
        MemberServiceListBean myLive = myLiveList.get(pos);
        boolean isSelect = myLive.isSelect();
        if (!isSelect) {
            index++;
            myLive.setSelect(true);
            if (index == myLiveList.size()) {
                isSelectAll = true;
//                tvSelectAll.setText("取消全选");
            }
        } else {
            myLive.setSelect(false);
            index--;
            isSelectAll = false;
//            tvSelectAll.setText("全选");
        }
        if(index!=0){
            iv_all.setVisibility(View.VISIBLE);
        }else{
            iv_all.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    public static View dialogContentView;
    private static Dialog bottomDialog;
    private LinearLayout ll_send_msg,ll_send_volume,ll_push_shop,ll_weixin_service,ll_set_tag;

    public void showBottonDialog(Context mContext){
        bottomDialog= new Dialog(mContext, R.style.BottomDialog);
        dialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_send_member_service_normal, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        ll_send_msg= (LinearLayout) bottomDialog.findViewById(R.id.ll_send_msg);
        ll_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                bundle.putSerializable("MemberList", (Serializable)selectMember);
                Skip.mNextFroData(getActivity(),GroupSendMessageActivity.class,bundle);
            }
        });
        ll_send_volume= (LinearLayout) bottomDialog.findViewById(R.id.ll_send_volume);
        ll_send_volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                bundle.putSerializable("MemberList", (Serializable)selectMember);
                Skip.mNextFroData(getActivity(),SendVolumeActivity.class,bundle);
            }
        });
        ll_push_shop= (LinearLayout) bottomDialog.findViewById(R.id.ll_push_shop);
        ll_weixin_service= (LinearLayout) bottomDialog.findViewById(R.id.ll_weixin_service);
        ll_set_tag= (LinearLayout) bottomDialog.findViewById(R.id.ll_set_tag);

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }
}
