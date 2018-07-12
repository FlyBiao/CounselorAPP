package com.cesaas.android.counselor.order.fans.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesaas.android.counselor.order.R;

public class TestFragment4 extends Fragment {
	
	public static TestFragment4 instance=null;
	public static TestFragment4 getInstance(){
		if(instance==null){
			instance=new TestFragment4();
		}
		return instance;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lay4, container, false);
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
