package com.cesaas.android.counselor.order.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetCommsion.GetCommsionBean;
import com.cesaas.android.counselor.order.earnings.activity.DevelopCommisonActivity;
import com.cesaas.android.counselor.order.earnings.activity.SendCommisionActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 订单发货佣金总收入数据适配器
 * 
 * @author fgb
 * 
 */
public class GetSendCommsionSumAdapter extends BaseAdapter implements
		OnClickListener {

	public static final String TAG = "GetCommsionSumAdapter";

	private Context ct;
	private Activity activity;
	private List<GetCommsionBean> data = new ArrayList<GetCommsionBean>();

	DecimalFormat df = new DecimalFormat("zero.00");

	public String SendOrderCommisionSum;
	public double SendOrderCommisionTotals = 0;// 发货订单总收入
	public double SendOrderCommisionSettle = 0;// 可以结算收入
	public double SendOrderCommisionNoSettle = 0;// 不能结算收入

	private double nulls;
	private String commisionNoSettle;// 不能结算
	private String commisionTotals;// 冻结

	public GetSendCommsionSumAdapter(Context ct, Activity activity,
			List<GetCommsionBean> data) {
		this.ct = ct;
		this.activity = activity;
		this.data = data;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<GetCommsionBean> list) {
		this.data = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(ct).inflate(
					R.layout.activity_sendorder_commision_layout, null);

			viewHolder.tv_sendorder_commision_totals = (TextView) convertView
					.findViewById(R.id.tv_sendorder_commision_totals);
			viewHolder.tv_sendorder_commision_settle = (TextView) convertView
					.findViewById(R.id.tv_sendorder_commision_settle);
			viewHolder.tv_sendorder_commision_noSettle = (TextView) convertView
					.findViewById(R.id.tv_sendorder_commision_noSettle);

			viewHolder.iv_commision_detail = (ImageView) convertView
					.findViewById(R.id.iv_commision_detail);
			// viewHolder.rl_settle_commision=(RelativeLayout)
			// convertView.findViewById(R.id.rl_settle_commision);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 设置收益监听
		// viewHolder.rl_settle_commision.setOnClickListener(this);
		viewHolder.iv_commision_detail.setOnClickListener(this);

		GetCommsionBean commsionBean = data.get(position);

		// 发货订单佣金
		if (commsionBean.BonusStatus == 0) {// 暂时冻结
			// 拿到总数
			SendOrderCommisionTotals = data.get(position).OrderBonus;
			commisionTotals = df.format(SendOrderCommisionTotals);
			viewHolder.tv_sendorder_commision_settle.setText(commisionTotals);

		} else if (commsionBean.BonusStatus == 3) {// 不能结算
			SendOrderCommisionNoSettle = data.get(position).OrderBonus;
			commisionNoSettle = df.format(SendOrderCommisionNoSettle);
			viewHolder.tv_sendorder_commision_noSettle
					.setText(commisionNoSettle);
		} else {
			viewHolder.tv_sendorder_commision_settle.setText("zero.zero");
		}

		// 总收入
		viewHolder.tv_sendorder_commision_totals.setText(commisionTotals);
		return convertView;
	}

	static class ViewHolder {

		TextView tv_sendorder_commision_totals;
		TextView tv_sendorder_commision_settle;
		TextView tv_sendorder_commision_noSettle;

		// RelativeLayout rl_settle_commision;
		ImageView iv_commision_detail;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_commision_detail:// 发货佣金
			Skip.mNext(activity, SendCommisionActivity.class);
			break;

		default:
			break;
		}
	}

}
