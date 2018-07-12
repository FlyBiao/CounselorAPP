package com.cesaas.android.counselor.order.activity.user.vip;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultVipOrderListBean;
import com.cesaas.android.counselor.order.bean.SortBean;
import com.cesaas.android.counselor.order.bean.SubOrder;
import com.cesaas.android.counselor.order.manager.bean.GetLstByVipTagBean;
import com.cesaas.android.counselor.order.manager.bean.ResultGetLstByVipTagBean;
import com.cesaas.android.counselor.order.member.StyleRecommendActivity;
import com.cesaas.android.counselor.order.member.adapter.GetLstByVipTagAdapter;
import com.cesaas.android.counselor.order.member.adapter.VipOrderLstAdapter;
import com.cesaas.android.counselor.order.member.adapter.VipTagAdapter;
import com.cesaas.android.counselor.order.member.adapter.VipTagAdapters;
import com.cesaas.android.counselor.order.member.bean.ResultVipGetOneBean;
import com.cesaas.android.counselor.order.member.bean.ResultVipOrderStatisticBean;
import com.cesaas.android.counselor.order.member.bean.Tags;
import com.cesaas.android.counselor.order.member.bean.VipOrderListSection;
import com.cesaas.android.counselor.order.member.net.VipGetOneNet;
import com.cesaas.android.counselor.order.member.net.VipOrderStatisticNet;
import com.cesaas.android.counselor.order.net.GetLstByVipTagNet;
import com.cesaas.android.counselor.order.net.VipOrderListNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.CalenderUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 人脸识别会员画像
 */
public class MemberPortraitActivity extends BasesActivity implements View.OnClickListener{

    RecyclerView rv_shopping_record_list;
    RecyclerView rv_shoppingcart_list;
    RecyclerView rv_vip_tag,rv_vip_tags;
    ImageView iv_vip_img,iv_face_image;
    TextView tv_card_name,tv_vip_name,tv_birth_day,tv_shop_name,tv_consume_amount,tv_create_time,tv_last_buy,tv_counselor_name;
    TextView tv_Quantity,tv_RetailTotal,tv_Payment;

    private LinearLayout llBack;
    private TextView mTvTitle;
    private TextView mTvRightTitle;

    private SortBean sortBean;
    private JSONArray arraySort;

    private GetLstByVipTagAdapter shopPingCartAdapter;
    private List<GetLstByVipTagBean> goodsInfoBeen;

    private VipOrderLstAdapter orderLstAdapter;
    private List<VipOrderListSection> mData;

    private VipTagAdapter vipTagAdapter;
    private List<Tags> tagsList;
    private VipTagAdapters vipTagAdapters;
    private List<Tags> tagsLists;

    private int pageIndex=1;
    private String VipId;
    private String ImageUrl;
    private String Sex;
    private String FaceFrame;
    private String CreateTime;

    private GetLstByVipTagNet getLstByVipTagNet;
    private VipOrderListNet vipOrderListNet;
    private VipGetOneNet getOneNet;
    private VipOrderStatisticNet vipOrderStatisticNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_portait);
        initView();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            VipId=bundle.getString("VipId");
            ImageUrl=bundle.getString("ImageUrl").replace("https://","http://");
            FaceFrame=bundle.getString("FaceFrame").replace("https://","http://");
            Sex=bundle.getString("Sex");
            CreateTime=bundle.getString("CreateTime");
        }

        initData();
    }

    public void onEventMainThread(ResultVipOrderStatisticBean msg){
        if(msg.IsSuccess!=false){
            tv_Quantity.setText(msg.TModel.getQuantity()+"");
            tv_RetailTotal.setText(msg.TModel.getRetailTotal()+"");
            tv_Payment.setText(msg.TModel.getPayment()+"");
        }else{
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }

    public void onEventMainThread(ResultVipGetOneBean msg){
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null){
                tv_vip_name.setText(msg.TModel.getNickName()+"("+Sex+"士)");
                tv_card_name.setText(msg.TModel.getCardName());
                tv_consume_amount.setText("￥"+msg.TModel.getConsumeAmount());
                if(msg.TModel.getCounselorName()!=null && !"".equals(msg.TModel.getCounselorName())){
                    tv_counselor_name.setText(msg.TModel.getCounselorName());
                }else{
                    tv_counselor_name.setText("暂无顾问");
                }
                if(msg.TModel.getShopName()!=null && !"".equals(msg.TModel.getShopName())){
                    tv_shop_name.setText(msg.TModel.getShopName());
                }else{
                    tv_shop_name.setText("暂无店铺");
                }
                if(msg.TModel.getBirthDay()!=null){
                    tv_birth_day.setText(AbDateUtil.toDateMDs(msg.TModel.getBirthDay()));
                }else{
                    tv_birth_day.setText("暂无生日");
                }
                if(msg.TModel.getLastBuy()!=null){
                    tv_last_buy.setText(AbDateUtil.format(msg.TModel.getLastBuy()));
                }else{
                    tv_last_buy.setText("暂无消费");
                }

                tagsList=new ArrayList<>();
                tagsLists=new ArrayList<>();
                for (int i=0;i<msg.TModel.getTags().size();i++){
                    if(msg.TModel.getTags().get(i).getTagType()==0){//手动标签
                        Tags tags=new Tags();
                        tags.setTagId(msg.TModel.getTags().get(i).getTagId());
                        tags.setTagName(msg.TModel.getTags().get(i).getTagName());
                        tagsList.add(tags);
                    }else{//动态标签
                        Tags tag=new Tags();
                        tag.setTagId(msg.TModel.getTags().get(i).getTagId());
                        tag.setTagName(msg.TModel.getTags().get(i).getTagName());
                        tagsLists.add(tag);
                    }
                }

                vipTagAdapter=new VipTagAdapter(tagsList);
                rv_vip_tag.setAdapter(vipTagAdapter);

                vipTagAdapters=new VipTagAdapters(tagsLists);
                rv_vip_tags.setAdapter(vipTagAdapters);
            }
        }else{
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }

    public void onEventMainThread(final ResultVipOrderListBean msg){
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){

                mData=new ArrayList<>();
                for(int i=0;i<msg.TModel.size();i++){
                    for (int j=0;j<msg.TModel.get(i).getOrderItem().size();j++){
                        mData.add(new VipOrderListSection(true, "Section", false,msg.TModel.get(i).getCreateTime()));
                        mData.add(new VipOrderListSection(new SubOrder(msg.TModel.get(i).getOrderItem().get(j).getImageUrl(),msg.TModel.get(i).getOrderItem().get(j).getStyleName(),msg.TModel.get(i).getOrderItem().get(j).getBarcodeNo(),msg.TModel.get(i).getOrderItem().get(j).getPayMent(),msg.TModel.get(i).getOrderItem().get(j).getSkuValue1(),msg.TModel.get(i).getOrderItem().get(j).getSkuValue2(),msg.TModel.get(i).getOrderItem().get(j).getSkuValue3(),msg.TModel.get(i).getOrderItem().get(j).getBarcodeId())));
                    }
                }
                orderLstAdapter=new VipOrderLstAdapter(R.layout.item_vip_order_content, R.layout.item_vip_order_header, mData);
                orderLstAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        VipOrderListSection mySection = mData.get(position);
                        if (mySection.isHeader){

                        } else{
                            bundle.putString("barcodeId",mySection.t.getBarcodeId()+"");
                            Skip.mNextFroData(mActivity, StyleRecommendActivity.class,bundle);
                        }
                    }
                });

                rv_shopping_record_list.setAdapter(orderLstAdapter);
            }
        }else{
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }

    public void onEventMainThread(ResultGetLstByVipTagBean msg){
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                goodsInfoBeen=new ArrayList<>();
                goodsInfoBeen=msg.TModel;
                shopPingCartAdapter=new GetLstByVipTagAdapter(goodsInfoBeen);
                rv_shoppingcart_list.setAdapter(shopPingCartAdapter);
            }
        }else{
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }

    private void initData(){
        CalenderUtils.testtett();
        tv_create_time.setText(AbDateUtil.getDateYMD(CreateTime));
        if(ImageUrl!=null && !"".equals(ImageUrl)){
//            Glide.with(mContext).load(ImageUrl).crossFade().into(iv_face_image);
            Glide
                .with(mContext)
                .load(ImageUrl)
                .placeholder(R.mipmap.load_ing) //设置占位图
                .error(R.mipmap.ic_launcher) //设置错误图片
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                //.dontAnimate() //不显示动画效果
                .into(iv_face_image);
        }else{
            iv_face_image.setImageResource(R.mipmap.ic_launcher);
        }

        if(FaceFrame!=null && !"".equals(FaceFrame)){
//            Glide.with(mContext).load(FaceFrame).crossFade().into(iv_vip_img);
                Glide
                    .with(mContext)
                    .load(FaceFrame)
                    .placeholder(R.mipmap.load_ing) //设置占位图
                    .error(R.mipmap.ic_launcher) //设置错误图片
                    .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                    //.dontAnimate() //不显示动画效果
                    .into(iv_vip_img);
        }else{
        iv_vip_img.setImageResource(R.mipmap.ic_launcher);
    }

        arraySort=new JSONArray();
        sortBean=new SortBean();
        sortBean.setField("CreateTime");
        sortBean.setValue("desc");
        arraySort.put(sortBean.getSortIdInfo());

        vipOrderStatisticNet=new VipOrderStatisticNet(mContext);
        vipOrderStatisticNet.setData(VipId);

        getOneNet=new VipGetOneNet(mContext,1);
        getOneNet.setData(VipId);

        getLstByVipTagNet=new GetLstByVipTagNet(mContext);
        getLstByVipTagNet.setData(pageIndex,VipId);

        vipOrderListNet=new VipOrderListNet(mContext);
        vipOrderListNet.setData(pageIndex,VipId,arraySort);
    }

    private void initView() {
        mTvTitle= (TextView) findViewById(R.id.tv_base_title);
        mTvTitle.setText("会员画像");
        mTvRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTvRightTitle.setText("会员详情");
        mTvRightTitle.setVisibility(View.GONE);
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);

        iv_face_image= (ImageView) findViewById(R.id.iv_face_image);
        iv_vip_img= (ImageView) findViewById(R.id.iv_vip_img);
        tv_card_name= (TextView) findViewById(R.id.tv_card_name);
        tv_vip_name= (TextView) findViewById(R.id.tv_vip_name);
        tv_birth_day= (TextView) findViewById(R.id.tv_birth_day);

        tv_counselor_name= (TextView) findViewById(R.id.tv_counselor_name);
        tv_last_buy= (TextView) findViewById(R.id.tv_last_buy);
        tv_create_time= (TextView) findViewById(R.id.tv_create_time);
        tv_consume_amount= (TextView) findViewById(R.id.tv_consume_amount);
        tv_shop_name= (TextView) findViewById(R.id.tv_shop_name);
        tv_Quantity= (TextView) findViewById(R.id.tv_Quantity);
        tv_RetailTotal= (TextView) findViewById(R.id.tv_RetailTotal);
        tv_Payment= (TextView) findViewById(R.id.tv_Payment);

        rv_shopping_record_list= (RecyclerView) findViewById(R.id.rv_shopping_record_list);
        rv_shopping_record_list.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        rv_shoppingcart_list= (RecyclerView) findViewById(R.id.rv_shoppingcart_list);
        rv_shoppingcart_list.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。

        rv_vip_tag= (RecyclerView) findViewById(R.id.rv_vip_tag);
        rv_vip_tag.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        rv_vip_tag.setLayoutManager(new GridLayoutManager(this, 4));

        rv_vip_tags= (RecyclerView) findViewById(R.id.rv_vip_tags);
        rv_vip_tags.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        rv_vip_tags.setLayoutManager(new GridLayoutManager(this, 4));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
        }
    }
}
