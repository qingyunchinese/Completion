package com.qingyun.completion.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/**
 * 作者： qingyun on 16/12/4.
 * 邮箱：419254872@qq.com
 * 版本.0
 */
public class BlankSpan extends ReplacementSpan {

    private Paint paint;
    private int width;
    private float fontHeight = 0;
    private RectF rectF;
    private int editBoxIndex = 0;
    private  OnBlankSpanInitRectListener listener;

    public BlankSpan(OnBlankSpanInitRectListener listener, int editBoxIndex) {
        this.editBoxIndex = editBoxIndex;
        this.listener=listener;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        this.paint = paint;
        paint.setTextSize(paint.getTextSize());
        fontHeight = Math.round(Math.ceil(paint.getFontMetrics().bottom - paint.getFontMetrics().top));
        width = (int) paint.measureText("&")*7;
        return width;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        rectF= new RectF(x+width/10, top, x + width, bottom);
        if(listener!=null) {
            listener.onBlankRectF(editBoxIndex, rectF);
        }
    }


    public interface OnBlankSpanInitRectListener{
        public void onBlankRectF(int editBoxIndex, RectF rectF);
    }
}
