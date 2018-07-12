package com.cesaas.android.counselor.order.rong.message;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.message.TextMessage;

/**
 * 自定义会话ui页面
 * @author fgb
 *
 */
@ProviderTag ( messageContent = TextMessage.class , showPortrait = false , centerInHorizontal = true )
public class MyTextMessageItemProvider extends TextMessageItemProvider{
	
}
