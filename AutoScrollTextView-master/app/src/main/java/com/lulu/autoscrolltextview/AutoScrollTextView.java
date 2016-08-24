package com.lulu.autoscrolltextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 自滚动TextView
 * Created by LuLu on 2016/5/16.
 */
public class AutoScrollTextView extends TextView {

    /*
    坐标计算方法
    y值是固定的 居住显示 算出是一个固定的值
    x值的变动 使得文字从头滚动到尾
    最开始 文字起点在屏幕最右边不可见 所以初试值为屏幕宽度 比如1080
    滚动后 x值慢慢变少 当变为0 在文字长度大于屏幕宽度的情况下 比如文字长度为4304 一个屏幕宽度的文字移出到了屏幕右侧
    最后 在x值为负值的情况下 文字完全移出在屏幕左侧 具体的值为 -4304  其实就是文字长度的负值
    于是循环开始 文字又在初试位置 即屏幕右侧 具体值为1080
     */

    public boolean isStarting = false; //是否开始滚动
    private float textLength = 0f; //文本长度
    private float screenWidth = 0f; //屏幕宽度
    private float x = 0f; //文字横坐标
    private float y = 0f; //文字纵坐标
    private String text = ""; //文本内容
    private Paint paint = null; //绘图样式

    private Context context;
    private int color; //字体颜色
    private float textSize; //字体大小 像素为单位
    private float speed = 5; //文字滚动速度 实际是一个偏移像素值 越大速度越快

    public AutoScrollTextView(Context context) {
        super(context);
        this.context = context;
    }

    public AutoScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public AutoScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }


    public void init() {
        paint = getPaint();
        text = getText().toString();
        paint.setColor(color);
        paint.setTextSize(textSize);

        textLength = paint.measureText(text); //4310 文字长度 相当于四个屏幕宽度
        screenWidth = getScreenWidth(context); //1080 屏幕宽度

        x = screenWidth;
    }

    public void startScroll() {
        isStarting = true;
        invalidate();
    }

    public void stopScroll() {
        isStarting = false;
        invalidate();
    }

    public void setScrollTextColor(int color) {
        this.color = color;
    }

    public void setScrollTextSize(int spValue) {
        this.textSize = sp2px(context, spValue);
    }

    public void setScrollSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void onDraw(Canvas canvas) {

        //在这里计算y坐标
        paint.setTextAlign(Paint.Align.LEFT);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int heigit = getMeasuredHeight();
        y = (heigit - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top; //173

        Log.i("TAG", "x-----------------x=" + x);
        //Log.i("TAG", "y-----------------" + y);

        canvas.drawText(text, x, y, paint);
        if (!isStarting) {
            return;
        }

        if (x < -textLength){
            x = screenWidth;
        }else {
            x = x - speed;
        }

        invalidate();
    }


    private float sp2px(Context context, int spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (spValue * fontScale + 0.5f);
    }

    private int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

}