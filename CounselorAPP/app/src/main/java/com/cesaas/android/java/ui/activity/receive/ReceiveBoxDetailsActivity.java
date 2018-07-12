package com.cesaas.android.java.ui.activity.receive;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cesaas.android.counselor.order.BasesSpecialActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.scan.ZxingScanReceiveBoxActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.cesaas.android.java.adapter.receive.ReceiveBoxDetailsListAdapter;
import com.cesaas.android.java.bean.inventory.ResultInventoryScanBean;
import com.cesaas.android.java.bean.move.MoveDeliveryGoodsDetailBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryDetailBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryGoodsDetailBean;
import com.cesaas.android.java.bean.receive.ResultReceivingSureBean;
import com.cesaas.android.java.net.receive.ReceiveDetailNet;
import com.cesaas.android.java.net.receive.ReceiveGoodsDetailNet;
import com.cesaas.android.java.net.receive.ReceiveScanNet;
import com.cesaas.android.java.net.receive.ReceivingSureNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 收货箱详情
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class ReceiveBoxDetailsActivity extends BasesSpecialActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private TextView tvTitle,tv_box_no,tv_complete_box,tv_back;
    private TextView tv_shop_icon,tv_org_icon,tv_send_num,tv_nums,tv_not_data,tv_angle_down,tv_angle_up;
    private TextView tv_no,tv_originOrganizationTitle,tv_originShopName,tv_receiveOrganizationTitle,tv_receiveShopName;
    private LinearLayout llBack,ll_scan_add_box,ll_search,ll_barcodeNo,lll;
    private MClearEditText et_search_content;
    private EditText et_barcodeNo,et_goods_num;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;
    private boolean isAddCode=false;

    private long id;
    private long boxId;
    private int boxNo;
    private int goodsNum=1;
    private int status;

    private ReceiveDetailNet deliveryDetailNet;
    private ReceiveGoodsDetailNet moveDeliveryGoodsDetailNet;
    private ReceiveScanNet receiveScanNet;
    private ReceivingSureNet receivingSureNet;
//    private ReceiveGetDetailListByBoxNet receiveGetDetailListByBoxNet;

    private ReceiveBoxDetailsListAdapter moveDeliveryBoxGoodsDetailAdapter;
    private List<MoveDeliveryGoodsDetailBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_box_details);

        Intent intent=getIntent();
        String strId = intent.getStringExtra("id");
        id=Long.valueOf(strId);
        String strBoxId = intent.getStringExtra("boxId");
        boxId=Long.valueOf(strBoxId);
        String strBoxNo = intent.getStringExtra("boxNo");
        boxNo=Integer.valueOf(strBoxNo);
        String statuss = intent.getStringExtra("status");
        status=Integer.valueOf(statuss);

        initView();
        initData();

        if(status==30 || status==40){
            lll.setVisibility(View.GONE);
        }else{
            lll.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 接收收货确认
     * @param msg
     */
    public void onEventMainThread(ResultReceivingSureBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "收货成功");
                Intent mIntent = new Intent();
                mIntent.putExtra("result", "true");
                setResult(10, mIntent);// 设置结果，并进行传送
                finish();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收调货条码列表
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryGoodsDetailBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    tv_not_data.setVisibility(View.GONE);
                    mData = new ArrayList<>();
                    mData.addAll(msg.arguments.resp.records.value);

                    moveDeliveryBoxGoodsDetailAdapter = new ReceiveBoxDetailsListAdapter(mData, mActivity, mContext);
                    recyclerView.setAdapter(moveDeliveryBoxGoodsDetailAdapter);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tv_not_data.setVisibility(View.VISIBLE);
                }
            } else {
                recyclerView.setVisibility(View.GONE);
                tv_not_data.setVisibility(View.VISIBLE);
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收扫描录入
     * @param msg
     */
    public void onEventMainThread(ResultInventoryScanBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "录入成功！");
                isAddCode = true;
                goodsNum = 1;
                et_goods_num.setText(String.valueOf(goodsNum));
                et_barcodeNo.setText("");
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收调货发货详情
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryDetailBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.model != null && !"".equals(msg.arguments.resp.model)) {
                    tv_send_num.setText(String.valueOf(msg.arguments.resp.model.getShipmentsNum()));
                    tv_nums.setText(String.valueOf(msg.arguments.resp.model.getNum()));

                    tv_no.setText(msg.arguments.resp.model.getNo());
                    tv_originOrganizationTitle.setText(msg.arguments.resp.model.getOriginOrganizationTitle());
                    tv_originShopName.setText(msg.arguments.resp.model.getOriginShopName());
                    tv_receiveOrganizationTitle.setText(msg.arguments.resp.model.getReceiveOrganizationTitle());
                    tv_receiveShopName.setText(msg.arguments.resp.model.getReceiveShopName());
                }
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    public void initData() {
        tv_box_no.setText(String.valueOf(boxNo));
        deliveryDetailNet=new ReceiveDetailNet(mContext);
        deliveryDetailNet.setData(id);

//        receiveGetDetailListByBoxNet=new ReceiveGetDetailListByBoxNet(mContext);
//        receiveGetDetailListByBoxNet.setData(boxId,id);

        moveDeliveryGoodsDetailNet=new ReceiveGoodsDetailNet(mContext);
        moveDeliveryGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPIdAndBoxId(id,boxId));
    }

    private void initView() {
        lll= (LinearLayout) findViewById(R.id.lll);
        ll_barcodeNo= (LinearLayout) findViewById(R.id.ll_barcodeNo);
        ll_barcodeNo.setOnClickListener(this);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        tv_send_num= (TextView) findViewById(R.id.tv_send_num);
        tv_nums= (TextView) findViewById(R.id.tv_nums);
        tv_complete_box= (TextView) findViewById(R.id.tv_complete_box);
        tv_complete_box.setOnClickListener(this);
        tv_back= (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
        ll_scan_add_box= (LinearLayout) findViewById(R.id.ll_scan_add_box);
        ll_scan_add_box.setOnClickListener(this);
        tv_receiveShopName= (TextView) findViewById(R.id.tv_receiveShopName);
        tv_receiveOrganizationTitle= (TextView) findViewById(R.id.tv_receiveOrganizationTitle);
        tv_originShopName= (TextView) findViewById(R.id.tv_originShopName);
        tv_originOrganizationTitle= (TextView) findViewById(R.id.tv_originOrganizationTitle);
        tv_no= (TextView) findViewById(R.id.tv_no);
        tv_shop_icon= (TextView) findViewById(R.id.tv_shop_icon);
        tv_shop_icon.setText(R.string.business_school);
        tv_shop_icon.setTypeface(App.font);
        tv_org_icon= (TextView) findViewById(R.id.tv_org_icon);
        tv_org_icon.setText(R.string.fa_long_arrow_right);
        tv_org_icon.setTypeface(App.font);
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("按箱收货");
        tv_box_no= (TextView) findViewById(R.id.tv_box_no);
        tv_angle_down= (TextView) findViewById(R.id.tv_angle_down);
        tv_angle_down.setText(R.string.fa_caret_down);
        tv_angle_down.setTypeface(App.font);
        tv_angle_down.setOnClickListener(this);
        tv_angle_up= (TextView) findViewById(R.id.tv_angle_up);
        tv_angle_up.setText(R.string.fa_caret_up);
        tv_angle_up.setTypeface(App.font);
        tv_angle_up.setOnClickListener(this);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ll_search= (LinearLayout) findViewById(R.id.ll_search);
        ll_search.setOnClickListener(this);
        et_barcodeNo= (EditText) findViewById(R.id.et_barcodeNo);
        et_goods_num= (EditText) findViewById(R.id.et_goods_num);
        et_search_content= (MClearEditText) findViewById(R.id.et_search_content);
        et_search_content.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    moveDeliveryGoodsDetailNet=new ReceiveGoodsDetailNet(mContext);
                    moveDeliveryGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPIdAndBoxIdAndNo(id,boxId,et_search_content.getText().toString()));
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_search:
                moveDeliveryGoodsDetailNet=new ReceiveGoodsDetailNet(mContext);
                moveDeliveryGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPIdAndBoxIdAndNo(id,boxId,et_search_content.getText().toString()));
                break;
            case R.id.tv_angle_down:
                if(goodsNum==1){
                }else{
                    goodsNum=goodsNum-1;
                }
                et_goods_num.setText(String.valueOf(goodsNum));
                break;
            case R.id.tv_angle_up:
                goodsNum=goodsNum+1;
                et_goods_num.setText(String.valueOf(goodsNum));
                break;
            case R.id.ll_base_title_back:
                if(isAddCode==true){
                    Intent mIntent = new Intent();
                    mIntent.putExtra("result","true");
                    setResult(10, mIntent);// 设置结果，并进行传送
                    finish();
                }else{
                    Skip.mBack(mActivity);
                }
                break;
            case R.id.tv_complete_box:
                receivingSureNet=new ReceivingSureNet(mContext);
                receivingSureNet.setData(id);
                break;
            case R.id.tv_back:
                if(isAddCode==true){
                    Intent mIntent = new Intent();
                    mIntent.putExtra("result","true");
                    setResult(10, mIntent);// 设置结果，并进行传送
                    finish();
                }else{
                    Skip.mBack(mActivity);
                }
                break;
            case R.id.ll_scan_add_box:
                bundle.putLong("id",id);
                bundle.putLong("boxId",boxId);
                Skip.mNextFroData(mActivity, ZxingScanReceiveBoxActivity.class,bundle);
                break;
            case R.id.ll_barcodeNo:
                if(!TextUtils.isEmpty(et_barcodeNo.getText().toString())){
                    receiveScanNet=new ReceiveScanNet(mContext);
                    receiveScanNet.setData(id,boxId,et_barcodeNo.getText().toString(),goodsNum,6);
                }else{
                    ToastFactory.getLongToast(mContext,"请输入商品条码");
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        moveDeliveryBoxGoodsDetailAdapter=new ReceiveBoxDetailsListAdapter(mData,mActivity,mContext);
        moveDeliveryBoxGoodsDetailAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                swipeLayout.setRefreshing(false);
                moveDeliveryBoxGoodsDetailAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }
}
