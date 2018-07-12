package com.cesaas.android.counselor.order.global;

import java.util.ArrayList;

import com.cesaas.android.counselor.order.bean.Fans;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import android.content.Context;

/**
 * 融云用户信息提供者
 * @author FGB
 *
 */
public class UserInfoProvider implements RongIM.UserInfoProvider{

	private Context mContext;
	private static UserInfoProvider mInstance;
	
	private ArrayList<Fans> fansLis= new ArrayList<Fans>();
	
	public UserInfoProvider(Context mContext){
		this.mContext=mContext;
	}
	
	 /**
     * 初始化 RongCloud.
     *
     * @param context 上下文。
     */
    public static void init(Context context) {

        if (mInstance == null) {

            synchronized (UserInfoProvider.class) {

                if (mInstance == null) {
                	mInstance = new UserInfoProvider(context);
                }
            }
        }

    }
	
	 /**
     * 获取RongCloud 实例。
     *
     * @return RongCloud。
     */
    public static UserInfoProvider getInstance() {
        return mInstance;
    }
	
	@Override
	public UserInfo getUserInfo(String arg0) {
		return null;
	}

}
