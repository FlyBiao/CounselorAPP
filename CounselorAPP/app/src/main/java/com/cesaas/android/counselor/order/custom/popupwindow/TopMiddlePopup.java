package com.cesaas.android.counselor.order.custom.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.flowlayout.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.flowlayout.OnTagClickListener;
import com.cesaas.android.counselor.order.custom.flowlayout.TagAdapter;
import com.cesaas.android.counselor.order.custom.flowlayout.TagSelectSalesTalkAdapter;
import com.cesaas.android.counselor.order.salestalk.bean.GetCategoryBean;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 自定义弹窗类
 * Created at 2017/5/4 16:31
 * Version 1.0
 */

public class TopMiddlePopup extends PopupWindow {

    private Context myContext;
    public List<GetCategoryBean> myItems;
    private int myWidth;
    private int myHeight;
    private int myType;

    // 判断是否需要添加或更新列表子类项
    private boolean myIsDirty = true;

    private LayoutInflater inflater = null;
    private View myMenuView;

    private LinearLayout popupLL;

    private TagSelectSalesTalkAdapter<String> mTagAdapter;
    private FlowTagLayout mColorFlowTagLayout;

    public TopMiddlePopup(Context context, int width, int height,List<GetCategoryBean> items, int type) {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myMenuView = inflater.inflate(R.layout.top_popup, null);

        this.myContext = context;
        this.myItems = items;
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

        myMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int height = popupLL.getBottom();
                int left = popupLL.getLeft();
                int right = popupLL.getRight();
                System.out.println("--popupLL.getBottom()--:"
                        + popupLL.getBottom());
                int y = (int) event.getY();
                int x = (int) event.getX();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height || x < left || x > right) {
                        System.out.println("---点击位置在列表下方--");
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 显示弹窗界面
     *
     * @param view
     */
    public void show(View view) {
        if (myIsDirty) {
            myIsDirty = false;
            //颜色
            mTagAdapter = new TagSelectSalesTalkAdapter<>(myContext);
            mColorFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
            mColorFlowTagLayout.setAdapter(mTagAdapter);

            mColorFlowTagLayout.setOnTagClickListener(new OnTagClickListener() {
                @Override
                public void onItemClick(FlowTagLayout parent, View view, int position) {

                    GetCategoryBean bean=new GetCategoryBean();
                    bean.Content=myItems.get(position).Content;
                    bean.Id=myItems.get(position).Id;
                    EventBus.getDefault().post(bean);
                    dismiss();
                }
            });
            mTagAdapter.onlyAddAll(myItems);
        }
        showAsDropDown(view, 0, 0);
    }
}
