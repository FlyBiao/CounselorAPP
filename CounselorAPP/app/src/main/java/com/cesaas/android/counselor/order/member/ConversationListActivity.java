package com.cesaas.android.counselor.order.member;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;

/**
 * 会话列表
 */
public class ConversationListActivity extends BasesActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
    }
}
