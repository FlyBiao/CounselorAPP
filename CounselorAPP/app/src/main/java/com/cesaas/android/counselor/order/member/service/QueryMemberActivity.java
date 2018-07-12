package com.cesaas.android.counselor.order.member.service;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.member.adapter.MemberQueryAdapter;
import com.cesaas.android.counselor.order.member.bean.VipIdsBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultCreateMemberService;
import com.cesaas.android.counselor.order.member.bean.service.ResultVipListBean;
import com.cesaas.android.counselor.order.member.bean.service.VipListBean;
import com.cesaas.android.counselor.order.member.bean.service.query.Grades;
import com.cesaas.android.counselor.order.member.bean.service.query.ResultQueryMemberBean;
import com.cesaas.android.counselor.order.member.net.GetVipListNet;
import com.cesaas.android.counselor.order.member.net.service.CreateMemberServiceNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询会员
 */
public class QueryMemberActivity extends BaseTemplateActivity implements SwipeRefreshLayout.OnRefreshListener,MemberQueryAdapter.OnItemClickListener{

    private TextView tvTitle,tvRightTitle,tv_not_data;
    private TextView tv_back,tv_query_size,tv_select_size;
    private TextView tv_create_service;
    private ImageView iv_tow;
    private LinearLayout llBack;
    private RecyclerView rv_member_list;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean isSelectAll = false;
    private boolean editorStatus = true;
    private int index = 0;

    private int delayMillis = 2000;

    private List<VipListBean> mData=new ArrayList<>();
    private MemberQueryAdapter adapter;
    private GetVipListNet net;
    private CreateMemberServiceNet serviceNet;

    private JSONArray grades=new JSONArray();
    private JSONArray tags=new JSONArray();
    private JSONArray vipIds=new JSONArray();
    private ResultQueryMemberBean queryMemberBean=new ResultQueryMemberBean();

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_query;
    }

    public void onEventMainThread(ResultVipListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                try{
                    tv_not_data.setVisibility(View.GONE);
                    tv_select_size.setText(String.valueOf(0));
                    tv_query_size.setText(String.valueOf(msg.RecordCount));

                    mData=new ArrayList<>();
                    mData.addAll(msg.TModel);

                    adapter = new MemberQueryAdapter(this);
                    rv_member_list.setAdapter(adapter);
                    adapter.notifyAdapter(mData, false);
                    adapter.setOnItemClickListener(this);

                    index = 0;
                    isSelectAll = false;

                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                tv_not_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }

    public void onEventMainThread(ResultCreateMemberService msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"生成服务成功!");
            Skip.mNext(mActivity,MemberServiceCheckActivity.class,true);
        }else{
            ToastFactory.getLongToast(mContext,"生成服务失败："+msg.Message);
        }
    }

    @Override
    public void initViews() {
        tv_query_size=findView(R.id.tv_query_size);
        tv_select_size=findView(R.id.tv_select_size);
        tv_not_data=findView(R.id.tv_not_data);
        tvTitle=findView(R.id.tv_base_title);
        tvTitle.setText("会员查询");
        tvRightTitle=findView(R.id.tv_base_title_right);
        tvRightTitle.setVisibility(View.VISIBLE);
        tvRightTitle.setText("全选");
        llBack=findView(R.id.ll_base_title_back);
        tv_create_service=findView(R.id.tv_create_service);
        tv_back=findView(R.id.tv_back);
        iv_tow=findView(R.id.iv_tow);
        iv_tow.setImageResource(R.mipmap.tow_bule);

        mSwipeRefreshLayout=findView(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        rv_member_list=findView(R.id.rv_member_list);
        rv_member_list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initListener() {
        llBack.setOnClickListener(this);
        tv_create_service.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tvRightTitle.setOnClickListener(this);
    }

    @Override
    public void initData() {
        try{
            Bundle bundle=getIntent().getExtras();
            queryMemberBean= (ResultQueryMemberBean) bundle.getSerializable("queryMemberBean");
            if(queryMemberBean.getGrades()!=null && queryMemberBean.getGrades().size()!=0){
                for (int i=0;i<queryMemberBean.getGrades().size();i++){
                    Grades g=new Grades();
                    g.setId(queryMemberBean.getGrades().get(i).getId());
                    g.setTitle(queryMemberBean.getGrades().get(i).getTitle());
                    grades.put(g.getGrades());
                }
            }
            if(queryMemberBean.getTags()!=null && queryMemberBean.getTags().size()!=0){
                for (int i=0;i<queryMemberBean.getTags().size();i++){
                    GetTagListBean t=new GetTagListBean();
                    t.setTagId(queryMemberBean.getTags().get(i).getTagId());
                    t.setTagName(queryMemberBean.getTags().get(i).getTagName());
                    tags.put(t.getTagInfo());
                }
            }

            net=new GetVipListNet(mContext);
            net.setData(queryMemberBean.getMoneyAreaMin(),queryMemberBean.getMoneyAreaMax(),queryMemberBean.getBuyDateAreaMin(),queryMemberBean.getBuyDateAreaMax(),
                    queryMemberBean.getBirthdayAreaMin(),queryMemberBean.getBirthdayAreaMax(),queryMemberBean.getNoBuyCount(),grades,queryMemberBean.getPointsAreaMin(),
                    queryMemberBean.getPointsAreaMax(),queryMemberBean.getCreateAreaMin(),queryMemberBean.getCreateAreaMax(),tags);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
            break;case R.id.tv_back:
                Skip.mBack(mActivity);
            break;
            case R.id.tv_base_title_right:
                selectAllMain();
                break;
            case R.id.tv_create_service:
                if(adapter.getMyLiveList()!=null &&adapter.getMyLiveList().size()!=0){
                    vipIds=new JSONArray();
                    for (int i=0;i<adapter.getMyLiveList().size();i++){
                        if(adapter.getMyLiveList().get(i).isSelect()==true){
                            VipIdsBean vipBean=new VipIdsBean();
                            vipBean.setId(adapter.getMyLiveList().get(i).getVipId());
                            vipBean.setCounselorId(adapter.getMyLiveList().get(i).getCounselorId());
                            vipBean.setName(adapter.getMyLiveList().get(i).getName());
                            vipIds.put(vipBean.getVipIds());
                        }
                    }
                    serviceNet=new CreateMemberServiceNet(mContext);
                    serviceNet.setData(queryMemberBean,grades,tags,vipIds);
                }else{
                    ToastFactory.getLongToast(mContext,"未获取相关会员信息！");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                net=new GetVipListNet(mContext);
                net.setData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, delayMillis);
    }


    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (adapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = adapter.getMyLiveList().size(); i < j; i++) {
                adapter.getMyLiveList().get(i).setSelect(true);
            }
            index = adapter.getMyLiveList().size();
            tvRightTitle.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = adapter.getMyLiveList().size(); i < j; i++) {
                adapter.getMyLiveList().get(i).setSelect(false);
            }
            index = 0;
            tvRightTitle.setText("全选");
            isSelectAll = false;
        }
        adapter.notifyDataSetChanged();
        tv_select_size.setText(String.valueOf(index));
    }


    @Override
    public void onItemClickListener(int pos, List<VipListBean> myLiveList) {
        if (editorStatus) {
            VipListBean myLive = myLiveList.get(pos);
            boolean isSelect = myLive.isSelect();
            if (!isSelect) {
                index++;
                myLive.setSelect(true);
                if (index == myLiveList.size()) {
                    isSelectAll = true;
                    tvRightTitle.setText("取消全选");
                }
            } else {
                myLive.setSelect(false);
                index--;
                isSelectAll = false;
                tvRightTitle.setText("全选");
            }
            tv_select_size.setText(String.valueOf(index));
            adapter.notifyDataSetChanged();
        }
    }
}
