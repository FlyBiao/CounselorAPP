package com.cesaas.android.counselor.order.member.adapter.service.returns;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.jianglei.view.AutoLocateHorizontalView;

import java.util.List;

/**
 * Created by jianglei on 2/4/17.
 */

public class ResultsMonthAdapter extends RecyclerView.Adapter<ResultsMonthAdapter.AgeViewHolder> implements AutoLocateHorizontalView.IAutoLocateHorizontalView {
    private Context context;
    private View view;
    private List<String> ages;
    public ResultsMonthAdapter(Context context, List<String>ages){
        this.context = context;
        this.ages = ages;
    }

    @Override
    public AgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_results,parent,false);
        return new AgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AgeViewHolder holder, int position) {
        holder.tvAge.setText(ages.get(position));
    }

    @Override
    public int getItemCount() {
        return  ages.size();
    }

    @Override
    public View getItemView() {
        return view;
    }

    @Override
    public void onViewSelected(boolean isSelected,int pos, RecyclerView.ViewHolder holder,int itemWidth) {
        if(isSelected) {
            ((AgeViewHolder) holder).ll_results_bg.setBackgroundResource(R.mipmap.results_month_b);
            ((AgeViewHolder) holder).tvAge.setTextSize(14);
            ((AgeViewHolder) holder).tvAge.setTextColor(context.getResources().getColor(R.color.white));
            ((AgeViewHolder) holder).tv_select.setVisibility(View.VISIBLE);
        }else{
            ((AgeViewHolder) holder).tvAge.setTextSize(14);
            ((AgeViewHolder) holder).tvAge.setTextColor(context.getResources().getColor(R.color.new_base_bg));
            ((AgeViewHolder) holder).ll_results_bg.setBackgroundResource(R.mipmap.results_month_w);
            ((AgeViewHolder) holder).tv_select.setVisibility(View.GONE);
        }
    }

    static class AgeViewHolder extends RecyclerView.ViewHolder{
        TextView tvAge,tv_select;
        LinearLayout ll_results_bg;
        AgeViewHolder(View itemView) {
            super(itemView);
            tvAge = (TextView)itemView.findViewById(R.id.tv_age);
            tv_select = (TextView)itemView.findViewById(R.id.tv_select);
            tv_select.setText(R.string.fa_circle);
            tv_select.setTypeface(App.font);
            ll_results_bg= (LinearLayout) itemView.findViewById(R.id.ll_results_bg);
        }
    }
}
