package com.cesaas.android.order.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.BasesSpecialActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.GetByBarcodeCode;
import com.cesaas.android.counselor.order.bean.ResultGetByBarcodeCodeBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.bean.service.ResultGetOneByMobileBean;
import com.cesaas.android.counselor.order.member.bean.service.VipListBean;
import com.cesaas.android.counselor.order.member.net.service.GetOneByMobileNet;
import com.cesaas.android.counselor.order.net.GetByBarcodeCodeNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.bean.goods.AddBarcodeModelBean;
import com.cesaas.android.java.bean.goods.AddBarcodeModelItemBean;
import com.cesaas.android.java.bean.move.MoveDeliveryListBeanBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryAddByO2OBean;
import com.cesaas.android.java.net.move.MoveDeliveryAddByO2ONet;
import com.cesaas.android.order.adapter.retail.QueryShopAdapter;
import com.cesaas.android.order.adapter.retail.QueryShopRadioAdapter;
import com.cesaas.android.order.bean.retail.ResultRouteBean;
import com.cesaas.android.order.bean.retail.ResultSearchByBarcodeBean;
import com.cesaas.android.order.bean.retail.ResultStockRouteTypeBean;
import com.cesaas.android.order.bean.retail.RouteItemBean;
import com.cesaas.android.order.bean.retail.SearchByBarcodeBean;
import com.cesaas.android.order.net.retail.AddressNet;
import com.cesaas.android.order.net.retail.RouteNet;
import com.cesaas.android.order.net.retail.SearchByBarcodeNet;
import com.cesaas.android.order.net.retail.StockRouteTypeNet;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cn.hugo.android.scanner.CaptureActivity;

/**
 * O2O店铺库存查询
 */
public class OrderInventoryActivity extends BasesSpecialActivity implements View.OnClickListener{
    private LinearLayout llBaseBack;
    private RecyclerView rv_shop_list;
    private TextView mTextViewTitle,tv_up,tv_bottom,tv_rout_transfer,tv_shop_transfer,tv_folder_open,tv_folder_open_mobile,tv_folder_opens,tv_shop_nums;
    private LinearLayout ll_search_vip,ll_scan_order,ll_query_member,ll_transfer_routing,ll_address,ll_automatic_route,ll_shop_route,ll_query_mobile;
    private EditText et_search,et_shop_count,et_address,et_details_address,et_mobile,et_name;
    private TextView tv_query_member,tv_barcode_no,tv_color,tv_size;
    private CheckBox cb_customer,cb_shop;

    private int REQUEST_CONTACT = 20;
    final int RESULT_CODE=101;
    private int isChecks=1;

    private  String City,Province,District;
    private String scanCode;
    private int shopCount=1;
    private int type=1;

    private CityPickerView mCityPickerView = new CityPickerView();
    private QueryShopRadioAdapter queryShopRadioAdapter;
    private List<SearchByBarcodeBean> mMyLiveList;
    private GetByBarcodeCode getByBarcodeCode;
    private RouteItemBean routeItemBean;
    private JSONArray routeArray;

    private StockRouteTypeNet routeTypeNet;
    private SearchByBarcodeNet searchByBarcodeNet;
    private RouteNet routeNet;
    private AddressNet addressNet;
    private GetByBarcodeCodeNet codeNet;
    private GetOneByMobileNet mobileNet;

    private MoveDeliveryAddByO2ONet moveDeliveryAddByO2ONet;
    private AddBarcodeModelBean barcodeModelBean;
    private JSONArray barcodeArr;
    private MoveDeliveryListBeanBean moveDeliveryListBeanBean;
    private SearchByBarcodeBean searchByBarcodeBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_inventory);
        initView();
        initData();
        /**
         * 预先加载仿iOS滚轮实现的全部数据
         */
        mCityPickerView.init(this);
    }

    /**
     * 查询商品信息
     * @param msg
     */
    public void onEventMainThread(ResultGetByBarcodeCodeBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null){
                getByBarcodeCode=new GetByBarcodeCode();
                getByBarcodeCode=msg.TModel;

                tv_barcode_no.setText(getByBarcodeCode.getBarcodeCode());
                tv_color.setText(getByBarcodeCode.BarcodeInfo.getName1()+":"+getByBarcodeCode.BarcodeInfo.getValue1());
                tv_size.setText(getByBarcodeCode.BarcodeInfo.getName2()+":"+getByBarcodeCode.BarcodeInfo.getValue2());
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取商品信息失败："+msg.Message);
        }
    }

    /**
     * 根据商品条码查询sku以及有货店铺列表信息
     * @param msg
     */
    public void onEventMainThread(ResultSearchByBarcodeBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_shop_nums.setText(String.valueOf(msg.TModel.size()));
                mMyLiveList=new ArrayList<>();
                mMyLiveList.addAll(msg.TModel);

                queryShopRadioAdapter=new QueryShopRadioAdapter(mMyLiveList);
                rv_shop_list.setAdapter(queryShopRadioAdapter);
                queryShopRadioAdapter.setOnItemClickListener(new QueryShopRadioAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        for (int i = 0; i < mMyLiveList.size(); i++) {
                            mMyLiveList.get(i).setSelect(false);
                        }
                        searchByBarcodeBean=new SearchByBarcodeBean();
                        searchByBarcodeBean=mMyLiveList.get(position);
                        mMyLiveList.get(position).setSelect(true);
                        queryShopRadioAdapter.notifyDataSetChanged();
                    }
                });
            }
        }else{
            ToastFactory.getLongToast(mContext,"商品条码查询失败："+msg.Message);
        }
    }

    /**
     * 查询店铺订单调货方式
     * @param msg
     */
    public void onEventMainThread(ResultStockRouteTypeBean msg) {
        if(msg.IsSuccess!=false && msg.TModel!=null){
            if(msg.TModel.getType()==0){//指定店铺
                ll_shop_route.setVisibility(View.VISIBLE);
                tv_shop_transfer.setVisibility(View.VISIBLE);
            }else{//自动路由
                tv_rout_transfer.setVisibility(View.VISIBLE);
                ll_automatic_route.setVisibility(View.VISIBLE);
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取调货方式失败："+msg.Message);
        }
    }

    /**
     * 发起店铺调货
     * @param msg
     */
    public void onEventMainThread(ResultRouteBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel.getOrganizationId()!=0){
                moveDeliveryListBeanBean=new MoveDeliveryListBeanBean();
                moveDeliveryListBeanBean.setOriginOrganizationId(msg.TModel.getOrganizationId());
                moveDeliveryListBeanBean.setOriginShopId(msg.TModel.getShopId());
                moveDeliveryListBeanBean.setReceiveOrganizationId(Integer.parseInt(prefs.getString("OrgId")));
                moveDeliveryListBeanBean.setReceiveShopId(Integer.parseInt(prefs.getString("ShopId")));
                moveDeliveryListBeanBean.setRemark(msg.TModel.getSyncCode());
                moveDeliveryListBeanBean.setFromType(2);

                barcodeModelBean=new AddBarcodeModelBean();
                barcodeModelBean.setBarcodeNo(et_search.getText().toString());
                barcodeModelBean.setNum(shopCount);
                barcodeArr=new JSONArray();
                barcodeArr.put(barcodeModelBean.getAddBarcodeModel());
                AddBarcodeModelItemBean modelItemBean=new AddBarcodeModelItemBean();
                modelItemBean.setValue(barcodeArr);

                moveDeliveryAddByO2ONet=new MoveDeliveryAddByO2ONet(mContext);
                moveDeliveryAddByO2ONet.setData(moveDeliveryListBeanBean.addMoveDeliveryAddByO2O(),modelItemBean.getAddBarcodeModelItem());
            }else{
                ToastFactory.getLongToast(mContext,"未获取相关机构Id");
            }

        }else{
            ToastFactory.getLongToast(mContext,"店铺调货失败："+msg.Message);
        }
    }

    /**
     * 发起生成o2o调拨单(直接生成发货单和收货单并更新库存)
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryAddByO2OBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, msg.arguments.resp.errorMessage);
                finish();
            } else {
                ToastFactory.getLongToast(mContext, "添加失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 获取会员信息
     * @param msg
     */
    public void onEventMainThread(ResultGetOneByMobileBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null){
                et_name.setText(msg.TModel.getName());
                Province=msg.TModel.getProvince();
                City=msg.TModel.getCity();
                District=msg.TModel.getArea();
                et_address.setText(Province+City+District);
                et_details_address.setText(msg.TModel.getAddress());
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取会员信息失败："+msg.Message);
        }
    }


    public void initView(){
        ll_query_mobile= (LinearLayout) findViewById(R.id.ll_query_mobile);
        ll_query_mobile.setOnClickListener(this);
        et_mobile= (EditText) findViewById(R.id.et_mobile);
        et_name= (EditText) findViewById(R.id.et_name);
        tv_size= (TextView) findViewById(R.id.tv_size);
        tv_color= (TextView) findViewById(R.id.tv_color);
        tv_barcode_no= (TextView) findViewById(R.id.tv_barcode_no);
        tv_shop_nums= (TextView) findViewById(R.id.tv_shop_nums);
        tv_folder_open= (TextView) findViewById(R.id.tv_folder_open);
        tv_folder_open.setOnClickListener(this);
        tv_folder_open.setText(R.string.fa_folder_open_o);
        tv_folder_open.setTypeface(App.font);

        tv_folder_open_mobile= (TextView) findViewById(R.id.tv_folder_open_mobile);
        tv_folder_open_mobile.setOnClickListener(this);
        tv_folder_open_mobile.setText(R.string.fa_search);
        tv_folder_open_mobile.setTypeface(App.font);

        tv_folder_opens= (TextView) findViewById(R.id.tv_folder_opens);
        tv_folder_opens.setOnClickListener(this);
        tv_folder_opens.setText(R.string.fa_folder_open_o);
        tv_folder_opens.setTypeface(App.font);
        ll_automatic_route= (LinearLayout) findViewById(R.id.ll_automatic_route);
        ll_shop_route= (LinearLayout) findViewById(R.id.ll_shop_route);
        tv_rout_transfer= (TextView) findViewById(R.id.tv_rout_transfer);
        tv_rout_transfer.setOnClickListener(this);
        tv_shop_transfer= (TextView) findViewById(R.id.tv_shop_transfer);
        tv_shop_transfer.setOnClickListener(this);
        rv_shop_list= (RecyclerView) findViewById(R.id.rv_shop_list);
        rv_shop_list.setLayoutManager(new LinearLayoutManager(this));
        et_address= (EditText) findViewById(R.id.et_address);
        et_address.setOnClickListener(this);
        et_details_address= (EditText) findViewById(R.id.et_details_address);
        ll_address= (LinearLayout) findViewById(R.id.ll_address);
        ll_address.setOnClickListener(this);
        ll_transfer_routing= (LinearLayout) findViewById(R.id.ll_transfer_routing);
        ll_transfer_routing.setOnClickListener(this);
        ll_query_member= (LinearLayout) findViewById(R.id.ll_query_member);
        ll_query_member.setOnClickListener(this);
        ll_scan_order= (LinearLayout) findViewById(R.id.ll_scan_order);
        ll_scan_order.setOnClickListener(this);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
        tv_up= (TextView) findViewById(R.id.tv_up);
        tv_up.setOnClickListener(this);
        tv_up.setText(R.string.fa_sort_up);
        tv_up.setTypeface(App.font);
        tv_bottom= (TextView) findViewById(R.id.tv_bottom);
        tv_bottom.setOnClickListener(this);
        tv_bottom.setText(R.string.fa_sort_desc);
        tv_bottom.setTypeface(App.font);
        tv_query_member= (TextView) findViewById(R.id.tv_query_member);
        tv_query_member.setText(R.string.fa_search);
        tv_query_member.setTypeface(App.font);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewTitle.setText("O2O店铺库存查询");
        ll_search_vip= (LinearLayout) findViewById(R.id.ll_search_vip);
        ll_search_vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByBarcodeNet=new SearchByBarcodeNet(mContext);
                searchByBarcodeNet.setData(shopCount,et_search.getText().toString());

                codeNet=new GetByBarcodeCodeNet(mContext);
                codeNet.setData(et_search.getText().toString());
            }
        });
        et_shop_count= (EditText) findViewById(R.id.et_shop_count);
        et_search= (EditText) findViewById(R.id.et_search);
        et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchByBarcodeNet=new SearchByBarcodeNet(mContext);
                    searchByBarcodeNet.setData(shopCount,et_search.getText().toString());

                    codeNet=new GetByBarcodeCodeNet(mContext);
                    codeNet.setData(et_search.getText().toString());
                }
                return false;
            }
        });
        cb_customer= (CheckBox) findViewById(R.id.cb_customer);
        cb_customer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isChecks=1;
                    cb_shop.setChecked(false);
                    et_mobile.setText("");
                    et_name.setText("");
                    et_address.setText("");
                    et_details_address.setText("");
                }
            }
        });
        cb_shop= (CheckBox) findViewById(R.id.cb_shop);
        cb_shop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isChecks=2;
                    cb_customer.setChecked(false);
                    if(prefs.getString("Mobile")!=null && !"".equals(prefs.getString("Mobile"))){
                        et_mobile.setText(prefs.getString("Mobile"));
                    }else{
                        et_mobile.setText(prefs.getString("shopMobile"));
                    }

                    if(prefs.getString("Name")!=null && !"".equals(prefs.getString("Name"))){
                        et_name.setText(prefs.getString("Name"));
                    }else{
                        et_name.setText(prefs.getString("NickName"));
                    }

                    et_address.setText(prefs.getString("ShopProvince")+prefs.getString("ShopCity")+prefs.getString("ShopArea"));
                    et_details_address.setText(prefs.getString("ShopAddress"));
                }
            }
        });
    }

    public void initData(){
        //查询调货路由方式
        routeTypeNet=new StockRouteTypeNet(mContext);
        routeTypeNet.setData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.ll_scan_order:
                Skip.mScanCheckCargoResult(mActivity, CaptureActivity.class, REQUEST_CONTACT);
                break;
            case R.id.ll_query_member:
                break;
            case R.id.ll_transfer_routing:
                Skip.mNext(mActivity, RetailOrderActivity.class);
                break;
            case R.id.ll_address:
                wheel();
                break;
            case R.id.ll_query_mobile:
                mobileNet=new GetOneByMobileNet(mContext);
                mobileNet.setData(et_mobile.getText().toString());
                break;
            case R.id.et_address:
//                wheel();
                break;
            case R.id.tv_up:
                shopCount=shopCount+1;
                et_shop_count.setText(String.valueOf(shopCount));
                break;
            case R.id.tv_bottom:
                if(shopCount<1){
                    shopCount=shopCount-1;
                }
                et_shop_count.setText(String.valueOf(shopCount));
                break;
            case R.id.tv_folder_open:
                wheel();
                break;
            case R.id.tv_folder_opens:
                wheel();
                break;
            case R.id.tv_folder_open_mobile:
                break;
            case R.id.tv_rout_transfer:
                if(!TextUtils.isEmpty(et_search.getText().toString())){
                    if(!TextUtils.isEmpty(et_mobile.getText().toString())){
                        if(!TextUtils.isEmpty(et_name.getText().toString())){
                            if(!TextUtils.isEmpty(et_address.getText().toString())){
                                routeNet=new RouteNet(mContext);
                                routeNet.setData(et_mobile.getText().toString(),"自动路由调货",et_name.getText().toString(),City,Province,District,et_address.getText().toString(),getRouteInfo());
                            }else{
                                ToastFactory.getLongToast(mContext,"收货地址不能为空！");
                            }
                        }else{
                            ToastFactory.getLongToast(mContext,"收货姓名不能为空！");
                        }
                    }else{
                        ToastFactory.getLongToast(mContext,"手机号不能为空！");
                    }
                }else{
                    ToastFactory.getLongToast(mContext,"请先查询商品信息！");
                }
                break;
            case R.id.tv_shop_transfer:
                if(!TextUtils.isEmpty(et_search.getText().toString())){
                    if(!TextUtils.isEmpty(et_mobile.getText().toString())){
                        if(!TextUtils.isEmpty(et_name.getText().toString())){
                            if(!TextUtils.isEmpty(et_address.getText().toString())){
                                if(mMyLiveList!=null &&mMyLiveList.size()!=0){
                                    for (int i=0;i<mMyLiveList.size();i++){
                                        if(mMyLiveList.get(i).isSelect()==true){
                                            routeNet=new RouteNet(mContext);
                                            routeNet.setData(mMyLiveList.get(i).getShopNo(),mMyLiveList.get(i).getShopNo(),et_mobile.getText().toString(),"指定店铺路由调货",et_name.getText().toString(),City,Province,District,et_address.getText().toString(),getRouteInfo());
                                        }
                                    }
                                }else{
                                    ToastFactory.getLongToast(mContext,"未获取相关订单信息！");
                                }
                            }else{
                                ToastFactory.getLongToast(mContext,"收货地址不能为空！");
                            }
                        }else{
                            ToastFactory.getLongToast(mContext,"收货姓名不能为空！");
                        }
                    }else{
                        ToastFactory.getLongToast(mContext,"手机号不能为空！");
                    }
                }else{
                    ToastFactory.getLongToast(mContext,"请先查询商品信息！");
                }
                break;
        }
    }

    public JSONArray getRouteInfo(){
        if(getByBarcodeCode!=null){
            routeItemBean=new RouteItemBean();
            routeItemBean.setQuantity(shopCount);
            routeItemBean.setStyleNo(getByBarcodeCode.getStyleCode());
            routeItemBean.setBarcodeNo(getByBarcodeCode.getBarcodeCode());
            routeItemBean.setAttr(getByBarcodeCode.BarcodeInfo.getName1()+getByBarcodeCode.BarcodeInfo.getValue1()+getByBarcodeCode.BarcodeInfo.getName2()+getByBarcodeCode.BarcodeInfo.getValue2());
            routeItemBean.setBarcodeId(getByBarcodeCode.getBarcodeId());
            routeItemBean.setImageUrl(getByBarcodeCode.getImageUrl());
            routeItemBean.setListPrice(getByBarcodeCode.getPrice());
            routeItemBean.setSellPrice(getByBarcodeCode.getPrice());
            routeItemBean.setPayMent(getByBarcodeCode.getPrice()*shopCount);
            if(type==1){
                routeItemBean.setRemark("自动路由调货");
            }else{
                routeItemBean.setRemark("指定店铺路由调货");
            }
            routeItemBean.setShopStyleId(getByBarcodeCode.getShopStyleId());
            routeItemBean.setSkuValue1(getByBarcodeCode.BarcodeInfo.getValue1());
            routeItemBean.setSkuValue2(getByBarcodeCode.BarcodeInfo.getValue2());
            routeItemBean.setSkuValue3(getByBarcodeCode.BarcodeInfo.getValue3());
            routeItemBean.setStyleName(getByBarcodeCode.getTitle());

            routeArray=new JSONArray();
            routeArray.put(routeItemBean.getRouteItem());
        }
        return routeArray;
    }

    /**
     * 弹出选择器
     */
    private void wheel() {

        CityConfig cityConfig = new CityConfig.Builder().title("选择城市")//标题
                .build();
        mCityPickerView.setConfig(cityConfig);
        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuilder sb = new StringBuilder();
                if (province != null) {
                    sb.append(province.getName());
                    Province=province.getName();
                }

                if (city != null) {
                    sb.append(city.getName());
                    City=city.getName();
                }

                if (district != null) {
                    sb.append(district.getName());
                    District=district.getName();
                }
                et_address.setText(sb.toString());
                et_details_address.setText(sb.toString());
            }

            @Override
            public void onCancel() {
                ToastFactory.getLongToast(mContext,"已取消");
            }
        });
        mCityPickerView.showCityPicker();
    }

    /**
     * 获取处理扫描Activity返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CODE && data.getStringExtra("resultCode")!=null) {
            if(data.getStringExtra("ScanCheckCargo")!=null){
                if(data.getStringExtra("ScanCheckCargo").equals("008")){
                    scanCode= data.getStringExtra("resultCode");
                    et_search.setText(scanCode);

                    searchByBarcodeNet=new SearchByBarcodeNet(mContext);
                    searchByBarcodeNet.setData(shopCount,scanCode);

                    codeNet=new GetByBarcodeCodeNet(mContext);
                    codeNet.setData(scanCode);
                }
            }
        }else{
            ToastFactory.show(mContext, "没有获取扫描结果！！", ToastFactory.CENTER);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
