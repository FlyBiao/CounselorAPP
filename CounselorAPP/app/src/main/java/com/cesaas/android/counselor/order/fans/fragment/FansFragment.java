package com.cesaas.android.counselor.order.fans.fragment;

import java.util.ArrayList;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.rong.activity.FansConversationListFragment;

/**
 * 粉丝页面
 * @author fgb
 *
 */
public class FansFragment extends BaseFragment{

	private View view;
	
	private ViewPager mPager;
    private ArrayList<Fragment> fragmentsList;
    private ImageView ivBottomLine;

    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    private int position_two;
    private int position_three;
    private Resources resources;
    
    private TextView tvTabActivity, tvTabGroups, tvTabFriends, tvTabChat;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		view=inflater.inflate(R.layout.activity_fans_layout,container, false);
		InitTextView();
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		resources = getResources();
        InitWidth();
        
        InitViewPager();
	}
	
	private void InitTextView() {
        tvTabActivity = (TextView) view.findViewById(R.id.tv_tab_activity);
        tvTabGroups = (TextView) view.findViewById(R.id.tv_tab_groups);
        tvTabFriends = (TextView) view.findViewById(R.id.tv_tab_friends);
        tvTabChat = (TextView) view.findViewById(R.id.tv_tab_chat);
        mPager = (ViewPager) view.findViewById(R.id.vPager);
        ivBottomLine = (ImageView) view.findViewById(R.id.iv_bottom_line);

        tvTabActivity.setOnClickListener(new MyOnClickListener(0));
        tvTabGroups.setOnClickListener(new MyOnClickListener(1));
        tvTabFriends.setOnClickListener(new MyOnClickListener(2));
        tvTabChat.setOnClickListener(new MyOnClickListener(3));
    }
	
	private void InitViewPager() {
        
        fragmentsList = new ArrayList<Fragment>();
       
//        FansConversationListFragment fragment1=new FansConversationListFragment();
        FansServiceFragment fansServiceFragment=new FansServiceFragment();

        fragmentsList.add(FansConversationListFragment.getInstance());
        fragmentsList.add(fansServiceFragment);
        fragmentsList.add(TestFragment3.getInstance());
        fragmentsList.add(TestFragment4.getInstance());
        
        mPager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(), fragmentsList));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
	
	private void InitWidth() {
        
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (int) ((screenW / 4.0 - bottomLineWidth) / 2);
        Log.i("MainActivity", "offset=" + offset);

        position_one = (int) (screenW / 4.0);
        position_two = position_one * 2;
        position_three = position_one * 3;
    }
	
	/**
	 * 点击监听
	 * @author fgb
	 *
	 */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    };
    
    /**
     * 滑动监听
     * @author fgb
     *
     */
    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, 0, 0, 0);
                    tvTabGroups.setTextColor(resources.getColor(R.color.white));
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, 0, 0, 0);
                    tvTabFriends.setTextColor(resources.getColor(R.color.white));
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(position_three, 0, 0, 0);
                    tvTabChat.setTextColor(resources.getColor(R.color.white));
                }
                tvTabActivity.setTextColor(resources.getColor(R.color.common_yellow));
                break;
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, position_one, 0, 0);
                    tvTabActivity.setTextColor(resources.getColor(R.color.white));
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, position_one, 0, 0);
                    tvTabFriends.setTextColor(resources.getColor(R.color.white));
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(position_three, position_one, 0, 0);
                    tvTabChat.setTextColor(resources.getColor(R.color.white));
                }
                tvTabGroups.setTextColor(resources.getColor(R.color.common_yellow));
                break;
            case 2:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, position_two, 0, 0);
                    tvTabActivity.setTextColor(resources.getColor(R.color.white));
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, position_two, 0, 0);
                    tvTabGroups.setTextColor(resources.getColor(R.color.white));
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(position_three, position_two, 0, 0);
                    tvTabChat.setTextColor(resources.getColor(R.color.white));
                }
                tvTabFriends.setTextColor(resources.getColor(R.color.common_yellow));
                break;
            case 3:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, position_three, 0, 0);
                    tvTabActivity.setTextColor(resources.getColor(R.color.white));
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, position_three, 0, 0);
                    tvTabGroups.setTextColor(resources.getColor(R.color.white));
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, position_three, 0, 0);
                    tvTabFriends.setTextColor(resources.getColor(R.color.white));
                }
                tvTabChat.setTextColor(resources.getColor(R.color.common_yellow));
                break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
