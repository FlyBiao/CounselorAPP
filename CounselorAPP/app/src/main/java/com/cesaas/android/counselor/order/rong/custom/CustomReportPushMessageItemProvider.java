package com.cesaas.android.counselor.order.rong.custom;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.report.ReportDetailActivity;
import com.cesaas.android.counselor.order.rong.activity.WebViewShopActivity;
import com.cesaas.android.counselor.order.utils.Skip;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Author FGB
 * Description 自定义报表推送消息显示类
 * Created 2017/4/1 10:44
 * Version 1.zero
 */
@ProviderTag(messageContent = CustomReportPushMessage.class)
public class CustomReportPushMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomReportPushMessage> {
    private static final String TAG = "CustomReportPushMessageItemProvider";

    private Activity mActivity;
    public CustomReportPushMessageItemProvider(Activity mActivity){
        this.mActivity=mActivity;
    }

    /**
     * 初始化View
     * @param context
     * @param viewGroup
     * @return
     */
    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customize_report_message, null);
        ViewHolder holder = new ViewHolder();
        holder.tv_customize_title= (TextView) view.findViewById(R.id.tv_customize_title);
        holder.tv_customize_id= (TextView) view.findViewById(R.id.tv_customize_id);
        view.setTag(holder);

        return view;
    }

    /**
     * 将数据填充 View 上。
     */
    @Override
    public void bindView(View view, int i, CustomReportPushMessage customReportPushMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.tv_customize_title.setText(customReportPushMessage.ReportName);
        holder.tv_customize_id.setText(customReportPushMessage.ReportId);
    }

    /**
     * 该条消息为该会话的最后一条消息时，会话列表要显示的内容，通过该方法进行定义。
     */
    @Override
    public Spannable getContentSummary(CustomReportPushMessage customReportPushMessage) {
        String title=customReportPushMessage.ReportName;
        if(title!=null){
            return new SpannableString(customReportPushMessage.ReportName);
        }else{
            return null;
        }
    }

    /**
     * 点击该类型消息触发。
     */
    @Override
    public void onItemClick(View view, int i, CustomReportPushMessage customReportPushMessage, UIMessage uiMessage) {
        CustomReportPushMessage mReportPushMessage=(CustomReportPushMessage)uiMessage.getContent();
        Bundle bundle = new Bundle();
        bundle.putString("ReportId",mReportPushMessage.ReportId);
        //跳转到报表详情页面
        Skip.mNextFroData(mActivity, ReportDetailActivity.class,bundle);
    }

    /**
     * 长按该类型消息触发。
     */
    @Override
    public void onItemLongClick(View view, int i, CustomReportPushMessage customReportPushMessage, UIMessage uiMessage) {

    }

    class ViewHolder {
        TextView tv_customize_title;
        TextView tv_customize_id;
    }
}
