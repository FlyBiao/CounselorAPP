package com.cesaas.android.counselor.order.rong.activity;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;


/**
 * 粉丝会话列表页面
 * 
 * @author FGB
 * 
 */
@SuppressLint("NewApi")
public class FansConversationListFragment extends BaseFragment{
	
    private View view;
    
    public static FansConversationListFragment instance=null;
    public static FansConversationListFragment getInstance(){
    	if(instance==null){
    		instance=new FansConversationListFragment();
    	}
    	
    	return instance;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		view=inflater.inflate(R.layout.fans_conversationlist,container, false);
		initConversationList();
		return view;
	}
	
	/**
     * 加载 会话列表 ConversationListFragment
     */
    private void initConversationList() {

    	ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();
    }

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
}
