package com.cesaas.android.counselor.order.zoomimageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

/**
 * 预览大图片
 */
public class PreviewImageActivity extends BasesActivity {

    private ArrayList<String> picList;
    private Button ll_image_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);

        picList = new ArrayList<>();
        picList.add("http://img.ivsky.com/img/bizhi/pre/201601/27/february_2016-001.jpg");
        picList.add("http://img.ivsky.com/img/bizhi/pre/201601/27/february_2016-002.jpg");
        picList.add("http://img.ivsky.com/img/bizhi/pre/201601/27/february_2016-003.jpg");
        picList.add("http://img.ivsky.com/img/bizhi/pre/201601/27/february_2016-004.jpg");
        picList.add("http://img.ivsky.com/img/tupian/pre/201511/16/chongwugou.jpg");
        final String content ="I'm alone above the atmosphere，And no one looking up can find me here" +
                "Cuz I can close my eyes and disappear，When I climb the stairs to watch the sun" +
                "Above the station walls  the colors run";

        ll_image_content= (Button) findViewById(R.id.ll_image_content);
        //点击缩略图看大图
        ll_image_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initImageLoader();
                ImagPagerUtil imagPagerUtil = new ImagPagerUtil(PreviewImageActivity.this, picList);
                imagPagerUtil.setContentText(content);
                imagPagerUtil.show();
            }
        });
    }


    public void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
