package com.cesaas.android.counselor.order.rong.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.cesaas.android.counselor.order.BasesActivity;

/**
 * 单人推送消息
 * 只有⼀个联系人发来一条或者多条消息时
 * @author FGB
 *
 */
public class SinglePushMessage extends BasesActivity{

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		
//		Intent intent = new Intent();
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		Uri.Builder builder = Uri.parse("rong://" + this.getPackageName()).buildUpon();
//
//		builder.appendPath("conversation").appendPath(type.getName())
//		        .appendQueryParameter("targetId", targetId)
//		        .appendQueryParameter("title", targetName);
//		uri = builder.build();
//		intent.setData(uri);
//		startActivity(intent);
//	}
}
