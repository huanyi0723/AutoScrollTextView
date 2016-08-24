package com.lulu.autoscrolltextview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends Activity {

    private AutoScrollTextView auto_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auto_tv = (AutoScrollTextView) findViewById(R.id.auto_textview);

        auto_tv.setText("特定的时间内"); //设置文字内容
        auto_tv.setScrollTextColor(Color.parseColor("#AB82FF")); //设置文字颜色
        auto_tv.setScrollTextSize(12); //设置字体大小 以sp为单位
        auto_tv.setScrollSpeed(4); //设置文字滚动速度
        auto_tv.init();


        auto_tv.startScroll();
    }
}
