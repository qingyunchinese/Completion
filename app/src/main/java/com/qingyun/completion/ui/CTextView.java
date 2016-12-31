package com.qingyun.completion.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 作者： qingyun on 16/11/23.
 * 邮箱：419254872@qq.com
 * 版本：v1.0
 */
public class CTextView extends TextView {

    public OnViewSizeChangeListener lisener;

    public CTextView(Context context) {
        super(context);
    }

    public CTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lisener != null) {
            lisener.onMeasuerSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public void setLisener(OnViewSizeChangeListener lisener) {
        this.lisener = lisener;
    }

    public void setTextSize(float size){
        setLineSpacing(size*2,1);
    }

    public interface OnViewSizeChangeListener {
        public void onMeasuerSize(int width, int height);
    }
}
