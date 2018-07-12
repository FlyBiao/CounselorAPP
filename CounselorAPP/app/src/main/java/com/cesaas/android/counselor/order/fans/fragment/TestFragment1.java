package com.cesaas.android.counselor.order.fans.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;

public class TestFragment1 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lay1, container, false);
        TextView viewhello = (TextView) view.findViewById(R.id.tv_hello);
        viewhello.setText("biao1");
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
