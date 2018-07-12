package com.cesaas.android.counselor.order.custom.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.flowlayout.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.flowlayout.OnTagClickListener;
import com.cesaas.android.counselor.order.custom.flowlayout.OnTagSelectListener;
import com.cesaas.android.counselor.order.custom.flowlayout.TagAdapter;
import com.cesaas.android.counselor.order.custom.flowlayout.TagSelectShopAdapter;
import com.cesaas.android.counselor.order.shopmange.bean.ResultBigSortAllBean;
import com.cesaas.android.counselor.order.shopmange.bean.SortAllBean;
import com.cesaas.android.counselor.order.shopmange.net.GetAllShopNet;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 自定义弹窗类
 * Created at 2017/5/4 16:31
 * Version 1.0
 */

public class SelectShopTopMiddlePopup extends PopupWindow {

    private Context myContext;
    private List<String> bigItems;
    private List<String> smallItems;
    private List<String> yearItems;
    private List<String> seasonItems;
    private int myWidth;
    private int myHeight;
    private int myType;
    private Button btn_sure_sel;

    // 判断是否需要添加或更新列表子类项
    private boolean myIsDirty = true;

    private LayoutInflater inflater = null;
    private View myMenuView;

    private LinearLayout popupLL;

    private TagSelectShopAdapter<String> mSizeTagAdapter;
    private TagSelectShopAdapter<String> mMobileTagAdapter;
    private TagSelectShopAdapter<String> mTagAdapter;
    private TagSelectShopAdapter<String> mMinTagAdapter;
    private FlowTagLayout mColorFlowTagLayout;
    private FlowTagLayout mSizeFlowTagLayout;
    private FlowTagLayout mMobileFlowTagLayout;
    private FlowTagLayout mMinFlowTagLayout;

    private String year="";
    private String season="";
    private String smallSortId="";
    private String bigSortId="";

    public SelectShopTopMiddlePopup(Context context, int width, int height, List<String> items, List<String> yearItems,List<String> seasonItems,List<String> smallItems,int type) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myMenuView = inflater.inflate(R.layout.top_popup_sel_shop, null);

        this.myContext = context;
        this.bigItems = items;
        this.yearItems=yearItems;
        this.seasonItems=seasonItems;
        this.smallItems=smallItems;
        this.myType = type;

        this.myWidth = width;
        this.myHeight = height;

        initWidget();
        setPopup();
    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        popupLL = (LinearLayout) myMenuView.findViewById(R.id.popup_layout);
        mColorFlowTagLayout= (FlowTagLayout) myMenuView.findViewById(R.id.color_flow_layout);
        mSizeFlowTagLayout = (FlowTagLayout) myMenuView.findViewById(R.id.size_flow_layout);
        mMobileFlowTagLayout = (FlowTagLayout)myMenuView. findViewById(R.id.mobile_flow_layout);
        mMinFlowTagLayout= (FlowTagLayout) myMenuView.findViewById(R.id.type_flow_layout);
//
        btn_sure_sel= (Button) myMenuView.findViewById(R.id.btn_sure_sel);
        btn_sure_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectShopTopMiddlePopup.this.dismiss();
                //执行条件查询操作
                GetAllShopNet mGetAllShopNet=new GetAllShopNet(myContext);
                mGetAllShopNet.setData(1,0,year,season,smallSortId,bigSortId);
            }
        });
    }

    /**
     * 设置popup的样式
     */
    private void setPopup() {
        // 设置AccessoryPopup的view
        this.setContentView(myMenuView);
        // 设置AccessoryPopup弹出窗体的宽度
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体的高度
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体可点击
        this.setFocusable(true);
        // 设置AccessoryPopup弹出窗体的动画效果
        if (myType == 1) {
            this.setAnimationStyle(R.style.AnimTopLeft);
        } else if (myType == 2) {
            this.setAnimationStyle(R.style.AnimTopRight);
        } else {
            //this.setAnimationStyle(R.style.AnimTop);
            this.setAnimationStyle(R.style.AnimTopMiddle);
        }
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x33000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);


    }

    /**
     * 显示弹窗界面
     *
     * @param view
     */
    public void show(View view) {
        if (myIsDirty) {
            myIsDirty = false;
            //年份
            mTagAdapter = new TagSelectShopAdapter<>(myContext);
            mColorFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
            mColorFlowTagLayout.setAdapter(mTagAdapter);
            mColorFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
                @Override
                public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                    if (selectedList != null && selectedList.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int i : selectedList) {
                            sb.append(parent.getAdapter().getItem(i));
                        }
                        year=sb.toString();
                    }else{
                        ToastFactory.getLongToast(myContext,"没有选择标签");
                    }
                }
            });

            //季节
            mSizeTagAdapter = new TagSelectShopAdapter<>(myContext);
            mSizeFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
            mSizeFlowTagLayout.setAdapter(mSizeTagAdapter);
            mSizeFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
                @Override
                public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                    if (selectedList != null && selectedList.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int i : selectedList) {
                            sb.append(parent.getAdapter().getItem(i));
                        }
                        season=sb.toString();
                    }else{
                        ToastFactory.getLongToast(myContext,"没有选择标签");
                    }
                }
            });

            //大类
            mMobileTagAdapter = new TagSelectShopAdapter<>(myContext);
            mMobileFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
            mMobileFlowTagLayout.setAdapter(mMobileTagAdapter);
            mMobileFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
                @Override
                public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                    if (selectedList != null && selectedList.size() > 0) {
                        StringBuilder sb = new StringBuilder();

                        for (int i : selectedList) {
                            sb.append(parent.getAdapter().getItem(i));
                        }
                        bigSortId=sb.toString();
                    }else{
                        ToastFactory.getLongToast(myContext,"没有选择标签");
                    }
                }
            });

            //小类
            mMinTagAdapter = new TagSelectShopAdapter<>(myContext);
            mMinFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
            mMinFlowTagLayout.setAdapter(mMinTagAdapter);
            mMinFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
                @Override
                public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                    if (selectedList != null && selectedList.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int i : selectedList) {
                            sb.append(parent.getAdapter().getItem(i));
                        }
                        smallSortId=sb.toString();
                    }else{
                        ToastFactory.getLongToast(myContext,"没有选择标签");
                    }
                }
            });

            mTagAdapter.onlyAddAll(yearItems);
            mSizeTagAdapter.onlyAddAll(seasonItems);
            mMobileTagAdapter.onlyAddAll(bigItems);
            mMinTagAdapter.onlyAddAll(smallItems);
        }

        showAsDropDown(view, 0, 0);
    }
}
