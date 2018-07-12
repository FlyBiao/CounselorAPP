package com.cesaas.android.counselor.order.apptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cesaas.android.counselor.order.R;

import java.io.Serializable;

public class TestActivity extends AppCompatActivity {

    private int resultCode=901;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button ben= (Button) findViewById(R.id.button_test);
        ben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.putExtra("selectList","testValue");
                setResult(resultCode, mIntent);// 设置结果，并进行传送
                finish();
            }
        });
    }
}
