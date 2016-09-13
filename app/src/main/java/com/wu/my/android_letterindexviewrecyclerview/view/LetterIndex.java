package com.wu.my.android_letterindexviewrecyclerview.view;

/***
 * 自定义view实现右侧索引
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class LetterIndex extends View {
    private Paint mPaint = null;
    private String[] arrLetters = new String[]{"#", "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z"};
    private int lineHeight = 0;
    private TextView alert_textView;
    private OnLetterClickedListener listener;
    private int changePainColor = -1;

    public LetterIndex(Context context) {
        super(context);
    }

    public LetterIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float density = getResources().getDisplayMetrics().density;
        mPaint.setTextSize(14 * density);
        int viewHeight = getHeight();
        int viewWidth = getWidth();

        //每一行的高度
        lineHeight = viewHeight / arrLetters.length;

        for (int i = 0; i < arrLetters.length; i++) {
            mPaint.setColor(Color.GRAY);
            //获取每个字体的宽度
            int textWidth = (int) mPaint.measureText(arrLetters[i]);
            //在画布上绘制对应的字体
            canvas.drawText(arrLetters[i], (viewWidth - textWidth) / 2, (i + 1) * lineHeight,
                    mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        //获取滑动的位置
        int position = (int) (y / lineHeight);
        if (position >= 0 && position < arrLetters.length) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    setBackgroundColor(Color.TRANSPARENT);
                    if (alert_textView != null && alert_textView.getVisibility() == View.VISIBLE) {
                        alert_textView.setVisibility(GONE);
                    }
                    invalidate();
                    break;
                default:
                    setBackgroundColor(Color.parseColor("#cccccc"));
                    if (alert_textView != null) {
                        alert_textView.setVisibility(View.VISIBLE);
                        alert_textView.setText(arrLetters[position]);
                    }
                    //为监听器中的抽象方法的参数进行赋值
                    if (listener != null) {
                        listener.onLetterClicked(arrLetters[position]);
                    }
                    //擦除当前View,重新进行绘制
                    invalidate();
                    break;
            }
        }
        return true;
    }

    //定义抽象方法，
    public interface OnLetterClickedListener {
        void onLetterClicked(String letter);
    }

    //自定义方法。用于调用者为指定参数进行赋值
    public void setOnLetterClickedListener(TextView alert_textView, OnLetterClickedListener
            listener) {
        this.listener = listener;
        this.alert_textView = alert_textView;
    }
}
