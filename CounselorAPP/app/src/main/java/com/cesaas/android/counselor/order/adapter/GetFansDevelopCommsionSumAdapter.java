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
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetCommsion.GetCommsionBean;
import com.cesaas.android.counselor.order.earnings.activity.DevelopCommisonActivity;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 粉丝推荐订单佣金总收入数据适配器
 * 
 * @author fgb
 * 
 */
public class GetFansDevelopCommsionSumAdapter extends BaseAdapter implements
		OnClickListener {

	public static final String TAG = "GetCommsionSumAdapter";

	private Context ct;
	private Activity activity;
	private List<GetCommsionBean> data = new ArrayList<GetCommsionBean>();

	DecimalFormat df = new DecimalFormat("zero.00");

	public double fansDevelopCommisionSum = 0;
	public double fansDevelopCommisionTotals = 0;// 发货订单总收入
	public double fansDevelopCommisionSettle = 0;// 可以结算收入
	public double fansDevelopCommisionNoSettle = 0;// 不能结算收入

	private String fansDevelopNoSettle;// 不能结算
	private String fansDevelopTotals;// 冻结

	public GetFansDevelopCommsionSumAdapter(Context ct, Activity activity,
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
					R.layout.activity_fans_develop_commision_layout, null);

			viewHolder.tv_fans_develop_total = (TextView) convertView
					.findViewById(R.id.tv_fans_develop_total);
			viewHolder.tv_fans_develop_settle = (TextView) convertView
					.findViewById(R.id.tv_fans_develop_settle);
			viewHolder.tv_fans_develop_noSettle = (TextView) convertView
					.findViewById(R.id.tv_fans_develop_noSettle);

			viewHolder.rl_deposit_commision = (RelativeLayout) convertView
					.findViewById(R.id.rl_deposit_commision);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		

		// 设置收益监听
		viewHolder.rl_deposit_commision.setOnClickListener(this);

		GetCommsionBean commsionBean = data.get(position);

		// 发货订单佣金
		if (commsionBean.BonusStatus == 0) {// 暂时冻结
			fansDevelopCommisionTotals = data.get(position).OrderBonus;
			fansDevelopTotals = df.format(fansDevelopCommisionTotals);
			viewHolder.tv_fans_develop_settle.setText(fansDevelopTotals);
		} else if (commsionBean.BonusStatus == 3) {// 不能结算
			fansDevelopCommisionNoSettle = data.get(position).OrderBonus;
			viewHolder.tv_fans_develop_noSettle.setText(""
					+ fansDevelopCommisionNoSettle);
		} else {
			viewHolder.tv_fans_develop_settle.setText("zero.zero");
		}

		// 总收入
		// fansDevelopCommisionSum=fansDevelopCommisionTotals+fansDevelopCommisionSettle+fansDevelopCommisionNoSettle;
		viewHolder.tv_fans_develop_total.setText(fansDevelopTotals);
		return convertView;
	}

	static class ViewHolder {

		TextView tv_fans_develop_total;
		TextView tv_fans_develop_settle;
		TextView tv_fans_develop_noSettle;

		RelativeLayout rl_deposit_commision;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.rl_deposit_commision:// 推荐佣金
			Skip.mNext(activity, DevelopCommisonActivity.class);
			break;

		default:
			break;
		}
	}

}
